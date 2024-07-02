package pl.psi;

import java.beans.PropertyChangeEvent;
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
import pl.psi.event.BattleEndedEvent;
import pl.psi.event.CreatureMovedEvent;
import pl.psi.event.GameEventType;
import pl.psi.obstacles.ObstaclesWithHP;
import pl.psi.spells.object.Spell;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class GameEngine implements PropertyChangeListener {
    private final TurnQueue turnQueue;

    @Getter
    private final Board board;
    private final PropertyChangeSupport observerSupport = new PropertyChangeSupport(this);

    private final Hero hero1;
    private final Hero hero2;


    public GameEngine(final Hero aHero1, final Hero aHero2) {
        this.hero1 = aHero1;
        this.hero2 = aHero2;

        turnQueue = new TurnQueue(aHero1.getCreatures(), aHero2.getCreatures());
        board = new Board(aHero1.getCreatures(), aHero2.getCreatures());

        turnQueue.addObserver(this);
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

    public boolean canCastSpell(final Spell spell, final Point p) {
        Location l = new Location(p, this.board);
        return spell.canCast(this.getHeroToMove(), l);
    }

    public void castSpell(final Spell spell, final Point p) {
        Location l = new Location(p, this.board);
        spell.cast(this.getHeroToMove(), l);


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
        } else return board.isObstacle(aPoint);
    }

    public boolean canMove(final Point aPoint) {
        if(isPointAnObject(aPoint) )
        {
            return false;
        }
        return board.canMove(turnQueue.getCurrentCreature(), aPoint);
    }

    public void move(final Point aPoint) {
        Point origin = getCreaturePosition(turnQueue.getCurrentCreature());
        board.move(turnQueue.getCurrentCreature(), aPoint);
        observerSupport.firePropertyChange(GameEventType.CREATURE_MOVED.toString(),
                null, new CreatureMovedEvent(turnQueue.getCurrentCreature(), origin.asLocation(board), aPoint.asLocation(board)));
    }

    public Optional<Creature> getCreature(final Point aPoint) {
        return board.getCreature(aPoint);
    }

    public void pass() {
        turnQueue.next();
    }

    public void addObserver(final PropertyChangeListener aObserver) {
        observerSupport.addPropertyChangeListener(aObserver);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(TurnQueue.END_OF_TURN) || evt.getPropertyName().equals(TurnQueue.NEXT_CREATURE)) {
            observerSupport.firePropertyChange(TurnQueue.END_OF_TURN, evt.getOldValue(), evt.getNewValue());
        }
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
        observerSupport.firePropertyChange(GameEventType.BATTLE_ENDED.toString(), null, new BattleEndedEvent(attackerWon ? hero1 : hero2));
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
