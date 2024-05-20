// ******************************************************************
//
// Copyright 2024 PSI Software SE. All rights reserved.
// PSI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms
//
// ******************************************************************

package pl.psi;

import lombok.Builder;
import lombok.Value;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
@Value
@Builder
public class Resources
{
    private final int gold;
    private final int wood;
    private final int ore;
    private final int mercury;
    private final int sulfur;
    private final int cristals;
    private final int gems;

    public Resources subtract(Resources aResourcesToSubtract){
        return Resources.builder()
                .gold(this.gold - aResourcesToSubtract.getGold())
                .wood(this.wood - aResourcesToSubtract.getWood())
                .ore(this.ore - aResourcesToSubtract.getOre())
                .mercury(this.mercury - aResourcesToSubtract.getMercury())
                .sulfur(this.sulfur - aResourcesToSubtract.getSulfur())
                .cristals(this.cristals - aResourcesToSubtract.getCristals())
                .gems(this.gems - aResourcesToSubtract.getGems())
                .build();
    }


    public Resources addResources(Resources aResourcesToSubtract){
        return Resources.builder()
                .gold(this.gold + aResourcesToSubtract.getGold())
                .wood(this.wood + aResourcesToSubtract.getWood())
                .ore(this.ore + aResourcesToSubtract.getOre())
                .mercury(this.mercury + aResourcesToSubtract.getMercury())
                .sulfur(this.sulfur + aResourcesToSubtract.getSulfur())
                .cristals(this.cristals + aResourcesToSubtract.getCristals())
                .gems(this.gems + aResourcesToSubtract.getGems())
                .build();
    }

    public static Resources startRes(){
        return Resources.builder()
                .gold(15000)
                .wood(20)
                .ore(20)
                .mercury(10)
                .sulfur(10)
                .cristals(10)
                .gems(10)
                .build();
    }

    public Resources multiply(double factor) {
        return Resources.builder()
                .gold((int) (this.gold * factor))
                .wood((int) (this.wood * factor))
                .ore((int) (this.ore * factor))
                .mercury((int) (this.mercury * factor))
                .sulfur((int) (this.sulfur * factor))
                .cristals((int) (this.cristals * factor))
                .gems((int) (this.gems * factor))
                .build();
    }

    public boolean hasEnough(Resources requiredResources) {
        return gold >= requiredResources.getGold() &&
                wood >= requiredResources.getWood() &&
                ore >= requiredResources.getOre() &&
                mercury >= requiredResources.getMercury() &&
                sulfur >= requiredResources.getSulfur() &&
                cristals >= requiredResources.getCristals() &&
                gems >= requiredResources.getGems();
    }

    public String getAllResourcesAsString() {
        return "Gold: " + gold +
                ", Wood: " + wood +
                ", Ore: " + ore +
                ", Mercury: " + mercury +
                ", Sulfur: " + sulfur +
                ", Crystals: " + cristals +
                ", Gems: " + gems;
    }
}
