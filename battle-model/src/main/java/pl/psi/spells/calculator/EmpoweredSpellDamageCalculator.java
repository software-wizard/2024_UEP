package pl.psi.spells.calculator;

import pl.psi.Hero;
import pl.psi.creatures.Creature;
import pl.psi.spells.object.interfaces.SpellStatisticIf;

public class EmpoweredSpellDamageCalculator implements SpellDamageCalculatorIf {
    private final SpellStatisticIf spellStatistic;

    public EmpoweredSpellDamageCalculator(final SpellStatisticIf spellStatistic) {
        this.spellStatistic = spellStatistic;
    }
    @Override
    public int calculateDamage(Hero caster, Creature targetCreature) {
        return spellStatistic.getBaseDmg() + caster.getPrimarySkills().getSpellPower() * spellStatistic.getPowerMultiplier();
    }

}
