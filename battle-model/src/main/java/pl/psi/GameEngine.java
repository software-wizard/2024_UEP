package pl.psi;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import pl.psi.creatures.Creature;
import pl.psi.obstacles.ObstaclesWithHP;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class GameEngine {

    public static final String CREATURE_MOVED = "CREATURE_MOVED";

    public static final String BATTLE_ENDED = "BATTLE_ENDED";

    private final TurnQueue turnQueue;
    private final Board board;
    private final PropertyChangeSupport observerSupport = new PropertyChangeSupport(this);

    private final Hero hero1;
    private final Hero hero2;


    public GameEngine(final Hero aHero1, final Hero aHero2) {
        hero1 = (Hero) EngineEntity.bindEngine(aHero1, this);
        hero2 = (Hero) EngineEntity.bindEngine(aHero2, this);
        turnQueue = new TurnQueue(aHero1.getCreatures(), aHero2.getCreatures());
        board = new Board(aHero1.getCreatures(), aHero2.getCreatures());
    }

    public void attack(final Point point) {
        Optional<Creature> optionalDefender = board.getCreature(point);
        if (optionalDefender.isPresent()) {
            Creature defender = optionalDefender.get();
            turnQueue.getCurrentCreature().attack(defender);
        } else if (board.isObstacleWithHP(point)) {
            Optional<ObstaclesWithHP> optionalObstacleWithHP = board.getObstacleWithHP(point);
            if (optionalObstacleWithHP.isPresent()) {
                ObstaclesWithHP obstacleWithHP = optionalObstacleWithHP.get();
                turnQueue.getCurrentCreature().attackObstacle(obstacleWithHP, point);
            }
        }
        pass();

        checkBattleEnd();
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

    public List<Creature> getAllCreatures() {
        return Stream.concat(hero1.getCreatures().stream(), hero2.getCreatures().stream()).collect(Collectors.toList());
    }

    public boolean isCurrentCreature(Point aPoint) {
        return Optional.of(turnQueue.getCurrentCreature()).equals(board.getCreature(aPoint));
    }

    public boolean isValidPoint(Point p) {
        return board.isWithinBounds(p);
    }

    public void endBattle(boolean attackerWon){
        observerSupport.firePropertyChange(BATTLE_ENDED, null, attackerWon);
    }


    public void checkBattleEnd() {
        boolean attackerWon = determineWinner();
        endBattle(attackerWon);
    }

    private Boolean determineWinner() {

        boolean attackerHasCreatures = hero1.getCreatures().stream()
                .anyMatch(creature -> creature.getCurrentHp() > 0);

        boolean defenderHasCreatures = hero2.getCreatures().stream()
                .anyMatch(creature -> creature.getCurrentHp() > 0);

        if (attackerHasCreatures && !defenderHasCreatures) {
            return true;
        } else if (!attackerHasCreatures && defenderHasCreatures) {
            return false;
        } else {
            return null;
        }
    }

    public boolean isWall(Point currentPoint) {
        return false;
    }
}
