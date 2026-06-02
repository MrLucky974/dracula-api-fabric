package io.github.mrlucky974.dracula_api.api.util;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.resources.Identifier;

import java.util.function.Function;

public class RegistryHelper {
    public static <T extends Block> T registerBlock(Identifier identifier, Function<Block.Properties, T> blockFactory, Block.Properties blockSettings, boolean shouldRegisterItem) {
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, identifier);
        T block = blockFactory.apply(blockSettings.setId(blockKey));

        if (shouldRegisterItem) {
            ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, identifier);

            BlockItem blockItem = new BlockItem(block, new net.minecraft.world.item.Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
            Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
        }

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    public static <T extends DataComponentType<?>> T registerComponent(Identifier identifier, T component) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, identifier, component);
    }

    public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(Identifier identifier, FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory, Block... blocks) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, identifier, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static <T extends CriterionTrigger<?>> T registerCriterion(Identifier identifier, T criterion) {
        return CriteriaTriggers.register(identifier.getNamespace() + "/" + identifier.getPath(), criterion);
    }

    public static <T extends CreativeModeTab> T registerItemGroup(Identifier identifier, T itemGroup) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, identifier, itemGroup);
    }

    public static <T extends Item> T registerItem(Identifier identifier, Function<net.minecraft.world.item.Item.Properties, T> itemFactory, net.minecraft.world.item.Item.Properties itemSettings) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, identifier);
        T item = itemFactory.apply(itemSettings.setId(itemKey));
        return Registry.register(BuiltInRegistries.ITEM, itemKey, item);
    }

    public static <T extends Potion> T registerPotion(Identifier identifier, T potion) {
        return Registry.register(BuiltInRegistries.POTION, identifier, potion);
    }

    public static <T extends RecipeType<?>> T registerRecipeType(Identifier identifier, T type) {
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, identifier, type);
    }

    public static <T extends RecipeSerializer<?>> T registerRecipeSerializer(Identifier identifier, T serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, identifier, serializer);
    }

    public static <T extends AbstractContainerMenu> MenuType<T> registerScreenHandler(Identifier identifier, MenuType.MenuSupplier<T> factory) {
        return Registry.register(BuiltInRegistries.MENU, identifier, new MenuType<>(factory, FeatureFlagSet.of()));
    }

    public static <T extends AbstractContainerMenu, D extends CustomPacketPayload> ExtendedScreenHandlerType<T, D>
    registerScreenHandler(Identifier identifier,
                          ExtendedScreenHandlerType.ExtendedFactory<T, D> factory,
                          StreamCodec<? super RegistryFriendlyByteBuf, D> codec) {
        return Registry.register(BuiltInRegistries.MENU, identifier, new ExtendedScreenHandlerType<>(factory, codec));
    }

    public static <T extends MobEffect> Holder<T> registerStatusEffect(Identifier identifier, T statusEffect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, identifier, statusEffect);
    }
}
