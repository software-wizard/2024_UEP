package pl.psi.effects.object;

import pl.psi.effects.effect.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CreatureEffectFactory {
    private static final Map<CreatureEffectStatistic, Function<CreatureEffectStatistic, CreatureEffect>> effectMap = new HashMap<>();

    static {
        effectMap.put(CreatureEffectStatistic.TEST, TestEffect::new);

        effectMap.put(CreatureEffectStatistic.DISRUPTING_RAY, AbsoluteArmorModifierEffect::new);
        effectMap.put(CreatureEffectStatistic.BLESS, MaximumDamageOnlyEffect::new);
        effectMap.put(CreatureEffectStatistic.HASTE, AbsoluteSpeedModifierEffect::new);
        effectMap.put(CreatureEffectStatistic.ANTI_MAGIC, AntiSpellDamageEffect::new);
    }

    public static CreatureEffect fromStatistic(CreatureEffectStatistic effectStatistic) {
        Function<CreatureEffectStatistic, CreatureEffect> effectConstructor = effectMap.get(effectStatistic);

        if (effectConstructor != null) {
            return effectConstructor.apply(effectStatistic);
        }

        return null;
    }
}
