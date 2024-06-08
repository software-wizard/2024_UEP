package pl.psi.spells.calculator;

import pl.psi.creatures.Creature;
import pl.psi.creatures.DamageCalculatorIf;
import pl.psi.obstacles.ObstaclesWithHP;
import pl.psi.obstacles.Wall;

import java.util.Random;

public class EmpoweredSpellDamageCalculator implements DamageCalculatorIf {

    final int power;
    final int multiplier;
    final int baseDamage;

    public EmpoweredSpellDamageCalculator(final int baseDmg, final int powerMultiplier, final int power) {
        this.baseDamage = baseDmg;
        this.power = power;
        this.multiplier = powerMultiplier;
    }
    @Override
    public int calculateDamage(Creature aAttacker, Creature aDefender) {
        return baseDamage + power * multiplier;
    }

    @Override
    public Random getRand() {
        return null;
    }

    @Override
    public int calculateDamageToObstacle(Creature attacker, ObstaclesWithHP obstacleWithHP) {
        return 0;
    }

    @Override
    public int calculateDamageToWall(Creature attacker, Wall wall) {
        return 0;
    }
}
