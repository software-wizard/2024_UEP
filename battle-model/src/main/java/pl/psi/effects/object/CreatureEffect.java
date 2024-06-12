package pl.psi.effects.object;

import lombok.Getter;
import lombok.Setter;
import pl.psi.creatures.CreatureStatisticIf;
import pl.psi.creatures.DamageApplier;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public abstract class CreatureEffect {
    public static String EFFECT_ENDED = "EFFECT_ENDED";
    public static int EFFECT_LENGTH_INDEFINITE = 0;

    @Getter
    private final CreatureEffectStatisticIf effectStatistic;

    @Getter
    @Setter
    private int amount;

    @Getter
    private int turnsPassed;

    private final PropertyChangeSupport propChangeSupport = new PropertyChangeSupport(this);

    protected CreatureEffect(CreatureEffectStatisticIf effectStatistic) {
        this.effectStatistic = effectStatistic;
        this.turnsPassed = 0;
    }

    public CreatureStatisticIf applyStatisticEffect(CreatureStatisticIf baseStats, CreatureStatisticIf prevStats) {
        return prevStats;
    }

    public DamageApplier applyDamageApplierEffect(DamageApplier baseApplier, DamageApplier prevApplier) {
        return prevApplier;
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
        this.propChangeSupport.addPropertyChangeListener(listener);
    }
}
