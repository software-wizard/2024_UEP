package pl.psi.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import pl.psi.obstacles.Wall;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

//    void catapultShouldDestroyWall() {
//        //given
//        final Wall wall = new Wall();
//        MachineFactory machineFactory = new MachineFactory();
//        Creature catapult = machineFactory.create("Catapult");
//        //when
//        catapult.setAttackStrategy(new WallAttackStrategy());
//        catapult.attack(wall);
//        //then
//        assertThat(wall.getCurrentHp()).isEqualTo(0);
//    }
}