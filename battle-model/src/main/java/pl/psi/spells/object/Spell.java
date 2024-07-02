package pl.psi.spells.object;

import lombok.Getter;
import pl.psi.Location;
import pl.psi.spells.calculator.SpellCostCalculatorIf;
import pl.psi.spells.object.interfaces.CastableSpellIf;
import pl.psi.spells.object.interfaces.SpellStatisticIf;

import java.util.List;

@Getter
public abstract class Spell implements CastableSpellIf {
    private SpellStatisticIf stats;
    private SpellCostCalculatorIf costCalculator;

    public Spell(final SpellStatisticIf aStats, final SpellCostCalculatorIf aCalculator) {
        this.stats = aStats;
        this.costCalculator = aCalculator;
    }
    public String getName() {
        return this.stats.getName();
    }

    public List<Location> getTargetPoints(Location origin) {
        return List.of(origin);
    }
}

