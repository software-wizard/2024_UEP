package pl.psi.spells.calculator;

import pl.psi.spells.Spellbook;
import pl.psi.spells.object.enums.SpellSchool;
import pl.psi.spells.object.interfaces.SpellStatisticIf;

public class ReducedSpellCostCalculator extends StaticSpellCostCalculator {

    public ReducedSpellCostCalculator(final SpellStatisticIf spellStat) {
        super(spellStat);
    }

    @Override
    public int getCost(Spellbook spellbook) {
        if (spellbook.hasSpell(spellStatistic.getSchool().toString()) || (
                        spellStatistic.getSchool().equals(SpellSchool.ALL) &&
                                spellbook.getSpells().stream().anyMatch((s) ->
                                s.getName().equals("Air Magic") || s.getName().equals("Earth Magic")
                                || s.getName().equals("Fire Magic") || s.getName().equals("Water Magic")
                        )
        )) {
            return Math.max(1, spellStatistic.getCost() - spellStatistic.getLevel());
        }

        return spellStatistic.getCost();
    }
}
