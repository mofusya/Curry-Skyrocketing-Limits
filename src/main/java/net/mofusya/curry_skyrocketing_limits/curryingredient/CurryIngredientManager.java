package net.mofusya.curry_skyrocketing_limits.curryingredient;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.mofusya.curry_skyrocketing_limits.C;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurryIngredientManager {
    private static final Map<ResourceLocation, CurryIngredientHolder> ITEM_INGREDIENTS = new HashMap<>();
    private static final Map<ResourceLocation, CurryIngredientHolder> FLUID_INGREDIENTS = new HashMap<>();

    public static void create(String modId, String ingredientId, ResourceLocation reference, Type type) {
        create(new ResourceLocation(modId, ingredientId), reference, type);
    }

    public static void create(ResourceLocation id, ResourceLocation reference, Type type) {
        if (id.equals(reference)) return;
        var registry = type.getIngredientRegistry();
        if (!registry.containsKey(reference)) return;

        registry.put(id, new CurryIngredientHolder(reference));
    }

    public static void create(String modId, String ingredientId, CurryIngredient ingredient, Type type) {
        create(new ResourceLocation(modId, ingredientId), ingredient, type);
    }

    public static void create(ResourceLocation id, CurryIngredient ingredient, Type type) {
        var registry = type.getIngredientRegistry();
        registry.put(id, new CurryIngredientHolder(ingredient));
    }

    public static void createFromJsons(@Nullable JsonArray jsonArray) {
        jsonArray.asList().stream().map(JsonElement::getAsJsonObject).forEach(jsonObject -> {
            Type type = Type.valueOf(GsonHelper.getAsString(jsonObject, C.TYPE));
            List<ResourceLocation> ingredients = GsonHelper.getAsJsonArray(jsonObject, C.INGREDIENTS).asList().stream().map(JsonElement::getAsString).map(ResourceLocation::new).toList();

            @Nullable
            JsonPrimitive primReference = jsonObject.getAsJsonPrimitive(C.REFERENCE);
            @Nullable
            JsonPrimitive primNutrition = jsonObject.getAsJsonPrimitive(C.NUTRITION);
            @Nullable
            JsonPrimitive primSaturationModifier = jsonObject.getAsJsonPrimitive(C.SATURATION_MODIFIER);

            if (primReference == null){
                for (ResourceLocation ingredient : ingredients) {
                    create(ingredient, new CurryIngredient(
                            primNutrition == null ? null : primNutrition.getAsInt(),
                            primSaturationModifier == null ? null : primSaturationModifier.getAsFloat()
                    ), type);
                }
            } else {
                for (ResourceLocation ingredient : ingredients) {
                    create(ingredient, new ResourceLocation(primReference.getAsString()), type);
                }
            }
        });
    }

    @Nullable
    public static CurryIngredient get(ResourceLocation id, Type type) {
        var ingredientHolder = getAll(type).get(id);
        if (ingredientHolder == null) return null;
        return ingredientHolder.hasRef() ? get(ingredientHolder.getCurryReference(), type) : ingredientHolder.getCurryIngredient();
    }

    public static Map<ResourceLocation, CurryIngredientHolder> getAll(Type type) {
        return new HashMap<>(type.getIngredientRegistry());
    }

    public enum Type {
        ITEMS(CurryIngredientManager.ITEM_INGREDIENTS),
        FLUID(CurryIngredientManager.FLUID_INGREDIENTS);

        private final Map<ResourceLocation, CurryIngredientHolder> ingredientRegistry;

        Type(Map<ResourceLocation, CurryIngredientHolder> ingredientRegistry) {
            this.ingredientRegistry = ingredientRegistry;
        }

        public boolean is(Type type) {
            return this == type;
        }

        public Map<ResourceLocation, CurryIngredientHolder> getIngredientRegistry() {
            return ingredientRegistry;
        }
    }
}
