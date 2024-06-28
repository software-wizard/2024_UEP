package pl.psi.creatures;

import pl.psi.Point;
import pl.psi.enums.AttackTypeEnum;

public class CreatureAttackStrategy implements AttackStrategy {
    @Override
    public void attack(Creature attacker, DefenderIf target, AttackTypeEnum aAttackType, Point aPoint) {
            // Your existing logic for attacking a creature
            if (isAlive(attacker) && !attacker.getMorale().shouldFreeze()) {

                final int damage = attacker.getCalculator().calculateDamage(attacker, target, aAttackType);
                DamageValueObject damageObject = new DamageValueObject(damage, attacker.getAttackType(), attacker.getCreatureType());
                target.getDamageApplier().applyDamage(damageObject, target);
                if (attacker.canCounterAttack(target) && aAttackType.equals(AttackTypeEnum.MELEE)) {
                    attacker.counterAttack(target);
                }
            }
        } else {
            throw new IllegalArgumentException("Target must be a Creature");
        }

    }

    public boolean isAlive(Creature attacker){
        return attacker.getAmount() > 0;
    }
}