package pl.psi.spells.object;

import pl.psi.Hero;
import pl.psi.Point;
import pl.psi.spells.aoe.AOEPointSelectionStrategyIf;
import pl.psi.spells.object.Spell;
import pl.psi.spells.object.SpellStatisticIf;

import java.util.List;

public class AOESpellDecorator extends Spell {
    private final Spell decorated;
    private final AOEPointSelectionStrategyIf pointSelectionStrategy;

    public AOESpellDecorator(final AOEPointSelectionStrategyIf pointSelectionStrategy, final Spell decorated) {
        super(null, null);
        this.decorated = decorated;
        this.pointSelectionStrategy = pointSelectionStrategy;
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
        return !pointSelectionStrategy.getTargetPoints(caster.getParentEngine(), targetPoint, this.getStats().getSize()).isEmpty() && this.decorated.canCast(caster, targetPoint);
    }

    @Override
    public void cast(Hero caster, Point targetPoint) {
        List<Point> adjacent = pointSelectionStrategy.getTargetPoints(caster.getParentEngine(), targetPoint, this.getStats().getSize());

        for (Point p : adjacent) {
            this.decorated.cast(caster, p);
        }
    }
}
