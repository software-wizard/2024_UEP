package pl.psi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;
import pl.psi.obstacles.ObstaclesIF;
import pl.psi.obstacles.ObstaclesWithHP;

class BoardTest
{

    private Board board;
    private Creature creature;

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
    void creatureCannotEnterObstacle() throws ObstacleException {

        for (int x = 0; x < ObstaclesIF.MAX_WITDH; x++) {
            for (int y = 0; y < ObstaclesIF.MAX_HEIGHT; y++) {
                Point point = new Point(x, y);
                if (x != 0 && x != 1) {
                    if (board.isObstacle(point) || board.isObstacleWithHP(point)) {
                        board.move(creature, point);
                        if (board.getCreature(point).isEmpty()){
                            throw new ObstacleException("Creature cannot move into : " + point + ", because it's an obstacle");
                        }
                    }
                }
            }
        }
    }

    @Test
    void obstacleWithHPRemove() {
        Optional<Point> obstacleWithHPPoint = findObstacleWithHP();
        assertThat(obstacleWithHPPoint).isPresent();

        Point point = obstacleWithHPPoint.get();
        ObstaclesWithHP obstacleWithHP = board.getObstacleWithHP(point).orElseThrow();

        obstacleWithHP.takeDamage(point, obstacleWithHP.getHP());

       assertThat(board.isObstacleWithHP(point)).isFalse();
    }

    private Optional<Point> findObstacleWithHP() {
        for (int x = 0; x < ObstaclesIF.MAX_WITDH; x++) {
            for (int y = 0; y < ObstaclesIF.MAX_HEIGHT; y++) {
                Point point = new Point(x, y);
                if (board.isObstacleWithHP(point)) {
                    return Optional.of(point);
                }
            }
        }
        return Optional.empty();
    }

    class ObstacleException extends Exception {
        public ObstacleException(String message) {
            super(message);
        }
    }

}