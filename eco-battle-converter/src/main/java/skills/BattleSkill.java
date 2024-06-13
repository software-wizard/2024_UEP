package skills;

import pl.psi.creatures.Creature;

import java.util.List;

public interface BattleSkill  {
    void cast(List<Creature> creatures);
}
