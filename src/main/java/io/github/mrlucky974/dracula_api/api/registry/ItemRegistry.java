package io.github.mrlucky974.dracula_api.api.registry;

import io.github.mrlucky974.dracula_api.api.util.RegistryHelper;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public abstract class ItemRegistry extends BaseRegistry {
    protected static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties itemSettings) {
        return RegistryHelper.registerItem(id(name), itemFactory, itemSettings);
    }
}
