package io.github.mrlucky974.dracula_api.api.registry;

import io.github.mrlucky974.dracula_api.api.util.RegistryHelper;
import net.minecraft.advancements.CriterionTrigger;

public abstract class CriterionRegistry extends BaseRegistry {
    protected static <T extends CriterionTrigger<?>> T register(String name, T criterion) {
        return RegistryHelper.registerCriterion(id(name), criterion);
    }
}
