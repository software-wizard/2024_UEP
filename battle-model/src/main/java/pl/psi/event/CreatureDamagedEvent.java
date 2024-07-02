package pl.psi.event;

import lombok.Value;
import pl.psi.creatures.Creature;
import pl.psi.creatures.DamageValueObject;

@Value
public class CreatureDamagedEvent extends GameEvent {
    private final Creature creature;
    private final Creature attacker;
    private final DamageValueObject damage;

    public CreatureDamagedEvent(final Creature creature, final Creature attacker, final DamageValueObject damage) {
        super(GameEventType.CREATURE_DAMAGED);
        this.creature = creature;
        this.attacker = attacker;
        this.damage = damage;
    }
}
