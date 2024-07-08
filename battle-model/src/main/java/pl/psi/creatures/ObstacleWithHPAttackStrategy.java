package pl.psi.creatures;

import pl.psi.Point;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.obstacles.ObstaclesWithHP;

public class ObstacleWithHPAttackStrategy implements AttackStrategy{
    @Override
    public void attack(Creature attacker, DefenderIf target, AttackTypeEnum AttackType, Point aPoint) {
        if (target instanceof ObstaclesWithHP) {
            ObstaclesWithHP obstacleWithHP = (ObstaclesWithHP) target;
            final int damageToObstacle = attacker.getCalculator().calculateDamageToObstacle(attacker, obstacleWithHP);
            obstacleWithHP.takeDamage(aPoint, damageToObstacle);
            System.out.println("Obstacle received: " + damageToObstacle + " damage");
        }else {
            throw new IllegalArgumentException("Target must be an ObstacleWithHP");
        }

    }

}