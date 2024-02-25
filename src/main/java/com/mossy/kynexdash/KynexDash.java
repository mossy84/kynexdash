package com.mossy.kynexdash;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod("kynexdash")
public class KynexDash
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "kynexdash";

    public KynexDash() {
    	registerItem(FMLJavaModLoadingContext.get().getModEventBus());
    	ModLoadingContext.get().registerConfig(Type.COMMON, OptionsHolder.SPEC, "kynexdash-config.toml");
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
    }
    
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    
    public static class BaubleDash extends Item {
		public BaubleDash(Properties properties) {
			super(properties);
			this.isFoil(getDefaultInstance());
		}
		
		public boolean isFoil(ItemStack stack) {
			return OptionsHolder.SURVIVAL_REQUIRES_BAUBLE.get();
		}		
    }
    
    public static final ItemGroup BAUBLE_TAB = new ItemGroup("baubleTab") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(BAUBLE_DASH.get());
		}
    };
    
    public static final RegistryObject<Item> BAUBLE_DASH = ITEMS.register(
    	"bauble_dash", () -> new BaubleDash(new Item.Properties()
    		.stacksTo(1)
    		.tab(BAUBLE_TAB)
    	)
    );
    
    public static void registerItem(IEventBus eventBus) {
    	ITEMS.register(eventBus);
    }
    
    public static class KeyBinds {
    	public static KeyBinding dash;
    	
    	public static void register(final FMLClientSetupEvent event) {
    		dash = create("dash", 0x52); // press R
    		
    		ClientRegistry.registerKeyBinding(dash);
    	}
    	
    	private static KeyBinding create(String name, int key) {
    		return new KeyBinding("key." + KynexDash.MODID + "." + name, key, "key.category." + KynexDash.MODID);
    	}
    }
    
    public static class Network {
    	public static final String NETWORK_VERSION = "1";
    	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
    		new ResourceLocation(KynexDash.MODID, "network"), 
    		() -> NETWORK_VERSION, 
    		version -> version.equals(NETWORK_VERSION), // client version
    		version -> version.equals(NETWORK_VERSION)  // server version
    	);
    	
    	public static void init() {
    		CHANNEL.registerMessage(0, HungerPacket.class, HungerPacket::encode, HungerPacket::decode, HungerPacket::handle);
    	}
    }
    
    public void commonSetup(final FMLCommonSetupEvent event) {
    	Network.init();
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.error("Hello your computer has virus");
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            LOGGER.info("No blocks registered");
        }
    }
}
