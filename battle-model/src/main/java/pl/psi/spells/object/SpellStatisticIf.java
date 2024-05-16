package pl.psi.spells.object;

public interface SpellStatisticIf {
    int getSpellId();
    String getName();
    String getDescription();
    int getCost();
    SpellType getType();
    int getLevel();
    int getBaseDmg();
    int getSize();

    SpellSchool getSchool();
    int getPowerMultiplier();
}
