package pl.psi;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Optional;

import pl.psi.creatures.Creature;
import pl.psi.enums.CreatureTypeEnum;

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

    BattleAttacker battleAttacker;

    public  GameEngine(final Hero aHero1, final Hero aHero2) {
        hero1 = aHero1;
        hero2 = aHero2;
        turnQueue = new TurnQueue(aHero1.getCreatures(), aHero2.getCreatures());
        board = new Board(aHero1.getCreatures(), aHero2.getCreatures());
        battleAttacker = new BattleAttacker(board);
    }

    public void attack(final Point point) {
        battleAttacker.attack(point, this);
    }


    public boolean canMove(final Point aPoint) {
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
        if (getCreatureToMove().getCreatureType().equals(CreatureTypeEnum.MACHINE)) { //to czy do game attackera czy zostawic
            battleAttacker.machineAttack(this);
        }
    }

    public void addObserver(final PropertyChangeListener aObserver) {
        observerSupport.addPropertyChangeListener(aObserver);
        turnQueue.addObserver(aObserver);
    }

    public boolean canAttack(final Point point) {
        return battleAttacker.canAttack(point, turnQueue.getCurrentCreature());
    }

    public Creature getCreatureToMove() {
        return this.turnQueue.getCurrentCreature();
    }

    public Point getCreatureLocation(Creature aCreature) {
        return board.getPosition(aCreature);
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

    public List<Creature> getEnemyCreatures() {
        List<Creature> creatures;
        if (getHeroToMove().equals(hero1)) {
            creatures = hero2.getCreatures();
        }
        else {
            creatures = hero1.getCreatures();
        }
        return List.copyOf(creatures);
    }

    public BoardIf getBoard() {
        return board;
    }

    public boolean isObstacle(Point aCurrentPoint) {
        return false;
    }

    public boolean isObstacleWithHP(Point aCurrentPoint) {
        return false;
    }

    public boolean isWall(Point aCurrentPoint) {
        return false;
    }
}
