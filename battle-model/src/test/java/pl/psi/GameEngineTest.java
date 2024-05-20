package pl.psi;

import java.util.List;

import org.junit.jupiter.api.Test;

import pl.psi.creatures.CastleCreatureFactory;
import pl.psi.spells.Spellbook;

import static org.assertj.core.api.Assertions.assertThat;

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
}
