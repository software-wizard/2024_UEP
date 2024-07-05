package pl.psi.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.psi.Hero;
import pl.psi.obstacles.Wall;

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
        assertThat(angel.getCurrentHp()).isBetween(4,36);

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
        //when
        ballista.attack(angel);
        //then
        assertThat(angel.getCurrentHp()).isBetween(4,36);

    }

    @Test
    void ballistaShouldAttackMoreOnLevel2() {
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
        ballista.levelUpSpell();
        ballista.attack(angel);
        //then
        assertThat(angel.getCurrentHp()).isBetween(32,98);
    }

    @Test
    void ballistaShouldNotAttackWall() {
        //given
        final Wall wall = new Wall();
        MachineFactory machineFactory = new MachineFactory();
        Creature ballista = machineFactory.create("Ballista");
        //when
        ballista.attack(wall);
        //then
        assertThat(wall.getCurrentHP()).isEqualTo(1500);
    }
}