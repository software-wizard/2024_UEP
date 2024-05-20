package pl.psi.spells;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class SpellTest {

    @Test
    void damagingSpellShouldDamageDefenderTest() {
        final Creature attacker = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .build())
                .build();

        final Creature defender = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .build())
                .build();

        final Spell spell = new DamageCreatureSpell(new SimpleSpellDamageCalculator(10), attacker, defender, new Spell(SpellStatistic.DAMAGING_SPELL));
        spell.cast();

        assertThat(defender.getCurrentHp()).isEqualTo(90);


    }
}
