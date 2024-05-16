package pl.psi.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
class MachineFactoryTest {

    private static final Range<Integer> NOT_IMPORTANT_DMG = Range.closed(0, 0);

    @Test
    void TestHealCreature() {
        // given
        final Creature angel = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .attack(50)
                        .armor(0)
                        .build())
                .build();
        final Creature dragon = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(0)
                        .armor(10)
                        .build())
                .build();
        MachineFactory machineFactory = new MachineFactory();
        Creature firstAidTent = machineFactory.create("First Aid Tent");
        // when
        angel.attack(dragon);
        firstAidTent.healHPCreature(dragon);
        // then
        //assertThat(dragon.getCurrentHp()).isEqualTo(dragon.getMaxHp());
        assertThat(dragon.getCurrentHp()).isBetween(71,95);
    }

    @Test
    void TestHealListCreature() {
        // given
        final Creature dragon = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(0)
                        .armor(10)
                        .build())
                .build();

        MachineFactory machineFactory = new MachineFactory();

        Creature ballista = machineFactory.create("Ballista");
        Creature firstAidTent = machineFactory.create("First Aid Tent");
        List<Creature> creaturesList = new ArrayList<>();
        creaturesList.add(firstAidTent);
        creaturesList.add(ballista);
        creaturesList.add(dragon);
        // when
        ballista.attack(dragon);
        firstAidTent.chooseHealCreature(creaturesList);
        // then
        assertThat(dragon.getCurrentHp()).isEqualTo(70);
        assertThat(dragon.getCurrentHp()).isEqualTo(dragon.getMaxHp());
    }

}