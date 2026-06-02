package io.github.mrlucky974.dracula_api.test;

import io.github.mrlucky974.dracula_api.DraculaAPI;
import io.github.mrlucky974.dracula_api.api.ModReference;
import io.github.mrlucky974.dracula_api.api.ModRegistry;
import io.github.mrlucky974.dracula_api.api.registry.BlockEntityTypeRegistry;
import io.github.mrlucky974.dracula_api.test.block.entity.ExampleBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

@ModRegistry(value = @ModReference(DraculaAPI.class))
public class ExampleBlockEntityTypeRegistry extends BlockEntityTypeRegistry {
    public static BlockEntityType<@NotNull ExampleBlockEntity> EXAMPLE_BLOCK_ENTITY = register("example_block",
            ExampleBlockEntity::new,
            ExampleBlockRegistry.EXAMPLE_BLOCK);
}
