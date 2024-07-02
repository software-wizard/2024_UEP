package pl.psi.event;

import lombok.Getter;

@Getter
public class GameEvent {
    private final GameEventType type;

    public GameEvent(final GameEventType type) {
        this.type = type;
    }
}
