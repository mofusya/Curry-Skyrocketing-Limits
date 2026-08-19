package net.mofusya.curry_skyrocketing_limits.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.curry_skyrocketing_limits.C;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, C.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        {
            ArrayList<RegistryObject<Block>> registries = new ArrayList<>();


            var tags = this.tag(BlockTags.MINEABLE_WITH_PICKAXE);
            for (RegistryObject<Block> block : registries) {
                tags.add(block.get());
            }
        }

        {
            ArrayList<RegistryObject<Block>> registries = new ArrayList<>();


            var tags = this.tag(BlockTags.NEEDS_IRON_TOOL);
            for (RegistryObject<Block> block : registries) {
                tags.add(block.get());
            }
        }
    }
}
