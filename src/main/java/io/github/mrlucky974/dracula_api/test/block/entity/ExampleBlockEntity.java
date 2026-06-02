package io.github.mrlucky974.dracula_api.test.block.entity;

import io.github.mrlucky974.dracula_api.test.ExampleBlockEntityTypeRegistry;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.BlockPos;

public class ExampleBlockEntity extends BlockEntity {
    public ExampleBlockEntity(BlockPos pos, BlockState state) {
        super(ExampleBlockEntityTypeRegistry.EXAMPLE_BLOCK_ENTITY, pos, state);
    }
}
