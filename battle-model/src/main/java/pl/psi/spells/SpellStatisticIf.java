package pl.psi.spells;

public interface SpellStatisticIf {
    String getName();
    String getDescription();
    int getCost();
    SpellType getType();
    int getLevel();
    int getBaseDmg();
    int getPowerMultiplier();
}
