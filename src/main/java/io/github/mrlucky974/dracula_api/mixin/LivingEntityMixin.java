package io.github.mrlucky974.dracula_api.mixin;

import io.github.mrlucky974.dracula_api.api.effect.EffectApplyCallback;
import io.github.mrlucky974.dracula_api.api.effect.EffectRemoveCallback;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(
            method = "onEffectsRemoved",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/effect/MobEffect;removeAttributeModifiers(Lnet/minecraft/world/entity/ai/attributes/AttributeMap;)V"
            )
    )
    private void draculaAPI$onStatusEffectRemoved(Collection<MobEffectInstance> effects, CallbackInfo ci) {
        for (MobEffectInstance effect : effects) {
            if (effect.getEffect().value() instanceof EffectRemoveCallback parnse) {
                parnse.onRemoved((LivingEntity) (Object) this);
            }
        }
    }

    @Inject(method = "onEffectAdded", at = @At("TAIL"))
    private void draculaAPI$onStatusEffectApplied(MobEffectInstance effect, Entity source, CallbackInfo ci) {
        if (!level().isClientSide()) {
            if (effect.getEffect().value() instanceof EffectApplyCallback parnse) {
                parnse.onApplied((LivingEntity) (Object) this, source, effect.getAmplifier());
            }
        }
    }
}
