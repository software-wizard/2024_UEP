package pl.psi.creatures;

import pl.psi.Point;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.obstacles.Wall;


public class WallAttackStrategy implements AttackStrategy {
    @Override
    public void attack(Creature attacker, DefenderIf target, AttackTypeEnum aAttackType, Point aPoint) {
        if (target instanceof Wall) {
            Wall wall = (Wall) target;
            if (wall.getCurrentLevel() == 2 || wall.getCurrentLevel() == 3) {
                final int creatureDamage = attacker.getCalculator().calculateDamageToWall(attacker, wall);
                int creatureDamageAbs = Math.abs(creatureDamage);
                wall.takeDamageFromCreature(creatureDamageAbs, aPoint);
                System.out.println("Creature hit the wall with " + creatureDamageAbs + " damage");
            } else {
                System.out.println("Wall is too strong for creature to attack");
            }
        } else {
            throw new IllegalArgumentException("Target must be a Wall");
        }
    }
}

