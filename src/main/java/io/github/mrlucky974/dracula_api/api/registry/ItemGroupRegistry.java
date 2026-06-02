package io.github.mrlucky974.dracula_api.api.registry;

import io.github.mrlucky974.dracula_api.api.util.RegistryHelper;
import net.minecraft.world.item.CreativeModeTab;

public abstract class ItemGroupRegistry extends BaseRegistry {
    protected static <T extends CreativeModeTab> T register(String name, T itemGroup) {
        return RegistryHelper.registerItemGroup(id(name), itemGroup);
    }
}
