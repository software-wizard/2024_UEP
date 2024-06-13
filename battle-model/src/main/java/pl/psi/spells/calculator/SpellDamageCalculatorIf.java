package pl.psi.spells.calculator;

import pl.psi.Hero;
import pl.psi.creatures.Creature;

public interface SpellDamageCalculatorIf {
    int calculateDamage(Hero caster, Creature creatureTarget);
}
