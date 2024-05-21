package pl.psi.skills;

import lombok.Getter;
import pl.psi.enums.SkillEnum;

import java.util.Objects;

public abstract class Skill {


    protected int level;
    @Getter
    protected SkillEnum skillName;

    public Skill(SkillEnum aSkillEnum, int aLevel) {
        skillName = aSkillEnum;
        level = validateLevel(aLevel);
    }

    private int validateLevel(int aLevel) {
        if (aLevel < 1) {
            return 1;
        }
        return Math.min(aLevel, 3);
    }

    @Override
    public String toString() {
        String enumString = skillName.name();
        return enumString.charAt(0) + enumString.substring(1).toLowerCase() + " (" + level + ")";
    }
}
