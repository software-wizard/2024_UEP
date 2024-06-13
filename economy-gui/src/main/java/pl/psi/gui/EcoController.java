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
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
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
    @FXML
    private ListView<String> skillsList;

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
//        initSkillToolTip();

    }

    void refreshGui() {
        grid.getChildren()
                .clear();


        for (int x = 0; x < EconomyEngine.BOARD_WEIGHT; x++) {
            for (int y = 0; y < EconomyEngine.BOARD_HEIGHT; y++) {
                Point currentPoint = new Point(x, y);
                final EcoMapTile mapTile = new EcoMapTile("");

                engine.getHero(currentPoint).ifPresent(h -> {
                    mapTile.setName(h.getName());
                    Tooltip tooltip = new Tooltip("Hero: " + h.getName());
                    tooltip.setShowDelay(new Duration(200));
//                    System.out.println("show delay: " + tooltip.getShowDelay());
//                    System.out.println("hide delay: " + tooltip.getHideDelay());
                    tooltip.setText(h.skillsAsList());
                    Tooltip.install(mapTile, tooltip);
                });

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


                if (engine.isCurrentHero(currentPoint)) {
                    mapTile.setBackground(Color.GREENYELLOW);
                }

                if (engine.isCastle(currentPoint)) {
                    mapTile.setBackground(Color.BROWN);
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        if (engine.isCurrentHero(currentPoint)) {
                            CastleWindow castleWindow = new CastleWindow();
                            castleWindow.show();
                        }
                    });
                }

                if (engine.isFieldPoint(currentPoint)) {
                    mapTile.setBackground(Color.GREENYELLOW);
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        if (engine.isCurrentHero(currentPoint)) {
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
        skillsList.setItems(skills);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent aPropertyChangeEvent) {
        refreshGui();

    }
}
