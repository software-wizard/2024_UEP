package pl.psi.hero;

import org.junit.jupiter.api.Test;
import pl.psi.EconomyHero;
import pl.psi.converter.EcoBattleConverter;
import pl.psi.creatures.*;
import skills.ArcherySkill;
import skills.ArmorerSkill;
import skills.OffenseSkill;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BattleSkillsTest {
    //given

    @Test
    void ArcherySkillTest(){
        EconomyHero hero = new EconomyHero("name");
        hero.addSkill(new ArcherySkill(2));
        final EconomyNecropolisFactory factory = new EconomyNecropolisFactory();
        hero.addCreature(factory.create(false, 5));
        hero.addCreature(factory.create(false, 1));
        //when
        final List<Creature> convertedCreatures = EcoBattleConverter.convert(hero)
                .getCreatures();
        //then
        Class<ArcheryCalculatorDecorator> archeryCalculatorDecoratorClass = ArcheryCalculatorDecorator.class;
        Object calculator1 = convertedCreatures.get(0).getCalculator();
        Object calculator2 = convertedCreatures.get(1).getCalculator();
        assertTrue(archeryCalculatorDecoratorClass.isInstance(calculator1));
        assertFalse(archeryCalculatorDecoratorClass.isInstance(calculator2));
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
