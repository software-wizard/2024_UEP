package pl.psi.gui;

import java.io.IOException;
import java.util.List;

import pl.psi.Hero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.psi.PrimarySkill;
import pl.psi.creatures.NecropolisFactory;
import pl.psi.spells.object.SpellFactory;
import pl.psi.spells.spellbook.Spellbook;

public class Start extends Application
{

    public Start()
    {
    }

    static void main( final String[] args )
    {
        launch( args );
    }

    @Override
    public void start( final Stage primaryStage )
    {
        Scene scene = null;
        try
        {
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation( Start.class.getClassLoader()
                .getResource( "fxml/main-battle.fxml" ) );
            loader.setController( new MainBattleController( createP1(), createP2() ) );
            scene = new Scene( loader.load() );
            primaryStage.setScene( scene );
            primaryStage.setX( 5 );
            primaryStage.setY( 5 );
            primaryStage.show();
        }
        catch( final IOException aE )
        {
            aE.printStackTrace();
        }
    }

    private Hero createP2()
    {
        final Hero ret = new Hero(
                List.of(
                        new NecropolisFactory().create( true, 1, 5 )
                ),
                new PrimarySkill(0, 0, 2, 0),
                new Spellbook(SpellFactory.all()));
        return ret;
    }

    private Hero createP1()
    {
        final Hero ret = new Hero(
                List.of(
                        new NecropolisFactory().create( true, 1, 5 )
                ),
                new PrimarySkill(0, 0, 1, 0),
                new Spellbook(SpellFactory.all()));
        return ret;
    }

}
