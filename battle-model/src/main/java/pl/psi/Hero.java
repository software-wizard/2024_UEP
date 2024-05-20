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

    public Hero( final List< Creature > aCreatures, final Spellbook aSpellbook )
    {
        spellbook = aSpellbook;
        creatures = aCreatures;
    }
}
