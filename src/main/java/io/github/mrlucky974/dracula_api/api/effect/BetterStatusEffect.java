package io.github.mrlucky974.dracula_api.api.effect;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.core.particles.ParticleOptions;
import org.jspecify.annotations.Nullable;

public abstract class BetterStatusEffect extends MobEffect
        implements EffectApplyCallback, EffectRemoveCallback {
    public BetterStatusEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public BetterStatusEffect(MobEffectCategory category, int color, ParticleOptions particleEffect) {
        super(category, color, particleEffect);
    }

    @Override
    public void onApplied(LivingEntity entity, @Nullable Entity source, int amplifier) {

    }

    @Override
    public void onRemoved(LivingEntity livingEntity) {

    }
}
