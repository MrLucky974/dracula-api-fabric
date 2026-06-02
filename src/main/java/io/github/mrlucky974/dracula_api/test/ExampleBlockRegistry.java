package io.github.mrlucky974.dracula_api.test;

import io.github.mrlucky974.dracula_api.DraculaAPI;
import io.github.mrlucky974.dracula_api.api.ModReference;
import io.github.mrlucky974.dracula_api.api.ModRegistry;
import io.github.mrlucky974.dracula_api.api.registry.BlockRegistry;
import io.github.mrlucky974.dracula_api.test.block.ExampleBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Blocks;

@ModRegistry(value = @ModReference(DraculaAPI.class))
public class ExampleBlockRegistry extends BlockRegistry {
    public static ExampleBlock EXAMPLE_BLOCK = register("example_block", ExampleBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE), true);
}
