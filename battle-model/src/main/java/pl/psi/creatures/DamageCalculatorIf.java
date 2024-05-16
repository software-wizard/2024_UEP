package pl.psi.creatures;
import java.util.Random;

public interface DamageCalculatorIf
{
    int calculateDamage( Creature aAttacker, Creature aDefender );

    Random getRand();
}
