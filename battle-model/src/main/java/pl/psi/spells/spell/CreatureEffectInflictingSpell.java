package pl.psi.spells.spell;

import pl.psi.Hero;
import pl.psi.Location;
import pl.psi.Point;
import pl.psi.creatures.Creature;
import pl.psi.effects.generic.EffectStatistic;
import pl.psi.spells.calculator.ReducedSpellCostCalculator;
import pl.psi.spells.object.Spell;
import pl.psi.spells.object.interfaces.SpellStatisticIf;

import java.util.Optional;

public class CreatureEffectInflictingSpell extends Spell {
    private final EffectStatistic effectToInflict;

    public CreatureEffectInflictingSpell(SpellStatisticIf stats, EffectStatistic effectToInflict) {
        super(stats, new ReducedSpellCostCalculator(stats));

        this.effectToInflict = effectToInflict;
    }

    @Override
    public boolean canCast(Hero caster, Location l) {
        Optional<Creature> optCreature = l.getBoard().getCreature(l);

        return optCreature.isPresent() && optCreature.get().hasEffect(effectToInflict) == effectToInflict.isStackable();
    }

    @Override
    public void cast(Hero caster, Location l) {
        Optional<Creature> optCreature = l.getBoard().getCreature(l);
        if (optCreature.isEmpty()) throw new IllegalStateException("no creature present at position");

        optCreature.get().applyEffect(effectToInflict);
    }
}
