package pl.psi.effects.effect;

import pl.psi.creatures.CreatureStatisticIf;
import pl.psi.effects.object.CreatureEffect;
import pl.psi.effects.object.CreatureEffectStatisticIf;
import pl.psi.effects.object.CreatureStatisticModifier;

public class TestEffect extends CreatureEffect {
    public TestEffect(final CreatureEffectStatisticIf stats) {
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
