package pl.psi.creatures;

import pl.psi.enums.AttackTypeEnum;

public class ArmoredDamageApplierDecorator extends DamageApplier {
    private final int level;
    private final DamageApplier decorated;

    public ArmoredDamageApplierDecorator(DamageApplier aDecorated, int aLevel) {
        decorated = aDecorated;
        level = aLevel;
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
    public void applyDamage(DamageValueObject aDamageValueObject, Creature aCreature) {
        int dmg = calculateDamage(aDamageValueObject);
        int hpToSubtract = decorated.calculateHpToSubtract(dmg, aCreature);
        int amountToSubtract = decorated.calculateAmountToSubtract(dmg, aCreature);
        decorated.dealDamageToCreature(hpToSubtract, amountToSubtract, aCreature);
    }

    private int calculateDamage(DamageValueObject aDamageValueObject) {
        int dmg = aDamageValueObject.getDamageAmount();
        if (aDamageValueObject.getAttackType().equals(AttackTypeEnum.MELEE) || aDamageValueObject.getAttackType().equals(AttackTypeEnum.RANGE)) {
            return (int) (dmg * getMultiplier());
        }
        return dmg;
    }
}
