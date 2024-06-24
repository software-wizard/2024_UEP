package pl.psi.spells.spell;

import pl.psi.Hero;
import pl.psi.Location;
import pl.psi.Point;
import pl.psi.spells.calculator.BaseSpellCostCalculator;
import pl.psi.spells.object.Spell;
import pl.psi.spells.object.interfaces.SpellStatisticIf;

public class PassiveSpell extends Spell {
    public PassiveSpell(SpellStatisticIf aStats) {
        super(aStats, new BaseSpellCostCalculator(aStats));
    }

    @Override
    public boolean canCast(Hero caster, Location targetPoint) {
        return false;
    }

    @Override
    public void cast(Hero caster, Location targetPoint) {}
}
