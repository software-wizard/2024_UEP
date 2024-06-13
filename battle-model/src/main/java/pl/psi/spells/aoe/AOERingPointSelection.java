package pl.psi.spells.aoe;

import pl.psi.GameEngine;
import pl.psi.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AOERingPointSelection implements AOEPointSelectionStrategyIf {


    @Override
    public List<Point> getTargetPoints(final GameEngine ge, final Point originPoint, final int size) {
        final List<Point> pointList = new ArrayList<>();

        int startX = originPoint.getX() - size;
        int endX = originPoint.getX() + size;
        int startY = originPoint.getY() - size;
        int endY = originPoint.getY() + size;

        for (int x = startX; x <= endX; x++) {
            Point p1 = new Point(x, startY);
            Point p2 = new Point(x, endY);

            if (ge.isValidPoint(p1)) {
                pointList.add(new Point(x, startY));
            }
            if (ge.isValidPoint(p2)) {
                pointList.add(new Point(x, endY));
            }
        }

        for (int y = startY + 1; y < endY; y++) {
            Point p1 = new Point(startX, y);
            Point p2 = new Point(endX, y);

            if (ge.isValidPoint(p1)) {
                pointList.add(new Point(startX, y));
            }
            if (ge.isValidPoint(p2)) {
                pointList.add(new Point(endX, y));
            }
        }

        return pointList;
    }
}
