package pl.psi.spells;

import pl.psi.Hero;
import pl.psi.Point;
import pl.psi.spells.object.Spell;
import pl.psi.spells.object.enums.SpellSchool;
import pl.psi.spells.object.enums.SpellSchoolMastery;

import java.util.*;

public class Spellbook {
    private final List<Spell> spells;
    private final Map<SpellSchool, SpellSchoolMastery> schoolMasteryLevels;

    public Spellbook(final List<Spell> aSpells, final Map<SpellSchool, SpellSchoolMastery> masteryLevels) {
        this.spells = aSpells;
        this.schoolMasteryLevels = masteryLevels;
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

    public SpellSchoolMastery getSchoolMastery(final SpellSchool school) {
        return schoolMasteryLevels.getOrDefault(school, SpellSchoolMastery.NONE);
    }

    public boolean canCast(Spell spell, Hero hero, Point p) {
        return hasSpell(spell)
                && hero.getMana() >= spell.getCostCalculator().getCost(this)
                && spell.canCast(hero, p);
    }

    public void castSpell(Spell spell, Hero hero, Point p) {
        if (!canCast(spell, hero, p)) throw new IllegalStateException("Spell cannot be casted, run canCast first");

        hero.setMana(hero.getMana() - spell.getCostCalculator().getCost(this));
        spell.cast(hero, p);
    }

    public class Builder {

    }
}
