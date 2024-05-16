package pl.psi.spells;

import pl.psi.Hero;
import pl.psi.Point;
import pl.psi.spells.object.Spell;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Spellbook {
    private final List<Spell> spells;

    public Spellbook(final List<Spell> aSpells) {
        this.spells = aSpells;
    }

    public boolean hasSpell(Spell spell) {
        return spells.contains(spell);
    }

    public boolean hasSpell(int spellId) {
        return spells.stream().anyMatch((spell) -> spell.getStats().getSpellId() == spellId);
    }

    public boolean hasSpell(String spellName) {
        return spells.stream().anyMatch((spell) -> spell.getStats().getName().equalsIgnoreCase(spellName));
    }

    public List<Spell> getSpells() {
        // clone copy
        return new ArrayList<>(spells);
    }

    public Spell getSpell(int spellId) {
        Optional<Spell> spell = spells.stream().filter((s) -> s.getStats().getSpellId() == spellId).findFirst();
        return spell.orElse(null);
    }

    public boolean canCast(int spellId, Hero hero, Point p) {
        return hasSpell(spellId)
                && hero.getMana() >= getSpell(spellId).getCostCalculator().getCost(hero)
                && getSpell(spellId).canCast(hero, p);
    }

    public void castSpell(int spellId, Hero hero, Point p) {
        if (!canCast(spellId, hero, p)) throw new IllegalStateException("Spell cannot be casted");

        Spell spell = getSpell(spellId);

        hero.setMana(hero.getMana() - spell.getCostCalculator().getCost(hero));
        spell.cast(hero, p);
    }
}
