package io.github.mrlucky974.dracula_api.api.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public abstract class SimpleCrossbowProjectileItem extends Item implements CrossbowProjectileItem {
    public SimpleCrossbowProjectileItem(Properties settings) {
        super(settings);
    }
}