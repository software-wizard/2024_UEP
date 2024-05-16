package pl.psi;

import java.util.List;

import pl.psi.creatures.Creature;

import lombok.Getter;
import pl.psi.spells.Spellbook;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class Hero
{
    @Getter
    private final List< Creature > creatures;

    @Getter
    private final Spellbook spellbook;

    @Getter
    private final PrimarySkill primarySkills;

    @Getter
    private int mana;

    public Hero( final List< Creature > aCreatures, final PrimarySkill aPrimarySkills, final Spellbook aSpellbook )
    {
        spellbook = aSpellbook;
        primarySkills = aPrimarySkills;
        creatures = aCreatures;
        this.mana = getMaxMana();
    }

    public void setMana(final int aMana) {
        if (aMana < 0) throw new IllegalArgumentException("aMana < 0");
        this.mana = aMana;
    }

    public int getMaxMana() {
        return this.primarySkills.getKnowledge() * 10;
    }
}
