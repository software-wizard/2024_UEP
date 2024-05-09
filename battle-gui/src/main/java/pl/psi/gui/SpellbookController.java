package pl.psi.gui;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import pl.psi.GameEngine;
import pl.psi.Hero;
import pl.psi.TurnQueue;
import pl.psi.spells.Spell;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SpellbookController implements PropertyChangeListener {
    public static final String SPELL_SELECTED_EVENT = "SPELL_SELECTED_EVENT";
    private static final int COLUMN_COUNT = 5;
    private final GameEngine ge;

    @FXML
    private GridPane spellGrid;

    private final PropertyChangeSupport propChangeSupport = new PropertyChangeSupport(this);

    public SpellbookController(final GameEngine aGe) {
        ge = aGe;
    }

    @FXML
    public void initialize() {
        ge.addObserver(this);
        refreshGUI();
    }

    private void onClickSpell(Spell spell) {
        propChangeSupport.firePropertyChange(SPELL_SELECTED_EVENT, null, spell);
    }

    private void refreshGUI() {
        spellGrid.getChildren().clear();

        Hero currentHero = ge.getHeroToMove();


        int countAdded = 0;

        for (Spell spell : currentHero.getSpellbook().getSpells()) {
            GridTile tile = new GridTile(spell.getStats().getName());

            tile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> onClickSpell(spell));

            int x = countAdded % COLUMN_COUNT;
            int y = Math.floorDiv(countAdded, COLUMN_COUNT);

            spellGrid.add(tile, x, y);
            countAdded++;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(TurnQueue.END_OF_TURN)) {
            refreshGUI();
        }
    }

    public void addObserver(final PropertyChangeListener aObserver) {
        propChangeSupport.addPropertyChangeListener(aObserver);
    }
}
