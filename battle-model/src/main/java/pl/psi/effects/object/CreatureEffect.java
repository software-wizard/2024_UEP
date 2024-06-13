package pl.psi.effects.object;

import lombok.Getter;
import lombok.Setter;
import pl.psi.creatures.CreatureStatisticIf;
import pl.psi.creatures.DamageApplier;

@Getter
public abstract class CreatureEffect {
    private final CreatureEffectStatisticIf effectStatistic;

    @Setter
    private int amount;
    private int turnsPassed;

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
}
