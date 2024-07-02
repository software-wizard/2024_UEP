package pl.psi.spells.object.decorators;

import pl.psi.Hero;
import pl.psi.Location;
import pl.psi.Point;
import pl.psi.spells.aoe.AOEPointSelectionStrategyIf;
import pl.psi.spells.object.Spell;
import pl.psi.spells.object.interfaces.SpellStatisticIf;

import java.util.List;

public class AOESpellDecorator extends Spell {
    private final Spell decorated;
    private final AOEPointSelectionStrategyIf pointSelectionStrategy;

    public AOESpellDecorator(final AOEPointSelectionStrategyIf pointSelectionStrategy, final Spell decorated) {
        super(decorated.getStats(), decorated.getCostCalculator());
        this.decorated = decorated;
        this.pointSelectionStrategy = pointSelectionStrategy;
    }

    public List<Location> getTargetPoints(Location origin) {
        return this.pointSelectionStrategy.getTargetPoints(origin, this.getStats().getSize());
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
    public boolean canCast(Hero caster, Location l) {
        return !pointSelectionStrategy.getTargetPoints(l, this.getStats().getSize()).isEmpty() && this.decorated.canCast(caster, l);
    }

    @Override
    public void cast(Hero caster, Location l) {
        List<Location> adjacent = pointSelectionStrategy.getTargetPoints(l, this.getStats().getSize());

        for (Location p : adjacent) {
            this.decorated.cast(caster, p);
        }
    }
}
