package pl.psi.spells;

import lombok.Getter;
import pl.psi.creatures.Creature;

@Getter
public class Spell {
    private SpellStatisticIf stats;

    Spell() {}
    public Spell(final SpellStatisticIf aStats) {
        this.stats = aStats;
    }
    public void cast() {}
}

