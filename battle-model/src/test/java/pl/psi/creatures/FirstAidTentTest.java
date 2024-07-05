package pl.psi.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FirstAidTentTest {
    private static final Range<Integer> NOT_IMPORTANT_DMG = Range.closed(0, 0);

    @Test
    void creatureShouldHaveFullHPAfterHealing() {
        //given
        final Creature angel = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(1, 1))
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
        assertThat(dragon.getCurrentHp()).isEqualTo(dragon.getMaxHp());
    }

}