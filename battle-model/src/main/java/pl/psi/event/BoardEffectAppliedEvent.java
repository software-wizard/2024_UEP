package pl.psi.event;

import lombok.Getter;
import pl.psi.Location;
import pl.psi.effects.generic.EffectStatistic;

@Getter
public class BoardEffectAppliedEvent extends GameEvent {
    private final EffectStatistic effect;
    private final Location location;

    public BoardEffectAppliedEvent(final EffectStatistic effect, final Location location) {
        super(GameEventType.BOARD_EFFECT_APPLIED);
        this.effect = effect;
        this.location = location;
    }

}
