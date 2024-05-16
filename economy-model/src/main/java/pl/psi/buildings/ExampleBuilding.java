package pl.psi.buildings;

import pl.psi.Resources;

public class ExampleBuilding implements BuildingFactory {
    @Override
    public Building createBuilding() {
        return new Building("Example", Resources.builder()
                .gold(1)
                .wood(1)
                .ore(1)
                .build());
    }
}

