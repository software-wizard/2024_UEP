package pl.psi;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import pl.psi.creatures.*;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.CreatureTypeEnum;
import pl.psi.spells.Spellbook;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class GameEngineTest {
    private final QuarterRandom quarterRandom = new QuarterRandom();

    @Test
    void gameEngineShouldDeliverInformationAboutCurrentHeroToMove() {
        final CastleCreatureFactory creatureFactory = new CastleCreatureFactory();

        final Hero hero1 = new Hero(List.of(creatureFactory.create(1, false, 5)),
                new PrimarySkill(1, 2, 3, 4),
                new Spellbook(List.of()));
        final Hero hero2 = new Hero(List.of(creatureFactory.create(1, false, 5)),
                new PrimarySkill(1, 2, 3, 4),
                new Spellbook(List.of()));

        final GameEngine gameEngine = new GameEngine(hero1, hero2);

        assertThat(gameEngine.getHeroToMove()).isEqualTo(hero1);
        gameEngine.pass();
        assertThat(gameEngine.getHeroToMove()).isEqualTo(hero2);
    }

    @Test
    void rangedCreatureCorrectlyStatesAttackType() {
        final int maxHp = 30;
        //given
        final Creature lichWithArchery = new Creature.Builder()
                .statistic(CreatureStats.builder()
                        .armor(10)
                        .attack(13)
                        .maxHp(maxHp)
                        .damage(Range.closed(10, 10))
                        .build())
                .attackType(AttackTypeEnum.RANGE)
                .build();
        lichWithArchery.setCalculator(new ArcheryCalculatorDecorator(lichWithArchery.getCalculator(), 2));

        final Creature lichWithoutArchery = new Creature.Builder()
                .statistic(CreatureStats.builder()
                        .armor(10)
                        .attack(13)
                        .maxHp(maxHp)
                        .damage(Range.closed(10, 10))
                        .build())
                .build();
        Hero hero1 = new Hero(List.of(lichWithArchery), null, null);
        Hero hero2 = new Hero(List.of(lichWithoutArchery), null, null);

        GameEngine gameEngine = new GameEngine(hero1, hero2);

        Point enemyLocation = gameEngine.getCreatureLocation(lichWithoutArchery);
        gameEngine.attack(enemyLocation);
        assertThat(gameEngine.getCreature(enemyLocation).get().getCurrentHp()).isEqualTo(maxHp - 13);
    }

    @Test
    void creatureShouldActAgainAfterReceivingGoodMorale() {
        Range<Integer> notImportantDamage = Range.closed(0, 0);
        final Creature shouldActTwice = new Creature.Builder()
                .statistic(CreatureStats.builder()
                        .damage(notImportantDamage)
                        .maxHp(200)
                        .build())
                .morale(new Morale(3, quarterRandom))
                .build();
        final Creature shouldActOnce = new Creature.Builder().statistic(CreatureStats.builder()
                        .damage(notImportantDamage)
                        .maxHp(200)
                        .build())
                .morale(new Morale(2, quarterRandom))
                .build();
        final Creature enemyCreature = new Creature.Builder().statistic(CreatureStats.builder()
                        .damage(notImportantDamage)
                        .maxHp(200)
                        .build())
                .build();
        Hero hero1 = new Hero(List.of(shouldActTwice, shouldActOnce), null, null);
        Hero hero2 = new Hero(List.of(enemyCreature), null, null);
        GameEngine gameEngine = new GameEngine(hero1, hero2);

        Point enemyLocation = gameEngine.getCreatureLocation(enemyCreature);

        assertEquals(gameEngine.getHeroToMove(), hero1);
        assertEquals(gameEngine.getCreatureToMove(), shouldActTwice);
        assertThat(gameEngine.getCreature(enemyLocation).isPresent()).isTrue();
        assertEquals(gameEngine.getCreature(enemyLocation).get(), enemyCreature);

        assertThat(gameEngine.getCreatureToMove()).isEqualTo(shouldActTwice);
        gameEngine.attack(enemyLocation);

        assertThat(gameEngine.getCreatureToMove()).isEqualTo(shouldActTwice);
        gameEngine.attack(enemyLocation);

        assertThat(gameEngine.getCreatureToMove()).isEqualTo(shouldActOnce);
        gameEngine.attack(enemyLocation);

        assertEquals(gameEngine.getHeroToMove(), hero2);
        assertEquals(gameEngine.getCreatureToMove(), enemyCreature);
    }

    @Test
    void warMachinesWithLevelSmallerThan2ShootOnItsOwn() {
        int maxHp = 100;
        Creature catapult1 = new Creature.Builder().statistic((CreatureStats.builder()
                        .damage(Range.closed(10, 10))
                        .maxHp(maxHp)
                        .build()))
                .creatureType(CreatureTypeEnum.MACHINE)
                .attackType(AttackTypeEnum.RANGE)
                .calculator(new MachineCalculatorDecorator(new DefaultDamageCalculator(quarterRandom), 1))
                .morale(new Morale(0))
                .build();

        Creature creature1 = new Creature.Builder()
                .statistic(CreatureStats.builder()
                        .maxHp(maxHp)
                        .damage(Range.closed(10, 10))
                        .attack(10)
                        .build())
                .build();

        Hero hero1 = new Hero(List.of(creature1, catapult1),
                new PrimarySkill(1, 2, 3, 4),
                null);

        Creature catapult2 = new Creature.Builder().statistic((CreatureStats.builder()
                        .damage(Range.closed(10, 10))
                        .maxHp(maxHp)
                        .build()))
                .creatureType(CreatureTypeEnum.MACHINE)
                .attackType(AttackTypeEnum.RANGE)
                .calculator(new MachineCalculatorDecorator(new DefaultDamageCalculator(quarterRandom), 0))
                .morale(new Morale(0))
                .build();

        Creature creature2 = new Creature.Builder()
                .statistic(CreatureStats.builder()
                        .maxHp(maxHp)
                        .damage(Range.closed(10, 10))
                        .attack(10)
                        .build())
                .build();

        Hero hero2 = new Hero(List.of(creature2, catapult2),
                new PrimarySkill(1, 2, 3, 4),
                null);

        GameEngine gameEngine = new GameEngine(hero1, hero2);

        assertThat(gameEngine.getHeroToMove()).isEqualTo(hero1);
        assertThat(gameEngine.getCreatureToMove()).isEqualTo(creature1);
        gameEngine.attack(gameEngine.getCreatureLocation(creature2));
        assertThat(creature2.getCurrentHp()).isEqualTo(maxHp - 15);
        //catapult 1 should fire after this attack
        assertThat(catapult2.getCurrentHp()).isEqualTo(maxHp - 10);
        assertThat(gameEngine.getHeroToMove()).isEqualTo(hero2);
        assertThat(gameEngine.getCreatureToMove()).isEqualTo(creature2);
        gameEngine.attack(gameEngine.getCreatureLocation(creature1));
        //catapult 2 should fire after this attack
        assertThat(catapult1.getCurrentHp()).isEqualTo(maxHp - 10);
    }

    @Test
    void warMachinesCanOnlyAttackWarMachines() {
        int maxHp = 100;
        Creature catapult1 = new Creature.Builder().statistic((CreatureStats.builder()
                        .damage(Range.closed(10, 10))
                        .maxHp(maxHp)
                        .build()))
                .creatureType(CreatureTypeEnum.MACHINE)
                .attackType(AttackTypeEnum.RANGE)
                .calculator(new MachineCalculatorDecorator(new DefaultDamageCalculator(quarterRandom), 3))
                .morale(new Morale(0))
                .build();

        Hero hero1 = new Hero(List.of(catapult1),
                new PrimarySkill(1, 2, 3, 4),
                null);

        Creature catapult2 = new Creature.Builder().statistic((CreatureStats.builder()
                        .damage(Range.closed(10, 10))
                        .maxHp(maxHp)
                        .build()))
                .creatureType(CreatureTypeEnum.MACHINE)
                .attackType(AttackTypeEnum.RANGE)
                .calculator(new MachineCalculatorDecorator(new DefaultDamageCalculator(quarterRandom), 2))
                .morale(new Morale(0))
                .build();

        Creature creature2 = new Creature.Builder()
                .statistic(CreatureStats.builder()
                        .maxHp(maxHp)
                        .damage(Range.closed(10, 10))
                        .attack(10)
                        .build())
                .build();

        Hero hero2 = new Hero(List.of(creature2, catapult2),
                new PrimarySkill(1, 2, 3, 4),
                null);

        GameEngine gameEngine = new GameEngine(hero1, hero2);

        assertThat(gameEngine.getHeroToMove()).isEqualTo(hero1);
        assertThat(gameEngine.getCreatureToMove()).isEqualTo(catapult1);
        assertThat(gameEngine.canAttack(gameEngine.getCreatureLocation(catapult2))).isTrue();
        assertThat(gameEngine.canAttack(gameEngine.getCreatureLocation(creature2))).isFalse();
    }

    private static class QuarterRandom extends Random {
        @Override
        public double nextDouble() {
            return 0.25;
        }
    }
}
