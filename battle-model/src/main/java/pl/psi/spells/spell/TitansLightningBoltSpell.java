package pl.psi.spells.spell;

import pl.psi.Hero;
import pl.psi.Point;
import pl.psi.spells.object.Spell;
import pl.psi.spells.object.SpellStatisticIf;

public class TitansLightningBoltSpell extends Spell {

    private final SingleUnitAttackSpell decorated;

    public TitansLightningBoltSpell(SpellStatisticIf stats) {
        super(stats, null);
        this.decorated = new SingleUnitAttackSpell(stats);
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
        this.decorated.cast(caster, targetPoint);
    }
}
