package pl.psi.spells.aoe;

import pl.psi.BoardIf;
import pl.psi.GameEngine;
import pl.psi.Location;
import pl.psi.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AOERingPointSelection implements AOEPointSelectionStrategyIf {


    @Override
    public List<Location> getTargetPoints(final Location origin, final int size) {
        final List<Location> pointList = new ArrayList<>();
        final BoardIf board = origin.getBoard();

        int startX = origin.getX() - size;
        int endX = origin.getX() + size;
        int startY = origin.getY() - size;
        int endY = origin.getY() + size;

        for (int x = startX; x <= endX; x++) {
            Point p1 = new Point(x, startY);
            Point p2 = new Point(x, endY);

            if (origin.getBoard().isValidPoint(p1)) {
                pointList.add(new Location(x, startY, board));
            }
            if (origin.getBoard().isValidPoint(p2)) {
                pointList.add(new Location(x, endY, board));
            }
        }

        for (int y = startY + 1; y < endY; y++) {
            Point p1 = new Point(startX, y);
            Point p2 = new Point(endX, y);

            if (origin.getBoard().isValidPoint(p1)) {
                pointList.add(new Location(startX, y, board));
            }
            if (origin.getBoard().isValidPoint(p2)) {
                pointList.add(new Location(endX, y, board));
            }
        }

        return pointList;
    }
}
