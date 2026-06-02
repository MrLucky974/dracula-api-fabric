package io.github.mrlucky974.dracula_api.api.datagen.provider;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.world.item.Item;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public abstract class DraculaModelProvider extends FabricModelProvider {
    public DraculaModelProvider(FabricDataOutput output) {
        super(output);
    }

    public final void registerPotionType(@NonNull ItemModelGenerators itemModelGenerator, Item item) {
        Identifier identifier = itemModelGenerator.generateLayeredItem(item, ModelLocationUtils.getModelLocation(item, "_overlay"), ModelLocationUtils.getModelLocation(item));
        itemModelGenerator.addPotionTint(item, identifier);
    }
}
