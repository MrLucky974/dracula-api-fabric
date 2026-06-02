package io.github.mrlucky974.dracula_api.api.client.render;

import com.google.gson.JsonSyntaxException;
import io.github.mrlucky974.dracula_api.DraculaAPI;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelTargetBundle;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShaderManager {
    private static final Minecraft CLIENT = Minecraft.getInstance();
    private static final ObjectAllocator ALLOCATOR = ObjectAllocator.TRIVIAL;
    private static final Map<Identifier, ShaderReference> SHADER_REFERENCES = new LinkedHashMap<>();
    private static final List<ShaderInstance> ACTIVE_SHADERS = new ArrayList<>();

    public static void render(DeltaTracker tickCounter) {
        LocalPlayer player = CLIENT.player;
        if (player == null) {
            return;
        }

        fillActiveShaders(player, tickCounter);

        if (ACTIVE_SHADERS.isEmpty()) {
            return;
        }

        for (ShaderInstance shader : ACTIVE_SHADERS) {
            shader.render(ALLOCATOR, CLIENT);
        }
    }

    public static boolean register(String namespace, String name) {
        return register(ShaderReference.create(namespace, name));
    }

    public static boolean register(String namespace, String name, RenderCondition renderCondition) {
        return register(ShaderReference.create(namespace, name, renderCondition));
    }

    public static boolean register(Identifier identifier) {
        return register(ShaderReference.create(identifier));
    }

    public static boolean register(Identifier identifier, RenderCondition renderCondition) {
        return register(ShaderReference.create(identifier, renderCondition));
    }

    public static boolean register(ShaderReference shaderReference) {
        if (SHADER_REFERENCES.containsKey(shaderReference.id())) {
            return false;
        }
        SHADER_REFERENCES.put(shaderReference.id(), shaderReference);
        return true;
    }

    private static PostChain createShaderGroup(ShaderReference shaderReference) {
        Identifier shaderId = shaderReference.id();
        try {
            return CLIENT.getShaderManager().getPostChain(shaderId, LevelTargetBundle.MAIN_TARGETS);
        } catch (JsonSyntaxException jsonSyntaxException) {
            DraculaAPI.LOGGER.warn("Failed to parse shader: {}", shaderId, jsonSyntaxException);
        }
        return null;
    }

    private static void fillActiveShaders(LocalPlayer player, DeltaTracker tickCounter) {
        ACTIVE_SHADERS.clear();

        for (ShaderReference shaderReference : SHADER_REFERENCES.values()) {
            if (shaderReference.shouldRender(player, tickCounter)) {
                registerShaderInstance(shaderReference);
            }
        }
    }

    private static void registerShaderInstance(ShaderReference shaderReference) {
        ShaderInstance instance = createShaderInstance(shaderReference);
        ACTIVE_SHADERS.add(instance);
    }

    private static ShaderInstance createShaderInstance(ShaderReference shaderReference) {
        PostChain shaderGroup = createShaderGroup(shaderReference);
        return new ShaderInstance(shaderGroup);
    }

    private record ShaderInstance(PostChain postEffectProcessor) {
        public void render(ObjectAllocator allocator, Minecraft client) {
            if (postEffectProcessor != null) {
                RenderTarget framebuffer = client.getMainRenderTarget();
                FrameGraphBuilder frameGraphBuilder = new FrameGraphBuilder();
                PostChain.TargetBundle framebufferSet = PostChain.TargetBundle.of(PostChain.MAIN_TARGET_ID, frameGraphBuilder.importExternal("main", framebuffer));
                postEffectProcessor.addToFrame(frameGraphBuilder, framebuffer.width, framebuffer.height, framebufferSet);
                frameGraphBuilder.run(allocator);
            }
        }
    }
}
