package skills;

import pl.psi.creatures.Creature;
import pl.psi.enums.SkillEnum;
import pl.psi.skills.Skill;

import java.util.List;

public class LeadershipSkill extends Skill implements BattleSkill {

    public LeadershipSkill(int aLevel) {
        super(SkillEnum.LEADERSHIP, aLevel);
    }
    @Override
    public void cast(List<Creature> creatures) {
        creatures.forEach(s -> s.getMorale().setValue(s.getMorale().getValue() + level));
    }
}
