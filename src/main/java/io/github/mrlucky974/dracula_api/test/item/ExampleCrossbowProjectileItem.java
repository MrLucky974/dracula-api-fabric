package io.github.mrlucky974.dracula_api.test.item;

import io.github.mrlucky974.dracula_api.api.item.SimpleCrossbowProjectileItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ExampleCrossbowProjectileItem extends SimpleCrossbowProjectileItem {
    public ExampleCrossbowProjectileItem(Properties settings) {
        super(settings);
    }

    @Override
    public Projectile createProjectileEntity(Level world, ItemStack projectileStack, ItemStack weaponStack, LivingEntity shooter, double x, double y, double z, boolean critical) {
        return new Snowball(world, shooter, projectileStack);
    }

    @Override
    public CrossbowItem.ChargeType getChargeType() {
        return ExampleCrossbowChargeTypes.TEST.asEnumValue();
    }
}
