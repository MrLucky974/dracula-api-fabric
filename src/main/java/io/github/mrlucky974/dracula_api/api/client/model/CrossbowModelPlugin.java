package io.github.mrlucky974.dracula_api.api.client.model;

import io.github.mrlucky974.dracula_api.DraculaAPI;
import io.github.mrlucky974.dracula_api.api.item.CrossbowChargeType;
import io.github.mrlucky974.dracula_api.api.item.CrossbowChargeTypeBootstrap;
import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelModifier;
import net.fabricmc.fabric.api.client.model.loading.v1.SimpleUnbakedExtraModel;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.select.Charge;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import net.minecraft.client.renderer.block.model.TextureSlots;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class CrossbowModelPlugin implements ModelLoadingPlugin, ModelModifier.AfterBakeItem {
    private CrossbowModelPlugin() {}

    public static void register() {
        ModelLoadingPlugin.register(new CrossbowModelPlugin());
    }

    @Override
    public void initialize(ModelLoadingPlugin.@NonNull Context pluginContext) {
        List<CrossbowChargeType> values = CrossbowChargeTypeBootstrap.values();

        for (CrossbowChargeType type : values) {
            Identifier modelId = type.getModelId();
            SimpleUnbakedExtraModel<@NotNull ItemModel> model = new SimpleUnbakedExtraModel<>(modelId, (bakedSimpleModel, baker) -> {
                TextureSlots modelTextures = bakedSimpleModel.getTopTextureSlots();
                List<BakedQuad> list = bakedSimpleModel.bakeTopGeometry(modelTextures, baker,
                        BlockModelRotation.IDENTITY).getAll();
                ModelRenderProperties modelSettings = ModelRenderProperties.fromResolvedModel(baker,
                        bakedSimpleModel, modelTextures);
                Function<ItemStack, RenderType> function = BlockModelWrapper.detectRenderType(list);
                return new BlockModelWrapper(Collections.emptyList(), list, modelSettings, function);
            });

            pluginContext.addModel(ExtraModelKey.create(modelId::toString), model);

            DraculaAPI.LOGGER.info("Adding model for charge type: {} (model id: {})", type.getSerializedName(), modelId);
        }

        pluginContext.modifyItemModelAfterBake()
                .register(this);
    }

    @Override
    public @NonNull ItemModel modifyModelAfterBake(@NonNull ItemModel model,
                                                   ModelModifier.AfterBakeItem.Context context) {
        if (context.itemId().equals(BuiltInRegistries.ITEM.getKey(Items.CROSSBOW))) {
            List<CrossbowChargeType> values = CrossbowChargeTypeBootstrap.values();

            List<SelectItemModel.SwitchCase<CrossbowItem.ChargeType>> cases = new ArrayList<>();

            for (CrossbowChargeType type : values) {
                Identifier modelId = type.getModelId();
                ItemModel.Unbaked typeUnbakedModel = ItemModelUtils.plainModel(modelId);
                cases.add(ItemModelUtils.when(type.asEnumValue(), typeUnbakedModel));
            }

            ItemModel.Unbaked newModel = ItemModelUtils.select(
                    new Charge(),
                    context.sourceModel(),
                    cases
            );

            return newModel.bake(context.bakeContext());
        }

        return model;
    }
}
