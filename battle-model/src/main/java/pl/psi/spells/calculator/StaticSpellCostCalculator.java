package pl.psi.spells.calculator;

import pl.psi.Hero;
import pl.psi.spells.Spellbook;
import pl.psi.spells.object.interfaces.SpellStatisticIf;

public class StaticSpellCostCalculator implements SpellCostCalculatorIf {

    protected SpellStatisticIf spellStatistic;

    public StaticSpellCostCalculator(final SpellStatisticIf spellStat) {
        this.spellStatistic = spellStat;
    }

    @Override
    public int getCost(Spellbook spellbook) {
        return this.spellStatistic.getCost();
    }
}
