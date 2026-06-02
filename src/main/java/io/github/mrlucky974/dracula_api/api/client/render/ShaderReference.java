package io.github.mrlucky974.dracula_api.api.client.render;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.resources.Identifier;

public final class ShaderReference {
    private final Identifier identifier;
    private final RenderCondition renderCondition;

    private ShaderReference(String namespace, String name, RenderCondition renderCondition) {
        this(Identifier.fromNamespaceAndPath(namespace, name), renderCondition);
    }

    private ShaderReference(Identifier identifier, RenderCondition renderCondition) {
        this.identifier = identifier;
        this.renderCondition = renderCondition;
    }

    public Identifier id() {
        return identifier;
    }

    public static ShaderReference create(Identifier identifier) {
        return create(identifier, ShaderReference::alwaysRender);
    }

    public static ShaderReference create(Identifier identifier, RenderCondition renderCondition) {
        return new ShaderReference(identifier, renderCondition);
    }

    public static ShaderReference create(String namespace, String name) {
        return create(namespace, name, ShaderReference::alwaysRender);
    }

    public static ShaderReference create(String namespace, String name, RenderCondition renderCondition) {
        return new ShaderReference(namespace, name, renderCondition);
    }

    public boolean shouldRender(LocalPlayer player, DeltaTracker tickCounter) {
        RenderCondition.Context context = new RenderCondition.Context(player, tickCounter);
        return renderCondition.shouldRender(context);
    }

    public static boolean alwaysRender(RenderCondition.Context context) {
        return true;
    }

    public static boolean neverRender(RenderCondition.Context context) {
        return false;
    }
}
