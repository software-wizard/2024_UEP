package pl.psi.effects.creature.effect;

import pl.psi.creatures.CreatureStatisticIf;
import pl.psi.effects.creature.object.CreatureEffect;
import pl.psi.effects.generic.EffectStatisticIf;
import pl.psi.effects.creature.object.CreatureStatisticModifier;

public class TestEffect extends CreatureEffect {
    public TestEffect(final EffectStatisticIf stats) {
        super(stats);
    }

    @Override
    public CreatureStatisticIf applyStatisticEffect(final CreatureStatisticIf baseStats, final CreatureStatisticIf prevStats) {
        return new CreatureStatisticModifier(baseStats, prevStats) {
            @Override
            public int getArmor() {
                return prevStats.getArmor() + 1;
            }
        };
    }
}
