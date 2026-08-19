package net.mofusya.curry_skyrocketing_limits.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.mofusya.curry_skyrocketing_limits.accessor.FoodPropertiesAccessor;
import net.mofusya.ornatelib.util.annotation.FieldsMayBeNullByDefault;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mixin({FoodProperties.class})
@FieldsMayBeNullByDefault
public class FoodPropertiesMixin implements FoodPropertiesAccessor {

    @Unique
    private Integer nutrition$currySL;
    @Unique
    private Float saturationModifier$currySL;
    @Unique
    private Boolean isMeat$currySL;
    @Unique
    private Boolean canAlwaysEat$currySL;
    @Unique
    private Boolean fastFood$currySL;
    @Unique
    private List<Pair<Supplier<MobEffectInstance>, Float>> effects$currySL;

    @Override
    public FoodPropertiesAccessor nutrition$currySL(int nutrition$currySL) {
        this.nutrition$currySL = nutrition$currySL;
        return this;
    }

    @Override
    public FoodPropertiesAccessor saturationModifier$currySL(float saturationModifier$currySL) {
        this.saturationModifier$currySL = saturationModifier$currySL;
        return this;
    }

    @Override
    public FoodPropertiesAccessor isMeat$currySL(boolean isMeat$currySL) {
        this.isMeat$currySL = isMeat$currySL;
        return this;
    }

    @Override
    public FoodPropertiesAccessor canAlwaysEat$currySL(boolean canAlwaysEat$currySL) {
        this.canAlwaysEat$currySL = canAlwaysEat$currySL;
        return this;
    }

    @Override
    public FoodPropertiesAccessor fastFood$currySL(boolean fastFood$currySL) {
        this.fastFood$currySL = fastFood$currySL;
        return this;
    }

    @Override
    public FoodPropertiesAccessor effects$currySL(List<Pair<Supplier<MobEffectInstance>, Float>> effects$currySL) {
        this.effects$currySL = effects$currySL;
        return this;
    }

    @Override
    public FoodProperties cast$currySL() {
        return (FoodProperties) (Object) this;
    }

    @Inject(method = "getNutrition", at = @At("HEAD"), cancellable = true)
    private void getNutrition(CallbackInfoReturnable<Integer> cir){
        if (this.nutrition$currySL != null) cir.setReturnValue(this.nutrition$currySL);
    }

    @Inject(method = "getSaturationModifier", at = @At("HEAD"), cancellable = true)
    private void getSaturationModifier(CallbackInfoReturnable<Float> cir){
        if (this.saturationModifier$currySL != null) cir.setReturnValue(this.saturationModifier$currySL);
    }

    @Inject(method = "isMeat", at = @At("HEAD"), cancellable = true)
    private void isMeat(CallbackInfoReturnable<Boolean> cir){
        if (this.isMeat$currySL != null) cir.setReturnValue(this.isMeat$currySL);
    }

    @Inject(method = "canAlwaysEat", at = @At("HEAD"), cancellable = true)
    private void canAlwaysEat(CallbackInfoReturnable<Boolean> cir){
        if (this.canAlwaysEat$currySL != null) cir.setReturnValue(this.canAlwaysEat$currySL);
    }

    @Inject(method = "isFastFood", at = @At("HEAD"), cancellable = true)
    private void isFastFood(CallbackInfoReturnable<Boolean> cir){
        if (this.fastFood$currySL != null) cir.setReturnValue(this.fastFood$currySL);
    }

    @Inject(method = "getEffects", at = @At("HEAD"), cancellable = true)
    private void getEffects(CallbackInfoReturnable<List<Pair<MobEffectInstance, Float>>> cir){
        if (this.effects$currySL != null) cir.setReturnValue(this.effects$currySL.stream().map(pair -> Pair.of(pair.getFirst() != null ? pair.getFirst().get() : null, pair.getSecond())).collect(Collectors.toList()));
    }
}
