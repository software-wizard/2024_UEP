package pl.psi.spells.calculator;

import pl.psi.Hero;
import pl.psi.creatures.Creature;
import pl.psi.spells.object.enums.SpellSchool;
import pl.psi.spells.object.interfaces.SpellStatisticIf;

public class EmpoweredSpellDamageCalculator extends BaseSpellDamageCalculator {

    public EmpoweredSpellDamageCalculator(final SpellStatisticIf spellStatistic) {
        super(spellStatistic);
    }

    @Override
    public int calculateDamage(Hero caster, Creature targetCreature) {
        return getBaseDmg(caster) + caster.getPrimarySkills().getSpellPower() * spellStatistic.getPowerMultiplier();
    }

}
