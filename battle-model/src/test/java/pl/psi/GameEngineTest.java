package pl.psi;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;

import pl.psi.creatures.CastleCreatureFactory;
import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;
import pl.psi.creatures.Morale;
import pl.psi.spells.Spellbook;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class GameEngineTest
{
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
        Point enemyLocation = new Point(14, 1);
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

    private class QuarterRandom extends Random {
        @Override
        public double nextDouble() {
            return 0.25;
        }
    }
}
