package pl.psi;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import pl.psi.creatures.*;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.spells.Spellbook;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class GameEngineTest
{

    final static Point ENEMY_LOCATION = new Point(14, 1);
    @Test
    void shoudWorksHeHe()
    {
        final CastleCreatureFactory creatureFactory = new CastleCreatureFactory();
        final GameEngine gameEngine =
            new GameEngine( new Hero( List.of( creatureFactory.create( 1, false, 5 ) ),
                            new Spellbook( List.of() )),
                            new Hero( List.of( creatureFactory.create( 1, false, 5 ) ),
                            new Spellbook( List.of() )));

        gameEngine.attack( new Point( 1, 1 ) );
    }

    @Test
    void gameEngineShouldDeliverInformationAboutCurrentHeroToMove() {
        final CastleCreatureFactory creatureFactory = new CastleCreatureFactory();

        final Hero hero1 = new Hero( List.of( creatureFactory.create( 1, false, 5 ) ),
                new Spellbook( List.of() ));
        final Hero hero2 = new Hero( List.of( creatureFactory.create( 1, false, 5 ) ),
                new Spellbook( List.of() ));

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

        gameEngine.attack(ENEMY_LOCATION);
        assertThat(gameEngine.getCreature(ENEMY_LOCATION).get().getCurrentHp()).isEqualTo(maxHp - 13);
    }

    @Test
    void creatureShouldActAgainAfterReceivingGoodMorale() {
        //always return 0.25 random.
        QuarterRandom quarterRandom = new QuarterRandom();

        Range<Integer> notImportantDamage = Range.closed(0, 0);
        final Creature shouldActTwice = new Creature.Builder()
                .statistic( CreatureStats.builder()
                        .damage(notImportantDamage)
                        .maxHp(200)
                        .build() )
                .morale(new Morale(3, quarterRandom))
                .build();
        final Creature shouldActOnce = new Creature.Builder().statistic( CreatureStats.builder()
                        .damage(notImportantDamage)
                        .maxHp(200)
                        .build() )
                .morale(new Morale(2, quarterRandom))
                .build();
        final Creature enemyCreature = new Creature.Builder().statistic( CreatureStats.builder()
                        .damage(notImportantDamage)
                        .maxHp(200)
                        .build() )
                .build();
        Hero hero1 = new Hero(List.of(shouldActTwice, shouldActOnce), new Spellbook(List.of()));
        Hero hero2 = new Hero(List.of(enemyCreature), new Spellbook(List.of()));
        GameEngine gameEngine = new GameEngine(hero1, hero2);

        assertEquals(gameEngine.getHeroToMove(), hero1);
        assertEquals(gameEngine.getCreatureToMove(), shouldActTwice);
        assertThat(gameEngine.getCreature(ENEMY_LOCATION).isPresent()).isTrue();
        assertEquals(gameEngine.getCreature(ENEMY_LOCATION).get(), enemyCreature);

        assertThat(gameEngine.getCreatureToMove()).isEqualTo(shouldActTwice);
        gameEngine.attack(ENEMY_LOCATION);

        assertThat(gameEngine.getCreatureToMove()).isEqualTo(shouldActTwice);
        gameEngine.attack(ENEMY_LOCATION);

        assertThat(gameEngine.getCreatureToMove()).isEqualTo(shouldActOnce);
        gameEngine.attack(ENEMY_LOCATION);

        assertEquals(gameEngine.getHeroToMove(), hero2);
        assertEquals(gameEngine.getCreatureToMove(), enemyCreature);
    }

    private static class QuarterRandom extends Random {
        @Override
        public double nextDouble() {
            return 0.25;
        }
    }
}
