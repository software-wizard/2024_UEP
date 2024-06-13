package pl.psi.spells.aoe;

import pl.psi.GameEngine;
import pl.psi.Point;

import java.util.ArrayList;
import java.util.List;

public class AOERingPointSelection implements AOEPointSelectionStrategyIf {


    @Override
    public List<Point> getTargetPoints(final GameEngine ge, final Point originPoint, final int size) {
        final List<Point> pointList = new ArrayList<>();

        final AOELinearPointSelection linearVertical = new AOELinearPointSelection(AOELinearPointSelection.Axis.VERTICAL);
        final AOELinearPointSelection linearHorizontal = new AOELinearPointSelection(AOELinearPointSelection.Axis.HORIZONTAL);

        // top
        pointList.addAll(linearHorizontal.getTargetPoints(ge, new Point(originPoint.getX(), originPoint.getY() - size), size));

        // bottom
        pointList.addAll(linearHorizontal.getTargetPoints(ge, new Point(originPoint.getX(), originPoint.getY() + size), size));

        // left
        pointList.addAll(linearVertical.getTargetPoints(ge, new Point(originPoint.getX() - size, originPoint.getY()), size));

        // right
        pointList.addAll(linearVertical.getTargetPoints(ge, new Point(originPoint.getX() + size, originPoint.getY()), size));

        return pointList;
    }
}
