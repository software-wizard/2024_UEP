package pl.psi.obstacles;
import org.junit.jupiter.api.Test;

import pl.psi.Point;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObstaclesWithHPTest {

    private ObstaclesWithHP obstacle;
    private static final int INITIAL_HP = 10;


    @Test
    public void takeDamageReducesHP() {
        obstacle = new ObstaclesWithHP(INITIAL_HP);
        int damage = 5;
        obstacle.takeDamage(new Point(0, 0), damage);

        assertEquals(5, obstacle.getHP());
    }



}
