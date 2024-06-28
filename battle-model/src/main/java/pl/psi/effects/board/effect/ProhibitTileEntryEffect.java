package pl.psi.effects.board.effect;

import pl.psi.creatures.Creature;
import pl.psi.effects.board.object.BoardEffect;
import pl.psi.effects.generic.EffectStatisticIf;

public class ProhibitTileEntryEffect extends BoardEffect {
    public ProhibitTileEntryEffect(EffectStatisticIf effectStatistic) {
        super(effectStatistic);
    }

    @Override
    public boolean canEnter(Creature creature) {
        return false;
    }
}
