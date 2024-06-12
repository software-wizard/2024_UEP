package pl.psi.spells;

import pl.psi.creatures.Creature;
import pl.psi.creatures.DamageCalculatorIf;
import pl.psi.enums.AttackTypeEnum;

import java.util.Random;

public class SimpleSpellDamageCalculator implements DamageCalculatorIf {
    final int damage;

    public SimpleSpellDamageCalculator(final int aDamage) {
        this.damage = aDamage;
    }

    @Override
    public int calculateDamage(Creature aAttacker, Creature aDefender, AttackTypeEnum attackTypeEnum) {
        return this.damage;
    }

    @Override
    public Random getRand() {
        return new Random();
    }
}
