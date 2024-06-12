package pl.psi.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.psi.EconomyHero;
import pl.psi.blacksmith.ShopItem;
import pl.psi.enums.ItemType;

import java.util.List;

public class BlacksmithGUI extends Stage {

    private BlacksmithController controller;

    public BlacksmithGUI(EconomyHero economyHero, EcoController ecoController) {
        this.controller = new BlacksmithController(economyHero, ecoController);

        setTitle("Heroes III Blacksmith");

        Label magicItemsLabel = new Label("Magiczne przedmioty:");
        Label militaryUnitsLabel = new Label("Jednostki wojskowe:");
        Label spellsLabel = new Label("Zaklęcia:");

        VBox magicItemsSection = new VBox(10);
        VBox militaryUnitsSection = new VBox(10);
        VBox spellsSection = new VBox(10);

        magicItemsSection.getChildren().add(magicItemsLabel);
        militaryUnitsSection.getChildren().add(militaryUnitsLabel);
        spellsSection.getChildren().add(spellsLabel);

        addItemsToSection(magicItemsSection, ItemType.MAGIC_ITEM);
        addItemsToSection(militaryUnitsSection, ItemType.MILITARY_UNIT);
        addItemsToSection(spellsSection, ItemType.SPELL);

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

    private void addItemsToSection(VBox section, ItemType type) {
        List<ShopItem> items = controller.getShop().getItemsByType(type);
        for (ShopItem item : items) {
            section.getChildren().add(createBuyButton(item));
        }
    }

    private Button createBuyButton(ShopItem item) {
        Button buyButton = new Button("Kup " + item.getName() + " za " + item.getRequiredResources().getGold() + " złota");
        buyButton.setOnAction(event -> controller.buyItem(item));
        return buyButton;
    }

}
