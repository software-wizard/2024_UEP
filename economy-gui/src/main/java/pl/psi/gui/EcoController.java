package pl.psi.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.sun.javafx.charts.Legend;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import lombok.NoArgsConstructor;
import pl.psi.EconomyEngine;
import pl.psi.EconomyHero;
import pl.psi.Point;
import pl.psi.converter.EcoBattleConverter;
import pl.psi.skills.Skill;

@NoArgsConstructor
public class EcoController implements PropertyChangeListener {
    private EconomyEngine engine;


    @FXML
    private VBox resourcesBox;

    @FXML
    private GridPane grid;
    @FXML
    private Button passButton;
    @FXML
    private Label allResourcesLabel;
//    @FXML
//    private ListView<String> skillsList;

    private ObservableList<String> skills;

    public EcoController(final EconomyHero aHero1, final EconomyHero aHero2) {
        engine = new EconomyEngine(aHero1, aHero2);
    }

    @FXML
    void initialize() {
        refreshGui();
        engine.addObserver(EconomyEngine.HERO_MOVED, this);
        engine.addObserver(EconomyEngine.ACTIVE_HERO_CHANGED, this);
        engine.addObserver(EconomyEngine.TURN_END, this);
        passButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> engine.pass());


    }

    void refreshGui() {
        grid.getChildren()
                .clear();


        for (int x = 0; x < EconomyEngine.BOARD_WEIGHT; x++) {
            for (int y = 0; y < EconomyEngine.BOARD_HEIGHT; y++) {
                Point currentPoint = new Point(x, y);
                final EcoMapTile mapTile = new EcoMapTile("");

                engine.getHero(currentPoint).ifPresent(h -> mapTile.setName(h.getName()));

                if (engine.canMove(currentPoint)) {
                    mapTile.setBackground(Color.LIGHTGRAY);
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            (e) -> engine.move(currentPoint));
                }

                if (engine.isBattlePoint(currentPoint)) {
                    mapTile.setBackground(Color.RED);
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            (e) -> EcoBattleConverter.startBattle(engine.getStartBattlePack(currentPoint)));
                }

                if (engine.isOpponentPoint(currentPoint)) {
                    mapTile.setBackground(Color.YELLOW);
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            (e) -> EcoBattleConverter.startBattle(engine.getStartBattlePack(currentPoint)));
                }


                if (engine.isCurrentHero(currentPoint)) {
                    mapTile.setBackground(Color.GREENYELLOW);
                }

                if (engine.isCastle(currentPoint)) {
                    mapTile.setBackground(Color.BROWN);
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        if (engine.isCurrentHero(currentPoint)) {
                            BlacksmithGUI castleWindow = new BlacksmithGUI(engine.getCurrentHero(), this);
                            castleWindow.show();
                        }
                    });
                }

                if (engine.isFieldPoint(currentPoint)) {
                    if (engine.isGoldField(currentPoint)) {
                        mapTile.setIcon("/resourcesIcons/gold.png");
                    } else if (engine.isWoodField(currentPoint)) {
                        mapTile.setIcon("/resourcesIcons/wood.png");
                    } else if (engine.isOreField(currentPoint)) {
                        mapTile.setIcon("/resourcesIcons/ore.png");
                    } else if (engine.isGemsField(currentPoint)) {
                        mapTile.setIcon("/resourcesIcons/gems.png");
                    } else if (engine.isSulfurField(currentPoint)) {
                        mapTile.setIcon("/resourcesIcons/sulfur.png");
                    } else if (engine.isMercuryField(currentPoint)) {
                        mapTile.setIcon("/resourcesIcons/mercury.png");
                    } else if (engine.isCristalsField(currentPoint)) {
                        mapTile.setIcon("/resourcesIcons/crystals.png");
                    }

                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        if (engine.isCurrentHero(currentPoint)) {
                            mapTile.removeIcon(); // nie działa
                            engine.collectField(engine.getField(currentPoint));

                            refreshGui();
                        }
                    });
                }


                grid.add(mapTile, x, y);
            }
        }

        allResourcesLabel.setText(engine.getCurrentHero().getResources().getAllResourcesAsString());

        List<String> skillsData = engine.getCurrentHero().getSkills().values().stream().map(Skill::toString).collect(Collectors.toList());
        skills = FXCollections.observableArrayList(skillsData);
//        skillsList.setItems(skills);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent aPropertyChangeEvent) {
        refreshGui();

    }

    public void updateAllResourcesLabel() {
        allResourcesLabel.setText(engine.getCurrentHero().getResources().getAllResourcesAsString());
    }
}
