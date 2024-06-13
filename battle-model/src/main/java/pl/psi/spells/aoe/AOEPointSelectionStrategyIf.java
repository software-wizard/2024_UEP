package pl.psi.spells.aoe;

import pl.psi.GameEngine;
import pl.psi.Point;

import java.util.List;

public interface AOEPointSelectionStrategyIf {

    // zostawiac GE jako argument, czy pozbyc sie go stad calkowicie i przeniesc sprawdzanie czy punkty sa poprawne do AOESpellDecorator?
    List<Point> getTargetPoints(final GameEngine ge, final Point originPoint, final int size);
}
