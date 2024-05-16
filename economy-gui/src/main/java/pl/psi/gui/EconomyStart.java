package pl.psi.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.psi.EconomyHero;
import pl.psi.creatures.EconomyNecropolisFactory;

public class EconomyStart extends Application {

    public static void main(final String[] args) {
        launch();
    }

    @Override
    public void start(final Stage aStage) throws Exception {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader()
                .getResource("fxml/eco.fxml"));
        loader.setController(new EcoController(getHero("P1", true),
                getHero("P2", false)));
        final Scene scene = new Scene(loader.load());
        aStage.setScene(scene);
        aStage.setX(5);
        aStage.setY(5);
        aStage.show();
    }

    private static EconomyHero getHero(String aHeroName, boolean aIsUpgraded) {
        EconomyHero hero = new EconomyHero(aHeroName);
        EconomyNecropolisFactory creatureFactory = new EconomyNecropolisFactory();
        for (int i = 1; i <= 7; i++) {
            hero.addCreature(creatureFactory.create(aIsUpgraded, i));
        }
        return hero;
    }
}
