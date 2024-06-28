package pl.psi.gui;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


class EcoMapTile extends StackPane
{

    private final Rectangle rect;
    private final Label label;
    private final ImageView imageView;
    private boolean collected;

    EcoMapTile(final String aName )
    {
        rect = new Rectangle( 40, 40 );
        rect.setFill( Color.WHITE );
        rect.setStroke( Color.RED );
        getChildren().add( rect );
        label = new Label( aName );
        getChildren().add( label );

        imageView = new ImageView();
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        imageView.setVisible(false);
        getChildren().add(imageView);

        collected = false;
    }

    void setName( final String aName )
    {
        label.setText( aName );
    }

    void setBackground( final Color aColor ) {
        imageView.setVisible(false);
//        Image img = new Image(path);
//        rect.setFill(new ImagePattern(img));
        rect.setFill( aColor );
    }

    void setIcon(final String imagePath) {
        if (collected) {
            return;
        }

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

    void removeIcon() {
        collected = true;
        imageView.setVisible(false);
        imageView.setImage(null);
        rect.setFill(Color.WHITE);
    }


}
