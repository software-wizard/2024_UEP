package pl.psi;


import pl.psi.enums.SkillEnum;
import pl.psi.objects.Field;
import pl.psi.objects.ResourcesField;
import pl.psi.objects.SkillsField;
import pl.psi.skills.EcoSkill;

import java.util.HashMap;
import java.util.Map;

public class FieldObjects implements MapObject{

    Map<Point, Field> fieldMap = new HashMap<>();


    public FieldObjects() {
        fieldMap.put(new Point(2,2), new ResourcesField(Resources.builder().gold(10).build()));
        fieldMap.put(new Point(5,10), new ResourcesField(Resources.builder().wood(100).build()));
        fieldMap.put(new Point(4, 4), new SkillsField(new EcoSkill(SkillEnum.LEADERSHIP, 1)));
        fieldMap.put(new Point(3, 3), new SkillsField(new EcoSkill(SkillEnum.ARMORER, 1)));
    }

}
