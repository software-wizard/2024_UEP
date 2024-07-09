package pl.psi.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.psi.EconomyHero;
import pl.psi.creatures.EconomyMachineFactory;
import pl.psi.creatures.EconomyNecropolisFactory;
import pl.psi.creatures.MachineFactory;
import skills.*;

public class EconomyStart extends Application {

    public static void main(final String[] args) {
        launch();
    }

    @Override
    public void start(final Stage aStage) throws Exception {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader()
                .getResource("fxml/eco.fxml"));

        EconomyHero hero1 = new EconomyHero("P1");
        EconomyHero hero2 = new EconomyHero("P2");

        loader.setController(new EcoController(getFirstHero(hero1, true), getSecondHero(hero2, false)));
        final Scene scene = new Scene(loader.load());
        aStage.setScene(scene);
        aStage.setX(5);
        aStage.setY(5);
        aStage.show();
    }

    private static EconomyHero getFirstHero(EconomyHero hero, boolean aIsUpgraded) {
        System.out.println("getFirstHero called");

        hero.addSkill(new BallisticsSkill(3));
        hero.addSkill(new LeadershipSkill(3));

        EconomyNecropolisFactory creatureFactory = new EconomyNecropolisFactory();
        EconomyMachineFactory machineFactory = new EconomyMachineFactory();
        for (int i = 1; i <= 1; i++) {
            hero.addCreature(creatureFactory.create(aIsUpgraded, i));
        }
        hero.addCreature(machineFactory.create("Ballista"));
        hero.addCreature(machineFactory.create("First Aid Tent"));
        hero.addCreature(machineFactory.create("Catapult"));
        return hero;
    }


    private static EconomyHero getSecondHero(EconomyHero hero, boolean aIsUpgraded) {
        System.out.println("getSecondHero called");

        hero.addSkill(new ArmorerSkill(2));
        EconomyNecropolisFactory creatureFactory = new EconomyNecropolisFactory();
        EconomyMachineFactory machineFactory = new EconomyMachineFactory();
        for (int i = 2; i <= 4; i++) {
            hero.addCreature(creatureFactory.create(aIsUpgraded, i));
        }
        hero.addCreature(machineFactory.create("Catapult"));
        return hero;
    }
}
