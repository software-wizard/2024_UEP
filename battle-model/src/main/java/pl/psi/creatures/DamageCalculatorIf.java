package pl.psi.creatures;
import java.util.Random;

import pl.psi.obstacles.ObstaclesWithHP;
import pl.psi.obstacles.Wall;


public interface DamageCalculatorIf
{
    int calculateDamage( Creature aAttacker, Creature aDefender );
    int calculateDamageToObstacle(Creature attacker, ObstaclesWithHP obstacleWithHP);
    int calculateDamageToWall(Creature attacker, Wall wall);

    Random getRand();
}
