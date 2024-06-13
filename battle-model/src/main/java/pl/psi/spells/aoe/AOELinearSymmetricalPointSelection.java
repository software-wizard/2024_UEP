package pl.psi.spells.aoe;

import pl.psi.GameEngine;
import pl.psi.Point;

import java.util.ArrayList;
import java.util.List;

public class AOELinearSymmetricalPointSelection implements AOEPointSelectionStrategyIf {
    public enum Axis {
        HORIZONTAL,
        VERTICAL
    }
    private final Axis axis;
    public AOELinearSymmetricalPointSelection(Axis axis) {
        this.axis = axis;
    }

    @Override
    public List<Point> getTargetPoints(final GameEngine ge, final Point originPoint, final int size) {
        final List<Point> pointList = new ArrayList<>();

        int halfSize = size / 2;

        int start = -halfSize;
        int end = halfSize;

        if (size % 2 == 0) {
            start += 1;
        }

        for (int i = start; i <= end; i++) {
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
