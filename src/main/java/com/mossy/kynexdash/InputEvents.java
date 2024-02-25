package com.mossy.kynexdash;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = KynexDash.MODID, bus = Bus.FORGE, value = Dist.CLIENT)
public class InputEvents {
	private static int cooldown = 0;
	private static int backDashPose = 0;
	
	@SubscribeEvent
	public static void onKeyPress(InputEvent.KeyInputEvent input) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level == null) return;
		onInput(minecraft, input.getKey());
	}
	
	@SubscribeEvent
	public static void onMousePress(InputEvent.MouseInputEvent input) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level == null) return;
		onInput(minecraft, input.getButton());
	}
	
	private static void onInput(Minecraft minecraft, int key) {
		PlayerEntity player = minecraft.player;
		// if dash not allowed in spectator and player spectator then cancel dash
		if (!OptionsHolder.ALLOWED_IN_SPECTATOR.get() && player.isSpectator()) return;
		// if dash requires bauble and player inventory does not contain bauble and player not creative then cancel dash
		if (OptionsHolder.SURVIVAL_REQUIRES_BAUBLE.get() && !player.inventory.contains(new ItemStack(KynexDash.BAUBLE_DASH.get())) && !player.isCreative()) return;
		// if dash not allowed in survival and player not creative then cancel dash
		if (!OptionsHolder.ALLOWED_IN_SURVIVAL.get() && !player.isCreative()) return;
		// if dash not allowed while gliding and player gliding then cancel dash
		if (!OptionsHolder.ALLOWED_WHILE_GLIDING.get() && player.isFallFlying()) return;
		// if player creative then set cooldown to 0
		if (player.isCreative()) cooldown = 0;
		// press dash key
		if (key == KynexDash.KeyBinds.dash.getKey().getValue()) {
			// if no gui open and keybind pressed and cooldown 0 then dash
			if (minecraft.screen == null && cooldown == 0) {
				// get player movement as vector
				Vector3d movement = player.getDeltaMovement();
				// get look angle as normalized vector and multiply by dash speed to get dash velocity vector
				Vector3d facing = player.getLookAngle().scale(OptionsHolder.BOOST_VELOCITY.get());
				// if player not above speed limit set player movement to movement + dash velocity
				if (movement.length() < OptionsHolder.FLY_SPEED_LIMIT.get()) player.setDeltaMovement(movement.add(facing));
				// set cooldown if player not creative
				if (!player.isCreative()) {
					cooldown = OptionsHolder.COOLDOWN.get();
					// send packet to server to deplete player hunger
					KynexDash.Network.CHANNEL.sendToServer(new Packet.HungerPacket(OptionsHolder.DASH_HUNGER.get()));
				}
			}
		}
		// press backDash key
		if (key == KynexDash.KeyBinds.backDash.getKey().getValue() && OptionsHolder.BACKDASH_ENABLED.get()) {
			// if no gui open and keybind pressed and cooldown 0 then dash
			if (minecraft.screen == null && cooldown == 0) {
				// get player movement as vector
				Vector3d movement = player.getDeltaMovement();
				// get look angle as normalized vector and multiply y value by 0.5 and multiply by dash speed to get dash velocity vector
				Vector3d facing = player.getLookAngle().multiply(new Vector3d(1, 0.5, 1)).scale(-0.5 * OptionsHolder.BOOST_VELOCITY.get());
				// if player not above speed limit set player movement to movement + dash velocity
				if (movement.length() < 0.2 * OptionsHolder.FLY_SPEED_LIMIT.get()) player.setDeltaMovement(movement.add(facing));
				// set cooldown if player not creative
				if (!player.isCreative()) {
					cooldown = OptionsHolder.COOLDOWN.get();
					// send packet to server to deplete player hunger
					KynexDash.Network.CHANNEL.sendToServer(new Packet.HungerPacket(OptionsHolder.DASH_HUNGER.get()));
					// set player pose for 10 game ticks
					backDashPose = (int) (OptionsHolder.BOOST_VELOCITY.get() * 8);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void tick(ServerTickEvent gameTick) {
		// decrement cooldown each game tick
		if (cooldown > 0) cooldown--;
		// instantiate player instance
		@SuppressWarnings("resource")
		PlayerEntity player = Minecraft.getInstance().player; 
		// if recently used backDash then set player pose crawling
		if (backDashPose > 0) {
			player.setPose(Pose.SWIMMING);
			KynexDash.Network.CHANNEL.sendToServer(new Packet.CrawlPacket(true));
			backDashPose--;
		}
	}
}
