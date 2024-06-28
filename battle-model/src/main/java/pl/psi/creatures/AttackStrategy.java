package pl.psi.creatures;

import pl.psi.Point;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.obstacles.Wall;

public interface AttackStrategy {
    void attack(Creature attacker, DefenderIf target, AttackTypeEnum AttackType, Point aPoint);

}
