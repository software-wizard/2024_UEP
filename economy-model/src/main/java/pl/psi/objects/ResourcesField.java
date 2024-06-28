package pl.psi.objects;

import pl.psi.EconomyHero;
import pl.psi.Resources;

public class ResourcesField implements Field{

    private final Resources resources;
    public ResourcesField(Resources amount) {
        this.resources = amount;
    }

    @Override
    public void apply(EconomyHero hero) {
         hero.changeResources(resources);
    }
    @Override
    public String getImageBackground() {return "creatures/Resource.png" ;}
}
