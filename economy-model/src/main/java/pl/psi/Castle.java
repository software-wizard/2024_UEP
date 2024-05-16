package pl.psi;

import lombok.Getter;
import lombok.Setter;
import pl.psi.buildings.Building;

import java.util.ArrayList;
import java.util.List;

public class Castle implements MapObject {

    private List<Building> buildings = new ArrayList<>();

    @Setter
    @Getter
    Resources resources;


    public Castle(){
    }


    public void addBuilding(Building building) {
        buildings.add(building);
    }

    public Building getBuilding(int index){
        return buildings.get(index);
    }


    public void upgradeBuilding(int index) throws InsufficientResourcesException {
        Building building = buildings.get(index);
        resources = building.upgrade(resources);
    }



}
