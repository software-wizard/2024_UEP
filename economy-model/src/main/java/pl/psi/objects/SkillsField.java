package pl.psi.objects;

import pl.psi.EconomyHero;
import pl.psi.Resources;
import pl.psi.skills.Skill;

public class SkillsField implements Field {

    private final Skill skill;
    public SkillsField(Skill aSkill) {
        skill = aSkill;
    }
    @Override
    public void apply(EconomyHero hero) {
        hero.addSkill(skill);
    }
    public Skill getSkill() {
        return skill;
    }

    public boolean isSkillField() {return true;}
}
