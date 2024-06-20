package pl.psi.spells.object.interfaces;

import pl.psi.spells.object.enums.SpellSchool;
import pl.psi.spells.object.enums.SpellType;

public interface SpellStatisticIf {
    int getSpellId();
    String getName();
    String getDescription();
    int[] getCost();
    SpellType getType();
    int getLevel();
    int[] getBaseDmg();
    int getSize();

    SpellSchool getSchool();
    int getPowerMultiplier();
}
