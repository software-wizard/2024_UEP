package pl.psi;

import org.junit.jupiter.api.Test;
import pl.psi.creatures.EconomyCreature;
import pl.psi.creatures.EconomyNecropolisFactory;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;


class OpponentTest {



    @Test
    void getCreatures() {
        EconomyNecropolisFactory factory = new EconomyNecropolisFactory();
        Resources opponentResources = Resources.builder()
                .gold(100)
                .wood(100)
                .ore(100)
                .gems(100)
                .sulfur(100)
                .mercury(100)
                .build();


        List<EconomyCreature> creatures = new ArrayList<>();
        creatures.add(factory.create(true,1));
        creatures.add(factory.create(true,2));
        Opponent opponent = new Opponent(opponentResources,creatures);

        assertEquals(opponent.getCreatures().get(0),factory.create(true,1));


    }
}