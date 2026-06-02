package io.github.mrlucky974.dracula_api.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.mrlucky974.dracula_api.api.item.CrossbowProjectileItem;
import net.minecraft.client.renderer.item.properties.select.Charge;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Charge.class)
public class ChargeMixin {
    @ModifyReturnValue(
            method = "get(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/world/entity/LivingEntity;ILnet/minecraft/world/item/ItemDisplayContext;)Lnet/minecraft/world/item/CrossbowItem$ChargeType;",
            at = @At("RETURN")
    )
    private CrossbowItem.ChargeType draculaAPI$useProjectileChargeType(
            CrossbowItem.ChargeType original,
            ItemStack stack,
            @Nullable ClientLevel world,
            @Nullable LivingEntity entity,
            int seed,
            ItemDisplayContext context
    ) {
        ChargedProjectiles charged =
                stack.get(DataComponents.CHARGED_PROJECTILES);

        if (charged == null || charged.isEmpty()) {
            return original;
        }

        ItemStack projectileStack = charged.getItems().getFirst();
        Item item = projectileStack.getItem();

        if (item instanceof CrossbowProjectileItem projectile) {
            return projectile.getChargeType();
        }

        return original;
    }
}
