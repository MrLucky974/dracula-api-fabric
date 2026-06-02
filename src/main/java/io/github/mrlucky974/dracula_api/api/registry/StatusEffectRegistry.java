package io.github.mrlucky974.dracula_api.api.registry;

import io.github.mrlucky974.dracula_api.api.util.RegistryHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.Holder;

public abstract class StatusEffectRegistry extends BaseRegistry {
    protected static <T extends MobEffect> Holder<T> register(String name, T statusEffect) {
        return RegistryHelper.registerStatusEffect(id(name), statusEffect);
    }
}
