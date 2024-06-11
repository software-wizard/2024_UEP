package pl.psi.effects.object;

import com.google.common.collect.Range;
import pl.psi.creatures.CreatureStatisticIf;

public abstract class AbstractCreatureStatisticEffectDecorator implements CreatureStatisticIf {
    private CreatureStatisticIf baseStats;
    private CreatureStatisticIf decorated;

    AbstractCreatureStatisticEffectDecorator(CreatureStatisticIf baseStats, CreatureStatisticIf decorated) {
        this.baseStats = baseStats;
        this.decorated = decorated;
    }

    @Override
    public String getName() {
        return decorated.getName();
    }

    @Override
    public int getAttack() {
        return decorated.getAttack();
    }

    @Override
    public int getArmor() {
        return decorated.getArmor();
    }

    @Override
    public int getMaxHp() {
        return decorated.getMaxHp();
    }

    @Override
    public int getMoveRange() {
        return decorated.getMoveRange();
    }

    @Override
    public Range<Integer> getDamage() {
        return decorated.getDamage();
    }

    @Override
    public int getTier() {
        return decorated.getTier();
    }

    @Override
    public String getDescription() {
        return decorated.getDescription();
    }

    @Override
    public boolean isUpgraded() {
        return decorated.isUpgraded();
    }
}
