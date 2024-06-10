package pl.psi.spells.object;

import pl.psi.GameEngine;
import pl.psi.Hero;
import pl.psi.Point;
import pl.psi.creatures.Creature;

import java.util.ArrayList;
import java.util.List;

public class TargetConstrainedCreaturesSpellDecorator extends Spell {
    private final Spell decorated;
    private final CreatureConstraintSpellLambda constraintLambda;

    public TargetConstrainedCreaturesSpellDecorator(final CreatureConstraintSpellLambda constraintLambda, final Spell decorated) {
        this.decorated = decorated;
        this.constraintLambda = constraintLambda;
    }

    private List<Creature> getTargetedCreatures(Hero caster) {
        final GameEngine ge = caster.getParentEngine();
        final List<Creature> creatureList = new ArrayList<>();

        for (Creature c : ge.getAllCreatures()) {
            if (constraintLambda.op(caster, c)) creatureList.add(c);
        }

        return creatureList;
    }

    @Override
    public boolean canCast(Hero caster, Point targetPoint) {
        return !getTargetedCreatures(caster).isEmpty() && decorated.canCast(caster, targetPoint);
    }

    @Override
    public void cast(Hero caster, Point targetPoint) {
        for (Creature c : getTargetedCreatures(caster)) {
            this.decorated.cast(caster, caster.getParentEngine().getCreaturePosition(c));
        }
    }
}
