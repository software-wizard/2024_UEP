package skills;

import pl.psi.creatures.Creature;
import pl.psi.creatures.MachineCalculatorDecorator;
import pl.psi.enums.CreatureTypeEnum;
import pl.psi.enums.SkillEnum;
import pl.psi.skills.Skill;

import java.util.List;

public class BallisticsSkill extends Skill implements BattleSkill {
    public BallisticsSkill(SkillEnum aSkillEnum, int aLevel) {
        super(SkillEnum.BALLISTICS, aLevel);
    }

    @Override
    public void cast(List<Creature> creatures) {
        creatures.stream()
                .filter(creature -> creature.getCreatureType().equals(CreatureTypeEnum.MACHINE))
                .map(Creature::getCalculator)
                .filter(calculator -> calculator instanceof MachineCalculatorDecorator) //wiem, ze umiera jednorozec, wole jednak tak dac wrazie czego gdyby powstala kreatura ktora jest typu machine a ma inny kalkulator
                .map(calculator -> (MachineCalculatorDecorator) calculator)
                .forEach(calculator -> calculator.setLevel(level));
    }
}
