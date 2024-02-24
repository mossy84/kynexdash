package com.mossy.kynexdash;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class KeyBinds {
	public static KeyBinding dash;
	
	public static void register(final FMLClientSetupEvent event) {
		dash = create("dash", 0x52); // press R
		
		ClientRegistry.registerKeyBinding(dash);
	}
	
	private static KeyBinding create(String name, int key) {
		return new KeyBinding("key." + KynexDash.MODID + "." + name, key, "key.category." + KynexDash.MODID);
	}
}
