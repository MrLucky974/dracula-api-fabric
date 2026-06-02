package io.github.mrlucky974.dracula_api.test;

import io.github.mrlucky974.dracula_api.DraculaAPI;
import io.github.mrlucky974.dracula_api.api.ModReference;
import io.github.mrlucky974.dracula_api.api.ModRegistry;
import io.github.mrlucky974.dracula_api.api.registry.ItemRegistry;
import io.github.mrlucky974.dracula_api.test.item.ExampleCrossbowProjectileItem;
import net.minecraft.world.item.Item;

@ModRegistry(value = @ModReference(DraculaAPI.class))
public class ExampleItemRegistry extends ItemRegistry {
    public static Item EXAMPLE_ITEM = register("example_item", Item::new, new Item.Properties());
    public static Item EXAMPLE_CROSSBOW_PROJECTILE_ITEM = register("example_crossbow_projectile_item",
            ExampleCrossbowProjectileItem::new, new Item.Properties());
}
