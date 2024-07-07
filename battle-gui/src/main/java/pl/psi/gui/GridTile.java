package pl.psi.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

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


        imageView = new ImageView();
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        getChildren().add(imageView);

        label = new Label( aName );
        label.setFont(new Font("Arial", 8));
        StackPane.setAlignment(label, Pos.TOP_LEFT);
        StackPane.setMargin(label, new Insets(0, 0, 0, 4));
        getChildren().add( label );
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
            System.err.println("ERROR with path: " + imagePath);
            e.printStackTrace();
}
    }

    void setBackground( final Color aColor )
    {
        rect.setFill( aColor );
    }
}
