package pl.psi.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CastleWindow extends Stage {

    public CastleWindow() {
        VBox root = new VBox();

        Text titleText = new Text("TEKST");
        root.getChildren().add(titleText);

        Scene scene = new Scene(root, 400, 300);
        setScene(scene);
    }


}
