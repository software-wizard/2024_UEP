package pl.psi.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.psi.Point;
import pl.psi.TurnQueue;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.obstacles.Wall;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class CreatureTest {

    private static final Range<Integer> NOT_IMPORTANT_DMG = Range.closed(0, 0);

    @Test
    void creatureShouldAttackProperly() {
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
        // when
        angel.attack(dragon);
        // then
        assertThat(dragon.getCurrentHp()).isEqualTo(70);
    }

    @Test
    void creatureShouldNotHealCreatureEvenHasLowerAttackThanDefenderArmor() {
        final Creature angel = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(1)
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
        // when
        angel.attack(dragon);
        // then
        assertThat(dragon.getCurrentHp()).isEqualTo(100);
    }

    @Test
    void defenderShouldCounterAttack() {
        final Creature attacker = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(0)
                        .armor(10)
                        .build())
                .build();
        final Creature defender = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .attack(10)
                        .build())
                .build();
        // when
        attacker.attack(defender);
        // then
        assertThat(attacker.getCurrentHp()).isEqualTo(90);
    }

    @Test
    void defenderShouldNotCounterAttackWhenIsDie() {
        final Creature attacker = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(1000)
                        .armor(10)
                        .build())
                .build();
        final Creature defender = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(20)
                        .armor(5)
                        .build())
                .build();
        // when
        attacker.attack(defender);
        // then asd
        assertThat(attacker.getCurrentHp()).isEqualTo(100);
    }

    @Test
    void defenderShouldCounterAttackOnlyOncePerTurn() {
        final Creature attacker = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(0)
                        .armor(0)
                        .build())
                .build();

        final Creature defender = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .attack(0)
                        .build())
                .build();

        // when
        attacker.attack(defender);
        attacker.attack(defender);
        // then
        assertThat(attacker.getCurrentHp()).isEqualTo(90);
    }

    @Test
    void counterAttackCounterShouldResetAfterEndOfTurn() {
        final Creature attacker = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .build())
                .build();

        final Creature defender = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .build())
                .build();

        final TurnQueue turnQueue = new TurnQueue(List.of(attacker), List.of(defender));

        attacker.attack(defender);
        attacker.attack(defender);
        assertThat(attacker.getCurrentHp()).isEqualTo(90);
        turnQueue.next();
        turnQueue.next();
        attacker.attack(defender);
        assertThat(attacker.getCurrentHp()).isEqualTo(80);
        // end of turn
    }

    @Test
    void creatureShouldHealAfterEndOfTurn() {
        final Creature attacker = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .build())
                .build();

        final Creature selfHealAfterEndOfTurnCreature = new SelfHealAfterTurnCreature(new Creature.Builder()
                .statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .build())
                .build());

        final TurnQueue turnQueue =
                new TurnQueue(List.of(attacker), List.of(selfHealAfterEndOfTurnCreature));

        attacker.attack(selfHealAfterEndOfTurnCreature);
        assertThat(selfHealAfterEndOfTurnCreature.getCurrentHp()).isEqualTo(90);
        turnQueue.next();
        turnQueue.next();
        assertThat(selfHealAfterEndOfTurnCreature.getCurrentHp()).isEqualTo(100);
    }

    @Test
    void rangedCreatureDealsHalfDamageInMeleeCombat() {
        final Creature rangedCreature = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .build())
                .attackType(AttackTypeEnum.RANGE)
                .build();

        final Creature defender = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .build())
                .build();
        rangedCreature.attack(defender, AttackTypeEnum.MELEE);
        assertThat(defender.getCurrentHp()).isEqualTo(95);

        rangedCreature.attack(defender, AttackTypeEnum.RANGE);
        assertThat(defender.getCurrentHp()).isEqualTo(85);
    }

    @Test
    void creatureShouldFailToAttackWall() {
        // given
        Wall wall = new Wall();
        final Creature rangedCreature = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .build())
                .attackType(AttackTypeEnum.RANGE)
                .build();

        // when


        rangedCreature.attack(wall);
        // then
        assertThat(wall.getCurrentHP()).isEqualTo(1500);
    }

    @Test
    @Disabled
    void meleeCreatureShouldBeAbleToDealDamageToWall() {
        // given
        Wall wall = new Wall();
        final Creature meleeCreature = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .build())
                .attackType(AttackTypeEnum.MELEE)
                .build();
        MachineFactory machineFactory = new MachineFactory();
        Creature catapult = machineFactory.create("Catapult");
        Creature spyCatapult = spy(catapult);
        Mockito.doReturn(true).when(spyCatapult).randomChance();
        // when
        spyCatapult.attack(wall);
        meleeCreature.attack(wall);

        // then
        assertThat(wall.getCurrentHP()).isLessThan(1500);
    }

    @Test
    void creatureDoesNotCounterAttackAfterBeingAttackedFromRange() {
        int maxHp = 100;
        final Creature rangedCreature = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(maxHp)
                        .damage(Range.closed(10, 10))
                        .build())
                .attackType(AttackTypeEnum.RANGE)
                .build();

        final Creature defender = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(maxHp)
                        .damage(Range.closed(10, 10))
                        .build())
                .build();

        rangedCreature.attack(defender, AttackTypeEnum.RANGE);
        assertThat(rangedCreature.getCurrentHp()).isEqualTo(maxHp);
    }

    @Test
    void creatureShouldAttackAnotherCreature() {
        // given
        final Creature attacker = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .attack(50)
                        .armor(0)
                        .build())
                .build();
        final Creature defender = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(0)
                        .armor(10)
                        .build())
                .build();
        // when
        attacker.attack(defender);
        // then
        assertThat(defender.getCurrentHp()).isEqualTo(70);
    }

    @Test
    void creatureAttackShouldNotDealDamageToWall() {
        // given
        final Creature attacker = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .attack(50)
                        .armor(0)
                        .build())
                .build();

        Wall wall = new Wall();
        // when
        attacker.attack(wall);
        // then
        assertThat(wall.getCurrentHP()).isEqualTo(1500);
    }

    @Test
    @Disabled
    void creatureAttackShouldDealDamageToWall() {
        // given
        final Creature attackerCreature = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .attack(50)
                        .armor(0)
                        .build())
                .build();
        Wall wall = new Wall();
        MachineFactory machineFactory = new MachineFactory();
        Creature catapult = machineFactory.create("Catapult");
        Creature spyCatapult = spy(catapult);
        Mockito.doReturn(true).when(spyCatapult).randomChance();

        //when
        spyCatapult.attack(wall);
        int wallHp = wall.getHP();
        attackerCreature.attack(wall);

        // then
        assertThat(wall.getCurrentHP()).isLessThan(wallHp);

    }

    @Test
    void creatureAttackShouldNotDealDamageToWallAfterCatapultMissesWall() {
        // given
        final Creature attacker = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .attack(50)
                        .armor(0)
                        .build())
                .build();

        Wall wall = new Wall();
        Creature catapult = mock(Creature.class);
        when(catapult.randomChance()).thenReturn(false);

        // when
        catapult.attack(wall);
        attacker.attack(wall);

        // then
        assertThat(wall.getCurrentHP()).isEqualTo(1500);
    }
}
