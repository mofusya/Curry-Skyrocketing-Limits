package net.mofusya.curry_skyrocketing_limits.items.item;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.mofusya.curry_skyrocketing_limits.items.CslItems;

public class CurryBaseItem extends Item {
    public CurryBaseItem(Properties build) {
        super(build);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack itemStack, ItemEntity entity) {
        Level level = entity.level();
        if (level.isClientSide) return super.onEntityItemUpdate(itemStack, entity);
        if (level.getFluidState(entity.blockPosition()).getType().getFluidType().isAir())
            return super.onEntityItemUpdate(itemStack, entity);

        var newEntity = new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(CslItems.CURRy.get()));
        newEntity.setPickUpDelay(10);
        level.addFreshEntity(newEntity);
        itemStack.shrink(1);

        return super.onEntityItemUpdate(itemStack, entity);
    }
}
