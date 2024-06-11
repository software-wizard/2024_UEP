package pl.psi.effects.object;

import lombok.Getter;

@Getter
public enum CreatureEffectStatistic implements CreatureEffectStatisticIf {
    TEST("Test", "Test", 1, false);

    private final String name;
    private final String description;
    private final int length; // 0 - indefinite
    private final boolean isStackable;

    CreatureEffectStatistic(String name, String description, int length, boolean isStackable) {
        this.name = name;
        this.description = description;
        this.length = length;
        this.isStackable = isStackable;
    }
}
