package pl.psi.spells.spell;

import pl.psi.Hero;
import pl.psi.Point;
import pl.psi.spells.calculator.SpellCostCalculatorIf;
import pl.psi.spells.calculator.StaticSpellCostCalculator;
import pl.psi.spells.object.Spell;
import pl.psi.spells.object.SpellStatisticIf;

public class PassiveSpell extends Spell {
    public PassiveSpell(SpellStatisticIf aStats) {
        super(aStats, new StaticSpellCostCalculator(aStats));
    }

    @Override
    public boolean canCast(Hero caster, Point targetPoint) {
        return false;
    }

    @Override
    public void cast(Hero caster, Point targetPoint) {}
}
