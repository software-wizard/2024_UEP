package pl.psi.gui;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Objects;

class GridTile extends StackPane
{

    private final Rectangle rect;
    private final Label label;
    private ImageView imageView;

    GridTile(final String aName )
    {
        rect = new Rectangle( 60, 60 );
        rect.setFill( Color.WHITE);
        rect.setStroke( Color.RED );
        getChildren().add( rect );
        label = new Label( aName );
        getChildren().add( label );
        imageView = new ImageView();
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        getChildren().add(imageView);
        label.maxWidth(rect.getWidth());
    }

    void setName( final String aName )
    {
        label.setText( aName );
    }

    void setIcon(final String imagePath) {
        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            imageView.setImage(image);
            imageView.setVisible(true);
            rect.setFill(Color.TRANSPARENT);
    } catch (Exception e) {
        System.err.println("Nie można załadować obrazu: " + imagePath);
        e.printStackTrace();
}
    }

    void setBackground( final Color aColor )
    {
        rect.setFill( aColor );
    }
}
