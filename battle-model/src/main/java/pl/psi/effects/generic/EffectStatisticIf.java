package pl.psi.effects.generic;

public interface EffectStatisticIf {
    String getName();
    String getDescription();
    int getBaseModifierValue();
    int getLength();
    boolean isStackable();
}
