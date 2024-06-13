package pl.psi.spells.spell;

import pl.psi.Hero;
import pl.psi.Point;
import pl.psi.spells.object.UnitAttackSpell;
import pl.psi.spells.object.interfaces.SpellStatisticIf;

public class TitansLightningBoltSpell extends UnitAttackSpell {

    public TitansLightningBoltSpell(SpellStatisticIf stats) {
        super(stats);
    }

    private boolean hasTitansThunderArtifact() {
        return false; // TODO: Change when artifacts implemented.
    }

    @Override
    public boolean canCast(Hero caster, Point targetPoint) {
        return hasTitansThunderArtifact();
    }

    @Override
    public void cast(Hero caster, Point targetPoint) {
        super.cast(caster, targetPoint);
    }
}
