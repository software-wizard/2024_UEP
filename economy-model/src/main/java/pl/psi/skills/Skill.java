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
        int levelToReturn;
        if (aLevel < 1) {
            levelToReturn = 1;
        } else levelToReturn = Math.min(aLevel, 3);
        return levelToReturn;
    }
}
