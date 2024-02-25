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
	public static final ForgeConfigSpec.ConfigValue<Integer> DASH_HUNGER;
	
	static String[] desc1 = {"Sets the boost velocity. Default: 2.0D", "Boost Velocity"};
	static String[] desc2 = {"Sets maximum dash speed. Default: 10.0D", "Speed Limit"};
	static String[] desc3 = {"Sets the cooldown. Default: 60", "Cooldown"};
	static String[] desc4 = {"Can the dash be used in survival", "Allowed in survival"};
	static String[] desc5 = {"Can the dash be used in spectator", "Allowed in spectator"};
	static String[] desc6 = {"Can the dash be used on elytra", "Allowed while gliding"};
	static String[] desc7 = {"Does dashing in survival require the dash bauble", "Requires dash bauble"};
	static String[] desc8 = {"Sets hunger from each use of dash. Default: 1", "Dash hunger"};
	
	static {
		BUILDER.push("Config for KynexDash");
		
		BOOST_VELOCITY = BUILDER.comment(desc1[0]).define(desc1[1], 2.0D);
		FLY_SPEED_LIMIT = BUILDER.comment(desc2[0]).define(desc2[1], 10.0D);
		COOLDOWN = BUILDER.comment(desc3[0]).define(desc3[1], 60);
		ALLOWED_IN_SURVIVAL = BUILDER.comment(desc4[0]).define(desc4[1], true);
		ALLOWED_IN_SPECTATOR = BUILDER.comment(desc5[0]).define(desc5[1], false);
		ALLOWED_WHILE_GLIDING = BUILDER.comment(desc6[0]).define(desc6[1], false);
		SURVIVAL_REQUIRES_BAUBLE = BUILDER.comment(desc7[0]).define(desc7[1], true);
		DASH_HUNGER = BUILDER.comment(desc8[0]).define(desc8[1], 1);
		
		BUILDER.pop();
		SPEC = BUILDER.build();
	}
}