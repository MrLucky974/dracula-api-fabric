package io.github.mrlucky974.dracula_api.api.effect;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jspecify.annotations.Nullable;

@FunctionalInterface
public interface EffectApplyCallback {
    void onApplied(LivingEntity entity, @Nullable Entity source, int amplifier);
}
