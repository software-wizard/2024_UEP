package pl.psi.hero;

import org.junit.jupiter.api.Test;
import pl.psi.EconomyHero;
import pl.psi.converter.EcoBattleConverter;
import pl.psi.creatures.*;
import skills.ArcherySkill;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BattleSkillsTest {
    //given

    @Test
    void ArcherySkillTest(){
        //given
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

}
