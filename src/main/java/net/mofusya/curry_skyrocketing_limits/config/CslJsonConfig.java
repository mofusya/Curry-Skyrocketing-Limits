package net.mofusya.curry_skyrocketing_limits.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.mofusya.curry_skyrocketing_limits.C;
import net.mofusya.curry_skyrocketing_limits.curryingredient.CurryIngredientManager;
import net.mofusya.ornatelib.config.JsonConfig;
import org.openjdk.nashorn.internal.scripts.JO;

public class CslJsonConfig {

    public static final JsonConfig CUSTOM_INGREDIENTS = new JsonConfig(C.MOD_ID + "/custom_ingredients", () -> {
        JsonObject jsonObject = new JsonObject();
        JsonArray ingredients = new JsonArray();

        //Adding water demo.
        JsonObject water = new JsonObject();
        water.addProperty(C.TYPE, CurryIngredientManager.Type.FLUID.toString());
        JsonArray waterIngredients = new JsonArray();
        waterIngredients.add("minecraft:water");
        water.add(C.INGREDIENTS, waterIngredients);
        water.addProperty(C.NUTRITION, 1);
        water.addProperty(C.SATURATION_MODIFIER, 0f);
        ingredients.add(water);

        //Adding lava demo.
        JsonObject lava = new JsonObject();
        lava.addProperty(C.TYPE, CurryIngredientManager.Type.FLUID.toString());
        JsonArray lavaIngredients = new JsonArray();
        lavaIngredients.add("lava");
        lava.add(C.INGREDIENTS, lavaIngredients);
        lava.addProperty(C.REFERENCE, "minecraft:water");
        ingredients.add(lava);

        //Adding gold demo.
        JsonObject gold = new JsonObject();
        gold.addProperty(C.TYPE, CurryIngredientManager.Type.ITEMS.toString());
        JsonArray goldIngredients = new JsonArray();
        goldIngredients.add("minecraft:gold_block");
        goldIngredients.add("minecraft:gold_ingot");
        goldIngredients.add("gold_nugget");
        gold.add(C.INGREDIENTS, goldIngredients);
        gold.addProperty(C.NUTRITION, 8);
        ingredients.add(gold);

        //Adding ironBlock demo.
        JsonObject ironBlock = new JsonObject();
        ironBlock.addProperty(C.TYPE, CurryIngredientManager.Type.ITEMS.toString());
        JsonArray ironBlockIngredients = new JsonArray();
        ironBlockIngredients.add("minecraft:iron_block");
        ironBlock.add(C.INGREDIENTS, ironBlockIngredients);
        ironBlock.addProperty(C.REFERENCE, "gold_ingot");
        ingredients.add(ironBlock);

        jsonObject.add(C.CUSTOM_INGREDIENTS, ingredients);
        return jsonObject;
    });
}
