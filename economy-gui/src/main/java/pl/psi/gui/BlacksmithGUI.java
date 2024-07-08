package pl.psi.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import pl.psi.EconomyHero;
import pl.psi.blacksmith.ShopItem;
import pl.psi.enums.ItemType;

import java.util.List;

public class BlacksmithGUI extends Stage {

    private BlacksmithController controller;
    private EconomyHero economyHero;
    private TextArea creaturesStatus;

    public BlacksmithGUI(EconomyHero economyHero, EcoController ecoController) {
        this.controller = new BlacksmithController(economyHero, ecoController);
        this.economyHero = economyHero;

        setTitle("Heroes III Blacksmith");

        Label magicItemsLabel = createStyledLabel("Artifacts:");
        Label militaryUnitsLabel = createStyledLabel("Creatures:");
        Label spellsLabel = createStyledLabel("Spells:");

        VBox magicItemsSection = createStyledVBox();
        VBox militaryUnitsSection = createStyledVBox();
        VBox spellsSection = createStyledVBox();

        magicItemsSection.getChildren().add(magicItemsLabel);
        militaryUnitsSection.getChildren().add(militaryUnitsLabel);
        spellsSection.getChildren().add(spellsLabel);

        addItemsToSection(magicItemsSection, ItemType.MAGIC_ITEM);
        addItemsToSection(militaryUnitsSection, ItemType.MILITARY_UNIT);
        addItemsToSection(spellsSection, ItemType.SPELL);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(20);
        grid.setHgap(20);
        grid.setStyle("-fx-background-color: #2e2e2e;");

        grid.add(magicItemsSection, 0, 0);
        grid.add(militaryUnitsSection, 1, 0);
        grid.add(spellsSection, 2, 0);

        creaturesStatus = new TextArea();
        creaturesStatus.setEditable(false);
        creaturesStatus.setFont(Font.font("Verdana", 14));
        creaturesStatus.setStyle("-fx-control-inner-background: #3e3e3e; -fx-text-fill: white;");
        updateCreaturesStatus();
        grid.add(createStyledLabel("Current status of units:"), 0, 1, 3, 1);
        grid.add(creaturesStatus, 0, 2, 3, 1);

        Scene scene = new Scene(grid, 900, 600);
        setScene(scene);
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Verdana", 18));
        label.setTextFill(Color.web("#eeeeee"));
        return label;
    }

    private VBox createStyledVBox() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(10));
        vbox.setStyle("-fx-background-color: #444444; -fx-border-color: #888888; -fx-border-width: 2px;");
        return vbox;
    }

    private void addItemsToSection(VBox section, ItemType type) {
        List<ShopItem> items = controller.getShop().getItemsByType(type);
        for (ShopItem item : items) {
            section.getChildren().add(createBuyButton(item));
        }
    }

    private Button createBuyButton(ShopItem item) {
        Button buyButton = new Button("Buy " + item.getName() + " for " + item.getRequiredResources().getGold() + " gold");
        buyButton.setFont(Font.font("Verdana", 14));
        buyButton.setStyle("-fx-background-color: #5a5a5a; -fx-text-fill: white; -fx-border-color: #888888; -fx-border-width: 1px;");
        buyButton.setOnMouseEntered(e -> buyButton.setStyle("-fx-background-color: #6a6a6a; -fx-text-fill: white; -fx-border-color: #888888; -fx-border-width: 1px;"));
        buyButton.setOnMouseExited(e -> buyButton.setStyle("-fx-background-color: #5a5a5a; -fx-text-fill: white; -fx-border-color: #888888; -fx-border-width: 1px;"));
        buyButton.setOnAction(event -> {
            controller.buyItem(item);
            controller.addCreatureToHero(item);
            updateCreaturesStatus();
        });

        return buyButton;
    }

    private void updateCreaturesStatus() {
        String statusText = controller.updateCreaturesStatus();
        creaturesStatus.setText(statusText);
    }
}
