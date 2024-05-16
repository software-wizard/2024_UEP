package pl.psi.creatures;

import lombok.Getter;

@Getter
public class DamageApplier {
    private Creature creature;

    public DamageApplier(Creature creature) {
        this.creature = creature;
    }

    public DamageApplier() {
    }

    public void applyDamage(DamageValueObject aDamageValueObject) {
        int dmg = aDamageValueObject.getDamageAmount();

        int hpToSubtract = calculateHpToSubtract(dmg);
        int amountToSubtract = calculateAmountToSubtract(dmg);

        dealDamageToCreature(hpToSubtract, amountToSubtract);
    }

    protected int calculateAmountToSubtract(int dmg) {
        return Math.round(dmg / getCreature().getMaxHp());
    }

    protected int calculateHpToSubtract(int dmg) {
        return dmg % getCreature().getMaxHp();
    }

    //wymyslic lepsza nazwe
    protected void dealDamageToCreature(int hpToSubtract, int amountToSubtract) {
        int hp = getCreature().getCurrentHp() - hpToSubtract;
        if (hp <= 0) {
            getCreature().setCurrentHp(getCreature().getMaxHp() - hp);
            getCreature().setAmount(getCreature().getAmount() - 1);
        }
        else{
            getCreature().setCurrentHp(hp);
        }
        getCreature().setAmount(getCreature().getAmount() - amountToSubtract);
    }
}
