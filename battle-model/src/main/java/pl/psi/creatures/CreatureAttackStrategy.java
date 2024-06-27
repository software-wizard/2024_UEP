package pl.psi.creatures;

import pl.psi.Point;
import pl.psi.enums.AttackTypeEnum;

public class CreatureAttackStrategy implements AttackStrategy {
    @Override
    public void attack(Creature attacker, Object target, AttackTypeEnum aAttackType, Point aPoint) {
        if (target instanceof Creature) {
            Creature aDefender = (Creature) target;
            // Your existing logic for attacking a creature
            if (isAlive(attacker) && !attacker.getMorale().shouldFreeze()) {

                final int damage = attacker.getCalculator().calculateDamage(attacker, aDefender, aAttackType);
                DamageValueObject damageObject = new DamageValueObject(damage, attacker.getAttackType(), attacker.getCreatureType());
                aDefender.getDamageApplier().applyDamage(damageObject, aDefender);
                if (attacker.canCounterAttack(aDefender) && aAttackType.equals(AttackTypeEnum.MELEE)) {
                    attacker.counterAttack(aDefender);
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