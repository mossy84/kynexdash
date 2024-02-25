package com.mossy.kynexdash;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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
