package BuildingsTest;

import org.junit.jupiter.api.Test;

import pl.psi.*;
import pl.psi.objects.ResourcesField;

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
}
