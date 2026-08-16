package net.mofusya.examplemod.data;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.examplemod.C;

import java.util.ArrayList;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, C.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ArrayList<RegistryObject<Block>> registries = new ArrayList<>();

        for (RegistryObject<Block> block : registries) {
            this.blockWithItem(block);
        }
    }

    private void blockWithItem(RegistryObject<Block> block) {
        this.simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }
}
