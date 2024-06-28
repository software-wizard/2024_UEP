package pl.psi.creatures;

import lombok.Getter;
import pl.psi.enums.AttackTypeEnum;

import java.util.Random;

public class MachineCalculatorDecorator extends AbstractCalculateDamageStrategy {
    @Getter
    private final int level;
    private final AbstractCalculateDamageStrategy decorated;
    private final Random random;


    public MachineCalculatorDecorator(DamageCalculatorIf aDecorated, int aLevel) {
        this(aDecorated, aLevel, aDecorated.getRand());
    }

    public MachineCalculatorDecorator(DamageCalculatorIf aDecorated, int aLevel, Random aRandom) {
        super(aDecorated.getRand());
        decorated = (AbstractCalculateDamageStrategy) aDecorated;
        level = aLevel;
        random = aRandom;
    }

    @Override
    public int calculateDamage(final Creature aAttacker, final Creature aDefender, AttackTypeEnum attackType) {
        int dmg = decorated.calculateDamage(aAttacker, aDefender, attackType);
        if (shouldHit()) {
            return dmg;
        }
        return 0;
    }

    private double getChance() {
        switch (level) {
            case 1:
                return 0.6;
            case 2:
                return 0.7;
            case 3:
                return 0.8;
            default:
                return 0.5;
        }
    }

    private boolean shouldHit() {
        return random.nextDouble() <= getChance();
    }
}
