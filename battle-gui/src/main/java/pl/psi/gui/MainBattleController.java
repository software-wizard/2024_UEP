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
import pl.psi.*;
import pl.psi.creatures.Creature;
import pl.psi.obstacles.Obstacle;
import pl.psi.obstacles.ObstaclesWithHP;
import pl.psi.obstacles.Wall;
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

    private void onSpellCastMapHover(MouseEvent e, Point p) {
        GridTile source = (GridTile)e.getSource();

        final Hero hero = gameEngine.getHeroToMove();

        if (hero.getSpellbook().canCast(selectedSpell, hero, new Location(p, gameEngine.getBoard()))) {
            source.setBackground(Color.AQUA);
        } else {
            source.setBackground(Color.DARKRED);
        }

    }

    private void onSpellCastMapHoverExit(MouseEvent e, Point p) {
        refreshGui();
    }

    private void onSpellCastMapClick(MouseEvent e, Point p) {
        final Hero hero = gameEngine.getHeroToMove();
        final Location loc = new Location(p, gameEngine.getBoard());

        if (hero.getSpellbook().canCast(selectedSpell, hero, loc)) {
            hero.getSpellbook().castSpell(selectedSpell, hero, loc);
        }

        gameEngine.pass();
    }

    private void refreshGui()
    {
        gridMap.getChildren()
            .clear();
        Obstacle obstacle = new Obstacle();
        for( int x = 0; x < 15; x++ )
        {
            for( int y = 0; y < 10; y++ )
            {
                Point currentPoint = new Point( x, y );
                Optional< Creature > creature = gameEngine.getCreature( currentPoint );
                Optional<Wall> wall = gameEngine.getWall(currentPoint);
                Optional<ObstaclesWithHP> obstaclesWithHP = gameEngine.getObstacleWithHP(currentPoint);
                final GridTile mapTile = new GridTile( "" );

                if (selectedSpell != null) {
                    mapTile.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> onSpellCastMapHover(e, currentPoint));
                    mapTile.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> onSpellCastMapHoverExit(e, currentPoint));
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> onSpellCastMapClick(e, currentPoint));
                }

                creature.ifPresent(c -> {
                    mapTile.setName(c.toString());
                    mapTile.setIcon(c.getImagePath());
                });
                wall.ifPresent( w -> {
                    mapTile.setIcon( w.getImagePath());
                    mapTile.setName( w.toStringHP());
                });
                obstaclesWithHP.ifPresent( o -> {
                    mapTile.setIcon(o.getImagePath());
                    mapTile.setName(o.toCurrentHPString() );
                });
                if ( gameEngine.isObstacle(currentPoint)) {
                    mapTile.setIcon(obstacle.getImagePath());
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
                if (gameEngine.canHeal(currentPoint)) {
                    mapTile.setBackground(Color.LIGHTGREEN);
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            (e) -> { gameEngine.heal(currentPoint); });
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
