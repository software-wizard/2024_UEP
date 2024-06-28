package pl.psi.spells.aoe;

import pl.psi.GameEngine;
import pl.psi.Location;
import pl.psi.Point;

import java.util.List;

public interface AOEPointSelectionStrategyIf {

    // zostawiac GE jako argument, czy pozbyc sie go stad calkowicie i przeniesc sprawdzanie czy punkty sa poprawne do AOESpellDecorator?
    List<Location> getTargetPoints(final Location origin, final int size);
}
