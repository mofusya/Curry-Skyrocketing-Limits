package net.mofusya.curry_skyrocketing_limits.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.curry_skyrocketing_limits.C;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, C.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ArrayList<RegistryObject<Item>> registries = new ArrayList<>();

        for (RegistryObject<Item> item : registries) {
            this.simpleItem(item);
        }
    }

    private void simpleItem(RegistryObject<Item> item) {
        this.withExistingParent(item.getId().getPath(),
                        new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(C.MOD_ID, "item/" + item.getId().getPath()));
    }

    private void layeredSimpleItem(RegistryObject<Item> item, @Nullable String... textures) {
        var model = this.withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"));
        for (int i = 0; i < textures.length; i++) {
            model.texture("layer" + i, new ResourceLocation(C.MOD_ID, "item/" + (textures[i] == null ? item.getId().getPath() : textures[i])));
        }
    }

    private void simpleItemWithSublayer(RegistryObject<Item> item, String prefix) {
        this.layeredSimpleItem(item, item.getId().getPath(), item.getId().getPath() + prefix);
    }

    private static String getPath(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }
}
