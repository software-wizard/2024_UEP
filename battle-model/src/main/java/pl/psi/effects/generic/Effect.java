package pl.psi.effects.generic;

import lombok.Getter;
import lombok.Setter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;

public abstract class Effect {
    public static String EFFECT_ENDED = "EFFECT_ENDED";
    public static int EFFECT_LENGTH_INDEFINITE = 0;

    @Getter
    protected final EffectStatisticIf effectStatistic;

    @Getter
    @Setter
    protected int amount;

    @Getter
    protected int turnsPassed;

    protected final PropertyChangeSupport propChangeSupport = new PropertyChangeSupport(this);

    public Effect(EffectStatisticIf effectStatistic) {
        this.effectStatistic = effectStatistic;
        this.turnsPassed = 0;
    }

    public void turnPassed() {
        this.turnsPassed++;
        if (this.effectStatistic.getLength() == EFFECT_LENGTH_INDEFINITE) return;

        if (this.turnsPassed >= effectStatistic.getLength()) {
            propChangeSupport.firePropertyChange(EFFECT_ENDED, false, true);
            this.effectEnded();
        }
    }

    private void effectEnded() {} // for overloads.

    public void addObserver(PropertyChangeListener listener) {
        // don't want multiple observers, since:
        // Add a PropertyChangeListener to the listener list. The listener is registered for all properties.
        // The same listener object may be added more than once, and will be called as many times as it is added.

        if (Arrays.stream(this.propChangeSupport.getPropertyChangeListeners()).anyMatch((l) -> l == listener)) return;
        this.propChangeSupport.addPropertyChangeListener(listener);
    }
}
