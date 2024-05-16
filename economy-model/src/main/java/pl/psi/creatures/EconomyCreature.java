package pl.psi.creatures;

import lombok.Data;



@Data
public class EconomyCreature
{
    private final CreatureStatistic stats;
    private final boolean isUpgraded;
    private final int tier;
    private final int goldCost;
}
