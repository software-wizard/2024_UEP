package pl.psi.spells.object;

import pl.psi.spells.aoe.AOERectangularPointSelection;
import pl.psi.spells.aoe.AOERingPointSelection;
import pl.psi.spells.object.decorators.AOESpellDecorator;
import pl.psi.spells.object.decorators.TargetConstrainedCreaturesSpellDecorator;
import pl.psi.spells.object.enums.SpellStatistic;
import pl.psi.spells.object.enums.SpellType;
import pl.psi.spells.spell.PassiveSpell;
import pl.psi.spells.spell.TitansLightningBoltSpell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class SpellFactory {
    private static final Map<SpellStatistic, Function<SpellStatistic, Spell>> spellMap = new HashMap<>();

    static {
        spellMap.put(SpellStatistic.MAGIC_ARROW, UnitAttackSpell::new);
        spellMap.put(SpellStatistic.LIGHTNING_BOLT, UnitAttackSpell::new);
        spellMap.put(SpellStatistic.TITANS_LIGHTNING_BOLT, TitansLightningBoltSpell::new);
        spellMap.put(SpellStatistic.IMPLOSION, UnitAttackSpell::new);
        spellMap.put(SpellStatistic.FIREBALL, stat -> new AOESpellDecorator(new AOERectangularPointSelection(), new UnitAttackSpell(stat)));
        spellMap.put(SpellStatistic.ICE_BOLT, UnitAttackSpell::new);
        spellMap.put(SpellStatistic.METEOR_SHOWER, stat -> new AOESpellDecorator(new AOERectangularPointSelection(), new UnitAttackSpell(stat)));
        spellMap.put(SpellStatistic.FROST_RING, stat -> new AOESpellDecorator(new AOERingPointSelection(), new UnitAttackSpell(stat)));
        spellMap.put(SpellStatistic.DEATH_RIPPLE, stat -> new TargetConstrainedCreaturesSpellDecorator(((caster, c) -> !caster.hasCreature(c)), new UnitAttackSpell(stat)));
    }

    public static Spell fromStatistic(SpellStatistic spellStatistic) {
        Function<SpellStatistic, Spell> spellConstructor = spellMap.get(spellStatistic);

        if (spellConstructor != null) {
            return spellConstructor.apply(spellStatistic);
        }

        if (spellStatistic.getType().equals(SpellType.PASSIVE)) {
            return new PassiveSpell(spellStatistic);
        } else {
            return new UnitAttackSpell(spellStatistic);
        }
    }

    public static List<Spell> all() {
        final List<Spell> list = new ArrayList<>();
        for (SpellStatistic stat : SpellStatistic.values()) {
            list.add(fromStatistic(stat));
        }

        return list;
    }
}
