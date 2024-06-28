package pl.psi.creatures;

import pl.psi.Point;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.obstacles.Wall;

import java.util.Random;

public class CatapultAttackStrategy implements AttackStrategy {
    @Override
    public void attack(Creature attacker, DefenderIf target, AttackTypeEnum AttackType, Point aPoint) {
            if (RandomChance()) {
                Random random = new Random();
                int damageMultiplier = random.nextInt(101) + 50;
                final int catapultDamage = 10 * damageMultiplier;
                target.applyDmg(catapultDamage, aPoint);
                System.out.println("Catapult hit the wall with " + catapultDamage + " damage");
            } else {
                final int zeroDmg = 0;
                target.applyDmg(zeroDmg, aPoint);
                System.out.println("Catapult missed the wall");
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
