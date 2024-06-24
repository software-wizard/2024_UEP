package pl.psi.spells.aoe;

import pl.psi.GameEngine;
import pl.psi.Location;
import pl.psi.Point;

import java.util.ArrayList;
import java.util.List;

public class AOERectangularPointSelection implements AOEPointSelectionStrategyIf {

    @Override
    public List<Location> getTargetPoints(final Location origin, final int size) {
        final List<Location> pointList = new ArrayList<>();

        for (int y = origin.getY() - size; y <= origin.getY() + size; y++) {
            for (int x = origin.getX() - size; x <= origin.getX() + size; x++) {
                Point p = new Point(x, y);
                if (origin.getBoard().isValidPoint(p)) {
                    pointList.add(new Location(p, origin.getBoard()));
                }
            }
        }

        return pointList;
    }
}
