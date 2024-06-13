package skills;

import pl.psi.creatures.Creature;
import pl.psi.enums.SkillEnum;
import pl.psi.skills.Skill;

import java.util.List;

public class BallisticsSkill extends Skill implements BattleSkill {
    public BallisticsSkill(SkillEnum aSkillEnum, int aLevel) {
        super(SkillEnum.BALLISTICS, aLevel);
    }

    @Override //todo
    public void cast(List<Creature> creatures) {
//        creatures.stream()
//                .filter(c -> c.getCreatureType().equals(CreatureTypeEnum.MACHINE))
//                .forEach(c -> c.setLevel);
    }
}
