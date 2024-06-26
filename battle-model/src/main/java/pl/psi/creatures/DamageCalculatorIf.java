package pl.psi.creatures;

import pl.psi.enums.AttackTypeEnum;

import java.util.Random;

import pl.psi.obstacles.ObstaclesWithHP;
import pl.psi.obstacles.Wall;


public interface DamageCalculatorIf {
    int calculateDamage(Creature aAttacker, Creature aDefender, AttackTypeEnum attackTypeEnum);

    int calculateDamageToObstacle(Creature attacker, ObstaclesWithHP obstacleWithHP);

    int calculateDamageToWall(Creature attacker, Wall wall);

    Random getRand();
}
