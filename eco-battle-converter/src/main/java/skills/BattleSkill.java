package skills;

import pl.psi.creatures.Creature;
import pl.psi.enums.SkillEnum;
import pl.psi.skills.Skill;

import java.util.List;

public interface BattleSkill  {
    void cast(List<Creature> creatures);
}
