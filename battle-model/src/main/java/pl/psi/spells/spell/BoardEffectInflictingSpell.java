package pl.psi.spells.spell;

import pl.psi.Board;
import pl.psi.Hero;
import pl.psi.Location;
import pl.psi.effects.generic.EffectStatistic;
import pl.psi.effects.generic.EffectTargetType;
import pl.psi.spells.calculator.ReducedSpellCostCalculator;
import pl.psi.spells.object.Spell;
import pl.psi.spells.object.interfaces.SpellStatisticIf;

public class BoardEffectInflictingSpell extends Spell {
    private final EffectStatistic effectToInflict;

    public BoardEffectInflictingSpell(final SpellStatisticIf stats, final EffectStatistic effectToInflict) {
        super(stats, new ReducedSpellCostCalculator(stats));
        this.effectToInflict = effectToInflict;

        if (!effectToInflict.getTargetType().equals(EffectTargetType.BOARD))
            throw new IllegalArgumentException("only board effects can be used in BoardEffectInflictingSpell");
    }


    @Override
    public boolean canCast(Hero caster, Location target) {
        return true;
    }

    @Override
    public void cast(Hero caster, Location target) {
        Board b = (Board) target.getBoard();
        b.applyEffect(target, this.effectToInflict);
    }
}
