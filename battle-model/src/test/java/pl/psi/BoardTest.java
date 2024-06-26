package pl.psi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;

import pl.psi.creatures.MachineFactory;
import pl.psi.obstacles.ObstaclesWithHP;
import pl.psi.obstacles.Wall;

class BoardTest
{

    private Board board;
    private Creature creature;
    private Wall wall;

    @BeforeEach
    void setUp() {
        creature = new Creature.Builder().statistic(CreatureStats.builder()
                        .moveRange(5)
                        .build())
                .build();
        final List<Creature> c1 = List.of(creature);
        final List<Creature> c2 = List.of();
        board = new Board(c1, c2);
    }

    @Test
    void unitsMoveProperly()
    {

        board.move( creature, new Point( 3, 3 ) );

        assertThat( board.getCreature( new Point( 3, 3 ) )
            .isPresent() ).isTrue();
    }

    @Test
    @Disabled // Bartek
    void creatureCannotEnterObstacle() throws ObstacleException {

        Point point = new Point(3,3);

        board.addObstacle(point);
        board.move(creature,point);

        if (board.getCreature(point).isEmpty()){
            throw new ObstacleException("Creature cannot move into : " + point + ", because it's an obstacle");
        }

    }

    @Test
    void obstacleWithHPRemovesFromMap() {

        Point point = new Point(3,3);
        ObstaclesWithHP obstacleWithHP = new ObstaclesWithHP(10);

        board.addObstacleWithHP(point,obstacleWithHP);

        board.getObstacleWithHP(point);

        obstacleWithHP.takeDamage(point,10);

        assertThat(board.isObstacleWithHP(point)).isFalse();
    }

    @Disabled
    @Test
    public void wallRemovesFromMap(){
        wall = new Wall();
        Point point = new Point(0,0);

        MachineFactory machineFactory = new MachineFactory();
        Creature catapult = machineFactory.create("Catapult");

        for(int i = 0; i <6; i++){
            catapult.attackWall(wall,point);
        }

        assertThat(board.isWall(point)).isFalse();

    }


    class ObstacleException extends Exception {
        public ObstacleException(String message) {
            super(message);
        }
    }

}