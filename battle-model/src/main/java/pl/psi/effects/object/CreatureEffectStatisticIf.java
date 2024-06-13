package pl.psi.effects.object;

public interface CreatureEffectStatisticIf {
    String getName();
    String getDescription();
    int getBaseModifierValue();
    int getLength();
    boolean isStackable();
}
