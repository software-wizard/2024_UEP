package pl.psi.spells;

import pl.psi.Hero;
import pl.psi.Location;
import pl.psi.Point;
import pl.psi.spells.object.Spell;
import pl.psi.spells.object.enums.SpellSchool;

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

    public boolean canCast(Spell spell, Hero hero, Location l) {
        return hasSpell(spell)
                && hero.getMana() >= spell.getCostCalculator().getCost(hero)
                && spell.canCast(hero, l);
    }

    public void castSpell(Spell spell, Hero hero, Location p) {
        if (!canCast(spell, hero, p)) throw new IllegalStateException("Spell cannot be casted");

        hero.setMana(hero.getMana() - spell.getCostCalculator().getCost(hero));
        spell.cast(hero, p);
    }

    public TempObject getSchoolMastery(SpellSchool aSchool) {
        return new TempObject();
    }

    public class TempObject {
        public int getMasteryLevel() {
            return 0;
        }
    }
}
