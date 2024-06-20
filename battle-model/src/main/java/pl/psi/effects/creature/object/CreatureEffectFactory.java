package pl.psi.effects.creature.object;

import pl.psi.effects.creature.effect.*;
import pl.psi.effects.generic.EffectStatistic;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CreatureEffectFactory {
    private static final Map<EffectStatistic, Function<EffectStatistic, CreatureEffect>> effectMap = new HashMap<>();

    static {
        effectMap.put(EffectStatistic.TEST, TestEffect::new);

        effectMap.put(EffectStatistic.DISRUPTING_RAY, AbsoluteArmorModifierEffect::new);
        effectMap.put(EffectStatistic.BLESS, MaximumDamageOnlyEffect::new);
        effectMap.put(EffectStatistic.HASTE, AbsoluteSpeedModifierEffect::new);
        effectMap.put(EffectStatistic.ANTI_MAGIC, AntiSpellDamageEffect::new);
    }

    public static CreatureEffect fromStatistic(EffectStatistic effectStatistic) {
        Function<EffectStatistic, CreatureEffect> effectConstructor = effectMap.get(effectStatistic);

        if (effectConstructor != null) {
            return effectConstructor.apply(effectStatistic);
        }

        return null;
    }
}
