package pl.psi.event;

import lombok.Getter;
import pl.psi.Location;
import pl.psi.creatures.Creature;

@Getter
public class CreatureMovedEvent extends GameEvent {
    private final Creature creature;
    private final Location origin;
    private final Location target;

    public CreatureMovedEvent(final Creature creature, final Location origin, final Location target) {
        super(GameEventType.CREATURE_MOVED);
        this.creature = creature;
        this.origin = origin;
        this.target = target;
    }
}
