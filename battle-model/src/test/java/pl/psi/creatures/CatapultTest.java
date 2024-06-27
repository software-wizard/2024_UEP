package pl.psi.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.psi.obstacles.Wall;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class CatapultTest {

    @Test
    void catapultShouldANotAttackCreature() {
        //given
        final Creature angel = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .attack(50)
                        .armor(0)
                        .build())
                .build();
        MachineFactory machineFactory = new MachineFactory();
        Creature catapult = machineFactory.create("Catapult");
        //when
        catapult.attack(angel);
        //then
        assertThat(angel.getCurrentHp()).isEqualTo(100);
    }
    @Test
    void catapultShouldDestroyWall() {
        //given
        final Wall wall = Mockito.mock(Wall.class);
        MachineFactory machineFactory = new MachineFactory();
        Creature catapult = machineFactory.create("Catapult");
        when(wall.getCurrentHP()).thenReturn(1000);
        //when
        catapult.attack(wall);
        //then
        assertThat(wall.getCurrentHP()).isEqualTo(1000);
    }
}