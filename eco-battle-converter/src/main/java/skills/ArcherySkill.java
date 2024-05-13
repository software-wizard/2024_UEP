package skills;

import pl.psi.creatures.ArcheryCalculatorDecorator;
import pl.psi.creatures.Creature;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.SkillEnum;
import pl.psi.skills.Skill;

import java.util.List;

public class ArcherySkill extends Skill implements BattleSkill {

    public ArcherySkill(int aLevel) {
        super(SkillEnum.OFFENSE, aLevel);
    }

    @Override
    public void cast(List<Creature> creatures) {
        creatures.stream()
                .filter(c -> c.getAttackType().equals(AttackTypeEnum.RANGE))
                .forEach(c -> c.setCalculator(new ArcheryCalculatorDecorator(c.getCalculator(), level)));
    }
}
