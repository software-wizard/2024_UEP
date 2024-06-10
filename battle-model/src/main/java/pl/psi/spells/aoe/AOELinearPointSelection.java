package pl.psi.spells.aoe;

import pl.psi.GameEngine;
import pl.psi.Point;

import java.util.ArrayList;
import java.util.List;

public class AOELinearPointSelection implements AOEPointSelectionStrategyIf {
    public enum Axis {
        HORIZONTAL,
        VERTICAL
    }
    private final Axis axis;
    public AOELinearPointSelection(Axis axis) {
        this.axis = axis;
    }

    @Override
    public List<Point> getTargetPoints(final GameEngine ge, final Point originPoint, final int size) {
        final List<Point> pointList = new ArrayList<>();

        for (int i = -size; i <= size; i++) {
            Point p = new Point(
                    this.axis == Axis.HORIZONTAL ? originPoint.getX() + i : originPoint.getX(),
                    this.axis == Axis.VERTICAL ? originPoint.getY() + i : originPoint.getY());

            if (ge.isValidPoint(p)) {
                pointList.add(p);
            }
        }

        return pointList;
    }
}
