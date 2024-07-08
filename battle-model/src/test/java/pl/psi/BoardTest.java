package pl.psi;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


import java.util.List;

import com.google.common.collect.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import pl.psi.creatures.Catapult;
import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;

import pl.psi.creatures.MachineFactory;
import pl.psi.enums.AttackTypeEnum;
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
                        .damage(Range.closed(10,10))
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
    void obstacleWithHPRemovesFromMap() {

        Point point = new Point(3,3);
        ObstaclesWithHP obstacleWithHP = new ObstaclesWithHP(10);

        board.addObstacleWithHP(point,obstacleWithHP);
        creature.attack(obstacleWithHP,point);

        assertThat(board.isObstacleWithHP(point)).isFalse();
    }
    @Disabled
    @Test
    public void wallRemovesFromMapAfterCreatureAttack(){
        Wall wall = new Wall();
        Point point = new Point(0,0);
        board.addWall(point);

        wall.setCurrentLevel(2);
            while (wall.getCurrentHP() >0 && wall.getCurrentLevel() >0){
                creature.attack(wall,point);
            }

        assertEquals(false,board.getWall(point).isPresent());

    }
    @Disabled
    @Test
    public void wallRemovesFromMapAfterCatapultAttack(){
        Wall wall = new Wall();
        Point point = new Point(0,0);
        board.addWall(point);
        MachineFactory machineFactory = new MachineFactory();
        Creature catapult = machineFactory.create("Catapult");

        while (wall.getCurrentHP() >0 && wall.getCurrentLevel() >0){
            catapult.attack(wall,point);
        }

        assertThat(board.isWall(point)).isFalse();

    }




}