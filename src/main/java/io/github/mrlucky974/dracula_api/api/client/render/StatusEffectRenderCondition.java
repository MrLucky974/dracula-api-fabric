package io.github.mrlucky974.dracula_api.api.client.render;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.Holder;

public final class StatusEffectRenderCondition implements RenderCondition {
    private final Holder<MobEffect> statusEffect;

    private StatusEffectRenderCondition(Holder<MobEffect> statusEffect) {
        this.statusEffect = statusEffect;
    }

    public static StatusEffectRenderCondition hasEffect(Holder<MobEffect> statusEffect) {
        return new StatusEffectRenderCondition(statusEffect);
    }

    @Override
    public boolean shouldRender(Context context) {
        LocalPlayer player = context.player();
        return statusEffect != null && player.hasEffect(statusEffect);
    }
}
