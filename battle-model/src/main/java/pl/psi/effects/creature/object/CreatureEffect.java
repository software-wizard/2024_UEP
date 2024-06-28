package pl.psi.effects.creature.object;

import lombok.Getter;
import lombok.Setter;
import pl.psi.creatures.CreatureStatisticIf;
import pl.psi.creatures.DamageApplier;
import pl.psi.effects.generic.Effect;
import pl.psi.effects.generic.EffectStatisticIf;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public abstract class CreatureEffect extends Effect {
    protected CreatureEffect(EffectStatisticIf effectStatistic) {
        super(effectStatistic);
    }

    public CreatureStatisticIf applyStatisticEffect(CreatureStatisticIf baseStats, CreatureStatisticIf prevStats) {
        return prevStats;
    }

    public DamageApplier applyDamageApplierEffect(DamageApplier baseApplier, DamageApplier prevApplier) {
        return prevApplier;
    }
}
