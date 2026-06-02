package io.github.mrlucky974.dracula_api.api.item;

import io.github.mrlucky974.dracula_api.api.ModEntrypoint;
import io.github.mrlucky974.dracula_api.api.ModReference;
import io.github.mrlucky974.dracula_api.api.ModRegistry;
import io.github.mrlucky974.dracula_api.api.util.ModUtil;
import net.minecraft.resources.Identifier;

public abstract class CrossbowChargeTypeProvider {

    /**
     * Registers a crossbow charge type and returns it.
     */
    protected static CrossbowChargeType of(String name) {
        return of(id(name));
    }

    private static CrossbowChargeType of(Identifier identifier) {
        return CrossbowChargeTypeInternals.register(identifier);
    }

    /**
     * Builds an Identifier for a registry entry using the mod class from @Registry.
     * Detects the calling registry class automatically.
     */
    static Identifier id(String name) {
        Class<?> registryClass = findConcreteRegistryClass();

        ModReference registryAnnotation = registryClass.getAnnotation(ModReference.class);
        if (registryAnnotation == null) {
            throw new IllegalStateException("@Registry annotation missing on " + registryClass.getName());
        }

        Class<? extends ModEntrypoint> modClass = registryAnnotation.value();

        return ModUtil.id(modClass, name);
    }

    private static Class<?> findConcreteRegistryClass() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();

        for (StackTraceElement element : stack) {
            try {
                Class<?> cls = Class.forName(element.getClassName());

                // Must be a provider
                if (!CrossbowChargeTypeProvider.class.isAssignableFrom(cls)) continue;

                // Skip the base class itself
                if (cls == CrossbowChargeTypeProvider.class) continue;

                return cls;
            } catch (ClassNotFoundException ignored) {
            }
        }

        throw new IllegalStateException(
                "Unable to determine concrete CrossbowChargeTypeProvider class"
        );
    }
}