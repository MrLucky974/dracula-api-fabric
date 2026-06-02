package io.github.mrlucky974.dracula_api.api.registry;

import io.github.mrlucky974.dracula_api.api.util.RegistryHelper;
import net.minecraft.world.item.alchemy.Potion;

public abstract class PotionRegistry extends BaseRegistry {
    protected static <T extends Potion> T register(String name, T potion) {
        return RegistryHelper.registerPotion(id(name), potion);
    }
}
