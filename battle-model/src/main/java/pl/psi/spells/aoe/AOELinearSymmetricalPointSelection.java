package pl.psi.spells.aoe;

import pl.psi.GameEngine;
import pl.psi.Location;
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
    public List<Location> getTargetPoints(final Location origin, final int size) {
        final List<Location> pointList = new ArrayList<>();

        int halfSize = size / 2;

        int start = -halfSize;
        int end = halfSize;

        if (size % 2 == 0) {
            start += 1;
        }

        for (int i = start; i <= end; i++) {
            Point p = new Point(
                    this.axis == Axis.HORIZONTAL ? origin.getX() + i : origin.getX(),
                    this.axis == Axis.VERTICAL ? origin.getY() + i : origin.getY());

            if (origin.getBoard().isValidPoint(p)) {
                pointList.add(new Location(p, origin.getBoard()));
            }
        }


        return pointList;
    }
}
