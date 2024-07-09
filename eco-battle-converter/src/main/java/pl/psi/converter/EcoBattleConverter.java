package pl.psi.converter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.psi.*;
import pl.psi.creatures.Creature;
import pl.psi.creatures.MachineFactory;
import pl.psi.creatures.NecropolisFactory;
import pl.psi.gui.MainBattleController;
import pl.psi.skills.Skill;
import pl.psi.spells.Spellbook;
import skills.SkillFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EcoBattleConverter implements PropertyChangeListener {

    private static StartBattlePack lastBattlePack;

    public static void startBattle(StartBattlePack aPack) {
        lastBattlePack = aPack;
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
        SkillFactory skillFactory = new SkillFactory();
        final List<Creature> creatures = new ArrayList<>();
        final NecropolisFactory factory = new NecropolisFactory();
        final MachineFactory machineFactory = new MachineFactory();
        aPlayer1.getCreatures().forEach(ecoCreature -> {
            if (ecoCreature.isMachine()) {
                // Convert using MachineFactory
                creatures.add(machineFactory.create(ecoCreature.getName()));
            } else {
                // Convert using NecropolisFactory for regular creatures
                creatures.add(factory.create(ecoCreature.isUpgraded(), ecoCreature.getTier(), 10, ecoCreature.getMoraleValue()));
            }
        });
        for (Skill skill : aPlayer1.getSkills().values()) {
            skillFactory.create(skill.getSkillName(), skill.getLevel()).cast(creatures);
        }

        return new Hero(creatures, new PrimarySkill(0, 0, 0, 0), new Spellbook(Collections.emptyList()));
    }

    public static Hero hardConvert1(final EconomyHero aPlayer1) {
        SkillFactory skillFactory = new SkillFactory();
        final List<Creature> creatures = new ArrayList<>();
        final NecropolisFactory factory = new NecropolisFactory();
        final MachineFactory machineFactory = new MachineFactory();
        aPlayer1.getCreatures().forEach(ecoCreature -> {
            if (ecoCreature.isMachine()) {
                // Convert using MachineFactory
                creatures.add(machineFactory.create(ecoCreature.getName()));
            } else {
                // Convert using NecropolisFactory for regular creatures
                creatures.add(factory.create(ecoCreature.isUpgraded(), ecoCreature.getTier(), 10, ecoCreature.getMoraleValue()));
            }
        });
        for (Skill skill : aPlayer1.getSkills().values()) {
            skillFactory.create(skill.getSkillName(), skill.getLevel()).cast(creatures);
        }

        return new Hero(creatures, new PrimarySkill(0, 0, 0, 0), new Spellbook(Collections.emptyList()));
    }

    public static Hero hardConvert2(final EconomyHero aPlayer1) {
        SkillFactory skillFactory = new SkillFactory();
        final List<Creature> creatures = new ArrayList<>();
        final NecropolisFactory factory = new NecropolisFactory();
        final MachineFactory machineFactory = new MachineFactory();

        Creature luckyLitch = new NecropolisFactory().create(false, 5, 10, 3);


        aPlayer1.getCreatures().forEach(ecoCreature -> {
            if (ecoCreature.isMachine()) {
                // Convert using MachineFactory
                creatures.add(machineFactory.create(ecoCreature.getName()));
            } else {
                // Convert using NecropolisFactory for regular creatures
                creatures.add(factory.create(ecoCreature.isUpgraded(), ecoCreature.getTier(), 10, ecoCreature.getMoraleValue()));
            }
        });

        for (Skill skill : aPlayer1.getSkills().values()) {
            skillFactory.create(skill.getSkillName(), skill.getLevel()).cast(creatures);
        }

        return new Hero(creatures, new PrimarySkill(0, 0, 0, 0), new Spellbook(Collections.emptyList()));
    }

    //obserwer i potem aktualizuje od walki i potem ustawia lastBattlePack na null
    void update() {
        lastBattlePack.getAttacker().changeResources(lastBattlePack.getDefender().getResources());

        lastBattlePack = null;
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (GameEngine.BATTLE_ENDED.equals(evt.getPropertyName())) {
            boolean attackerWon = (boolean) evt.getNewValue();
            if (attackerWon) {
                update();
            }
        }
    }


}

