package pl.psi.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;

import java.nio.file.Paths;


class EcoMapTile extends StackPane
{

    private final Rectangle rect;
    private final Label label;

    EcoMapTile(final String aName )
    {
        rect = new Rectangle( 40, 40 );
        rect.setFill( Color.WHITE );
        rect.setStroke( Color.RED );
        getChildren().add( rect );
        label = new Label( aName );
        getChildren().add( label );
    }

    void setName( final String aName )
    {
        label.setText( aName );
    }

    void setBackground( final Color aColor )
    {
        rect.setFill( aColor );
    }

    void setBackgroundImage(String path)
    {
//        Image image = new Image(path);
//        BackgroundImage backgroundImage = new BackgroundImage(image,
//                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.CENTER,
//                new BackgroundSize(40, 40, true, true, false, true));
//        setBackground(new Background(backgroundImage));
//        rect.setVisible(false);
        Image img = new Image(path);
        rect.setFill(new ImagePattern(img));
    }
}
