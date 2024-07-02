package pl.psi.event;

import lombok.Getter;
import lombok.Value;
import pl.psi.creatures.Creature;
import pl.psi.creatures.DamageValueObject;
import pl.psi.effects.generic.EffectStatistic;

@Getter
public class CreatureEffectAppliedEvent extends GameEvent {
    private final Creature creature;
    private final EffectStatistic effect;

    public CreatureEffectAppliedEvent(final Creature creature, final EffectStatistic effect) {
        super(GameEventType.CREATURE_EFFECT_APPLIED);
        this.creature = creature;
        this.effect = effect;
    }
}
