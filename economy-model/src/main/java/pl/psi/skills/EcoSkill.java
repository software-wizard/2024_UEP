package pl.psi.skills;

import pl.psi.enums.SkillEnum;

public class EcoSkill extends Skill {
    private SkillEnum aSkillEnum;
    private int aLevel;
    public EcoSkill(SkillEnum aSkillEnum, int aLevel){
        super(aSkillEnum, aLevel);
        this.aSkillEnum = aSkillEnum;
        this.aLevel = aLevel;
    }

}
