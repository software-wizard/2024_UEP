package pl.psi.objects;

import lombok.Getter;
import pl.psi.EconomyHero;
import pl.psi.Resources;
import pl.psi.skills.Skill;
import pl.psi.skills.SkillImageHashMap;

@Getter
public class SkillsField implements Field {

    private final Skill skill;
    public SkillsField(Skill aSkill) {
        skill = aSkill;
    }
    @Override
    public void apply(EconomyHero hero) {
        hero.addSkill(skill);
    }

    @Override
    public String getImageBackground() {
        return SkillImageHashMap.get(this.skill.getSkillName());
    }

    @Override
    public boolean isSkillField() {return true;}


}
