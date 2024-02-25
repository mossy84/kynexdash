package com.mossy.kynexdash;

import net.minecraftforge.common.ForgeConfigSpec;

public class OptionsHolder {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;
	
	public static final ForgeConfigSpec.ConfigValue<Double> BOOST_VELOCITY;
	public static final ForgeConfigSpec.ConfigValue<Double> FLY_SPEED_LIMIT;
	public static final ForgeConfigSpec.ConfigValue<Integer> COOLDOWN;
	public static final ForgeConfigSpec.ConfigValue<Boolean> ALLOWED_IN_SURVIVAL;
	public static final ForgeConfigSpec.ConfigValue<Boolean> ALLOWED_IN_SPECTATOR;
	public static final ForgeConfigSpec.ConfigValue<Boolean> ALLOWED_WHILE_GLIDING;
	public static final ForgeConfigSpec.ConfigValue<Boolean> SURVIVAL_REQUIRES_BAUBLE;
	
	static {
		BUILDER.push("Config for KynexDash");
		
		BOOST_VELOCITY = BUILDER.comment("Sets the boost velocity. Default: 2.0D").define("Boost Velocity", 2.0D);
		FLY_SPEED_LIMIT = BUILDER.comment("Sets maximum dash speed. Default: 10.0D").define("Speed Limit", 10.0D);
		COOLDOWN = BUILDER.comment("Sets the cooldown. Default: 60").define("Cooldown", 60);
		ALLOWED_IN_SURVIVAL = BUILDER.comment("Can the dash be used in survival").define("Allowed in survival", true);
		ALLOWED_IN_SPECTATOR = BUILDER.comment("Can the dash be used in spectator").define("Allowed in spectator", false);
		ALLOWED_WHILE_GLIDING = BUILDER.comment("Can the dash be used on elytra").define("Allowed while gliding", false);
		SURVIVAL_REQUIRES_BAUBLE = BUILDER.comment("Does dashing in survival require the dash bauble").define("Requires dash bauble", true);
		
		BUILDER.pop();
		SPEC = BUILDER.build();
	}
}