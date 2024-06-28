package pl.psi.spells.object.decorators;

import pl.psi.*;
import pl.psi.creatures.Creature;
import pl.psi.spells.object.interfaces.CreatureConstraintSpellLambda;
import pl.psi.spells.object.Spell;

import java.util.ArrayList;
import java.util.List;

public class TargetConstrainedCreaturesSpellDecorator extends Spell {
    private final Spell decorated;
    private final CreatureConstraintSpellLambda constraintLambda;

    public TargetConstrainedCreaturesSpellDecorator(final CreatureConstraintSpellLambda constraintLambda, final Spell decorated) {
        super(decorated.getStats(), decorated.getCostCalculator());
        this.decorated = decorated;
        this.constraintLambda = constraintLambda;
    }

    private List<Creature> getTargetedCreatures(Hero caster, BoardIf board) {
        final List<Creature> creatureList = new ArrayList<>();

        for (Creature c : board.getAllCreatures()) {
            if (constraintLambda.op(caster, c)) creatureList.add(c);
        }

        return creatureList;
    }

    @Override
    public boolean canCast(Hero caster, Location targetPoint) {
        return !getTargetedCreatures(caster, targetPoint.getBoard()).isEmpty() && decorated.canCast(caster, targetPoint);
    }

    @Override
    public void cast(Hero caster, Location targetPoint) {
        for (Creature c : getTargetedCreatures(caster, targetPoint.getBoard())) {
            this.decorated.cast(caster, targetPoint.getBoard().getCreatureLocation(c).orElseThrow());
        }
    }
}
