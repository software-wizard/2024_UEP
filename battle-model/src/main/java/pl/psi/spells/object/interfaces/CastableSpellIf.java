package pl.psi.spells.object.interfaces;

import pl.psi.Hero;
import pl.psi.Location;
import pl.psi.Point;

public interface CastableSpellIf {

    public boolean canCast(Hero caster, Location target);
    public void cast(Hero caster, Location target);
}
