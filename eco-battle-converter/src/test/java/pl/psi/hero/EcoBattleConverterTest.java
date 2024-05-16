package pl.psi.hero;

import org.junit.jupiter.api.Test;
import pl.psi.EconomyHero;
import pl.psi.converter.EcoBattleConverter;
import pl.psi.creatures.OffenseCalculatorDecorator;
import skills.ArmorerSkill;
import pl.psi.creatures.ArmoredDamageApplierDecorator;
import pl.psi.creatures.Creature;
import pl.psi.creatures.EconomyNecropolisFactory;
import skills.OffenseSkill;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EcoBattleConverterTest {

    @Test
    void shouldConvertCreaturesCorrectly() {
        final EconomyHero ecoHero = new EconomyHero("name");
        final EconomyNecropolisFactory factory = new EconomyNecropolisFactory();
        ecoHero.addCreature(factory.create(false, 1));
        ecoHero.addCreature(factory.create(false, 2));
        ecoHero.addCreature(factory.create(false, 3));
        ecoHero.addCreature(factory.create(false, 4));
        ecoHero.addCreature(factory.create(false, 5));
        ecoHero.addCreature(factory.create(false, 6));
        ecoHero.addCreature(factory.create(false, 7));

        final List<Creature> convertedCreatures = EcoBattleConverter.convert(ecoHero)
                .getCreatures();

        assertEquals(7, convertedCreatures.size());

        assertEquals("Skeleton", convertedCreatures.get(0)
                .getName());
        assertEquals(1, convertedCreatures.get(0)
                .getAmount());

        assertEquals("Walking Dead", convertedCreatures.get(1)
                .getName());
        assertEquals(2, convertedCreatures.get(1)
                .getAmount());

        assertEquals("Wight", convertedCreatures.get(2)
                .getName());
        assertEquals(3, convertedCreatures.get(2)
                .getAmount());

        assertEquals("Vampire", convertedCreatures.get(3)
                .getName());
        assertEquals(4, convertedCreatures.get(3)
                .getAmount());

        assertEquals("Lich", convertedCreatures.get(4)
                .getName());
        assertEquals(5, convertedCreatures.get(4)
                .getAmount());

        assertEquals("Black Knight", convertedCreatures.get(5)
                .getName());
        assertEquals(6, convertedCreatures.get(5)
                .getAmount());

        assertEquals("Bone Dragon", convertedCreatures.get(6)
                .getName());
        assertEquals(7, convertedCreatures.get(6)
                .getAmount());
    }

    @Test
    void armorerSkillTest() {
        final EconomyHero ecoHero = new EconomyHero("name");
        final EconomyNecropolisFactory factory = new EconomyNecropolisFactory();
        ecoHero.addCreature(factory.create(false, 1));
        ecoHero.addCreature(factory.create(false, 2));
        ecoHero.addSkill(new ArmorerSkill(1));
        final List<Creature> convertedCreatures = EcoBattleConverter.convert(ecoHero)
                .getCreatures();
        //Czy da sie w jakis fajny sposob posprawdzac pola tych obiektow, np czy level tego damageAppliera sie zgadza
        assertTrue(convertedCreatures.get(0).getDamageApplier() instanceof ArmoredDamageApplierDecorator);
        assertTrue(convertedCreatures.get(1).getDamageApplier() instanceof ArmoredDamageApplierDecorator);
    }

    @Test
    void offenseSkillTest() {
        final EconomyHero ecoHero = new EconomyHero("name");
        final EconomyNecropolisFactory factory = new EconomyNecropolisFactory();
        ecoHero.addCreature(factory.create(false, 1));
        ecoHero.addCreature(factory.create(false, 2));
        ecoHero.addSkill(new OffenseSkill(1));
        final List<Creature> convertedCreatures = EcoBattleConverter.convert(ecoHero)
                .getCreatures();
        assertTrue(convertedCreatures.get(0).getCalculator() instanceof OffenseCalculatorDecorator);
        assertTrue(convertedCreatures.get(1).getCalculator() instanceof OffenseCalculatorDecorator);
    }

}