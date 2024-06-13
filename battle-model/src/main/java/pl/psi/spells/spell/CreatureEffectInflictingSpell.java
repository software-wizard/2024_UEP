package pl.psi.spells.spell;

import pl.psi.Hero;
import pl.psi.Point;
import pl.psi.creatures.Creature;
import pl.psi.effects.object.CreatureEffectStatistic;
import pl.psi.spells.calculator.ReducedSpellCostCalculator;
import pl.psi.spells.object.Spell;
import pl.psi.spells.object.interfaces.SpellStatisticIf;

import java.util.Optional;

public class CreatureEffectInflictingSpell extends Spell {
    private final CreatureEffectStatistic effectToInflict;

    public CreatureEffectInflictingSpell(SpellStatisticIf stats, CreatureEffectStatistic effectToInflict) {
        super(stats, new ReducedSpellCostCalculator(stats));

        this.effectToInflict = effectToInflict;
    }

    @Override
    public boolean canCast(Hero caster, Point targetPoint) {
        Optional<Creature> optCreature = caster.getParentEngine().getCreature(targetPoint);

        return optCreature.isPresent() && optCreature.get().hasEffect(effectToInflict) == effectToInflict.isStackable();
    }

    @Override
    public void cast(Hero caster, Point targetPoint) {
        Optional<Creature> optCreature = caster.getParentEngine().getCreature(targetPoint);
        if (optCreature.isEmpty()) throw new IllegalStateException("no creature present at position");

        optCreature.get().applyEffect(effectToInflict);
    }
}
