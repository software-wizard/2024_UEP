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
                .filter(c -> c.getCreatureType().equals(CreatureTypeEnum.MACHINE))
                .forEach(c -> c.setCalculator(new MachineCalculatorDecorator(c.getCalculator(), level)));
    }
}
