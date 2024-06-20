package pl.psi.objects;

import pl.psi.EconomyHero;
import pl.psi.skills.Skill;

public interface Field {
    public void apply(EconomyHero hero);

    public boolean isSkillField();
}
