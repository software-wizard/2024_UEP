package pl.psi.spells.object;

import pl.psi.effects.effect.AbsoluteArmorModifierEffect;
import pl.psi.effects.object.CreatureEffectStatistic;
import pl.psi.spells.aoe.AOERectangularPointSelection;
import pl.psi.spells.aoe.AOERingPointSelection;
import pl.psi.spells.object.decorators.AOESpellDecorator;
import pl.psi.spells.object.decorators.TargetConstrainedCreaturesSpellDecorator;
import pl.psi.spells.object.enums.SpellStatistic;
import pl.psi.spells.object.enums.SpellType;
import pl.psi.spells.spell.CreatureEffectInflictingSpell;
import pl.psi.spells.spell.PassiveSpell;
import pl.psi.spells.spell.TitansLightningBoltSpell;
import pl.psi.spells.spell.UnitAttackSpell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class SpellFactory {
    private static final Map<SpellStatistic, Function<SpellStatistic, Spell>> spellMap = new HashMap<>();

    static {
        // --- COMBAT SPELLS --- //
        spellMap.put(SpellStatistic.MAGIC_ARROW, UnitAttackSpell::new);
        spellMap.put(SpellStatistic.LIGHTNING_BOLT, UnitAttackSpell::new);
        spellMap.put(SpellStatistic.TITANS_LIGHTNING_BOLT, TitansLightningBoltSpell::new);
        spellMap.put(SpellStatistic.IMPLOSION, UnitAttackSpell::new);
        spellMap.put(SpellStatistic.FIREBALL, stat -> new AOESpellDecorator(new AOERectangularPointSelection(), new UnitAttackSpell(stat)));
        spellMap.put(SpellStatistic.ICE_BOLT, UnitAttackSpell::new);
        spellMap.put(SpellStatistic.METEOR_SHOWER, stat -> new AOESpellDecorator(new AOERectangularPointSelection(), new UnitAttackSpell(stat)));
        spellMap.put(SpellStatistic.FROST_RING, stat -> new AOESpellDecorator(new AOERingPointSelection(), new UnitAttackSpell(stat)));
        spellMap.put(SpellStatistic.DEATH_RIPPLE, stat -> new TargetConstrainedCreaturesSpellDecorator(((caster, c) -> !caster.hasCreature(c)), new UnitAttackSpell(stat)));


        // --- NON-DAMAGE COMBAT SPELLS --- //
        spellMap.put(SpellStatistic.DISRUPTING_RAY, stat -> new CreatureEffectInflictingSpell(stat, CreatureEffectStatistic.DISRUPTING_RAY));
        spellMap.put(SpellStatistic.BLESS, stat -> new CreatureEffectInflictingSpell(stat, CreatureEffectStatistic.BLESS));
        spellMap.put(SpellStatistic.HASTE, stat -> new CreatureEffectInflictingSpell(stat, CreatureEffectStatistic.HASTE));
        spellMap.put(SpellStatistic.ANTI_MAGIC, stat -> new CreatureEffectInflictingSpell(stat, CreatureEffectStatistic.ANTI_MAGIC));
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
