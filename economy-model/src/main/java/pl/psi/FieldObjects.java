package pl.psi;


import pl.psi.objects.ResourcesField;

import java.util.HashMap;
import java.util.Map;

public class FieldObjects implements MapObject{

    Map<Point, ResourcesField> fieldMap = new HashMap<>();


    public FieldObjects() {
        fieldMap.put(new Point(2,2), new ResourcesField(Resources.builder().gold(10).build()));
        fieldMap.put(new Point(5,10), new ResourcesField(Resources.builder().wood(100).build()));

    }

}
