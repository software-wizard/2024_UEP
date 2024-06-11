package pl.psi.effects.object;

import lombok.Getter;
import lombok.Setter;
import pl.psi.creatures.CreatureStatisticIf;
import pl.psi.creatures.DamageApplier;

import java.beans.PropertyChangeListener;

@Getter
public abstract class CreatureEffect {
    private CreatureEffectStatisticIf effectStatistic;

    @Setter
    private int amount;

    protected CreatureEffect(CreatureEffectStatisticIf effectStatistic) {
        this.effectStatistic = effectStatistic;
    }

    public CreatureStatisticIf applyStatisticEffect(CreatureStatisticIf baseStats) {
        return baseStats;
    }

    public DamageApplier applyDamageApplierEffect(DamageApplier baseApplier) {
        return baseApplier;
    }
}
