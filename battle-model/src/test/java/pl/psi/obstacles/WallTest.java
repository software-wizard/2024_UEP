package pl.psi.obstacles;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import pl.psi.Board;
import pl.psi.Point;
import pl.psi.creatures.*;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WallTest {
    private Wall wall;
    Creature creature;
    Board board;
    Point point = new Point(0,0);

    @Test
    public void takeDamageReducesHP() {
        wall = new Wall();
        int damage = 1000;

        wall.takeDamageFromCatapult(damage,point);
        assertEquals(500, wall.getCurrentHP());
    }
    @Test
    public void creatureCanAttackWallAtLVL2And3(){
        wall = new Wall();
        creature = new Creature.Builder().statistic(CreatureStats.builder()
                        .moveRange(5)
                        .damage(Range.closed(5,5))
                        .build())
                .build();
        final List<Creature> c1 = List.of(creature);
        final List<Creature> c2 = List.of();
        board = new Board(c1, c2);

        wall.setCurrentLevel(2);
        creature.attackWall(wall,point);
        assertThat(wall.getCurrentHP() <1000);


        wall.setCurrentLevel(3);
        creature.attackWall(wall,point);
        assertThat(wall.getCurrentHP() <500);
    }

    @Test
    public void catapultCanAttackWallAtLVL1(){
        wall = new Wall();
        MachineFactory machineFactory = new MachineFactory();
        Creature catapult = machineFactory.create("Catapult");
        catapult.attackWall(wall,point);

        assertEquals(1000, wall.getCurrentHP());

    }


    @Test
    public void wallLevelChangesAfterAttacks(){
        wall = new Wall();

        MachineFactory machineFactory = new MachineFactory();
        Creature catapult = machineFactory.create("Catapult");

        catapult.attackWall(wall,point);

        assertEquals(1000,wall.getCurrentHP());
        assertThat(wall.getCurrentLevel() == 1);

        catapult.attackWall(wall,point);
        catapult.attackWall(wall,point);

        assertEquals(1000,wall.getCurrentHP());
        assertThat(wall.getCurrentLevel() == 2);

        catapult.attackWall(wall,point);
        catapult.attackWall(wall,point);
        assertEquals(500,wall.getCurrentHP());
        assertThat(wall.getCurrentLevel() == 3);

    }

    @Test
    public void creatureCannotAttackWallAtLVL1(){
        wall = new Wall();

        creature = new Creature.Builder().statistic(CreatureStats.builder()
                        .moveRange(5)
                        .damage(Range.closed(5,5))
                        .build())
                .build();
        final List<Creature> c1 = List.of(creature);
        final List<Creature> c2 = List.of();
        board = new Board(c1, c2);
        creature.attackWall(wall,point);

        assertEquals(1500, wall.getCurrentHP());

    }

}
