package pl.psi.spells.spellbook;

import pl.psi.Hero;
import pl.psi.Point;
import pl.psi.spells.object.Spell;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Spellbook {
    private final List<Spell> spells;

    public static String SPELL_CASTED = "SPELL_CASTED";


    public Spellbook(final List<Spell> aSpells) {
        this.spells = aSpells;
    }

    public boolean hasSpell(Spell spell) {
        return spells.contains(spell);
    }

    public boolean hasSpell(String spellName) {
        return spells.stream().anyMatch((spell) -> { return spell.getName().equalsIgnoreCase(spellName); });
    }

    public List<Spell> getSpells() {
        // clone copy
        return new ArrayList<>(spells);
    }

    public Spell getSpell(String spellName) {
        Optional<Spell> spell = spells.stream().filter((s) -> { return s.getName().equalsIgnoreCase(spellName); }).findFirst();
        return spell.orElse(null);
    }

    public boolean canCast(String spellName, Hero hero, Point p) {
        return hasSpell(spellName)
                && hero.getMana() >= getSpell(spellName).getCostCalculator().getCost(hero)
                && getSpell(spellName).canCast(hero, p);
    }

    public void castSpell(String spellName, Hero hero, Point p) {
        if (!canCast(spellName, hero, p)) throw new IllegalStateException("Spell cannot be casted");

        Spell spell = getSpell(spellName);

        hero.setMana(hero.getMana() - spell.getCostCalculator().getCost(hero));
        spell.cast(hero, p);
    }
}
