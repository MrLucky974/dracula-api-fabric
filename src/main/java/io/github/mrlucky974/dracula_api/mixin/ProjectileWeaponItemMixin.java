package io.github.mrlucky974.dracula_api.mixin;

import io.github.mrlucky974.dracula_api.api.item.CrossbowProjectileItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ProjectileWeaponItem.class)
public abstract class ProjectileWeaponItemMixin {
    @Shadow
    protected abstract Projectile createProjectile(Level world, LivingEntity shooter, ItemStack weaponStack, ItemStack projectileStack, boolean critical);

    @Shadow
    protected abstract int getDurabilityUse(ItemStack projectile);

    @Shadow
    protected abstract void shootProjectile(LivingEntity var1, Projectile var2, int var3, float var4, float var5, float var6, @Nullable LivingEntity var7);

    @Inject(
            method = "shoot",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void draculaAPI$conditionalSpawn(ServerLevel world, LivingEntity shooter, InteractionHand hand, ItemStack stack, List<ItemStack> projectiles, float speed, float divergence, boolean critical, @Nullable LivingEntity target, CallbackInfo ci) {
        float f = EnchantmentHelper.processProjectileSpread(world, stack, shooter, 0.0f);
        float g = projectiles.size() == 1 ? 0.0f : 2.0f * f / (float)(projectiles.size() - 1);
        float h = (float)((projectiles.size() - 1) % 2) * g / 2.0f;
        float i = 1.0f;
        for (int projectileIndex = 0; projectileIndex < projectiles.size(); ++projectileIndex) {
            ItemStack itemStack = projectiles.get(projectileIndex);
            if (itemStack.isEmpty()) continue;
            float yaw = h + i * (float)((projectileIndex + 1) / 2) * g;
            i = -i;
            if (shootProjectile(world, shooter, hand, stack, itemStack, projectileIndex, speed, divergence, yaw, critical, target)) {
                break;
            }
        }
        ci.cancel();
    }

    @Inject(
            method = "draw",
            at = @At(value = "TAIL")
    )
    private static void draculaAPI$onProjectileLoad(ItemStack stack, ItemStack projectileStack, LivingEntity shooter, CallbackInfoReturnable<List<ItemStack>> cir) {
        if (projectileStack.getItem() instanceof CrossbowProjectileItem crossbowProjectileItem) {
            crossbowProjectileItem.onProjectileLoaded(stack, projectileStack, shooter);
        }
    }

    @Unique
    private boolean shootProjectile(ServerLevel world, LivingEntity shooter, InteractionHand hand,
                                    ItemStack weaponStack, ItemStack projectileStack,
                                    int projectileIndex, float speed, float divergence, float yaw,
                                    boolean critical, @Nullable LivingEntity target) {
        boolean shouldProjectile = true;

        if (projectileStack.getItem() instanceof CrossbowProjectileItem crossbowProjectileItem) {
            shouldProjectile = !crossbowProjectileItem.shoot(world, shooter, hand, weaponStack,
                    projectileStack, projectileIndex, speed, divergence, yaw, critical, target);
        }

        if (shouldProjectile) {
            Projectile.spawnProjectile(createProjectile(world, shooter, weaponStack, projectileStack,
                            critical), world, projectileStack,
                    projectile -> shootProjectile(shooter, projectile, projectileIndex, speed,
                            divergence, yaw, target));
        }

        weaponStack.hurtAndBreak(getDurabilityUse(projectileStack), shooter, hand.asEquipmentSlot());
        return weaponStack.isEmpty();
    }
}