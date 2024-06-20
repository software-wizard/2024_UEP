package pl.psi.effects.board.object;

import pl.psi.effects.creature.effect.*;
import pl.psi.effects.creature.object.CreatureEffect;
import pl.psi.effects.generic.EffectStatistic;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class BoardEffectFactory {
    private static final Map<EffectStatistic, Function<EffectStatistic, BoardEffect>> effectMap = new HashMap<>();

    static {

    }

    public static BoardEffect fromStatistic(EffectStatistic effectStatistic) {
        Function<EffectStatistic, BoardEffect> effectConstructor = effectMap.get(effectStatistic);

        if (effectConstructor != null) {
            return effectConstructor.apply(effectStatistic);
        }

        return null;
    }
}
