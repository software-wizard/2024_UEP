package pl.psi.gui;

import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import pl.psi.EconomyHero;

public class SkillTooltip extends Tooltip{

    private final EconomyHero hero;

    public SkillTooltip(EconomyHero hero) {
        this.hero = hero;
        initTooltip();
    }

    public void refresh() {
        setText(hero.skillsAsList());
    }

    private void initTooltip() {
        setText(hero.skillsAsList());
        setShowDelay(new Duration(200));
    }

}
