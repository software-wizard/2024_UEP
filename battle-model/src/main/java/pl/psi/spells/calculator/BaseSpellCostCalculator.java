package pl.psi.spells.calculator;

import pl.psi.Hero;
import pl.psi.spells.object.enums.SpellSchool;
import pl.psi.spells.object.interfaces.SpellStatisticIf;

public class BaseSpellCostCalculator implements SpellCostCalculatorIf {

    protected SpellStatisticIf spellStatistic;

    public BaseSpellCostCalculator(final SpellStatisticIf spellStat) {
        this.spellStatistic = spellStat;
    }

    protected int getBaseCost(Hero caster) {
        int baseCost = spellStatistic.getCost()[0];
        boolean shouldReduceBySpellLevel = false;

        if (spellStatistic.getSchool().equals(SpellSchool.ALL)) {
            for (SpellSchool school : SpellSchool.values()) {
                if (caster.getSpellbook().getSchoolMastery(school).getMasteryLevel() > 0) {
                    int costIndex = Math.min(caster.getSpellbook().getSchoolMastery(school).getMasteryLevel() - 1, spellStatistic.getCost().length - 1);
                    int newBaseCost = spellStatistic.getCost()[costIndex];
                    if (newBaseCost < baseCost) baseCost = newBaseCost;
                    shouldReduceBySpellLevel = true;
                }
            }
        } else  {
            if (caster.getSpellbook().getSchoolMastery(spellStatistic.getSchool()).getMasteryLevel() > 0) {
                int costIndex = Math.min(caster.getSpellbook().getSchoolMastery(spellStatistic.getSchool()).getMasteryLevel() - 1, spellStatistic.getCost().length - 1);
                baseCost = spellStatistic.getCost()[costIndex];
                shouldReduceBySpellLevel = true;
            }
        }

        return baseCost - (shouldReduceBySpellLevel ? spellStatistic.getLevel() : 0);
    }

    @Override
    public int getCost(Hero caster) {
        return this.getBaseCost(caster);
    }
}
