package pl.psi.obstacles;
import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;

import pl.psi.Board;
import pl.psi.Point;
import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;



import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObstaclesWithHPTest {

    private ObstaclesWithHP obstacle;
    Creature creature;
    Board board;


    @Test
    public void takeDamageReducesHP() {
        obstacle = new ObstaclesWithHP(10);
        int damage = 5;
        obstacle.takeDamage(new Point(0, 0), damage);

        assertEquals(5, obstacle.getHP());
    }

    @Test
    public void hpLevelDropsAfterCreatureAttack(){
        creature = new Creature.Builder().statistic(CreatureStats.builder()
                        .moveRange(5)
                        .damage(Range.closed(5,5))
                        .build())
                .build();
        final List<Creature> c1 = List.of(creature);
        final List<Creature> c2 = List.of();
        board = new Board(c1, c2);
        obstacle = new ObstaclesWithHP(10);
        creature.attack(obstacle);


        assertEquals(5,obstacle.getHP());


    }



}
