package pl.psi.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Disabled
class BallistaTest {

    @Test
    void ballistaShouldAttack() {
        //given
        final Creature angel = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .attack(50)
                        .armor(0)
                        .build())
                .build();
        MachineFactory machineFactory = new MachineFactory();
        Creature ballista = machineFactory.create("Ballista");
        //when
        ballista.attack(angel);
        //then
        assertThat(angel.getCurrentHp()).isBetween(80,90);

    }

    @Test
    void ballistaAttackShouldResultInDamageWithinExpectedRange() {
        //given
        final Creature angel = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .attack(50)
                        .armor(0)
                        .build())
                .build();
        MachineFactory machineFactory = new MachineFactory();
        Creature ballista = machineFactory.create("Ballista");

        ballista.attack(angel);

        assertThat(angel.getCurrentHp()).isBetween(78, 90);


    }
}