package skills;

import pl.psi.enums.SkillEnum;

public class SkillFactory {
    public BattleSkill create(SkillEnum aSkillEnum, int aLevel) {
        switch (aSkillEnum) {
            case ARCHERY:
                return new ArcherySkill(aLevel);
            case ARMORER:
                return new ArmorerSkill(aLevel);
            case OFFENSE:
                return new OffenseSkill(aLevel);
            case LEADERSHIP:
                return new LeadershipSkill(aLevel);
            case BALLISTICS:
                return new BallisticsSkill(aLevel);
            default:
                throw new IllegalArgumentException("Unknown skill.");

        }

    }
}
