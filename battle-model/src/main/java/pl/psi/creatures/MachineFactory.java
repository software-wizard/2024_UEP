package pl.psi.creatures;

import pl.psi.enums.AttackTypeEnum;

import java.util.List;

public class MachineFactory
{   private static final String EXCEPTION_MESSAGE = "Name of war machine not found";
    public Creature create( final String aName)
    {
            switch( aName )
            {
                case "First Aid Tent":
                    return new Creature.Builder().statistic( CreatureStatistic.FIRST_AID_TENT )
                            .build();
                case "Ballista":
                    return new Creature.Builder().statistic( CreatureStatistic.BALLISTA )
                            .attackType(AttackTypeEnum.RANGE)
                            .build();
                case "Catapult":
                    return new Creature.Builder().statistic( CreatureStatistic.CATAPULT )
                            .attackType(AttackTypeEnum.RANGE)
                            .build();
                default:
                    throw new IllegalArgumentException( EXCEPTION_MESSAGE );
            }
    }

}