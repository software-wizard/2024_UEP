package pl.psi.effects.board.object;

import pl.psi.creatures.Creature;

import java.util.ArrayList;
import java.util.List;

public class BoardEffectCombinator extends BoardEffect {
    private final List<BoardEffect> combinedEffects = new ArrayList<>();

    public BoardEffectCombinator() {
        super(null);
    }

    public BoardEffectCombinator(BoardEffect... boardEffects) {
        super(null);
        this.combinedEffects.addAll(List.of(boardEffects));
    }

    public void addEffect(BoardEffect effect) {
        this.combinedEffects.add(effect);
    }

    @Override
    public void onCreatureEnterTile(Creature creature) {
        this.combinedEffects.forEach((eff) -> eff.onCreatureEnterTile(creature));
    }

    @Override
    public void onCreatureExitTile(Creature creature) {
        this.combinedEffects.forEach((eff) -> eff.onCreatureExitTile(creature));
    }

    @Override
    public boolean canEnter(Creature creature) {
        for (BoardEffect effect : this.combinedEffects) {
            if (!effect.canEnter(creature)) return false;
        }

        return true;
    }

    @Override
    public boolean canExit(Creature creature) {
        for (BoardEffect effect : this.combinedEffects) {
            if (!effect.canExit(creature)) return false;
        }

        return true;
    }
}
