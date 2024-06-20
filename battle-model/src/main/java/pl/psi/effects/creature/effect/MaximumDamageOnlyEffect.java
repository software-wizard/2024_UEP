package pl.psi.effects.creature.effect;

import com.google.common.collect.Range;
import pl.psi.creatures.CreatureStatisticIf;
import pl.psi.effects.creature.object.CreatureEffect;
import pl.psi.effects.generic.EffectStatisticIf;
import pl.psi.effects.creature.object.CreatureStatisticModifier;

public class MaximumDamageOnlyEffect extends CreatureEffect {
    public MaximumDamageOnlyEffect(EffectStatisticIf effectStatistic) {
        super(effectStatistic);
    }

    @Override
    public CreatureStatisticIf applyStatisticEffect(CreatureStatisticIf baseStats, CreatureStatisticIf prevStats) {
        return new CreatureStatisticModifier(baseStats, prevStats) {
            @Override
            public Range<Integer> getDamage() {
                return Range.closed(baseStats.getDamage().upperEndpoint(), baseStats.getDamage().upperEndpoint());
            }
        };
    }
}
