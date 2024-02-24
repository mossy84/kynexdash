package com.mossy.kynexdash;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = KynexDash.MODID, bus = Bus.FORGE, value = Dist.CLIENT)
public class InputEvents {
	public static final double BOOST_VELOCITY = OptionsHolder.BOOST_VELOCITY.get();
	public static final int COOLDOWN = OptionsHolder.COOLDOWN.get();
	private static int tick = 0;
	
	@SubscribeEvent
	public static void onKeyPress(InputEvent.KeyInputEvent input) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level == null) return;
		onInput(minecraft, input.getKey(), input.getAction());
	}
	
	@SubscribeEvent
	public static void onMousePress(InputEvent.MouseInputEvent input) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level == null) return;
		onInput(minecraft, input.getButton(), input.getAction());
	}
	
	private static void onInput(Minecraft minecraft, int key, int action) {
		ClientPlayerEntity player = minecraft.player;
		if (player.isCreative()) tick = 0;
		if (minecraft.screen == null && key == KeyBinds.dash.getKey().getValue() && tick == 0 && !player.isFallFlying() && !player.isSpectator()) {
			Vector3d movement = player.getDeltaMovement();
			Vector3d facing = player.getLookAngle().scale(BOOST_VELOCITY); // getLookAngle() returns normalized vector
			if (movement.length() < 10) player.setDeltaMovement(movement.add(facing));
			if (!player.isCreative()) tick = COOLDOWN;
		}
	}
	
	@SubscribeEvent
	public static void tick(ServerTickEvent gameTick) {
		if (tick > 0) tick--;
	}
}
