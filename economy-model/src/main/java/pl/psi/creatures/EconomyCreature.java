package pl.psi.creatures;

import lombok.Data;



@Data
public class EconomyCreature
{
    private final CreatureStatistic stats;
    private final boolean isUpgraded;
    private final int tier;
    private final int goldCost;
    private final int moraleValue;

    public boolean isMachine() {
        return this.stats.isMachine();
    }

    public String getName() {
        return this.stats.getName();
    }
}
