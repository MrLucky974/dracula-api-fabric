package io.github.mrlucky974.dracula_api.mixin.client;

import io.github.mrlucky974.dracula_api.api.client.render.ShaderManager;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.DeltaTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "render",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/fog/FogRenderer;endFrame()V"))
    private void renderProxy(DeltaTracker tickCounter, boolean tick, CallbackInfo ci) {
        ShaderManager.render(tickCounter);
    }
}