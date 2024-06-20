package pl.psi.effects.generic;

import lombok.Getter;

@Getter
public enum EffectStatistic implements EffectStatisticIf {
    TEST("Test", "Test", 0, 1, false, EffectTargetType.CREATURE),
    DISRUPTING_RAY("Disrupting Ray", "Reduces the defense of the selected unit by 3/4/5.", -3, 0, false, EffectTargetType.CREATURE),
    BLESS("Bless", "Causes the selected/all friendly unit(s) to inflict maximum damage/(maximum damage + 1) when attacking.", 0, 1, false, EffectTargetType.CREATURE),
    HASTE("Haste", "Increases the speed of the selected/all friendly unit(s) by 3/5.", 3, 1, false, EffectTargetType.CREATURE),
    ANTI_MAGIC("Anti-Magic", "Target, allied troop becomes immune to spells with max level 3/4/5.", 0, 1, false, EffectTargetType.CREATURE);

    ;

    private final String name;
    private final String description;
    private final int baseModifierValue;
    private final int length; // 0 - indefinite
    private final boolean isStackable;
    private final EffectTargetType targetType;


    EffectStatistic(String name, String description, int baseModifierValue, int length, boolean isStackable, EffectTargetType targetType) {
        this.name = name;
        this.description = description;
        this.baseModifierValue = baseModifierValue;
        this.length = length;
        this.isStackable = isStackable;
        this.targetType = targetType;
    }
}
