package pl.psi.effects.creature.object;

import com.google.common.collect.Range;
import pl.psi.creatures.CreatureStatisticIf;

public abstract class CreatureStatisticModifier implements CreatureStatisticIf {
    private final CreatureStatisticIf baseStats;
    private final CreatureStatisticIf prevStats;

    public CreatureStatisticModifier(CreatureStatisticIf baseStats, CreatureStatisticIf prevStats) {
        this.baseStats = baseStats;
        this.prevStats = prevStats;
    }

    @Override
    public String getName() {
        return prevStats.getName();
    }

    @Override
    public int getAttack() {
        return prevStats.getAttack();
    }

    @Override
    public int getArmor() {
        return prevStats.getArmor();
    }

    @Override
    public int getMaxHp() {
        return prevStats.getMaxHp();
    }

    @Override
    public int getMoveRange() {
        return prevStats.getMoveRange();
    }

    @Override
    public Range<Integer> getDamage() {
        return prevStats.getDamage();
    }

    @Override
    public int getTier() {
        return prevStats.getTier();
    }

    @Override
    public String getDescription() {
        return prevStats.getDescription();
    }

    @Override
    public boolean isUpgraded() {
        return prevStats.isUpgraded();
    }
}
