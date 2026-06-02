package io.github.mrlucky974.dracula_api.test.effect;

import io.github.mrlucky974.dracula_api.api.effect.BetterStatusEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ExampleStatusEffect extends BetterStatusEffect {
    public ExampleStatusEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
}
