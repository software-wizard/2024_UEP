package pl.psi.hero;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.psi.*;
import pl.psi.converter.EcoBattleConverter;
import pl.psi.creatures.CreatureStatistic;
import pl.psi.creatures.EconomyCreature;
import pl.psi.creatures.EconomyNecropolisFactory;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EcoBattleConverterOpponentTest {

    private EconomyHero hero;
    private Opponent opponent;
    private StartBattlePack startBattlePack;
    private EcoBattleConverter ecoBattleConverter;



    @Test
    public void testResourceTransferAfterBattle() {
        EconomyNecropolisFactory factory = new EconomyNecropolisFactory();

        Resources heroResources = Resources.builder()
                .gold(50)
                .wood(50)
                .ore(50)
                .gems(50)
                .sulfur(50)
                .mercury(50)
                .build();

        Resources opponentResources = Resources.builder()
                .gold(100)
                .wood(100)
                .ore(100)
                .gems(100)
                .sulfur(100)
                .mercury(100)
                .build();

        hero = new EconomyHero("Hero");
        hero.addCreature(factory.create(true,1));
        List<EconomyCreature> creatures = new ArrayList<>();
        creatures.add(factory.create(true,1));
        creatures.add(factory.create(true,2));
        Opponent opponent = new Opponent(opponentResources,creatures);

        assertEquals(hero.getCreatures().get(0),factory.create(true,1));

        hero.setResources(heroResources);

        startBattlePack = StartBattlePack.builder()
                .attacker(hero)
                .defender(opponent.getHero())
                .build();

        EcoBattleConverter.startBattle(startBattlePack);
        ecoBattleConverter = new EcoBattleConverter();
        PropertyChangeEvent event = new PropertyChangeEvent(this, GameEngine.BATTLE_ENDED, false, true);
        ecoBattleConverter.propertyChange(event);


        Resources expectedResources = Resources.builder()
                .gold(150)
                .wood(150)
                .ore(150)
                .gems(150)
                .sulfur(150)
                .mercury(150)
                .build();


        //assertEquals(expectedResources, hero.getResources());


    }
}
