package pl.psi.creatures;

import lombok.Getter;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.CreatureTypeEnum;

@Getter
public class DamageValueObject {

        private final int damageAmount;
        private final AttackTypeEnum attackType;
        private final CreatureTypeEnum creatureType;

        public DamageValueObject(int aDamageAmount, AttackTypeEnum aAttackType, CreatureTypeEnum aCreatureType) {
            damageAmount = aDamageAmount;
            attackType = aAttackType;
            creatureType = aCreatureType;
        }
}
