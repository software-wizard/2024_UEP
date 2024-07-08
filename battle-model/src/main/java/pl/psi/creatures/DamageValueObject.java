package pl.psi.creatures;

import lombok.Value;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.CreatureTypeEnum;

@Value
public class DamageValueObject {

        int damageAmount;
        AttackTypeEnum attackType;
        CreatureTypeEnum creatureType;
}
