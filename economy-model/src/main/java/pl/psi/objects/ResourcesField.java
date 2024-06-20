package pl.psi.objects;

import pl.psi.EconomyHero;
import pl.psi.Resources;
import pl.psi.skills.Skill;

public class ResourcesField implements Field{

    private final Resources resources;
    public ResourcesField(Resources amount) {
        this.resources = amount;
    }

    @Override
    public void apply(EconomyHero hero) {
         hero.changeResources(resources);
    }


    public boolean isSkillField() { return false;}
}
