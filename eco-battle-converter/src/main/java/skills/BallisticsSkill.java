package skills;

import pl.psi.creatures.Creature;
import pl.psi.creatures.DefaultDamageCalculator;
import pl.psi.creatures.MachineCalculatorDecorator;
import pl.psi.enums.CreatureTypeEnum;
import pl.psi.enums.SkillEnum;
import pl.psi.skills.Skill;

import java.util.List;
import java.util.Random;

public class BallisticsSkill extends Skill implements BattleSkill {
    public BallisticsSkill(int aLevel) {
        super(SkillEnum.BALLISTICS, aLevel);
    }

    @Override
    public void cast(List<Creature> creatures) {
        creatures.stream()
                .filter(creature -> creature.getCreatureType().equals(CreatureTypeEnum.CATAPULT))
//                .map(Creature::getCalculator)
//                .map(calculator -> (MachineCalculatorDecorator) calculator)
//                .forEach(calculator -> calculator.setLevel(level));
                .forEach(ballista -> ballista.setCalculator(new MachineCalculatorDecorator(new DefaultDamageCalculator(new Random()), level)));
    }
}
