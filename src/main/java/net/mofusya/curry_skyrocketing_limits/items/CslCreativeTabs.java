package net.mofusya.curry_skyrocketing_limits.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.curry_skyrocketing_limits.C;
import net.mofusya.curry_skyrocketing_limits.util.ItemHelpers;

public class CslCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, C.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAIN = CREATIVE_TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("title.curry_skyrocketing_limits"))
            .icon(() -> new ItemStack(CslItems.CURRY_BASE.get()))
            .displayItems((parameters, output) -> {
                ItemHelpers.itemRegistries2ItemStacks(CslItems.ITEMS.getItems()).forEach(output::accept);
            })
            .build());
}
