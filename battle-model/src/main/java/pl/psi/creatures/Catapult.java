package pl.psi.creatures;

import lombok.Getter;
import lombok.Setter;
import pl.psi.Point;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.CreatureTypeEnum;
import pl.psi.obstacles.Wall;

import java.util.Random;

public class Catapult extends Creature implements  DefenderIf {
    private CreatureStatisticIf stats;
    @Getter
    @Setter
    private int level;
    private int amount;
    private int currentHp;
    private int counterAttackCounter = 1;
    @Setter
    private MachineCalculatorDecorator calculator;
    private CreatureTypeEnum creatureType;
    private AttackTypeEnum attackType;
    @Setter
    private DamageApplier damageApplier = new DamageApplier();

    private Catapult(final CreatureStatisticIf aStats, final DamageCalculatorIf aCalculator,
                     final int aAmount, CreatureTypeEnum aCreatureType, AttackTypeEnum aAttackType) {
        super(aStats, aCalculator, aAmount, aCreatureType, aAttackType, new Morale(0));
        this.level = 1;
    }

    public static class Builder {
        private int amount = 1;
        private DamageCalculatorIf calculator = new MachineCalculatorDecorator(new DefaultDamageCalculator(new Random()), 0);
        private CreatureStatisticIf statistic;
        private CreatureTypeEnum creatureType = CreatureTypeEnum.MACHINE;
        private AttackTypeEnum attackType = AttackTypeEnum.RANGE;
        private AttackStrategy strategy;
        private Morale morale;

        public Catapult.Builder statistic(final CreatureStatisticIf aStatistic) {
            statistic = aStatistic;
            return this;
        }

        public Catapult.Builder amount(final int aAmount) {
            amount = aAmount;
            return this;
        }

        public Catapult.Builder attackType(final AttackTypeEnum aAttackType) {
            attackType = aAttackType;
            return this;
        }

        public Catapult.Builder creatureType(final CreatureTypeEnum aCreatureType) {
            creatureType = aCreatureType;
            return this;
        }

        public Catapult.Builder calculator(DamageCalculatorIf aCalculator) {
            calculator = aCalculator;
            return this;
        }

        public Catapult.Builder morale(Morale aMorale) {
            morale = aMorale;
            return this;
        }

        public Catapult build() {
            return new Catapult(statistic, calculator, amount, creatureType, attackType);
        }
    }

    public void levelUpSpell() {
        this.level++;
    }

//    @Override
//    public void attack(DefenderIf target, AttackTypeEnum attackType, Point aPoint) {
//        attackStrategy.attack(this, target, attackType, aPoint);
//    }
    @Override
    public void attack(DefenderIf target, Point aPoint) {
        if (target.getType()==TargetTypeEnum.WALL) {
            Wall wall = (Wall) target;
            if (randomChance()) {
                Random random = new Random();
                int damageMultiplier = random.nextInt(101) + 50;
                final int catapultDamage = 10 * damageMultiplier;
                wall.takeDamageFromCatapult(catapultDamage, aPoint);
                System.out.println("Catapult hit the wall with " + catapultDamage + " damage");
            } else {
                final int zeroDmg = 0;
                wall.takeDamageFromCatapult(zeroDmg, aPoint);
                System.out.println("Catapult missed the wall");
            }
        } else new IllegalArgumentException("Catapult can attack only walls");

    }
    @Override
    public void attack(DefenderIf target) {
        attack(target, (Point) null);
        System.out.println("Catapult attack");
    }

    @Override
    public void attack(DefenderIf target, AttackTypeEnum attackType) {
        attack(target);
    }

    //75% chance to hit
    @Override
    public boolean randomChance() {
        Random random = new Random();
        int randVal = random.nextInt(101);
        System.out.println("Valueeeee: " + randVal);
        return randVal < 75;

    }
}


