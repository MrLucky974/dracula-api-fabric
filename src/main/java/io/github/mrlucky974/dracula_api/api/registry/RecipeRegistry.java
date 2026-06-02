package io.github.mrlucky974.dracula_api.api.registry;

import io.github.mrlucky974.dracula_api.api.util.RegistryHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public abstract class RecipeRegistry extends BaseRegistry {
    protected static <T extends RecipeType<?>> T registerType(String name, T type) {
        return RegistryHelper.registerRecipeType(id(name), type);
    }

    protected static <T extends RecipeSerializer<?>> T registerSerializer(String name, T serializer) {
        return RegistryHelper.registerRecipeSerializer(id(name), serializer);
    }
}
