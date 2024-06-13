package pl.psi.effects.object;

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
        return baseStats.getName();
    }

    @Override
    public int getAttack() {
        return baseStats.getAttack();
    }

    @Override
    public int getArmor() {
        return baseStats.getArmor();
    }

    @Override
    public int getMaxHp() {
        return baseStats.getMaxHp();
    }

    @Override
    public int getMoveRange() {
        return baseStats.getMoveRange();
    }

    @Override
    public Range<Integer> getDamage() {
        return baseStats.getDamage();
    }

    @Override
    public int getTier() {
        return baseStats.getTier();
    }

    @Override
    public String getDescription() {
        return baseStats.getDescription();
    }

    @Override
    public boolean isUpgraded() {
        return baseStats.isUpgraded();
    }
}
