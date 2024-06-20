package pl.psi.effects.board.effect;

import pl.psi.creatures.Creature;
import pl.psi.effects.board.object.BoardEffect;
import pl.psi.effects.generic.EffectStatisticIf;

public class ProhibitTileExitEffect extends BoardEffect {
    public ProhibitTileExitEffect(EffectStatisticIf effectStatistic) {
        super(effectStatistic);
    }

    @Override
    public boolean canExit(Creature creature) {
        return false;
    }
}
