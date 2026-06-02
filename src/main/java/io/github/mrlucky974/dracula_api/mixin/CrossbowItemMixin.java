package io.github.mrlucky974.dracula_api.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.mrlucky974.dracula_api.api.item.CrossbowProjectileItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {
    @ModifyReturnValue(
            method = "getSupportedHeldProjectiles",
            at = @At("RETURN")
    )
    private static Predicate<ItemStack> draculaAPI$allowCustomAmmo(
            Predicate<ItemStack> original
    ) {
        return original.or(itemStack ->
                itemStack.getItem() instanceof CrossbowProjectileItem);
    }

    @Inject(
            method = "createProjectile",
            at = @At("HEAD"),
            cancellable = true)
    private static void draculaAPI$shootCustomProjectileEntity(
            Level world,
            LivingEntity shooter,
            ItemStack weaponStack,
            ItemStack projectileStack,
            boolean critical,
            CallbackInfoReturnable<Projectile> cir) {
        if (projectileStack.getItem() instanceof CrossbowProjectileItem projectileItem) {
            Projectile projectileEntity = projectileItem.createProjectileEntity(world,
                    projectileStack,
                    weaponStack,
                    shooter,
                    shooter.getX(),
                    shooter.getEyeY() - 0.15F,
                    shooter.getZ(),
                    true
            );
            if (projectileEntity != null) {
                cir.setReturnValue(projectileEntity);
            }
        }
    }
}
