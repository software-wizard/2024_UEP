package pl.psi.effects.effect;

import pl.psi.creatures.CreatureStatisticIf;
import pl.psi.effects.object.CreatureEffect;
import pl.psi.effects.object.CreatureEffectStatisticIf;
import pl.psi.effects.object.CreatureStatisticModifier;

public class AbsoluteArmorModifierEffect extends CreatureEffect {

    public AbsoluteArmorModifierEffect(CreatureEffectStatisticIf effectStatistic) {
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
