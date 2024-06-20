package pl.psi.spells.calculator;

import pl.psi.Hero;
import pl.psi.spells.object.enums.SpellSchool;
import pl.psi.spells.object.interfaces.SpellStatisticIf;

public abstract class BaseSpellDamageCalculator implements SpellDamageCalculatorIf{

    protected SpellStatisticIf spellStatistic;

    public BaseSpellDamageCalculator(final SpellStatisticIf spellStat) {
        this.spellStatistic = spellStat;
    }

    protected int getBaseDmg(Hero caster) {
        int baseDmg = spellStatistic.getBaseDmg()[0];

        if (spellStatistic.getSchool().equals(SpellSchool.ALL)) {
            for (SpellSchool school : SpellSchool.values()) {
                if (caster.getSpellbook().getSchoolMastery(school).getMasteryLevel() > 0) {
                    int dmgIndex = Math.min(caster.getSpellbook().getSchoolMastery(school).getMasteryLevel() - 1, spellStatistic.getCost().length - 1);
                    int newBaseDmg = spellStatistic.getBaseDmg()[dmgIndex];
                    if (newBaseDmg > baseDmg) baseDmg = newBaseDmg;
                }
            }
        } else  {
            if (caster.getSpellbook().getSchoolMastery(spellStatistic.getSchool()).getMasteryLevel() > 0) {
                int dmgIndex = Math.min(caster.getSpellbook().getSchoolMastery(spellStatistic.getSchool()).getMasteryLevel() - 1, spellStatistic.getCost().length - 1);
                baseDmg = spellStatistic.getBaseDmg()[dmgIndex];
            }
        }

        return baseDmg;
    }
}
