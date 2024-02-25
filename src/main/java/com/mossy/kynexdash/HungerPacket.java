package com.mossy.kynexdash;

import java.util.function.Supplier;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class HungerPacket {
	
	private static int penalty;
	
	public HungerPacket() {
		
	}
	
	public HungerPacket(int configInput) {
		penalty = configInput;
	}
	
	@SuppressWarnings("static-access")
	public static void encode(HungerPacket input, PacketBuffer buffer) {
		buffer.writeInt(input.getPenalty());
	}
	
	public static HungerPacket decode(PacketBuffer buffer) {
		return new HungerPacket(buffer.readInt());
	}
	
	public static void handle(HungerPacket input, Supplier<NetworkEvent.Context> networkContext) {
		NetworkEvent.Context context = networkContext.get();
		context.enqueueWork(
			() -> {
				ServerPlayerEntity player = context.getSender();
				player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() - HungerPacket.getPenalty());
			}
		);
		
		context.setPacketHandled(true);
	}
	
	public static int getPenalty() {
		return penalty;
	}
}
