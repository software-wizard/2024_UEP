package pl.psi.effects.effect;

import pl.psi.creatures.CreatureStatisticIf;
import pl.psi.effects.object.CreatureEffect;
import pl.psi.effects.object.CreatureEffectStatisticIf;
import pl.psi.effects.object.CreatureStatisticModifier;

public class AbsoluteSpeedModifierEffect extends CreatureEffect {
    public AbsoluteSpeedModifierEffect(CreatureEffectStatisticIf effectStatistic) {
        super(effectStatistic);
    }

    @Override
    public CreatureStatisticIf applyStatisticEffect(CreatureStatisticIf baseStats, CreatureStatisticIf prevStats) {
        return new CreatureStatisticModifier(baseStats, prevStats) {
            @Override
            public int getMoveRange() {
                return prevStats.getMoveRange() + getEffectStatistic().getBaseModifierValue();
            }
        };
    }
}
