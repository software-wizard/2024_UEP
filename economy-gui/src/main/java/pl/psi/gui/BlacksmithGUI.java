package pl.psi.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.psi.EconomyHero;
import pl.psi.Resources;

public class BlacksmithGUI extends Stage {

    private EconomyHero economyHero;
    private EcoController ecoController;

    public BlacksmithGUI(EconomyHero economyHero, EcoController ecoController){
        this.economyHero = economyHero;
        this.ecoController = ecoController;

        setTitle("Heroes III Blacksmith");

        Label magicItemsLabel = new Label("Magiczne przedmioty:");
        Label militaryUnitsLabel = new Label("Jednostki wojskowe:");
        Label spellsLabel = new Label("Zaklęcia:");

        VBox magicItemsSection = new VBox(10);
        magicItemsSection.getChildren().addAll(magicItemsLabel, createMagicItemButton("Amulet Mocy", 200), createMagicItemButton("Szata Mędrcy", 150), createMagicItemButton("Miecz Ognia", 300));

        VBox militaryUnitsSection = new VBox(10);
        militaryUnitsSection.getChildren().addAll(militaryUnitsLabel, createMilitaryUnitButton("Łucznicy", 50), createMilitaryUnitButton("Piechurzy", 30), createMilitaryUnitButton("Kawaleria", 100));

        VBox spellsSection = new VBox(10);
        spellsSection.getChildren().addAll(spellsLabel, createSpellButton("Piorun", 80), createSpellButton("Ognista Kula", 120), createSpellButton("Leczenie", 50));

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(magicItemsSection, 0, 0);
        grid.add(militaryUnitsSection, 1, 0);
        grid.add(spellsSection, 2, 0);

        Scene scene = new Scene(grid, 700, 400);
        setScene(scene);
    }

    private Button createMagicItemButton(String itemName, int cost) {
        Button buyButton = new Button("Kup " + itemName + " za " + cost + " złota");
        buyButton.setOnAction(event -> {
            Resources requiredResources = Resources.builder().gold(cost).build();

            if (economyHero.getResources().hasEnough(requiredResources)) {
                economyHero.setResources(economyHero.getResources().subtract(requiredResources));
                ecoController.updateAllResourcesLabel();
                System.out.println(economyHero.getResources().getGold());
                updateResourceLabels();
            } else {
                System.out.println("Nie masz wystarczająco zasobów do zakupu " + itemName);
            }
        });
        return buyButton;
    }

    private Button createMilitaryUnitButton(String unitName, int cost) {
        Button buyButton = new Button("Kup " + unitName + " za " + cost + " złota");
        buyButton.setOnAction(event -> {
            Resources requiredResources = Resources.builder().gold(cost).build();

            if (economyHero.getResources().hasEnough(requiredResources)) {
                economyHero.setResources(economyHero.getResources().subtract(requiredResources));
                System.out.println(economyHero.getResources().getGold());
                ecoController.updateAllResourcesLabel();
            } else {
                System.out.println("Nie masz wystarczająco zasobów do zakupu " + unitName);
            }
        });
        return buyButton;
    }

    private Button createSpellButton(String spellName, int cost) {
        Button buyButton = new Button("Kup " + spellName + " za " + cost + " złota");
        buyButton.setOnAction(event -> {
            Resources requiredResources = Resources.builder().gold(cost).build();

            if (economyHero.getResources().hasEnough(requiredResources)) {
                economyHero.setResources(economyHero.getResources().subtract(requiredResources));
                System.out.println(economyHero.getResources().getGold());
                ecoController.updateAllResourcesLabel();
            } else {
                System.out.println("Nie masz wystarczająco zasobów do zakupu " + spellName);
            }
        });
        return buyButton;
    }

    private void updateResourceLabels() {



    }
}
