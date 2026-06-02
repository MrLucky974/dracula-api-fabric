package io.github.mrlucky974.dracula_api.api.item;

import com.chocohead.mm.api.ClassTinkerers;
import io.github.mrlucky974.dracula_api.DraculaAPI;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;

@ApiStatus.Internal
public final class CrossbowChargeTypeBootstrap {
    private static final String ENTRYPOINT =
            "dracula_api:crossbow_charge_types";

    private CrossbowChargeTypeBootstrap() {}

    public static void build() {
        List<CrossbowChargeTypeProvider> providers = FabricLoader.getInstance()
                .getEntrypoints(ENTRYPOINT, CrossbowChargeTypeProvider.class);
        for (CrossbowChargeTypeProvider provider : providers) {
            DraculaAPI.LOGGER.info("Found crossbow charge type provider: {}", provider.getClass().getName());
        }

        CrossbowChargeTypeInternals.freeze();

        var builder = ClassTinkerers.enumBuilder(
                "net.minecraft.item.CrossbowItem$ChargeType",
                String.class
        );

        for (CrossbowChargeType type : CrossbowChargeTypeBootstrap.values()) {
            String name = type.getSerializedName();
            builder.addEnum(name.toUpperCase(Locale.ROOT), name);

            DraculaAPI.LOGGER.info("Registering crossbow charge type: {}", type.getSerializedName());
        }

        builder.build();
    }

    public static List<CrossbowChargeType> values() {
        return CrossbowChargeTypeInternals.values();
    }
}