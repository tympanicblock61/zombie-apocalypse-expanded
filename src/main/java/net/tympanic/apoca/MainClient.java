package net.tympanic.apoca;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.tympanic.apoca.block.LootGen;
import net.tympanic.apoca.block.ModBlocks;

public class MainClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LOOT_GEN, RenderLayer.getCutout());
    }
}
