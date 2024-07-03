package pl.psi.objects;

import pl.psi.EconomyHero;

public interface Field {
    public void apply(EconomyHero hero);
    public String getImageBackground();
    public boolean isSkillField();
}
