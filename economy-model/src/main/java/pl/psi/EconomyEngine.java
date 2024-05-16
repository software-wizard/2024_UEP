package pl.psi;

import lombok.Getter;
import pl.psi.objects.ResourcesField;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Optional;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class EconomyEngine {

    public static final String HERO_MOVED = "HERO_MOVED";
    public static final String TURN_END = "TURN_END";
    public static final String ACTIVE_HERO_CHANGED = "HERO_MOVED";
    public static final int BOARD_WEIGHT = 25;
    public static final int BOARD_HEIGHT = 15;
    private final EcoTurnQueue turnQueue;
    @Getter
    private final EcoMap board;
    private final PropertyChangeSupport observerSupport = new PropertyChangeSupport(this);
    @Getter
    private final EconomyHero hero1;
    @Getter
    private final EconomyHero hero2;

    public EconomyEngine(final EconomyHero aHero1, final EconomyHero aHero2) {
        hero1 = aHero1;
        turnQueue = new EcoTurnQueue(hero1, aHero2, observerSupport);
        hero2 = aHero2;
        board = new EcoMap(aHero1, hero2, observerSupport);
    }

    public boolean canMove(final Point aPoint) {
        return board.canMove(turnQueue.getCurrentHero(), aPoint);
    }

    public void move(final Point aPoint) {
        board.move(turnQueue.getCurrentHero(), aPoint);
        observerSupport.firePropertyChange(HERO_MOVED, null, aPoint);
    }

    public Optional<EconomyHero> getHero(final Point aPoint) {
        return board.getHero(aPoint);
    }

    public void pass() {
        turnQueue.next();
    }

    public void addObserver(final String aEventName, PropertyChangeListener aObserver) {
        observerSupport.addPropertyChangeListener(aEventName, aObserver);
        turnQueue.addObserver(aEventName, aObserver);
    }

    public boolean isCurrentHero(Point aPoint) {
        return Optional.of(turnQueue.getCurrentHero())
                .equals(board.getHero(aPoint));
    }

    public boolean isBattlePoint(Point aPoint) {
        return board.isBattlePoint(aPoint) && aPoint.distance(board.getPosition(turnQueue.getCurrentHero())) < 2;
    }

    public StartBattlePack getStartBattlePack(Point aCurrentPoint) {
        EconomyHero heroToAttack = board.getHero(aCurrentPoint).orElseThrow();
        return StartBattlePack.builder().attacker(turnQueue.getCurrentHero()).defender(heroToAttack).build();
    }

    public boolean isCastle(Point aPoint) {
        return board.isCastlePoint(aPoint);
    }

    public boolean isFieldPoint(Point aPoint){
        return board.isFieldPoint(aPoint);
    }


    public void collectField(ResourcesField field) {
        EconomyHero hero = turnQueue.getCurrentHero();
        field.apply(hero);

    }

    public ResourcesField getField(Point currentPoint) {
        return board.getField(currentPoint);

    }


    public EconomyHero getCurrentHero() {
        return turnQueue.getCurrentHero();
    }
}
