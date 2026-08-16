package net.mofusya.examplemod.data.loot_tables;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    public ModBlockLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        ArrayList<RegistryObject<Block>> registries = new ArrayList<>();


        for (RegistryObject<Block> block : registries) {
            this.dropSelf(block.get());
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        ArrayList<RegistryObject<Block>> blocks = new ArrayList<>();


        return blocks.stream().map(RegistryObject::get).toList();
    }
}