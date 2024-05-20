package pl.psi.spells;

import pl.psi.creatures.Creature;
import pl.psi.creatures.DamageCalculatorIf;

import java.util.Random;

public class SimpleSpellDamageCalculator implements DamageCalculatorIf {
    final int damage;

    public SimpleSpellDamageCalculator(final int aDamage) {
        this.damage = aDamage;
    }

    @Override
    public int calculateDamage(Creature aAttacker, Creature aDefender) {
        return this.damage;
    }

    @Override
    public Random getRand() {
        return new Random();
    }
}
