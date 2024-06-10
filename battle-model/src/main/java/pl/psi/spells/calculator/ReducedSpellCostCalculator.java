package pl.psi.spells.calculator;

import pl.psi.Hero;
import pl.psi.spells.object.SpellSchool;
import pl.psi.spells.object.SpellStatistic;
import pl.psi.spells.object.SpellStatisticIf;

public class ReducedSpellCostCalculator extends StaticSpellCostCalculator {

    public ReducedSpellCostCalculator(final SpellStatisticIf spellStat) {
        super(spellStat);
    }

    @Override
    public int getCost(Hero aHero) {
        if (aHero.getSpellbook().hasSpell(spellStatistic.getSchool().toString()) || (
                        spellStatistic.getSchool().equals(SpellSchool.ALL) &&
                        aHero.getSpellbook().getSpells().stream().anyMatch((s) ->
                                s.getName().equals("Air Magic") || s.getName().equals("Earth Magic")
                                || s.getName().equals("Fire Magic") || s.getName().equals("Water Magic")
                        )
        )) {
            return spellStatistic.getCost() - spellStatistic.getLevel();
        }

        return spellStatistic.getCost();
    }
}
