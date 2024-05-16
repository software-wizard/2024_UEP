package pl.psi.objects;

import pl.psi.EconomyHero;
import pl.psi.Resources;

public class ResourcesField {

    private final Resources resources;
    public ResourcesField(Resources amount) {
        this.resources = amount;
    }

    public void apply(EconomyHero hero) {
         hero.changeResources(resources);
    }
}
