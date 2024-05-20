package BuildingsTest;


import org.junit.jupiter.api.Test;
import pl.psi.Castle;
import pl.psi.InsufficientResourcesException;
import pl.psi.Resources;
import pl.psi.buildings.Building;
import pl.psi.buildings.ExampleBuilding;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class BuildingTest1 {



    @Test
    void BuildingsShouldUpgradeCorrectly() throws InsufficientResourcesException {
        Building building = new ExampleBuilding().createBuilding();
        Resources availableResources = Resources.startRes();
        Castle castle = new Castle();
        castle.addBuilding(building);


        assertEquals(20, availableResources.getWood());

        castle.setResources(availableResources);

        castle.upgradeBuilding(0);

        availableResources = castle.getResources();
        assertEquals(2, castle.getBuilding(0).getTier());
        assertEquals(19, availableResources.getWood());

    }

}
