package pl.psi.spells.calculator;

import pl.psi.Hero;
import pl.psi.spells.object.enums.SpellExpertise;
import pl.psi.spells.object.enums.SpellSchool;
import pl.psi.spells.object.interfaces.SpellStatisticIf;

public class ReducedSpellCostCalculator implements SpellCostCalculatorIf {
    private final SpellStatisticIf spellStatistic;

    public ReducedSpellCostCalculator(final SpellStatisticIf spellStat) {
        this.spellStatistic = spellStat;
    }

    @Override
    public int getCost(Hero caster) {
        boolean universalDiscount = false;

        if (spellStatistic.getSchool().equals(SpellSchool.ALL)) {
            for (SpellSchool school : SpellSchool.values()) {
                if (caster.getSpellbook().getSchoolMastery(school).getMasteryLevel() > 0) universalDiscount = true;
            }
        }

        if (universalDiscount || caster.getSpellbook().getSchoolMastery(spellStatistic.getSchool()).getMasteryLevel() > 0) {
            return spellStatistic.getCost() - spellStatistic.getLevel();
        }

        return spellStatistic.getCost();
    }
}
