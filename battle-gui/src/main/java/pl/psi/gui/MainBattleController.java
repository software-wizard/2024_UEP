package pl.psi.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.psi.GameEngine;
import pl.psi.Hero;
import pl.psi.Point;
import pl.psi.creatures.Creature;
import pl.psi.spells.Spell;

public class MainBattleController implements PropertyChangeListener
{
    private final GameEngine gameEngine;
    @FXML
    private GridPane gridMap;
    @FXML
    private Button passButton;

    @FXML
    private Button spellButton;

    private Stage spellGridStage;
    private Spell selectedSpell = null;

    public MainBattleController( final Hero aHero1, final Hero aHero2 )
    {
        gameEngine = new GameEngine( aHero1, aHero2 );
        initializeSpellGrid();
    }

    private void initializeSpellGrid() {
        Scene scene = null;
        try
        {
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation( Start.class.getClassLoader()
                    .getResource( "fxml/spellbook.fxml" ) );
            SpellbookController controller = new SpellbookController(gameEngine);
            controller.addObserver(this);
            loader.setController(controller);
            scene = new Scene( loader.load() );
            spellGridStage = new Stage();
            spellGridStage.setTitle("Spellbook");
            spellGridStage.setScene(scene);
            spellGridStage.setX(5);
            spellGridStage.setY(5);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void showSpellGrid() {
        spellGridStage.show();
    }

    @FXML
    private void initialize()
    {
        refreshGui();
        gameEngine.addObserver( this );
        passButton.addEventHandler( MouseEvent.MOUSE_CLICKED, ( e ) -> gameEngine.pass() );
        spellButton.addEventHandler(MouseEvent.MOUSE_CLICKED, ( e ) -> showSpellGrid());
    }

    private void onMouseEnterGridTile(MouseEvent e) {
        GridTile source = (GridTile)e.getSource();
        source.setBackground(Color.AQUA);
    }

    private void onMouseExitGridTile(MouseEvent e) {
        refreshGui();
    }

    private void refreshGui()
    {
        gridMap.getChildren()
            .clear();
        for( int x = 0; x < 15; x++ )
        {
            for( int y = 0; y < 10; y++ )
            {
                Point currentPoint = new Point( x, y );
                Optional< Creature > creature = gameEngine.getCreature( currentPoint );
                final GridTile mapTile = new GridTile( "" );

                if (selectedSpell != null) {
                    mapTile.addEventHandler(MouseEvent.MOUSE_ENTERED, this::onMouseEnterGridTile);
                    mapTile.addEventHandler(MouseEvent.MOUSE_EXITED, this::onMouseExitGridTile);
                }

                creature.ifPresent( c -> mapTile.setName( c.toString() ) );
                if( gameEngine.isCurrentCreature( currentPoint ) )
                {
                    mapTile.setBackground( Color.GREENYELLOW );
                }
                if( gameEngine.canMove( currentPoint ) )
                {
                    mapTile.setBackground( Color.GREY );
                    mapTile.addEventHandler( MouseEvent.MOUSE_CLICKED,
                        ( e ) -> { gameEngine.move( currentPoint ); } );
                }
                if( gameEngine.canAttack( currentPoint ) )
                {
                    mapTile.setBackground( Color.RED );
                    mapTile.addEventHandler( MouseEvent.MOUSE_CLICKED,
                        ( e ) -> { gameEngine.attack( currentPoint ); } );
                }
                gridMap.add( mapTile, x, y );
            }
        }
    }

    @Override
    public void propertyChange( PropertyChangeEvent evt )
    {
        System.out.println(evt.getPropertyName());
        if (evt.getPropertyName().equals(SpellbookController.SPELL_SELECTED_EVENT)) {
            selectedSpell = (Spell)evt.getNewValue();
            spellGridStage.hide();
        }

        refreshGui();
    }
}
