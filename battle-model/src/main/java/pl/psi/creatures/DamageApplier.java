package pl.psi.creatures;

import lombok.Getter;

@Getter
public class DamageApplier {

    public DamageApplier() {
    }

    public void applyDamage(DamageValueObject aDamageValueObject, Creature aCreature) {
        int dmg = aDamageValueObject.getDamageAmount();

        int hpToSubtract = calculateHpToSubtract(dmg, aCreature);
        int amountToSubtract = calculateAmountToSubtract(dmg, aCreature);

        dealDamageToCreature(hpToSubtract, amountToSubtract, aCreature);
    }

    protected int calculateAmountToSubtract(int dmg, Creature aCreature) {
        return Math.round(dmg / aCreature.getMaxHp());
    }

    protected int calculateHpToSubtract(int dmg, Creature aCreature) {
        return dmg % aCreature.getMaxHp();
    }

    //wymyslic lepsza nazwe
    protected void dealDamageToCreature(int hpToSubtract, int amountToSubtract, Creature aCreature) {
        int hp = aCreature.getCurrentHp() - hpToSubtract;
        if (hp <= 0) {
            aCreature.setCurrentHp(aCreature.getMaxHp() - hp);
            aCreature.setAmount(aCreature.getAmount() - 1);
        }
        else{
            aCreature.setCurrentHp(hp);
        }
        aCreature.setAmount(aCreature.getAmount() - amountToSubtract);
    }
}
