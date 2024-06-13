package pl.psi.spells.aoe;

import pl.psi.GameEngine;
import pl.psi.Point;

import java.util.ArrayList;
import java.util.List;

public class AOERectangularPointSelection implements AOEPointSelectionStrategyIf {

    @Override
    public List<Point> getTargetPoints(final GameEngine ge, final Point originPoint, final int size) {
        final List<Point> pointList = new ArrayList<>();

        for (int y = originPoint.getY() - size; y <= originPoint.getY() + size; y++) {
            for (int x = originPoint.getX() - size; x <= originPoint.getX() + size; x++) {
                Point p = new Point(x, y);
                if (ge.isValidPoint(p)) {
                    pointList.add(p);
                }
            }
        }

        return pointList;
    }
}
