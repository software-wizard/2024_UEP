package pl.psi.spells.object;

import pl.psi.spells.calculator.ReducedSpellCostCalculator;
import pl.psi.spells.calculator.StaticSpellCostCalculator;
import pl.psi.spells.object.Spell;
import pl.psi.spells.object.SpellStatistic;
import pl.psi.spells.spell.AreaOfEffectAttackSpellDecorator;
import pl.psi.spells.spell.PassiveSpell;
import pl.psi.spells.spell.SingleUnitAttackSpell;
import pl.psi.spells.spell.TitansLightningBoltSpell;

import java.util.ArrayList;
import java.util.List;

public class SpellFactory {

    public static Spell fromStatistic(SpellStatistic spellStatistic) {
        if (spellStatistic.equals(SpellStatistic.MAGIC_ARROW)) return new SingleUnitAttackSpell(spellStatistic);
        else if (spellStatistic.equals(SpellStatistic.LIGHTNING_BOLT)) return new SingleUnitAttackSpell(spellStatistic);
        else if (spellStatistic.equals(SpellStatistic.TITANS_LIGHTNING_BOLT)) return new TitansLightningBoltSpell(spellStatistic);
        else if (spellStatistic.equals(SpellStatistic.IMPLOSION)) return new SingleUnitAttackSpell(spellStatistic);
        else if (spellStatistic.equals(SpellStatistic.FIREBALL)) return new AreaOfEffectAttackSpellDecorator(new SingleUnitAttackSpell(spellStatistic));
        else if (spellStatistic.equals(SpellStatistic.ICE_BOLT)) return new SingleUnitAttackSpell(spellStatistic);
        else if (spellStatistic.equals(SpellStatistic.METEOR_SHOWER)) return new AreaOfEffectAttackSpellDecorator(new SingleUnitAttackSpell(spellStatistic));

        if (spellStatistic.getType().equals(SpellType.PASSIVE)) {
            return new PassiveSpell(spellStatistic, new StaticSpellCostCalculator(spellStatistic));
        } else return new SingleUnitAttackSpell(spellStatistic);
    }

    public static List<Spell> all() {
        final List<Spell> list = new ArrayList<>();
        for (SpellStatistic stat : SpellStatistic.values()) {
            list.add(fromStatistic(stat));
        }

        return list;
    }
}
