package pl.psi.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.psi.*;
import pl.psi.creatures.Creature;
import pl.psi.spells.object.Spell;

import javax.annotation.Nullable;

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


    private Spell selectedSpell;

    public MainBattleController( final Hero aHero1, final Hero aHero2 )
    {
        this.gameEngine = new GameEngine( aHero1, aHero2 );
        this.selectedSpell = null;
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

    private GridTile getGridTileByPoint(Point p) {
        Node result = null;
        ObservableList<Node> childrens = gridMap.getChildren();

        for (Node node : childrens) {
            if(gridMap.getRowIndex(node) == p.getY() && gridMap.getColumnIndex(node) == p.getX()) {
                result = node;
                break;
            }
        }

        return (GridTile)result;
    }

    private void onSpellCastMapHover(MouseEvent e, Point p) {
        GridTile source = (GridTile)e.getSource();

        List<Location> affectedPoints = selectedSpell.getTargetPoints(new Location(p, gameEngine.getBoard()));
        List<GridTile> tiles = affectedPoints.stream().map(this::getGridTileByPoint).collect(Collectors.toList());

        if (gameEngine.canCastSpell(selectedSpell, p)) {
            for (GridTile tile : tiles) {
                tile.setBackground(Color.AQUA);
            }
        } else {
            source.setBackground(Color.DARKRED);
        }

    }

    private void onSpellCastMapHoverExit(MouseEvent e, Point p) {
        refreshGui();
    }

    private void onSpellCastMapClick(MouseEvent e, Point p) {
        if (gameEngine.canCastSpell(selectedSpell, p)) {
            gameEngine.castSpell(selectedSpell, p);
        }

        gameEngine.pass();
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
                    mapTile.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> onSpellCastMapHover(e, currentPoint));
                    mapTile.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> onSpellCastMapHoverExit(e, currentPoint));
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> onSpellCastMapClick(e, currentPoint));
                }

                creature.ifPresent( c -> mapTile.setName( c.toString() ) );
                if ( gameEngine.isObstacle(currentPoint)) {
                    mapTile.setBackground(Color.BLUE);
                }
                if ( gameEngine.isObstacleWithHP(currentPoint)) {
                    mapTile.setName("HP");
                    mapTile.setBackground(Color.GREEN);
                }
                if (gameEngine.isWall(currentPoint)){
                    mapTile.setBackground(Color.AQUA);
                }
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
        } else if (evt.getPropertyName().equals(TurnQueue.END_OF_TURN)) {
            selectedSpell = null;
        }

        refreshGui();
    }
}
