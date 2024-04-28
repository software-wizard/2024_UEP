package pl.psi.creatures;

import pl.psi.enums.AttackTypeEnum;

public class ArmoredDamageApplier extends DamageApplier {
    private final int level;
    private final DamageApplier decorated;

    public ArmoredDamageApplier(DamageApplier aDecorated, int aLevel) {
        decorated = aDecorated;
        if (aLevel < 1) {
            level = 1;
        } else level = Math.min(aLevel, 3);
    }

    private double getMultiplier() {
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
    public void applyDamage(DamageValueObject aDamageValueObject) {
        int dmg = calculateDamage(aDamageValueObject);
        int hpToSubtract = decorated.calculateHpToSubtract(dmg);
        int amountToSubtract = decorated.calculateAmountToSubtract(dmg);
        decorated.dealDamageToCreature(hpToSubtract, amountToSubtract);
    }

    private int calculateDamage(DamageValueObject aDamageValueObject) {
        int dmg = aDamageValueObject.getDamageAmount();
        if (aDamageValueObject.getAttackType().equals(AttackTypeEnum.MELEE) || aDamageValueObject.getAttackType().equals(AttackTypeEnum.RANGE)) {
            return (int) (dmg * getMultiplier());
        }
        return dmg;
    }

    @Override
    public Creature getCreature() {
        return decorated.getCreature();
    }
}
