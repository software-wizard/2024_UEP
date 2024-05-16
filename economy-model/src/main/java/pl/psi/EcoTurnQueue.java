package pl.psi;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.Queue;

import lombok.Getter;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class EcoTurnQueue
{

    private final EconomyHero hero1;
    private final EconomyHero hero2;
    private final PropertyChangeSupport observerSupport;
    @Getter
    private EconomyHero currentHero;
    private final Queue< EconomyHero > queue;
    private int roundNumber = 1;

    public EcoTurnQueue( EconomyHero aHero1, EconomyHero aHero2, PropertyChangeSupport aObserverSupport )
    {
        hero1 = aHero1;
        hero2 = aHero2;
        queue = new LinkedList<>();
        observerSupport = aObserverSupport;
        initQueue();
        queue.forEach( observerSupport::addPropertyChangeListener );
        next();
    }

    private void initQueue()
    {
        queue.add( hero1 );
        queue.add( hero2 );
    }

    public void next()
    {
        EconomyHero oldHero = currentHero;
        if( queue.isEmpty() )
        {
            endOfTurn();
        }
        currentHero = queue.poll();
        observerSupport.firePropertyChange( EconomyEngine.ACTIVE_HERO_CHANGED, oldHero, currentHero );
    }

    private void endOfTurn()
    {
        roundNumber++;
        initQueue();
        observerSupport.firePropertyChange( EconomyEngine.TURN_END, roundNumber - 1, roundNumber );
    }

    void addObserver( String aEventName, PropertyChangeListener aObserver )
    {
        observerSupport.addPropertyChangeListener( aEventName, aObserver );
    }
}
