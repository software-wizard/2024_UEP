package pl.psi.effects.effect;

import pl.psi.effects.object.AbstractCreatureStatisticEffectDecorator;
import pl.psi.effects.object.AbstractDamageApplierEffectDecorator;
import pl.psi.effects.object.CreatureEffect;
import pl.psi.effects.object.CreatureEffectStatistic;

public class TestEffect extends CreatureEffect {

    public TestEffect() {
        super(CreatureEffectStatistic.TEST);
    }


}
