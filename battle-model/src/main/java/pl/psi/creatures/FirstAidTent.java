package pl.psi.creatures;


import lombok.Setter;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.CreatureTypeEnum;

import java.util.List;
import java.util.Random;

public class FirstAidTent extends Creature {
    private CreatureStatisticIf stats;
    @Setter
    private int amount;
    private int currentHp;
    private int counterAttackCounter = 1;
    @Setter
    private DamageCalculatorIf calculator;
    private CreatureTypeEnum creatureType;
    private AttackTypeEnum attackType;
    @Setter
    private DamageApplier damageApplier = new DamageApplier();

    private FirstAidTent(final CreatureStatisticIf aStats, final DamageCalculatorIf aCalculator,
                     final int aAmount, CreatureTypeEnum aCreatureType, AttackTypeEnum aAttackType) {
        super(aStats, aCalculator, aAmount, aCreatureType,aAttackType, new Morale(0));
    }

    public static class Builder {
        private int amount = 1;
        private DamageCalculatorIf calculator = new DefaultDamageCalculator(new Random());
        private CreatureStatisticIf statistic;
        private CreatureTypeEnum creatureType = CreatureTypeEnum.GROUND;
        private AttackTypeEnum attackType = AttackTypeEnum.MELEE;

        public FirstAidTent.Builder statistic(final CreatureStatisticIf aStatistic) {
            statistic = aStatistic;
            return this;
        }

        public FirstAidTent.Builder amount(final int aAmount) {
            amount = aAmount;
            return this;
        }

        public FirstAidTent.Builder attackType(final AttackTypeEnum aAttackType) {
            attackType = aAttackType;
            return this;
        }

        public FirstAidTent.Builder creatureType(final CreatureTypeEnum aCreatureType) {
            creatureType = aCreatureType;
            return this;
        }

        public FirstAidTent.Builder calculator(DamageCalculatorIf aCalculator) {
            calculator = aCalculator;
            return this;
        }

        public FirstAidTent build() {
            return new FirstAidTent(statistic, calculator, amount, creatureType, attackType);
        }

    }
    @Override
    public void chooseHealCreature(List<Creature> creatureList) {
        System.out.println("TEST");
        Creature smallHP = creatureList.get(0);
        for (Creature creature : creatureList) {
            if (creature.getCurrentHp()<smallHP.getCurrentHp()){
                smallHP=creature;
            }
        }
        healHPCreature(smallHP);
    }

    protected void restoreCurrentHpToPartHP() {
        System.out.println("TEST restoreCurrentHpToPartHP");
        Random random = new Random();
        int healHP = random.nextInt(25)+1;
        if (currentHp+healHP >= stats.getMaxHp()) {
            currentHp = stats.getMaxHp();
        } else {
            currentHp = currentHp+healHP;
        }
    }
    @Override
    public void healHPCreature(Creature creature) {
        creature.restoreCurrentHpToPartHP();
    }

}
