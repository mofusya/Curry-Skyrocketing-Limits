package net.mofusya.curry_skyrocketing_limits.curryingredient;

import net.minecraft.resources.ResourceLocation;
import net.mofusya.ornatelib.util.annotation.FieldsMayBeNullByDefault;
import net.mofusya.ornatelib.util.annotation.MethodsMayReturnNullByDefault;

@FieldsMayBeNullByDefault
@MethodsMayReturnNullByDefault
public class CurryIngredientHolder {

    private CurryIngredient curryIngredient = null;
    private ResourceLocation curryReference = null;

    public CurryIngredientHolder(CurryIngredient curryIngredient) {
        this.curryIngredient = curryIngredient;
    }

    public CurryIngredientHolder(ResourceLocation curryReference) {
        this.curryReference = curryReference;
    }

    public boolean hasRef(){
        return this.getCurryReference() != null && this.getCurryIngredient() == null;
    }

    public CurryIngredient getCurryIngredient() {
        return curryIngredient;
    }

    public ResourceLocation getCurryReference() {
        return curryReference;
    }
}
