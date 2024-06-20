package pl.psi.effects.board.object;

import pl.psi.creatures.Creature;
import pl.psi.effects.generic.Effect;
import pl.psi.effects.generic.EffectStatisticIf;

public abstract class BoardEffect extends Effect {
    protected BoardEffect(EffectStatisticIf effectStatistic) {
        super(effectStatistic);
    }

    public void onCreatureEnterTile(Creature creature) {}
    public void onCreatureExitTile(Creature creature) {}

    public boolean canEnter(Creature creature) {
        return true;
    }

    public boolean canExit(Creature creature) {
        return true;
    }
}
