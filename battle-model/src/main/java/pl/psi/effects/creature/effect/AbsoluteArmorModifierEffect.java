package pl.psi.effects.creature.effect;

import pl.psi.creatures.CreatureStatisticIf;
import pl.psi.effects.creature.object.CreatureEffect;
import pl.psi.effects.generic.EffectStatisticIf;
import pl.psi.effects.creature.object.CreatureStatisticModifier;

public class AbsoluteArmorModifierEffect extends CreatureEffect {

    public AbsoluteArmorModifierEffect(EffectStatisticIf effectStatistic) {
        super(effectStatistic);
    }

    @Override
    public CreatureStatisticIf applyStatisticEffect(CreatureStatisticIf baseStats, CreatureStatisticIf prevStats) {
        return new CreatureStatisticModifier(baseStats, prevStats) {
            @Override
            public int getArmor() {
                return prevStats.getArmor() + getEffectStatistic().getBaseModifierValue();
            }
        };
    }
}
