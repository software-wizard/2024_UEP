package pl.psi.effects.effect;

import com.google.common.collect.Range;
import pl.psi.creatures.CreatureStatisticIf;
import pl.psi.effects.object.CreatureEffect;
import pl.psi.effects.object.CreatureEffectStatisticIf;
import pl.psi.effects.object.CreatureStatisticModifier;

import static com.google.common.collect.Range.closed;

public class MaximumDamageOnlyEffect extends CreatureEffect {
    public MaximumDamageOnlyEffect(CreatureEffectStatisticIf effectStatistic) {
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
