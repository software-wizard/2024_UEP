package pl.psi.effects.effect;

import pl.psi.creatures.Creature;
import pl.psi.creatures.DamageApplier;
import pl.psi.creatures.DamageValueObject;
import pl.psi.effects.object.CreatureEffect;
import pl.psi.effects.object.CreatureEffectStatisticIf;
import pl.psi.enums.AttackTypeEnum;

public class AntiSpellDamageEffect extends CreatureEffect {
    public AntiSpellDamageEffect(CreatureEffectStatisticIf effectStatistic) {
        super(effectStatistic);
    }

    @Override
    public DamageApplier applyDamageApplierEffect(DamageApplier baseApplier, DamageApplier prevApplier) {
        return new DamageApplier() {
            @Override
            public void applyDamage(DamageValueObject aDamageValueObject, Creature aCreature) {
                if (aDamageValueObject.getAttackType().equals(AttackTypeEnum.SPELL)) return;

                prevApplier.applyDamage(aDamageValueObject, aCreature);
            }
        };
    }


}
