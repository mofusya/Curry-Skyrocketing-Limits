package net.mofusya.curry_skyrocketing_limits.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ItemHelpers {
    public static void duoItemFunction(ItemStack itemStack, ItemStack subItemStack, DuoItemPredicate func) {
        if (!func.run(itemStack, subItemStack)) {
            func.run(subItemStack, itemStack);
        }
    }

    public static List<ItemStack> itemRegistries2ItemStacks(List<RegistryObject<Item>> items) {
        return items.stream().map(RegistryObject::get).map(ItemStack::new).toList();
    }

    public static List<ItemStack> blockRegistries2ItemStacks(List<RegistryObject<Block>> block) {
        return block.stream().map(RegistryObject::get).map(ItemStack::new).toList();
    }

    @FunctionalInterface
    public interface DuoItemPredicate {
        boolean run(ItemStack itemStack, ItemStack subItemStack);
    }
}
