package pl.psi.creatures;

import pl.psi.Point;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.obstacles.Wall;

import java.util.Random;

public class WallAttackStrategy implements AttackStrategy {
    @Override
    public void attack(Creature attacker, Object target, AttackTypeEnum aAttackType, Point aPoint) {
        if (target instanceof Wall) {
            Wall wall = (Wall) target;
            if (wall.getCurrentLevel() == 2 || wall.getCurrentLevel() == 3) {
                final int creatureDamage = attacker.getCalculator().calculateDamageToWall(attacker, wall);
                wall.takeDamageFromCreature(creatureDamage, aPoint);
                System.out.println("Creature hit the wall with " + creatureDamage + " damage");
            } else {
                System.out.println("Wall is too strong for creature to attack");
            }
        } else {
            throw new IllegalArgumentException("Target must be a Wall");
        }
    }
    public boolean RandomChance() {
        Random random = new Random();
        int randVal = random.nextInt(101);
        System.out.println("Value: " + randVal);
        return randVal < 75;

    }
}

