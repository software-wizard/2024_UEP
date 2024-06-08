package pl.psi.creatures;

import lombok.Setter;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.CreatureTypeEnum;

import java.util.Random;

public class Catapult extends Creature {
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
    private Catapult (final CreatureStatisticIf aStats, final DamageCalculatorIf aCalculator,
                      final int aAmount, CreatureTypeEnum aCreatureType, AttackTypeEnum aAttackType) {
        super(aStats,aCalculator,aAmount,aCreatureType,aAttackType);
    }
    // usun to
    public static class Builder {
        private int amount = 1;
        private DamageCalculatorIf calculator = new DefaultDamageCalculator(new Random());
        private CreatureStatisticIf statistic;
        private final CreatureTypeEnum creatureType = CreatureTypeEnum.GROUND;
        private AttackTypeEnum attackType = AttackTypeEnum.MELEE;

        public Catapult.Builder statistic(final CreatureStatisticIf aStatistic) {
            statistic = aStatistic;
            return this;
        }

        public Catapult.Builder amount(final int aAmount) {
            amount = aAmount;
            return this;
        }

        public Catapult build() {
            return new Catapult(statistic, calculator, amount, creatureType, attackType);
        }

    }
}
