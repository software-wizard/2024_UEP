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
import pl.psi.effects.board.object.BoardEffect;
import pl.psi.effects.board.object.BoardEffectFactory;
import pl.psi.effects.generic.Effect;
import pl.psi.effects.generic.EffectStatistic;
import pl.psi.effects.generic.EffectTargetType;

import static pl.psi.obstacles.ObstaclesIF.maxHP;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class Board implements ObstacleObserver, PropertyChangeListener
{
    private static final int MAX_WIDTH = 14;
    private static final int MAX_HEIGHT = 9;
    private final BiMap< Point, Creature > map = HashBiMap.create();
    private final HashMap<Point, ObstaclesWithHP> obstaclesWithHPMap = new HashMap<>();
    private final HashMap<Point, Obstacle> regularObstaclesMap = new HashMap<>();
    private final HashMap<Point, Wall> wallHashMap = new HashMap<>();
    private final HashMap<Point, List<BoardEffect>> effects = new HashMap<>();

    private final int width;
    private final int height;

    public Board( final List< Creature > aCreatures1, final List< Creature > aCreatures2 )
    {
        this.width = MAX_WIDTH;
        this.height = MAX_HEIGHT;

        addCreatures( aCreatures1, 0 );
        addCreatures( aCreatures2, MAX_WIDTH);
        addWall();
        addRandomObstacles();
    }

    private void addWall(){
        for (int i = 0;i <MAX_HEIGHT+1;i++){
            Wall wall = new Wall();
            wall.addObserver(this);
            wallHashMap.put(new Point(7,i),wall);
        }
    }
    boolean isWall(Point aPoint){
        return wallHashMap.containsKey(aPoint);
    }

    public void applyEffect(Point p, EffectStatistic effectStatistic) {
        if (!effectStatistic.getTargetType().equals(EffectTargetType.BOARD))
            throw new IllegalArgumentException("only board effects can be applied to the board");

        List<BoardEffect> effectsAtPoint = effects.get(p);

        if (effectsAtPoint != null) {
            for (BoardEffect effect : effectsAtPoint) {
                if (effect.getEffectStatistic().equals(effectStatistic)) {
                    if (effect.getEffectStatistic().isStackable()) {
                        effect.setAmount(effect.getAmount() + 1);
                    }

                    return;
                }
            }
        }

        BoardEffect effect = BoardEffectFactory.fromStatistic(effectStatistic);
        if (effect == null) return;

        effect.addObserver(this);

        if (effectsAtPoint != null) {
            effectsAtPoint.add(effect);
        } else {
            effects.put(p, List.of(effect));
        }
    }

    public boolean hasEffect(Point p, EffectStatistic effectStatistic) {
        if (effects.get(p) == null) return false;
        return effects.get(p).stream().anyMatch((effect) -> effect.getEffectStatistic().equals(effectStatistic));
    }

    private void addRandomObstacles() {
        Random random = new Random();


        while (regularObstaclesMap.size() < 8) {
            int x = random.nextInt(MAX_WIDTH);
            int y = random.nextInt(MAX_HEIGHT);
            Point point = new Point(x, y);

            if (!regularObstaclesMap.containsKey(point) &&
                    !obstaclesWithHPMap.containsKey(point) &&
                    !wallHashMap.containsKey(point)) {
                regularObstaclesMap.put(point, new Obstacle());
            }
        }

        while (obstaclesWithHPMap.size() < 2) {
            int x = random.nextInt(MAX_WIDTH);
            int y = random.nextInt(MAX_HEIGHT);
            Point point = new Point(x, y);

            if (!obstaclesWithHPMap.containsKey(point) &&
                    !regularObstaclesMap.containsKey(point) &&
                    !wallHashMap.containsKey(point)) {
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
            return false;
        }
        final Point oldPosition = getPosition( aCreature );
        if (aPoint.distance( oldPosition.getX(), oldPosition.getY() ) >= aCreature.getMoveRange()) return false;

        List<BoardEffect> effectsAtOriginPoint = effects.get(oldPosition);
        List<BoardEffect> effectsAtTargetPoint = effects.get(aPoint);

        for (BoardEffect effect : effectsAtOriginPoint) {
            if (!effect.canExit(aCreature)) return false;
        }

        for (BoardEffect effect : effectsAtTargetPoint) {
            if (!effect.canEnter(aCreature)) return false;
        }

        return true;
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


    @Override
    public void update(ObstaclesObservable o, Point point) {
        if (o instanceof ObstaclesWithHP && point != null) {
            removeFromTheMapObstacleWithHP(point);
        }else if (o instanceof Wall && point != null) {
            removeFromTheMapWall(point);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (TurnQueue.END_OF_TURN.equals(evt.getPropertyName())) {
            effects.forEach((point, effectList) -> {
                List<BoardEffect> temp = new ArrayList<>(effectList);
                temp.forEach(BoardEffect::turnPassed);
            });
        }
        else if (Effect.EFFECT_ENDED.equals(evt.getPropertyName())) {
            BoardEffect effect = (BoardEffect) evt.getSource();

            effects.forEach((point, effectList) -> {
                effectList.removeIf(e -> e == effect);
            });
        }
    }
}
