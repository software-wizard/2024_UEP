package pl.psi;


import pl.psi.objects.ResourcesField;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FieldObjects implements MapObject{

    Map<Point, ResourcesField> fieldMap = new HashMap<>();


    public FieldObjects() {
        Random random = new Random();

        String[] resourceTypes = {"gold", "gems", "wood", "ore", "mercury", "sulfur", "cristals"};

        int minNumOfResources = 3;
        int maxNumOfResources = 8;
        int numberOfResources = random.nextInt(maxNumOfResources - minNumOfResources + 1) + minNumOfResources;

        int maxQuantity = 100;
        int minQuantity = 5;


        for(int i = 0; i<=numberOfResources; i++){
            String randomResourceType = resourceTypes[random.nextInt(resourceTypes.length)];

            int resourceQuantity = random.nextInt(maxQuantity - minQuantity + 1)  - minQuantity;

            fieldMap.put(new Point(random.nextInt(25), random.nextInt(15)),
                    new ResourcesField(Resources.builder().withResource(randomResourceType, resourceQuantity).build()));



            }

    }

}
