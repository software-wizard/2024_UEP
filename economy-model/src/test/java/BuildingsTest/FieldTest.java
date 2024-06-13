package BuildingsTest;

import org.junit.jupiter.api.Test;

import pl.psi.*;
import pl.psi.enums.SkillEnum;
import pl.psi.objects.ResourcesField;
import pl.psi.objects.SkillsField;
import pl.psi.skills.EcoSkill;

import java.beans.PropertyChangeSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

public class FieldTest {



    @Test
    void FieldShouldAddWoodCorrectly() {
        ResourcesField woodField = new ResourcesField(Resources.builder().wood(10).build());

        EconomyHero hero = new EconomyHero("hero");


        assertThat(hero.getResources().getWood()).isEqualTo(20);

        woodField.apply(hero);

        assertThat(hero.getResources().getWood()).isEqualTo(30);


    }

    @Test
    void FieldShouldAddGoldCorrectly(){

        EconomyHero hero = new EconomyHero("hero");
        Resources resources = hero.getResources();
        ResourcesField goldField =  new ResourcesField(Resources.builder().gold(10).build());


        goldField.apply(hero);

        assertEquals(resources.getGold() + 10, hero.getResources().getGold());

    }

    @Test
    void FieldShouldBeCorruptedCorrectly() {

        EconomyHero hero1 = new EconomyHero("hero1");
        EconomyHero hero2 = new EconomyHero("hero2");
        EconomyEngine engine = new EconomyEngine(hero1,hero2);




    }

    @Test
    void pickingUpANewSkillAdsItToSkillList() {
        EconomyHero hero = new EconomyHero("hero");
        assertThat(hero.getSkills()).isEmpty();

        EcoSkill ecoSkill = new EcoSkill(SkillEnum.ARMORER, 2);
        SkillsField skillsField = new SkillsField(ecoSkill);
        skillsField.apply(hero);

        assertThat(hero.getSkills().get(SkillEnum.ARMORER)).isEqualTo(ecoSkill);
    }

    @Test
    void pickingUpASkillAlreadyKnownUpgradesItsLevel() {
        EconomyHero hero = new EconomyHero("hero");
        hero.addSkill(new EcoSkill(SkillEnum.ARMORER, 1));
        assertThat(hero.getSkills().get(SkillEnum.ARMORER).getLevel()).isEqualTo(1);

        SkillsField skillsField = new SkillsField(new EcoSkill(SkillEnum.ARMORER, 2));
        skillsField.apply(hero);

        assertThat(hero.getSkills().get(SkillEnum.ARMORER).getLevel()).isEqualTo(3);
    }

    @Test
    void pickingUpASkillAlreadyKnownWontUpgradeItsLevelPast3() {
        EconomyHero hero = new EconomyHero("hero");

        SkillsField skillsField = new SkillsField(new EcoSkill(SkillEnum.ARMORER, 2));
        skillsField.apply(hero);

        assertThat(hero.getSkills().get(SkillEnum.ARMORER).getLevel()).isEqualTo(3);
    }

    @Test
    void pickingUpASkillFieldMakesItDisappear() {
        EconomyHero hero1 = new EconomyHero("hero");
        EconomyHero hero2 = new EconomyHero("hero");

        SkillsField skillsField = new SkillsField(new EcoSkill(SkillEnum.ARMORER, 2));
        final PropertyChangeSupport observerSupport = new PropertyChangeSupport(this);
        EcoMap ecoMap = new EcoMap(hero1, hero2, observerSupport);
        ecoMap.
        skillsField.apply(hero1);

        assertThat(hero.getSkills().get(SkillEnum.ARMORER).getLevel()).isEqualTo(3);
    }
}
