package pl.psi.creatures;//  ******************************************************************

//
//  Copyright 2022 PSI Software AG. All rights reserved.
//  PSI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms
//
//  ******************************************************************

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Setter;
import pl.psi.Point;
import pl.psi.effects.object.CreatureEffect;
import pl.psi.effects.object.CreatureEffectFactory;
import pl.psi.effects.object.CreatureEffectStatistic;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.TurnQueue;

import com.google.common.collect.Range;

import lombok.Getter;
import pl.psi.enums.CreatureTypeEnum;

import static java.lang.Math.random;

import pl.psi.obstacles.ObstaclesWithHP;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
@Getter
public class Creature implements PropertyChangeListener {
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

    private final List<CreatureEffect> creatureEffects = new ArrayList<>();


    Creature() {
    }

    private Creature(final CreatureStatisticIf aStats, final DamageCalculatorIf aCalculator,
                     final int aAmount, CreatureTypeEnum aCreatureType, AttackTypeEnum aAttackType) {
        stats = aStats;
        amount = aAmount;
        currentHp = stats.getMaxHp();
        calculator = aCalculator;
        creatureType = aCreatureType;
        attackType = aAttackType;
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

    public void applyEffect(CreatureEffectStatistic effectStatistic) {
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

    public boolean hasEffect(CreatureEffectStatistic effectStatistic) {
        return creatureEffects.stream().anyMatch((effect) -> effect.getEffectStatistic().equals(effectStatistic));
    }

    public void attack(final Creature aDefender) {
        if (isAlive()) {
            final int damage = getCalculator().calculateDamage(this, aDefender);
            DamageValueObject damageObject = new DamageValueObject(damage, this.attackType, this.creatureType);
            aDefender.getDamageApplier().applyDamage(damageObject, aDefender);
            if (canCounterAttack(aDefender)) {
                counterAttack(aDefender);
            }
        }
    }

    public void attackObstacle(ObstaclesWithHP obstacleWithHP, Point aPoint) {
        final int damage = getCalculator().calculateDamageToObstacle(this,obstacleWithHP);
        obstacleWithHP.takeDamage(aPoint, damage);
    }



    public boolean isAlive() {
        return getAmount() > 0;
    }

    private void applyDamage(DamageValueObject aDamageValueObject) {
        getDamageApplier().applyDamage(aDamageValueObject, this);
    }

    public int getMaxHp() {
        return stats.getMaxHp();
    }

    protected void setCurrentHp(final int aCurrentHp) {
        currentHp = aCurrentHp;
    }

    private boolean canCounterAttack(final Creature aDefender) {
        return aDefender.getCounterAttackCounter() > 0 && aDefender.getCurrentHp() > 0;
    }

    private void counterAttack(final Creature aAttacker) {
        final int damage = aAttacker.getCalculator()
                .calculateDamage(aAttacker, this);
        DamageValueObject aDamageValueObject = new DamageValueObject(damage, this.attackType, this.creatureType);
        damageApplier.applyDamage(aDamageValueObject, this); //spytac czy lepiej uzywac getDamageApplier czy damageApplier
        aAttacker.counterAttackCounter--;
    }

    Range<Integer> getDamage() {
        return stats.getDamage();
    }

    int getAttack() {
        return stats.getAttack();
    }

    int getArmor() {
        return stats.getArmor();
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (TurnQueue.END_OF_TURN.equals(evt.getPropertyName())) {
            counterAttackCounter = 1;
        }
    }

    protected void restoreCurrentHpToMax() {
        currentHp = stats.getMaxHp();
    }

    protected void restoreCurrentHpToPartHP() {
        Random random = new Random();
        int healHP = random.nextInt(25)+1;
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
        return stats.getMoveRange();
    }

    public static class Builder {
        private int amount = 1;
        private DamageCalculatorIf calculator = new DefaultDamageCalculator(new Random());
        private CreatureStatisticIf statistic;
        private final CreatureTypeEnum creatureType = CreatureTypeEnum.GROUND;
        private AttackTypeEnum attackType = AttackTypeEnum.MELEE;

        public Builder statistic(final CreatureStatisticIf aStatistic) {
            statistic = aStatistic;
            return this;
        }

        public Builder amount(final int aAmount) {
            amount = aAmount;
            return this;
        }

        Builder calculator(final DamageCalculatorIf aCalc) {
            calculator = aCalc;
            return this;
        }

        public Builder attackType(final AttackTypeEnum aAttackType) {
            attackType = aAttackType;
            return this;
        }

        public Creature build() {
            return new Creature(statistic, calculator, amount, creatureType, attackType);
        }
    }

    @Override
    public String toString() {
        return getName() + System.lineSeparator() + getAmount();
    }


    //MachineFactoryMethods - FirstAidTent
    public void healHPCreature(Creature creature) {
        creature.restoreCurrentHpToPartHP();
    }

    public void chooseHealCreature(List<Creature> creatureList) {
        Creature smallHP = creatureList.get(0);
        for (Creature creature : creatureList) {
            if (creature.getCurrentHp()<smallHP.getCurrentHp()){
                smallHP=creature;
            }

        }
        healHPCreature(smallHP);

    }
}
