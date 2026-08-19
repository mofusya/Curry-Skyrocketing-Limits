package net.mofusya.curry_skyrocketing_limits;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.mofusya.curry_skyrocketing_limits.config.CslJsonConfig;
import net.mofusya.curry_skyrocketing_limits.curryingredient.CurryIngredient;
import net.mofusya.curry_skyrocketing_limits.curryingredient.CurryIngredientManager;
import net.mofusya.curry_skyrocketing_limits.items.CslItems;
import net.mofusya.curry_skyrocketing_limits.util.ItemHelpers;
import org.slf4j.Logger;

@Mod(CurrySkyrocketingLimits.MOD_ID)
public class CurrySkyrocketingLimits {
    public static final String MOD_ID = "curry_skyrocketing_limits";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CurrySkyrocketingLimits() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CslItems.ITEMS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

        createCurryIngredients();
        CslJsonConfig.CUSTOM_INGREDIENTS.load();
        CurryIngredientManager.createFromJsons(CslJsonConfig.CUSTOM_INGREDIENTS.get().getAsJsonArray(C.CUSTOM_INGREDIENTS));
    }

    public static void createCurryIngredients() {
        CurryIngredientManager.create(new ResourceLocation("diamond"), new CurryIngredient(), CurryIngredientManager.Type.ITEMS);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            ItemHelpers.itemRegistries2ItemStacks(CslItems.ITEMS.getItems()).forEach(event::accept);
        }
    }
}
