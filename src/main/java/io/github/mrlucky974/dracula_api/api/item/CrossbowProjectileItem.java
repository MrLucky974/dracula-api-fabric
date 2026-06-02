package io.github.mrlucky974.dracula_api.api.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public interface CrossbowProjectileItem {
    default boolean shoot(ServerLevel world, LivingEntity shooter, InteractionHand hand,
                          ItemStack weaponStack, ItemStack projectileStack,
                          int projectileIndex, float speed, float divergence, float yaw,
                          boolean critical, @Nullable LivingEntity target) {
        return false;
    }

    default void onProjectileLoaded(ItemStack weaponStack, ItemStack projectileStack, LivingEntity shooter)
    {

    }

    default Projectile createProjectileEntity(Level world, ItemStack projectileStack, ItemStack weaponStack, LivingEntity shooter, double x, double y, double z, boolean critical) {
        return null;
    }

    CrossbowItem.ChargeType getChargeType();
}
