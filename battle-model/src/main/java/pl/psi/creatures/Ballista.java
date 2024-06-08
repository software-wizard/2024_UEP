package pl.psi.creatures;

import lombok.Setter;
import pl.psi.Hero;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.CreatureTypeEnum;

import java.util.Random;

public class Ballista extends Creature {
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
    private Ballista (final CreatureStatisticIf aStats, final DamageCalculatorIf aCalculator,
                         final int aAmount, CreatureTypeEnum aCreatureType, AttackTypeEnum aAttackType) {
        super(aStats,aCalculator,aAmount,aCreatureType,aAttackType);
    }
    public static class Builder {
        private int amount = 1;
        private DamageCalculatorIf calculator = new DefaultDamageCalculator(new Random());
        private CreatureStatisticIf statistic;
        private final CreatureTypeEnum creatureType = CreatureTypeEnum.GROUND;
        private AttackTypeEnum attackType = AttackTypeEnum.RANGE;

        public Ballista.Builder statistic(final CreatureStatisticIf aStatistic) {
            statistic = aStatistic;
            return this;
        }

        public Ballista.Builder amount(final int aAmount) {
            amount = aAmount;
            return this;
        }

        public Ballista build() {
            return new Ballista(statistic, calculator, amount, creatureType, attackType);
        }

    }
    public void attack(Creature creature, Hero hero) {

        // docelowo:
        // int heroAttack = hero.getAttack();
        int heroAttack = Ballista.getAttackHero();
        int damageMin =  2 * (heroAttack + 1);
        int damageMax =  3 * (heroAttack + 1);
        // make int that stores damage value from this formula: random range( 2;3) * (hero attack + 1)
        Random random = new Random();
        int demig = random.nextInt(2) + 2;

        float damage = 2 + random.nextFloat();

        damage *= heroAttack + 1;


        int damageI = (int) damage;
        if (damageI > 0) {
            // Apply damage to the creature
            DamageValueObject damageValueObject = new DamageValueObject(damageI, this.attackType, this.creatureType);
            this.damageApplier.applyDamage(damageValueObject, creature);
        }
    }

    // create get attack method
    public static int getAttackHero() {
        return 10;
    }
}
