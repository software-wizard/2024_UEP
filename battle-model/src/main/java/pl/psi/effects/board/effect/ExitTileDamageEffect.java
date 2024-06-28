package pl.psi.effects.board.effect;

import pl.psi.creatures.Creature;
import pl.psi.creatures.DamageValueObject;
import pl.psi.effects.board.object.BoardEffect;
import pl.psi.effects.generic.EffectStatisticIf;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.CreatureTypeEnum;

public class ExitTileDamageEffect extends BoardEffect {
    public ExitTileDamageEffect(EffectStatisticIf effectStatistic) {
        super(effectStatistic);
    }

    @Override
    public void onCreatureExitTile(Creature creature) {
        creature.getDamageApplier().applyDamage(
                new DamageValueObject(effectStatistic.getBaseModifierValue(), AttackTypeEnum.SPELL, CreatureTypeEnum.UNKNOWN),
                creature);
    }
}
