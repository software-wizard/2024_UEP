package pl.psi.creatures;

import pl.psi.Point;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.obstacles.Wall;

import java.util.Random;

public class CatapultAttackStrategy implements AttackStrategy {
    @Override
    public void attack(Creature attacker, Object target, AttackTypeEnum AttackType, Point aPoint) {
        if (target instanceof Wall) {
            Wall wall = (Wall) target;
            if (RandomChance()) {
                Random random = new Random();
                int damageMultiplier = random.nextInt(101) + 50;
                final int catapultDamage = 10 * damageMultiplier;
                wall.takeDamageFromCatapult(catapultDamage, aPoint);
                System.out.println("Catapult hit the wall with " + catapultDamage + " damage");
            } else {
                final int zeroDmg = 0;
                wall.takeDamageFromCatapult(zeroDmg, aPoint);
                System.out.println("Catapult missed the wall");
            }
        }
    }
    //75% chance to hit
    public boolean RandomChance() {
        Random random = new Random();
        int randVal = random.nextInt(101);
        System.out.println("Value: " + randVal);
        return randVal < 75;

    }
}
