package net.mofusya.curry_skyrocketing_limits.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.curry_skyrocketing_limits.C;
import net.mofusya.curry_skyrocketing_limits.items.item.CurryBaseItem;
import net.mofusya.curry_skyrocketing_limits.items.item.CurryItem;
import net.mofusya.ornatelib.registries.OrnateItemDeferredRegister;

public class CslItems {
    public static final OrnateItemDeferredRegister ITEMS = OrnateItemDeferredRegister.create(C.MOD_ID);

    public static final RegistryObject<Item> CURRY_BASE = ITEMS.register("curry_base", CurryBaseItem::new);
    public static final RegistryObject<Item> CURRy = ITEMS.register("curry", CurryItem::new, new Item.Properties().stacksTo(1).fireResistant());
}
