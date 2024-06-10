package pl.psi.spells.calculator;

import pl.psi.Hero;
import pl.psi.creatures.Creature;
import pl.psi.creatures.DamageCalculatorIf;
import pl.psi.obstacles.ObstaclesWithHP;
import pl.psi.spells.object.SpellStatistic;
import pl.psi.spells.object.SpellStatisticIf;

import java.util.Random;

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
