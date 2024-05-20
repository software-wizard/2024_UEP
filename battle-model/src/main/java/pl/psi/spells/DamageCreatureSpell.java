package pl.psi.spells;

import pl.psi.creatures.Creature;
import pl.psi.creatures.DamageCalculatorIf;

public class DamageCreatureSpell extends Spell {
    private final Spell decorated;
    private final DamageCalculatorIf calculator;
    private final Creature owner;
    private final Creature target;

    public DamageCreatureSpell(final DamageCalculatorIf aCalculator, final Creature aOwner, final Creature aTarget, final Spell aDecorated) {
        super();
        this.decorated = aDecorated;
        this.calculator = aCalculator;
        this.owner = aOwner;
        this.target = aTarget;
    }

    @Override
    public SpellStatisticIf getStats() {
        return decorated.getStats();
    }

    @Override
    public void cast() {
        if (owner.isAlive()) {
            final int dmg = calculator.calculateDamage(owner, target);
            target.applyDamage(dmg);
        }

        decorated.cast();
    }
}
