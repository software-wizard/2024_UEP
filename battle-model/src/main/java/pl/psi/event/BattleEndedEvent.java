package pl.psi.event;

import lombok.Getter;
import pl.psi.Hero;

@Getter
public class BattleEndedEvent extends GameEvent {
    private final Hero winner;

    public BattleEndedEvent(final Hero winner) {
        super(GameEventType.BATTLE_ENDED);
        this.winner = winner;
    }
}
