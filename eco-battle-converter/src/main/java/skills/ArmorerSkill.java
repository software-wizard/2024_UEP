package skills;

import pl.psi.creatures.ArmoredDamageApplierDecorator;
import pl.psi.creatures.Creature;
import pl.psi.enums.SkillEnum;
import pl.psi.skills.Skill;

import java.util.List;

public class ArmorerSkill extends Skill implements BattleSkill {
    public ArmorerSkill(int aLevel) {
        super(SkillEnum.ARMORER, aLevel);
    }

    @Override
    public void cast(List<Creature> creatures) {
        creatures.forEach(c -> c.decorateDamageApplier(new ArmoredDamageApplierDecorator(c.getDamageApplier(), level)));
    }
}
