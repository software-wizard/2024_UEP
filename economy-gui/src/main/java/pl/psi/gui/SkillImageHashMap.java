package pl.psi.gui;

import pl.psi.enums.SkillEnum;

import java.util.HashMap;

public class SkillImageHashMap {
    private static final HashMap<SkillEnum, String> skillImageMap;

    static {
        skillImageMap = new HashMap<>();
        skillImageMap.put(SkillEnum.ARCHERY, "creatures/Archery.png");
        skillImageMap.put(SkillEnum.ARMORER, "creatures/Armorer.png");
        skillImageMap.put(SkillEnum.OFFENSE, "creatures/Offense.png");
        skillImageMap.put(SkillEnum.LEADERSHIP, "creatures/Leadership.png");
        skillImageMap.put(SkillEnum.BALLISTICS, "creatures/Ballistic.png");
    }

    public static String get(SkillEnum askillEnum) {
        return skillImageMap.get(askillEnum);
    }

}
