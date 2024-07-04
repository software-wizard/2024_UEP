package pl.psi.creatures;

import lombok.Getter;
import lombok.Setter;
import pl.psi.Hero;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.CreatureTypeEnum;

import java.util.Random;

public class Ballista extends Creature {
    private CreatureStatisticIf stats;
    @Setter
    private int amount;
    @Getter
    @Setter
    private int level;
    private int currentHp;
    private int counterAttackCounter = 1;
    @Setter
    private MachineCalculatorDecorator calculator;
    private CreatureTypeEnum creatureType;
    private AttackTypeEnum attackType;
    @Setter
    private DamageApplier damageApplier = new DamageApplier();

    protected Ballista(final CreatureStatisticIf aStats, final MachineCalculatorDecorator aCalculator,
                       final int aAmount, CreatureTypeEnum aCreatureType, AttackTypeEnum aAttackType) {
        super(aStats, aCalculator, aAmount, aCreatureType, aAttackType, new Morale(0));
        this.level = 1;
    }

    public static class Builder {
        private int amount = 1;
        private MachineCalculatorDecorator calculator = new MachineCalculatorDecorator(new DefaultDamageCalculator(new Random()), 0);
        private CreatureStatisticIf statistic;
        private CreatureTypeEnum creatureType = CreatureTypeEnum.MACHINE;
        private AttackTypeEnum attackType = AttackTypeEnum.RANGE;
        private Morale morale;

        public Ballista.Builder statistic(final CreatureStatisticIf aStatistic) {
            statistic = aStatistic;
            return this;
        }

        public Ballista.Builder amount(final int aAmount) {
            amount = aAmount;
            return this;
        }

        public Ballista.Builder attackType(final AttackTypeEnum aAttackType) {
            attackType = aAttackType;
            return this;
        }

        public Ballista.Builder creatureType(final CreatureTypeEnum aCreatureType) {
            creatureType = aCreatureType;
            return this;
        }

        public Ballista.Builder calculator(MachineCalculatorDecorator aMachineCalculatorDecorator) {
            calculator = aMachineCalculatorDecorator;
            return this;
        }

        public Ballista.Builder morale(Morale aMorale) {
            morale = aMorale;
            return this;
        }

        public Ballista build() {
            return new Ballista(statistic, calculator, amount, creatureType, attackType);
        }
    }

    public int damageMultiplier() {
        int hitPercentage;
        switch (this.level) {
            case 0:
                hitPercentage = 50;
                break;
            case 1:
                hitPercentage = 60;
                break;
            case 2:
                hitPercentage = 70;
                break;
            case 3:
                hitPercentage = 80;
                break;
            default:
                hitPercentage = 100;
        }
        return hitPercentage;
    }
    @Override
    public void attack(Creature creature) {
        ballistaDamageCalculator(creature);
    }


//    public void ballistaDamageCalculator(Creature creature) {
//        Random random = new Random();
//        int randomNumber = random.nextInt(101);
//        if (randomNumber < randomHitPercentage()) {
//            int heroAttack = 10;
//            float damage = 2 + random.nextFloat();
//            damage *= heroAttack + 1;
//            int damageI = (int) damage;
//            if (damageI > 0) {
//                DamageValueObject damageValueObject = new DamageValueObject(damageI, this.attackType, this.creatureType);
//                this.damageApplier.applyDamage(damageValueObject, creature);
//            }
//        }
//    }

    //dmg ballisty okolo 100. Czy nie za malo?
    public void ballistaDamageCalculator(Creature creature) {
        int randomNumber = 2 + new Random().nextInt(2);
        float percentageHit  = 1 + (float) damageMultiplier()/100;
        int damage = (int) ((randomNumber * getHeroAttack()) * percentageHit);
        DamageValueObject damageValueObject = new DamageValueObject(damage, this.attackType, this.creatureType);
        this.damageApplier.applyDamage(damageValueObject, creature);
        System.out.println("Ballista attacked creature with " + damage + " damage");

    }



    public void levelUpSpell() {
        this.level++;
    }

    public int getHeroAttack() {
        return 20;
    }
}
