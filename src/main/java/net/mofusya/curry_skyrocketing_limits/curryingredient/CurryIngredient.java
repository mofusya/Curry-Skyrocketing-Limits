package net.mofusya.curry_skyrocketing_limits.curryingredient;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class CurryIngredient {

    private int nutrition = 3;
    private float saturationModifier = 0.2f;

    public CurryIngredient() {
    }

    public CurryIngredient(int nutrition) {
        this.nutrition = nutrition;
    }

    public CurryIngredient(float saturationModifier) {
        this.saturationModifier = saturationModifier;
    }

    public CurryIngredient(@Nullable Integer nutrition, @Nullable Float saturationModifier) {
        if (nutrition != null) this.nutrition = nutrition;
        if (saturationModifier != null) this.saturationModifier = saturationModifier;
    }

    public int nutrition() {
        return this.nutrition;
    }

    public float saturationModifier() {
        return this.saturationModifier;
    }

    public void onEat(LivingEntity entity, ServerLevel server, ItemStack itemStack) {
    }
}
