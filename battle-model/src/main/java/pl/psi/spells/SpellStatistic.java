package pl.psi.spells;

import lombok.Getter;

@Getter
public enum SpellStatistic implements SpellStatisticIf {
    DAMAGING_SPELL("Damaging Spell", "Damages a creature", 0, SpellType.COMBAT, 0),
    SAMPLE_SPELL("Sample", "Sample spell", 0, SpellType.UNKNOWN, 0);

    private final String name;
    private final String description;
    private final int cost;
    private final SpellType type;
    private final int level;

    SpellStatistic(String name, String description, int cost, SpellType type, int level) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.type = type;
        this.level = level;
    }
}
