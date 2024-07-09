package pl.psi.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.psi.EconomyHero;
import pl.psi.creatures.EconomyNecropolisFactory;
import skills.ArcherySkill;
import skills.ArmorerSkill;
import skills.OffenseSkill;

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
        hero1.addSkill(new OffenseSkill(1));
        hero1.addSkill(new ArcherySkill(3));
        hero1.addSkill(new ArmorerSkill(2));
        hero2.addSkill(new ArmorerSkill(2));
        loader.setController(new EcoController(getHero(hero1, true),
                getHero(hero2, false)));
        final Scene scene = new Scene(loader.load());
        aStage.setScene(scene);
        aStage.setX(5);
        aStage.setY(5);
        aStage.show();
    }

    private static EconomyHero getHero(EconomyHero hero, boolean aIsUpgraded) {
        EconomyNecropolisFactory creatureFactory = new EconomyNecropolisFactory();
        for (int i = 1; i <= 5; i++) {
            hero.addCreature(creatureFactory.create(aIsUpgraded, i));
        }
        return hero;
    }
}
