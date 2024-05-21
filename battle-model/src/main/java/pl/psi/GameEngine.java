package pl.psi;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Optional;

import lombok.Getter;
import pl.psi.creatures.Creature;
import pl.psi.obstacles.ObstaclesWithHP;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class GameEngine {

    public static final String CREATURE_MOVED = "CREATURE_MOVED";

    private final TurnQueue turnQueue;
    private final Board board;
    private final PropertyChangeSupport observerSupport = new PropertyChangeSupport(this);

    private final Hero hero1;
    private final Hero hero2;

    private static GameEngine engine;

    public  GameEngine(final Hero aHero1, final Hero aHero2) {
        hero1 = aHero1;
        hero2 = aHero2;
        turnQueue = new TurnQueue(aHero1.getCreatures(), aHero2.getCreatures());
        board = new Board(aHero1.getCreatures(), aHero2.getCreatures());

        engine = this;
    }

    public static GameEngine getInstance() {
        return engine;
    }

    public void attack(final Point point) {
        Optional<Creature> optionalDefender = board.getCreature(point);
        if (optionalDefender.isPresent()) {
            Creature defender = optionalDefender.get();
            turnQueue.getCurrentCreature().attack(defender);
        } else if (board.isObstacleWithHP(point)) {
            Optional<ObstaclesWithHP> optionalObstacle = board.getObstacleWithHP(point);
            if (optionalObstacle.isPresent()) {
                ObstaclesWithHP obstacleWithHP = optionalObstacle.get();
                turnQueue.getCurrentCreature().attackObstacle(obstacleWithHP, point);
            }
        }
        pass();
    }
    public boolean isObstacle(final Point aPoint){
        return board.isObstacle(aPoint);
    }
    public boolean isObstacleWithHP(final Point aPoint){
        return board.isObstacleWithHP(aPoint);
    }
    public boolean isPointAnObject(Point aPoint) {

        if (board.getCreature(aPoint).isPresent()) {
            return true;
        } else if (board.isObstacle(aPoint)) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean canMove(final Point aPoint) {
        if(isPointAnObject(aPoint) )
        {
            return false;
        }
        return board.canMove(turnQueue.getCurrentCreature(), aPoint);
    }

    public void move(final Point aPoint) {
        board.move(turnQueue.getCurrentCreature(), aPoint);
        observerSupport.firePropertyChange(CREATURE_MOVED, null, aPoint);
    }

    public Optional<Creature> getCreature(final Point aPoint) {
        return board.getCreature(aPoint);
    }

    public void pass() {
        turnQueue.next();
    }

    public void addObserver(final PropertyChangeListener aObserver) {
        observerSupport.addPropertyChangeListener(aObserver);
        turnQueue.addObserver(aObserver);
    }

    public boolean canAttack(final Point point) {
        double distance = board.getPosition(turnQueue.getCurrentCreature())
                .distance(point);
        if (board.getCreature(point).isPresent()) {
            return distance < 2 && distance > 0;
        }

        if (board.isObstacleWithHP(point)) {
            return distance < 2 && distance > 0;
        }

        return false;
    }

    public Creature getCreatureToMove() {
        return this.turnQueue.getCurrentCreature();
    }

    public Point getCreaturePosition(Creature c) {
        return this.board.getPosition(c);
    }

    public Hero getHeroToMove() {
        if (hero1.getCreatures().contains(getCreatureToMove())) {
            return hero1;
        } else if (hero2.getCreatures().contains(getCreatureToMove())) {
            return hero2;
        } else {
            throw new IllegalStateException("neither of heroes contains current creature");
        }
    }

    public boolean isCurrentCreature(Point aPoint) {
        return Optional.of(turnQueue.getCurrentCreature()).equals(board.getCreature(aPoint));
    }

    public boolean isValidPoint(Point p) {
        return board.isWithinBounds(p);
    }
}
