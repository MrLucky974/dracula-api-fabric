package io.github.mrlucky974.dracula_api.api.effect;

import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface
public interface EffectRemoveCallback {
    void onRemoved(LivingEntity livingEntity);
}
