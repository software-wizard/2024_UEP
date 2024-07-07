package pl.psi.creatures;//  ******************************************************************

//
//  Copyright 2022 PSI Software AG. All rights reserved.
//  PSI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms
//
//  ******************************************************************

import com.google.common.collect.Range;
import lombok.Getter;
import lombok.Setter;
import pl.psi.Point;
import pl.psi.TurnQueue;
import pl.psi.effects.creature.object.CreatureEffect;
import pl.psi.effects.creature.object.CreatureEffectFactory;
import pl.psi.effects.generic.EffectStatistic;
import pl.psi.effects.generic.EffectTargetType;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.CreatureTypeEnum;
import pl.psi.obstacles.ObstaclesWithHP;
import pl.psi.obstacles.Wall;
import pl.psi.Hero;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * TODO: Describe this class (The first line - untorigin/WarMachines03il the first dot - will interpret as the brief description).
 */
@Getter
public class Creature implements PropertyChangeListener, DefenderIf {
    private CreatureStatisticIf stats;
    @Setter
    private int amount;
    private int currentHp;
    private int counterAttackCounter = 1;
    private Morale morale;
    @Setter
    private DamageCalculatorIf calculator;
    private CreatureTypeEnum creatureType;
    private AttackTypeEnum attackType;
    private TargetTypeEnum targetType = TargetTypeEnum.CREATURE;
    @Setter
    private DamageApplier damageApplier = new DamageApplier();

    protected AttackStrategy attackStrategy;
    private WallAttackStrategy wallAttackStrategy = new WallAttackStrategy();
    private CreatureAttackStrategy creatureAttackStrategy = new CreatureAttackStrategy();
    private final List<CreatureEffect> creatureEffects = new ArrayList<>();


    Creature() {
    }

    protected Creature(final CreatureStatisticIf aStats, final DamageCalculatorIf aCalculator,
                     final int aAmount, CreatureTypeEnum aCreatureType, AttackTypeEnum aAttackType, Morale aMorale) {
        stats = aStats;
        amount = aAmount;
        currentHp = stats.getMaxHp();
        calculator = aCalculator;
        creatureType = aCreatureType;
        attackType = aAttackType;
        morale = aMorale;
        attackStrategy = new CreatureAttackStrategy();
    }

    public CreatureStatisticIf getStats() {
        CreatureStatisticIf currStats = stats;

        for (CreatureEffect effect : creatureEffects) {
            currStats = effect.applyStatisticEffect(stats, currStats);
        }

        return currStats;
    }

    public DamageApplier getDamageApplier() {
        DamageApplier currApplier = damageApplier;

        for (CreatureEffect effect : creatureEffects) {
            currApplier = effect.applyDamageApplierEffect(damageApplier, currApplier);
        }

        return currApplier;
    }

    public void applyEffect(EffectStatistic effectStatistic) {
        if (!effectStatistic.getTargetType().equals(EffectTargetType.CREATURE))
            throw new IllegalArgumentException("only creature effects can be applied to the creature");

        for (CreatureEffect effect : creatureEffects) {
            if (effect.getEffectStatistic().equals(effectStatistic)) {
                if (effect.getEffectStatistic().isStackable()) {
                    effect.setAmount(effect.getAmount() + 1);
                }

                return;
            }
        }

        CreatureEffect effect = CreatureEffectFactory.fromStatistic(effectStatistic);
        if (effect == null) return;

        effect.addObserver(this);
        creatureEffects.add(effect);
    }

    public boolean hasEffect(EffectStatistic effectStatistic) {
        return creatureEffects.stream().anyMatch((effect) -> effect.getEffectStatistic().equals(effectStatistic));
    }

    public void attackObstacle(ObstaclesWithHP obstacleWithHP, Point aPoint) {
        final int damage = getCalculator().calculateDamageToObstacle(this,obstacleWithHP);
        obstacleWithHP.takeDamage(aPoint, damage);
    }

    public void attackWall(Wall wall,Point aPoint){

    }

//    public abstract void attack(DefenderIf target, Point aPoint);
    public boolean randomChance() {
        Random random = new Random();
        int randVal = random.nextInt(101);
        System.out.println("Value: " + randVal);
        return randVal < 75;

    }



    public boolean isAlive() {
        return getAmount() > 0;
    }

    public void applyDamage(DamageValueObject aDamageValueObject) {
        getDamageApplier().applyDamage(aDamageValueObject, this);
    }

    public int getMaxHp() {
        return stats.getMaxHp();
    }

    protected void setCurrentHp(final int aCurrentHp) {
        currentHp = aCurrentHp;
    }

    boolean canCounterAttack(final Creature aDefender) {
        return aDefender.getCounterAttackCounter() > 0 && aDefender.getCurrentHp() > 0;
    }

    void counterAttack(final Creature aAttacker) {
        final int damage = aAttacker.getCalculator()
                .calculateDamage(aAttacker, this, AttackTypeEnum.MELEE);
        DamageValueObject aDamageValueObject = new DamageValueObject(damage, this.attackType, this.creatureType);
        getDamageApplier().applyDamage(aDamageValueObject, this); //spytac czy lepiej uzywac getDamageApplier czy damageApplier.
        // odp: getdamageapplier bo efekty
        aAttacker.counterAttackCounter--;
    }

    Range<Integer> getDamage() {
        return getStats().getDamage();
    }

    int getAttack() {
        return getStats().getAttack();
    }

    int getArmor() {
        return getStats().getArmor();
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (TurnQueue.END_OF_TURN.equals(evt.getPropertyName())) {
            counterAttackCounter = 1;

            // copy list to avoid ConcurrentModificationException
            List<CreatureEffect> temp = new ArrayList<>(creatureEffects);
            temp.forEach(CreatureEffect::turnPassed);
        } else if (CreatureEffect.EFFECT_ENDED.equals(evt.getPropertyName())) {
            CreatureEffect effect = (CreatureEffect) evt.getSource();
            creatureEffects.remove(effect);
        }
    }

    protected void restoreCurrentHpToMax() {
        currentHp = stats.getMaxHp();
    }

    protected void restoreCurrentHpToPartHP() {
        Random random = new Random();
        int healHP = 20+ random.nextInt(25)+1;
        if (currentHp+healHP >= stats.getMaxHp()) {
            currentHp = stats.getMaxHp();
        } else {
            currentHp = currentHp+healHP;
        }
    }

    public String getName() {
        return stats.getName();
    }

    public int getMoveRange() {
        return getStats().getMoveRange();
    }

    public void levelUpSpell() {

    }


    public static class Builder {

        private int amount = 1;
        private DamageCalculatorIf calculator = new DefaultDamageCalculator(new Random());
        private CreatureStatisticIf statistic;
        private CreatureTypeEnum creatureType = CreatureTypeEnum.GROUND;
        private AttackTypeEnum attackType = AttackTypeEnum.MELEE;
        private Morale morale = new Morale(0);
        public Builder statistic(final CreatureStatisticIf aStatistic) {
            statistic = aStatistic;
            return this;
        }

        public Builder amount(final int aAmount) {
            amount = aAmount;
            return this;
        }

        public Builder calculator(final DamageCalculatorIf aCalc) {
            calculator = aCalc;
            return this;
        }

        public Builder attackType(final AttackTypeEnum aAttackType) {
            attackType = aAttackType;
            return this;
        }

        public Builder morale(final Morale aMorale) {
            morale = aMorale;
            return this;
        }

        public Builder creatureType(final CreatureTypeEnum aCreatureType) {
            creatureType = aCreatureType;
            return this;
        }

        public Creature build() {
            return new Creature(statistic, calculator, amount, creatureType, attackType, morale);
        }


    }
    @Override
    public String toString() {
        return getName() + System.lineSeparator() + getAmount();
    }


    public void healHPCreature(Creature creature) {
        creature.restoreCurrentHpToPartHP();
    }

    //MachineFactoryMethods - FirstAidTent
    //Implemented in FirstAidTent

    public void chooseHealCreature(List<Creature> creatureList) {

    }
//    public void setAttackStrategy(AttackStrategy attackStrategy) {
//        this.attackStrategy = attackStrategy;
//    }
    public void attack(DefenderIf target, AttackTypeEnum attackType, Point aPoint) {
        if (target.getType().equals(TargetTypeEnum.CREATURE)) {
            attackStrategy = creatureAttackStrategy;
        } else if (target.getType().equals(TargetTypeEnum.WALL)) {
            attackStrategy = wallAttackStrategy;
        } else throw new IllegalStateException("Attack strategy is not set");
        attackStrategy.attack(this, target, attackType, aPoint);
    }

    public void attack(final DefenderIf target) {
        attack(target, AttackTypeEnum.MELEE, null);
    }

    public void attack(DefenderIf target, Point aPoint) {
        attack(target, AttackTypeEnum.MELEE, aPoint);
    }

    public void attack(DefenderIf target, AttackTypeEnum attackType) {
        attack(target, attackType, null);
        System.out.println("creature attack");
    }

    public TargetTypeEnum getType() {
        return targetType;
    }

    public String getImagePath() {
        String basePath = "/creatures/" + this.getName() + ".png";
        return basePath;

    }



}
