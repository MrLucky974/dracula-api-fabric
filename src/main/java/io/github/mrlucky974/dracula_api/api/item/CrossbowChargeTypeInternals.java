package io.github.mrlucky974.dracula_api.api.item;

import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class CrossbowChargeTypeInternals {
    private static final List<CrossbowChargeType> TYPES = new ArrayList<>();
    private static boolean FROZEN = false;

    private CrossbowChargeTypeInternals() {}

    static CrossbowChargeType register(Identifier identifier) {
        if (FROZEN) {
            throw new IllegalStateException(
                    "CrossbowChargeType registration is frozen"
            );
        }

        CrossbowChargeType type = CrossbowChargeType.of(identifier);
        TYPES.add(type);
        return type;
    }

    static List<CrossbowChargeType> values() {
        return Collections.unmodifiableList(TYPES);
    }

    static void freeze() {
        FROZEN = true;
    }
}