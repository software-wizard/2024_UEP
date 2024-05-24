package pl.psi.objects;

import pl.psi.EconomyHero;
import pl.psi.skills.EcoSkill;
import pl.psi.skills.Skill;

public class SkillsField implements Field {

    private final EcoSkill aSkill;
    public SkillsField( EcoSkill aSkill) {
        this.aSkill = aSkill;
    }

    public void apply(EconomyHero hero) {
        hero.addSkill(aSkill);
    }
}
