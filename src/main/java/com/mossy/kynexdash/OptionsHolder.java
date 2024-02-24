package com.mossy.kynexdash;

import net.minecraftforge.common.ForgeConfigSpec;

public class OptionsHolder {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;
	
	public static final ForgeConfigSpec.ConfigValue<Double> BOOST_VELOCITY;
	public static final ForgeConfigSpec.ConfigValue<Integer> COOLDOWN;
	public static final ForgeConfigSpec.ConfigValue<Boolean> ALLOWED_IN_SURVIVAL;
	
	static {
		BUILDER.push("Config for KynexDash");
		
		BOOST_VELOCITY = BUILDER.comment("Sets the boost velocity. Default: 2.0D").define("Boost Velocity", 2.0D);
		COOLDOWN = BUILDER.comment("Sets the cooldown. Default: 60").define("Cooldown", 60);
		ALLOWED_IN_SURVIVAL = BUILDER.comment("Can the dash be used in survival").define("Allowed in survival", true);
		
		BUILDER.pop();
		SPEC = BUILDER.build();
	}
}
