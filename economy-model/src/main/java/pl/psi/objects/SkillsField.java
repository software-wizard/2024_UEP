package pl.psi.objects;

import lombok.Getter;
import pl.psi.EconomyHero;
import pl.psi.Resources;
import pl.psi.skills.Skill;
import pl.psi.skills.SkillImageHashMap;

public class SkillsField implements Field {

    private final SkillImageHashMap hashMap;

    @Getter
    private final Skill skill;
    public SkillsField(Skill aSkill) {
        skill = aSkill;
        hashMap = new SkillImageHashMap();
    }
    @Override
    public void apply(EconomyHero hero) {
        hero.addSkill(skill);
    }

    @Override
    public String getImageBackground() {
        return hashMap.get(this.skill.getSkillName());
    }

    @Override
    public boolean isSkillField() {return true;}


}
