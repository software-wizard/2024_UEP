package pl.psi.obstacles;

import org.junit.jupiter.api.Test;
import pl.psi.Point;
import pl.psi.creatures.Creature;
import pl.psi.creatures.MachineFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WallTest {
    private Wall wall;

    @Test
    public void takeDamageReducesHP() {
        wall = new Wall();
        int damage = 1000;
        wall.takeDamageFromCatapult(damage,new  Point(0, 0));


        assertEquals(500, wall.getHP());
    }

}
