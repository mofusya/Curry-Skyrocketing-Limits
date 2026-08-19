package net.mofusya.curry_skyrocketing_limits.accessor;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

import java.util.List;
import java.util.function.Supplier;

public interface FoodPropertiesAccessor {
    FoodPropertiesAccessor nutrition$currySL(int nutrition$currySL);

    FoodPropertiesAccessor saturationModifier$currySL(float saturationModifier$currySL);

    FoodPropertiesAccessor isMeat$currySL(boolean isMeat$currySL);

    FoodPropertiesAccessor canAlwaysEat$currySL(boolean canAlwaysEat$currySL);

    FoodPropertiesAccessor fastFood$currySL(boolean fastFood$currySL);

    FoodPropertiesAccessor effects$currySL(List<Pair<Supplier<MobEffectInstance>, Float>> effects$currySL);

    FoodProperties cast$currySL();

    default FoodPropertiesAccessor addNutrition(int add) {
        this.nutrition$currySL(this.cast$currySL().getNutrition() + add);
        return this;
    }

    default FoodPropertiesAccessor addSaturationModifier(float add) {
        this.saturationModifier$currySL(this.cast$currySL().getSaturationModifier() + add);
        return this;
    }
}
