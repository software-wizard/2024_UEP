package pl.psi.spells.object.interfaces;

import pl.psi.Hero;
import pl.psi.creatures.Creature;

public interface CreatureConstraintSpellLambda {
    boolean op(Hero caster, Creature c);
}
