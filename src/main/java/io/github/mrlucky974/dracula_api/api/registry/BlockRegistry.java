package io.github.mrlucky974.dracula_api.api.registry;

import io.github.mrlucky974.dracula_api.api.util.RegistryHelper;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

public abstract class BlockRegistry extends BaseRegistry {
    protected static <T extends Block> T register(String name, Function<Block.Properties, T> blockFactory, Block.Properties blockSettings, boolean shouldRegisterItem) {
        return RegistryHelper.registerBlock(id(name), blockFactory, blockSettings, shouldRegisterItem);
    }
}
