package pl.psi.spells;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Spellbook {
    @Getter
    private final List<Spell> spells;

    public Spellbook(final List<Spell> aSpells) {
        this.spells = aSpells;
    }

    public boolean hasSpell(Spell spell) {
        return spells.contains(spell);
    }

}
