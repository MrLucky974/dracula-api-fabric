package io.github.mrlucky974.dracula_api.test.block;

import com.mojang.serialization.MapCodec;
import io.github.mrlucky974.dracula_api.test.block.entity.ExampleBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class ExampleBlock extends BaseEntityBlock {
    public static final MapCodec<ExampleBlock> CODEC = FurnaceBlock.simpleCodec(ExampleBlock::new);

    public ExampleBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new ExampleBlockEntity(pos, state);
    }
}
