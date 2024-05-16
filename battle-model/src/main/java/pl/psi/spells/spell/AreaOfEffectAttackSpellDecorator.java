package pl.psi.spells.spell;

import pl.psi.GameEngine;
import pl.psi.Hero;
import pl.psi.Point;
import pl.psi.spells.object.Spell;
import pl.psi.spells.object.SpellStatisticIf;
import pl.psi.spells.object.SpellStats;

import java.util.ArrayList;
import java.util.List;

public class AreaOfEffectAttackSpellDecorator extends Spell {
    private final Spell decorated;
    private final int radius;

    public AreaOfEffectAttackSpellDecorator(final Spell decorated) {
        super(null, null);
        this.radius = decorated.getStats().getSize() - 1;
        this.decorated = decorated;
    }

    private List<Point> getAdjacentPoints(Point targetPoint) {
        GameEngine ge = GameEngine.getInstance();

        final Point minPoint = new Point(targetPoint.getX() - radius, targetPoint.getY() - radius);
        final Point maxPoint = new Point(targetPoint.getX() + radius, targetPoint.getY() + radius);

        final List<Point> pointList = new ArrayList<>();

        for (int y = minPoint.getY(); y <= maxPoint.getY(); y++) {
            for (int x = minPoint.getX(); x <= maxPoint.getX(); x++) {
                Point p = new Point(x, y);
                if (ge.isValidPoint(p)) {
                    pointList.add(p);
                }
            }
        }

        return pointList;
    }

    @Override
    public SpellStatisticIf getStats() {
        return this.decorated.getStats();
    }

    @Override
    public String getName() {
        return this.decorated.getName();
    }

    @Override
    public boolean canCast(Hero caster, Point targetPoint) {
        return !getAdjacentPoints(targetPoint).isEmpty() && this.decorated.canCast(caster, targetPoint);
    }

    @Override
    public void cast(Hero caster, Point targetPoint) {
        List<Point> adjacent = getAdjacentPoints(targetPoint);

        for (Point p : adjacent) {
            this.decorated.cast(caster, p);
        }
    }
}
