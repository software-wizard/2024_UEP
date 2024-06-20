package pl.psi;

import lombok.Getter;

import pl.psi.creatures.CreatureStatistic;
import pl.psi.creatures.EconomyCreature;

import java.util.Collections;
import java.util.List;


@Getter
public class Opponent  {

    Resources resources;
    List<EconomyCreature> creatures;
    EconomyHero hero = new EconomyHero("Opponent", creatures,0,0,resources,null,Collections.emptyMap());

    public Opponent(Resources resources, List<EconomyCreature> creatures) {
        this.resources = resources;
        this.creatures = creatures;
    }

    public void addCreature(EconomyCreature aEconomyCreature) {
        getCreatures().add(aEconomyCreature);
    }
}
