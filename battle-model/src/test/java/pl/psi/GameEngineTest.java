package pl.psi;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import pl.psi.creatures.CastleCreatureFactory;
import pl.psi.creatures.*;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.CreatureTypeEnum;
import pl.psi.spells.Spellbook;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class GameEngineTest
{

    @Test
    void gameEngineShouldDeliverInformationAboutCurrentHeroToMove() {
        final CastleCreatureFactory creatureFactory = new CastleCreatureFactory();

        final Hero hero1 = new Hero( List.of( creatureFactory.create( 1, false, 5 ) ),
                new PrimarySkill(1, 2, 3, 4),
                new Spellbook( List.of()));
        final Hero hero2 = new Hero( List.of( creatureFactory.create( 1, false, 5 ) ),
                new PrimarySkill(1, 2, 3, 4),
                new Spellbook( List.of()));

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
        Hero hero1 = new Hero(List.of(lichWithArchery), new Spellbook(List.of()));
        Hero hero2 = new Hero(List.of(lichWithoutArchery), new Spellbook(List.of()));

        GameEngine gameEngine = new GameEngine(hero1, hero2);

        Point enemyLocation = gameEngine.getCreatureLocation(lichWithoutArchery);
        gameEngine.attack(enemyLocation);
        assertThat(gameEngine.getCreature(enemyLocation).get().getCurrentHp()).isEqualTo(maxHp - 13);
    }

    @Test
    void creatureShouldActAgainAfterReceivingGoodMorale() {
        //always return 0.25 random.
        QuarterRandom quarterRandom = new QuarterRandom();

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
        Hero hero1 = new Hero(List.of(shouldActTwice, shouldActOnce), new Spellbook(List.of()));
        Hero hero2 = new Hero(List.of(enemyCreature), new Spellbook(List.of()));
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

    private static class QuarterRandom extends Random {
        @Override
        public double nextDouble() {
            return 0.25;
        }
    }

    @Test
    void warMachinesAttackCorrectly() {
        QuarterRandom quarterRandom = new QuarterRandom(); //both machines will always hit
        int maxHp = 100;
        Creature ballista = new Creature.Builder().statistic((CreatureStats.builder()
                        .maxHp(maxHp)
                        .damage(Range.closed(10, 10))
                        .build()))
                .creatureType(CreatureTypeEnum.MACHINE)
                .attackType(AttackTypeEnum.RANGE)
                .calculator(new MachineCalculatorDecorator(new DefaultDamageCalculator(quarterRandom), 0))
                .morale(new Morale(0))
                .build();


        Creature skeleton1 = new NecropolisFactory().create(true, 1, 5, 0);
        final Hero hero1 = new Hero(
                List.of(skeleton1,
                        ballista),
                new Spellbook(List.of(new SampleSpell())));


        Creature catapult = new MachineFactory().create("Catapult");
        catapult.setCalculator(new MachineCalculatorDecorator(new DefaultDamageCalculator(quarterRandom), 3));

        Creature skeleton2 = new NecropolisFactory().create(true, 1, 5, 0);
        final Hero hero2 = new Hero(
                List.of(skeleton2,
                        catapult),
                new Spellbook(List.of(new SampleSpell())));

        GameEngine gameEngine = new GameEngine(hero1, hero2);

        assertThat(gameEngine.getHeroToMove()).isEqualTo(hero1);
        assertThat(gameEngine.getCreatureToMove()).isEqualTo(skeleton1);

        Point skeleton2Location = gameEngine.getCreatureLocation(skeleton2);
        gameEngine.attack(skeleton2Location);
        assertThat(skeleton2.getCurrentHp()).isBetween(1, 3);
        assertThat(gameEngine.getHeroToMove()).isEqualTo(hero2); //this means that hero 1 ballista shot on its own
        assertThat(catapult.getCurrentHp()).isEqualTo(993);
        assertThat(gameEngine.getCreatureToMove()).isEqualTo(catapult); //that means that ballista can choose its own target, because it has lvl 3 calculator
        assertThat(gameEngine.canAttack(gameEngine.getCreatureLocation(catapult))).isTrue();
        assertThat(gameEngine.canAttack(gameEngine.getCreatureLocation(skeleton1))).isFalse();
    }



}
