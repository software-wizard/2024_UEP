package pl.psi.effects.object;

import lombok.Getter;

@Getter
public enum CreatureEffectStatistic implements CreatureEffectStatisticIf {
    TEST("Test", "Test", 0, 1, false),
    DISRUPTING_RAY("Disrupting Ray", "Reduces the defense of the selected unit by 3/4/5.", -3, 0, false),
    BLESS("Bless", "Causes the selected/all friendly unit(s) to inflict maximum damage/(maximum damage + 1) when attacking.", 0, 1, false),
    HASTE("Haste", "Increases the speed of the selected/all friendly unit(s) by 3/5.", 3, 1, false),
    ANTI_MAGIC("Anti-Magic", "Target, allied troop becomes immune to spells with max level 3/4/5.", 0, 1, false);

    ;

    private final String name;
    private final String description;
    private final int baseModifierValue;
    private final int length; // 0 - indefinite
    private final boolean isStackable;


    CreatureEffectStatistic(String name, String description, int baseModifierValue, int length, boolean isStackable) {
        this.name = name;
        this.description = description;
        this.baseModifierValue = baseModifierValue;
        this.length = length;
        this.isStackable = isStackable;
    }
}
