package io.github.mrlucky974.dracula_api.api.registry;

import io.github.mrlucky974.dracula_api.api.util.RegistryHelper;
import net.minecraft.core.component.DataComponentType;

public abstract class ComponentRegistry extends BaseRegistry {
    protected static <T extends DataComponentType<?>> T register(String name, T component) {
        return RegistryHelper.registerComponent(id(name), component);
    }
}
