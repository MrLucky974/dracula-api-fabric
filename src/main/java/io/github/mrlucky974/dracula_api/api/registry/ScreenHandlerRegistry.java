package io.github.mrlucky974.dracula_api.api.registry;

import io.github.mrlucky974.dracula_api.api.util.RegistryHelper;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

public abstract class ScreenHandlerRegistry extends BaseRegistry {
    protected static <T extends AbstractContainerMenu> MenuType<T> register(String name, MenuType.MenuSupplier<T> factory) {
        return RegistryHelper.registerScreenHandler(id(name), factory);
    }

    protected static <T extends AbstractContainerMenu, D extends CustomPacketPayload> ExtendedScreenHandlerType<@NotNull T, @NotNull D>
    register(String name,
             ExtendedScreenHandlerType.ExtendedFactory<@NotNull T, @NotNull D> factory,
             StreamCodec<? super RegistryFriendlyByteBuf, D> codec) {
        return RegistryHelper.registerScreenHandler(id(name), factory, codec);
    }
}
