package pl.psi;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import pl.psi.creatures.Creature;
import pl.psi.obstacles.Obstacle;
import pl.psi.obstacles.ObstaclesWithHP;
import pl.psi.obstacles.ObstaclesWithHPObservable;
import pl.psi.obstacles.ObstacleObserver;

import static pl.psi.obstacles.ObstaclesIF.MAX_HEIGHT;
import static pl.psi.obstacles.ObstaclesIF.maxHP;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class Board implements ObstacleObserver
{
    private static final int MAX_WITDH = 14;
    private final BiMap< Point, Creature > map = HashBiMap.create();
    private  final HashMap<Point, ObstaclesWithHP> obstaclesWithHPMap = new HashMap<>();
    private  final HashMap<Point, Obstacle> regularObstaclesMap = new HashMap<>();

    public Board( final List< Creature > aCreatures1, final List< Creature > aCreatures2 )
    {
        addCreatures( aCreatures1, 0 );
        addCreatures( aCreatures2, MAX_WITDH );
        addRandomObstacles();
    }

    void addRandomObstacles() {
        Random random = new Random();


        while (regularObstaclesMap.size() < 8) {
            int x = random.nextInt(MAX_WITDH);
            int y = random.nextInt(MAX_HEIGHT);
            Point point = new Point(x, y);

            if (!regularObstaclesMap.containsKey(point) && !obstaclesWithHPMap.containsKey(point)) {
                regularObstaclesMap.put(point, new Obstacle());
            }
        }

        while (obstaclesWithHPMap.size() < 2) {
            int x = random.nextInt(MAX_WITDH);
            int y = random.nextInt(MAX_HEIGHT);
            Point point = new Point(x, y);

            if (!obstaclesWithHPMap.containsKey(point) &&
                    !regularObstaclesMap.containsKey(point) &&
                    x != 0 &&
                    y != 1) {
                ObstaclesWithHP obstacleWithHP = new ObstaclesWithHP(maxHP);
                obstacleWithHP.addObserver(this);
                obstaclesWithHPMap.put(point, obstacleWithHP);

            }
        }
    }

    private void addCreatures( final List< Creature > aCreatures, final int aXPosition )
    {
        for( int i = 0; i < aCreatures.size(); i++ )
        {
            map.put( new Point( aXPosition, i * 2 + 1 ), aCreatures.get( i ) );
        }
    }

    Optional< Creature > getCreature( final Point aPoint )
    {
        return Optional.ofNullable( map.get( aPoint ) );
    }

    void move( final Creature aCreature, final Point aPoint )
    {
        if( canMove( aCreature, aPoint ) )
        {
            map.inverse()
                .remove( aCreature );
            map.put( aPoint, aCreature );
        }
    }

    boolean canMove( final Creature aCreature, final Point aPoint )
    {
        if(regularObstaclesMap.containsKey(aPoint) ||
                obstaclesWithHPMap.containsKey(aPoint) ||
                map.containsKey(aPoint)){
            return  false;
        }
        final Point oldPosition = getPosition( aCreature );
        return aPoint.distance( oldPosition.getX(), oldPosition.getY() ) < aCreature.getMoveRange();
    }

    Point getPosition( Creature aCreature )
    {
        return map.inverse()
            .get( aCreature );
    }

    boolean isWithinBounds(final Point p) {
        return map.containsKey(p);
    }

    public boolean isObstacleWithHP(Point aPoint) {
        return obstaclesWithHPMap.containsKey(aPoint);

    }
    public boolean isObstacle(Point aPoint) {
        return regularObstaclesMap.containsKey(aPoint);

    }

    public void removeFromTheMapObstacleWithHP(Point aPoint) {
        if (isObstacleWithHP(aPoint)){
            obstaclesWithHPMap.remove(aPoint);
        }
    }
    public Optional<ObstaclesWithHP> getObstacleWithHP(Point point) {
        return Optional.ofNullable(obstaclesWithHPMap.get(point));
    }

    @Override
    public void update(ObstaclesWithHPObservable o, Object arg) {
        if (o instanceof ObstaclesWithHP && arg instanceof Point) {
            removeFromTheMapObstacleWithHP((Point) arg);
        }
    }
}
