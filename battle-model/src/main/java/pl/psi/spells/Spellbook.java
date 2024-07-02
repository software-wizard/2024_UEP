package pl.psi.spells;

import pl.psi.Hero;
import pl.psi.Location;
import pl.psi.Point;
import pl.psi.spells.object.Spell;
import pl.psi.spells.object.enums.SpellExpertise;
import pl.psi.spells.object.enums.SpellSchool;

import java.util.*;

public class Spellbook {
    private final List<Spell> spells;
    private final Map<SpellSchool, SpellExpertise> expertise;

    public Spellbook(final List<Spell> aSpells, final Map<SpellSchool, SpellExpertise> expertise) {
        this.spells = aSpells;
        this.expertise = new EnumMap<>(expertise);
    }

    public SpellExpertise getSchoolMastery(final SpellSchool school) {
        return expertise.getOrDefault(school, SpellExpertise.NONE);
    }

    public boolean hasSpell(Spell spell) {
        return spells.contains(spell);
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
}
