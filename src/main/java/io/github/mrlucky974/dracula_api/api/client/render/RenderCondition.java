package io.github.mrlucky974.dracula_api.api.client.render;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.DeltaTracker;

@FunctionalInterface
public interface RenderCondition {
    boolean shouldRender(Context context);

    record Context(LocalPlayer player, DeltaTracker tickCounter) {
    }
}