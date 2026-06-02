package io.github.mrlucky974.dracula_api.api;

import io.github.mrlucky974.dracula_api.DraculaAPI;
import net.fabricmc.api.ModInitializer;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.Set;

public abstract class ModEntrypoint implements ModInitializer {
    protected abstract void onModInitialize();

    @Override
    public final void onInitialize() {
        DraculaAPI.LOGGER.info("Initializing entrypoint: {}", this.getClass().getName());
        initRegistries();
        onModInitialize();
    }

    // ----------------------------
    // Registry loading
    // ----------------------------

    /**
     * Automatically finds and loads all @Registry classes under the mod's package.
     * This forces their static fields to initialize.
     */
    protected void initRegistries() {
        Reflections reflections = getReflections();
        Set<Class<?>> registryClasses = reflections.getTypesAnnotatedWith(ModRegistry.class);

        for (Class<?> registryClass : registryClasses) {
            try {
                // Only load non-abstract classes
                if (!Modifier.isAbstract(registryClass.getModifiers())) {
                    DraculaAPI.LOGGER.info("Loading registry: {}", registryClass.getName());
                    Class.forName(registryClass.getName());
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Failed to load registry class: " + registryClass, e);
            }
        }
    }

    @NotNull
    private Reflections getReflections() {
        String basePackage = this.getClass().getPackageName();
        return new Reflections(basePackage);
    }
}