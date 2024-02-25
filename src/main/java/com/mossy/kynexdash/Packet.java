package com.mossy.kynexdash;

import java.util.function.Supplier;

import net.minecraft.entity.Pose;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class Packet {
	
	public static class HungerPacket {
		
		private static int penalty;
		
		public HungerPacket() {
			penalty = 1;
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
	
	public static class CrawlPacket {
		
		private static boolean forceCrawl;
		
		public CrawlPacket() {
			forceCrawl = true;
		}
		
		public CrawlPacket(boolean clientInput) {
			forceCrawl = clientInput;
		}
		
		@SuppressWarnings("static-access")
		public static void encode(CrawlPacket input, PacketBuffer buffer) {
			buffer.writeBoolean(input.getCrawlState());
		}
		
		public static CrawlPacket decode(PacketBuffer buffer) {
			return new CrawlPacket(buffer.readBoolean());
		}
		
		public static void handle(CrawlPacket input, Supplier<NetworkEvent.Context> networkContext) {
			NetworkEvent.Context context = networkContext.get();
			context.enqueueWork(
				() -> {
					ServerPlayerEntity player = context.getSender();
					if (forceCrawl) player.setPose(Pose.SWIMMING);
				}
			);
			
			context.setPacketHandled(true);
		}
		
		public static boolean getCrawlState() {
			return forceCrawl;
		}
	}
}
