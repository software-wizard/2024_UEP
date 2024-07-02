package pl.psi.event;

import lombok.Getter;
import pl.psi.Location;
import pl.psi.spells.object.Spell;

@Getter
public class SpellCastedEvent extends GameEvent {
    private final Spell spell;
    private final Location location;

    public SpellCastedEvent(final Spell spell, final Location location) {
        super(GameEventType.SPELL_CASTED);

        this.spell = spell;
        this.location = location;
    }
}
