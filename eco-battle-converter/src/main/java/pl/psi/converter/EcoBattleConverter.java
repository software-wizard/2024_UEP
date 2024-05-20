package pl.psi.converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.psi.EconomyHero;
import pl.psi.Hero;
import pl.psi.StartBattlePack;
import pl.psi.spells.Spellbook;
import skills.BattleSkill;
import pl.psi.creatures.Creature;
import pl.psi.creatures.NecropolisFactory;
import pl.psi.gui.MainBattleController;
import pl.psi.skills.Skill;

public class EcoBattleConverter {

    public static void startBattle(StartBattlePack aPack) {
        Scene scene = null;
        try {
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EcoBattleConverter.class.getClassLoader()
                    .getResource("fxml/main-battle.fxml"));
            loader.setController(new MainBattleController(convert(aPack.getAttacker()), convert(aPack.getDefender())));
            scene = new Scene(loader.load());
            final Stage aStage = new Stage();
            aStage.setScene(scene);
            aStage.setX(5);
            aStage.setY(5);
            aStage.show();
        } catch (final IOException aE) {
            aE.printStackTrace();
        }
    }

    public static Hero convert(final EconomyHero aPlayer1) {
        final List<Creature> creatures = new ArrayList<>();
        final NecropolisFactory factory = new NecropolisFactory();
        aPlayer1.getCreatures()
                .forEach(ecoCreature -> creatures
                        .add(factory.create(ecoCreature.isUpgraded(), ecoCreature.getTier(), 1)));

        // Zakładam ze skille "nie battle" nie muszą byc zalaczane tutaj, poniewaz one musza dzialac jak sobie biegamy po mapie
        for (Skill skill : aPlayer1.getSkills().values()) {
            if (skill instanceof BattleSkill) {
                ((BattleSkill) skill).cast(creatures);
            }
        }

        return new Hero(creatures, new Spellbook(Collections.emptyList()));
    }
}
