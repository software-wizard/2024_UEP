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
    EconomyHero hero = new EconomyHero("hero");


    public Opponent(Resources resources, List<EconomyCreature> creatures) {
        this.resources = resources;
        this.creatures = creatures;
        hero.setResources(resources);
        creatures.forEach(creature -> hero.addCreature(creature) );

    }

    public void addCreature(EconomyCreature aEconomyCreature) {
        getCreatures().add(aEconomyCreature);
    }
}
