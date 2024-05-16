package pl.psi.buildings;

import lombok.Builder;
import lombok.Getter;
import pl.psi.InsufficientResourcesException;
import pl.psi.Resources;

public class Building{

    String name;
    @Getter
    int tier;
    private final Resources requiredResources;
    private final static int maxTier = 2;

    public Building(String name, Resources requiredResources) {
        this.name = name;
        this.requiredResources = requiredResources;
        this.tier = 1;
    }

    public Resources upgrade(Resources availableResources) throws InsufficientResourcesException {
        if (canUpgrade(availableResources)) {
            if (tier < maxTier) {
                tier++;
                availableResources = availableResources.subtract(requiredResources);
                requiredResources.multiply(1.5);
                return availableResources;
            } else {
                throw new InsufficientResourcesException("Budynek " + name + " osiągnął już maksymalny poziom tieru.");
            }
        } else {
            throw new InsufficientResourcesException("Brak wystarczających zasobów do ulepszenia budynku " + name);
        }
    }
    private boolean canUpgrade(Resources availableResources) {
        return availableResources.hasEnough(requiredResources);
    }
}
