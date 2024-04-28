package pl.psi.creatures;

import com.google.common.collect.Range;
import pl.psi.skills.ArmoredDamageApplier;

import java.beans.PropertyChangeEvent;

//todo remove this class
public class ArmoredCreature extends Creature {
    private final Creature decorated;
    private final int level;
    private DamageApplier damageApplier;

    public ArmoredCreature(final Creature decorated, int aLevel) {
        this.decorated = decorated;
        if (aLevel < 1) {
            level = 1;
        } else level = Math.min(aLevel, 3);
        damageApplier = new ArmoredDamageApplier(decorated.getDamageApplier(), level);
    }

    public double getMultiplier() {
        switch (level) {
            case 1:
                return 0.95;
            case 2:
                return 0.9;
            case 3:
                return 0.85;
            default:
                return 1;
        }
    }

    @Override
    public CreatureStatisticIf getStats() {
        return decorated.getStats();
    }

    @Override
    public int getAmount() {
        return decorated.getAmount();
    }

    @Override
    public int getCounterAttackCounter() {
        return decorated.getCounterAttackCounter();
    }

    @Override
    public DamageCalculatorIf getCalculator() {
        return decorated.getCalculator();
    }

    @Override
    public void attack(final Creature aDefender) {
        decorated.attack(aDefender);
    }

    @Override
    public boolean isAlive() {
        return decorated.isAlive();
    }

    @Override
    public int getCurrentHp() {
        return decorated.getCurrentHp();
    }

    @Override
    Range<Integer> getDamage() {
        return decorated.getDamage();
    }

    @Override
    int getAttack() {
        return decorated.getAttack();
    }

    @Override
    int getArmor() {
        return decorated.getArmor();
    }

    @Override
    protected void restoreCurrentHpToMax() {
        decorated.restoreCurrentHpToMax();
    }

    @Override
    public int getMaxHp() {
        return decorated.getMaxHp();
    }

    @Override
    protected void setCurrentHp(int aCurrentHp)
    {
        decorated.setCurrentHp(aCurrentHp);
    }

    @Override
    public void setAmount(int amount) {
        decorated.setAmount(amount);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        decorated.propertyChange(evt);
    }

    @Override
    public String toString() {
        return decorated.toString();
    }

    @Override
    public DamageApplier getDamageApplier() {
        return damageApplier;
    }
}