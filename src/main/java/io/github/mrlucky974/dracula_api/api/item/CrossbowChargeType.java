package io.github.mrlucky974.dracula_api.api.item;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.resources.Identifier;
import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public final class CrossbowChargeType implements StringRepresentable {
    private final Identifier identifier;

    private CrossbowChargeType(Identifier identifier) {
        this.identifier = identifier;
    }

    static CrossbowChargeType of(Identifier identifier) {
        return new CrossbowChargeType(identifier);
    }

    public CrossbowItem.ChargeType asEnumValue() {
        return ClassTinkerers.getEnum(CrossbowItem.ChargeType.class, getSerializedName()
                .toUpperCase(Locale.ROOT));
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Identifier getModelId() {
        return getModelId(this);
    }

    public static Identifier getModelId(CrossbowChargeType type) {
        return type.getIdentifier().withPrefix("item/crossbow_");
    }

    @Override
    public String getSerializedName() {
        return identifier.toDebugFileName();
    }
}
