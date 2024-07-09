package skills;

import pl.psi.creatures.Creature;
import pl.psi.creatures.OffenseCalculatorDecorator;
import pl.psi.enums.CreatureTypeEnum;
import pl.psi.enums.SkillEnum;
import pl.psi.skills.Skill;

import java.util.List;

public class OffenseSkill extends Skill implements BattleSkill {

    public OffenseSkill(int aLevel) {
        super(SkillEnum.OFFENSE, aLevel);
    }

    @Override
    public void cast(List<Creature> creatures) {
        creatures.stream()
                .filter(c -> c.getCreatureType().equals(CreatureTypeEnum.GROUND))
                .forEach(c -> c.setCalculator(new OffenseCalculatorDecorator(c.getCalculator(), level)));
    }
}
