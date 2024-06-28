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
    public String toString() {
        return "ResourcesField{" +
                "resources=" + resources +
                '}';
    }

    public int getGold(){
        return this.resources.getGold();
    }
    public int getWood(){
        return this.resources.getWood();
    }

    public int getOre(){
        return this.resources.getOre();
    }

    public int getGems(){
        return this.resources.getGems();
    }

    public int getSulfur(){
        return this.resources.getSulfur();
    }

    public int getMercury(){
        return this.resources.getMercury();
    }

    public int getCristals(){
        return this.resources.getCristals();
    }
    @Override
    public String getImageBackground() {return "creatures/Black Knight.png" ;}
}
