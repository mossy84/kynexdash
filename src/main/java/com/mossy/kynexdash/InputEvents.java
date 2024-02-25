package com.mossy.kynexdash;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
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
	public static final double BOOST_VELOCITY = OptionsHolder.BOOST_VELOCITY.get();
	public static final int COOLDOWN = OptionsHolder.COOLDOWN.get();
	private static int cooldown = 0;
	
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
		ClientPlayerEntity player = minecraft.player;
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
		// if no gui open and keybind pressed and cooldown 0 then dash
		if (minecraft.screen == null && key == KeyBinds.dash.getKey().getValue() && cooldown == 0) {
			// get player movement as vector
			Vector3d movement = player.getDeltaMovement();
			// get look angle as normalized vector and multiply by dash speed to get dash velocity vector
			Vector3d facing = player.getLookAngle().scale(BOOST_VELOCITY);
			// if player not above speed limit set player movement to movement + dash velocity
			if (movement.length() < OptionsHolder.FLY_SPEED_LIMIT.get()) player.setDeltaMovement(movement.add(facing));
			// set cooldown if player not creative
			if (!player.isCreative()) cooldown = COOLDOWN;
		}
	}
	
	@SubscribeEvent
	public static void tick(ServerTickEvent gameTick) {
		// decrement cooldown each game tick
		if (cooldown > 0) cooldown--;
	}
}
