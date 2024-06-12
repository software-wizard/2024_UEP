package pl.psi.creatures;
import pl.psi.enums.AttackTypeEnum;

import java.util.Random;

public interface DamageCalculatorIf
{
    int calculateDamage(Creature aAttacker, Creature aDefender, AttackTypeEnum attackTypeEnum );

    Random getRand();
}
