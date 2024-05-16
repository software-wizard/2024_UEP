package pl.psi.spells.object;

import pl.psi.Hero;
import pl.psi.Point;

public interface CastableSpellIf {

    public boolean canCast(Hero caster, Point targetPoint);
    public void cast(Hero caster, Point targetPoint);
}
