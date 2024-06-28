package pl.psi.spells.calculator;

import pl.psi.Hero;
import pl.psi.spells.object.enums.SpellSchool;
import pl.psi.spells.object.interfaces.SpellStatisticIf;

public class ReducedSpellCostCalculator extends BaseSpellCostCalculator {
    public ReducedSpellCostCalculator(final SpellStatisticIf spellStat) {
        super(spellStat);
    }

    @Override
    public int getCost(Hero caster) {
        return getBaseCost(caster);
    }
}
