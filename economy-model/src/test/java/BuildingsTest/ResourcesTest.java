package BuildingsTest;

import org.junit.jupiter.api.Test;
import pl.psi.EconomyEngine;
import pl.psi.EconomyHero;
import pl.psi.FieldObjects;
import pl.psi.Resources;
import pl.psi.objects.ResourcesField;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourcesTest {

    @Test
    void ShouldSubtract() {
        Resources resources = Resources.builder().wood(2).build();
        Resources toSubtrack = Resources.builder().wood(1).build();
        resources = resources.subtract(toSubtrack);
        assertEquals(1, resources.getWood());
    }

    @Test
    void ShouldAddResources() {
        Resources resources = Resources.builder().wood(2).build();
        Resources addResources = Resources.builder().wood(2).build();
        resources = resources.addResources(addResources);
        assertThat(resources.getWood()).isEqualTo(4);

    }

    @Test
    void ShouldHeroCorrectlyChangeResources(){
        Resources resourcesToAdd = Resources.builder().wood(10).build();

        EconomyHero hero = new EconomyHero("name");

        assertThat(hero.getResources().getWood()).isEqualTo(20);

        hero.changeResources(resourcesToAdd);

        assertThat(hero.getResources().getWood()).isEqualTo(30);
    }


    @Test
    void ResourcesFieldShouldApplyResourcesCorrectly(){
        ResourcesField resources = new ResourcesField(Resources.builder().wood(10).build());
        EconomyHero hero = new EconomyHero("name");

        assertThat(hero.getResources().getWood()).isEqualTo(20);
        resources.apply(hero);
        assertThat(hero.getResources().getWood()).isEqualTo(30);
    }
}