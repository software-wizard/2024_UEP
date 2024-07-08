package pl.psi;

import java.util.*;
import java.util.stream.Stream;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import pl.psi.creatures.Creature;
import pl.psi.obstacles.*;


/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class Board implements ObstacleObserver, BoardIf
{
    private static final int MAX_WITDH = 14;
    private static final int MAX_HEIGHT = 9;
    private static final int maxHP = 10;
    private final BiMap< Point, Creature > map = HashBiMap.create();
    private  final HashMap<Point, ObstaclesWithHP> obstaclesWithHPMap = new HashMap<>();
    private  final HashMap<Point, Obstacle> regularObstaclesMap = new HashMap<>();
    private final HashMap<Point, Wall> wallHashMap = new HashMap<>();
    private final HashMap<Point, Creature> creatureHashMap = new HashMap<>();

    private final int width;
    private final int height;

    public Board( final List< Creature > aCreatures1, final List< Creature > aCreatures2 )
    {
        this.width = MAX_WITDH;
        this.height = MAX_HEIGHT;

        addCreatures( aCreatures1, 0 );
        addCreatures( aCreatures2, MAX_WITDH );
        addWalls();
        addRandomObstacles();
    }

    private void addWalls(){
        for (int i = 0;i <MAX_HEIGHT+1;i++){
            Wall wall = new Wall();
            wall.addObserver(this);
            wallHashMap.put(new Point(7,i),wall);
        }
    }
    public void addWall(Point point){
        Wall wall = new Wall();
        wall.addObserver(this);
        wallHashMap.put(point,wall);
    }
    boolean isWall(Point aPoint){
        return wallHashMap.containsKey(aPoint);
    }

    private void addRandomObstacles() {
        Random random = new Random();


        while (regularObstaclesMap.size() < 8) {
            int x = random.nextInt(MAX_WITDH);
            int y = random.nextInt(MAX_HEIGHT);
            Point point = new Point(x, y);

            if (!regularObstaclesMap.containsKey(point) &&
                    !obstaclesWithHPMap.containsKey(point) &&
                    !wallHashMap.containsKey(point) &&
                    !map.containsKey(point)) {
                regularObstaclesMap.put(point, new Obstacle());
            }
        }

        while (obstaclesWithHPMap.size() < 2) {
            int x = random.nextInt(MAX_WITDH);
            int y = random.nextInt(MAX_HEIGHT);
            Point point = new Point(x, y);

            if (!obstaclesWithHPMap.containsKey(point) &&
                    !regularObstaclesMap.containsKey(point) &&
                    !wallHashMap.containsKey(point) &&
                    !map.containsKey(point)){
                ObstaclesWithHP obstacleWithHP = new ObstaclesWithHP(maxHP);
                obstacleWithHP.addObserver(this);
                obstaclesWithHPMap.put(point, obstacleWithHP);

            }
        }
    }
    void addObstacle(Point aPoint){
        Obstacle obstacle = new Obstacle();
        regularObstaclesMap.put(aPoint,obstacle);
    }

    void addObstacleWithHP(Point aPoint,ObstaclesWithHP obstacleWithHP){
        obstacleWithHP.addObserver(this);
        obstaclesWithHPMap.put(aPoint,obstacleWithHP);

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
                wallHashMap.containsKey(aPoint)  ||
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
        return p.getX() >= 0 && p.getX() <= this.width && p.getY() >= 0 && p.getY() <= this.height;
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
    public void removeFromTheMapWall(Point aPoint) {
        if (isWall(aPoint)){
            wallHashMap.remove(aPoint);
        }
    }
    public Optional<ObstaclesWithHP> getObstacleWithHP(Point point) {
        return Optional.ofNullable(obstaclesWithHPMap.get(point));
    }

    public  Optional<Wall> getWall(Point point){
        return Optional.ofNullable(wallHashMap.get(point));
    }
    public Optional<Obstacle> getObstacle(Point point) {
        return Optional.ofNullable(regularObstaclesMap.get(point));
    }


    @Override
    public void update(ObstaclesObservable o, Point point) {
        if (o instanceof ObstaclesWithHP && point != null) {
            removeFromTheMapObstacleWithHP(point);
        }else if (o instanceof Wall && point != null) {
            removeFromTheMapWall(point);
        }
    }

    @Override
    public boolean isValidPoint(Point p) {
        return isWithinBounds(p);
    }

    @Override
    public Optional<Location> getCreatureLocation(Creature c) {
        if (getPosition(c) != null)
            return Optional.of(new Location(getPosition(c), this));
        else return Optional.empty();
    }

    @Override
    public Optional<Creature> getCreature(Location l) {
        return getCreature(l);
    }

    public Set<Creature> getAllCreatures() {
        return map.values();
    }
}
