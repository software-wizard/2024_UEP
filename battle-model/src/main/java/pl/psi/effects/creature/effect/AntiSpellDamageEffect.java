package pl.psi.effects.creature.effect;

import pl.psi.creatures.Creature;
import pl.psi.creatures.DamageApplier;
import pl.psi.creatures.DamageValueObject;
import pl.psi.effects.creature.object.CreatureEffect;
import pl.psi.effects.generic.EffectStatisticIf;
import pl.psi.enums.AttackTypeEnum;

public class AntiSpellDamageEffect extends CreatureEffect {
    public AntiSpellDamageEffect(EffectStatisticIf effectStatistic) {
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
