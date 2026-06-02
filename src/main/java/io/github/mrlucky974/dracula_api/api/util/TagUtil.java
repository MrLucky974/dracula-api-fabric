package io.github.mrlucky974.dracula_api.api.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.Identifier;

public final class TagUtil {
    private TagUtil() {}

    private static <T> TagKey<T> tagKey(Identifier identifier, ResourceKey<? extends Registry<T>> registry) {
        return TagKey.create(registry, identifier);
    }

    public static class Items {
        public static TagKey<Item> get(Identifier identifier) {
            return tagKey(identifier, Registries.ITEM);
        };
    }

    public static class Blocks {
        public static TagKey<Block> get(Identifier identifier) {
            return tagKey(identifier, Registries.BLOCK);
        };
    }
}
