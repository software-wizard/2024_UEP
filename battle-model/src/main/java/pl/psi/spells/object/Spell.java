package pl.psi.spells.object;

import lombok.Getter;
import pl.psi.spells.calculator.SpellCostCalculatorIf;
import pl.psi.spells.object.interfaces.CastableSpellIf;
import pl.psi.spells.object.interfaces.SpellStatisticIf;

@Getter
public abstract class Spell implements CastableSpellIf {
    private SpellStatisticIf stats;
    private SpellCostCalculatorIf costCalculator;

    Spell() {}
    public Spell(final SpellStatisticIf aStats, final SpellCostCalculatorIf aCalculator) {
        this.stats = aStats;
        this.costCalculator = aCalculator;
    }
    public String getName() {
        return this.stats.getName();
    }


}

