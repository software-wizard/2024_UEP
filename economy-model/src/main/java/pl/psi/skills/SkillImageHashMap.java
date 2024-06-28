package pl.psi.skills;

import pl.psi.enums.SkillEnum;

import java.util.HashMap;

public class SkillImageHashMap {
    private HashMap<SkillEnum, String> skillImageMap;

    public SkillImageHashMap() {
        skillImageMap = new HashMap<>();
        skillImageMap.put(SkillEnum.ARCHERY, "creatures/Archery.png");
        skillImageMap.put(SkillEnum.ARMORER, "creatures/Armorer.png");
        skillImageMap.put(SkillEnum.OFFENSE, "creatures/Offense.png");
        skillImageMap.put(SkillEnum.LEADERSHIP, "creatures/Leadership.png");
    }
    public String get(SkillEnum askillEnum) {
        return skillImageMap.get(askillEnum);
    }

}
