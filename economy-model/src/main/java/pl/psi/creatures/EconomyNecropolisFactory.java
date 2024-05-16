package pl.psi.creatures;

public class EconomyNecropolisFactory
{

    private static final String EXCEPTION_MESSAGE = "We support tiers from 1 to 7";

    public EconomyCreature create( final boolean aIsUpgraded, final int aTier)
    {
        if( !aIsUpgraded )
        {
            switch( aTier )
            {
                case 1:
                    return new EconomyCreature( CreatureStatistic.SKELETON, false,1, 60 );
                case 2:
                    return new EconomyCreature( CreatureStatistic.WALKING_DEAD, false,2, 100 );
                case 3:
                    return new EconomyCreature( CreatureStatistic.WIGHT, false,3, 200 );
                case 4:
                    return new EconomyCreature( CreatureStatistic.VAMPIRE, false,4, 360 );
                case 5:
                    return new EconomyCreature( CreatureStatistic.LICH, false,5, 550 );
                case 6:
                    return new EconomyCreature( CreatureStatistic.BLACK_KNIGHT, false,6, 1200 );
                case 7:
                    return new EconomyCreature( CreatureStatistic.BONE_DRAGON, false,7, 1800 );
                default:
                    throw new IllegalArgumentException( EXCEPTION_MESSAGE );
            }
        }
        else
        {
            switch( aTier )
            {
                case 1:
                    return new EconomyCreature( CreatureStatistic.SKELETON_WARRIOR, true,1, 70 );
                case 2:
                    return new EconomyCreature( CreatureStatistic.ZOMBIE, true,2, 125 );
                case 3:
                    return new EconomyCreature( CreatureStatistic.WRAITH, true,3, 230 );
                case 4:
                    return new EconomyCreature( CreatureStatistic.VAMPIRE_LORD, true,4, 500 );
                case 5:
                    return new EconomyCreature( CreatureStatistic.POWER_LICH, true,5, 600 );
                case 6:
                    return new EconomyCreature( CreatureStatistic.DREAD_KNIGHT, true,6, 1500 );
                case 7:
                    return new EconomyCreature( CreatureStatistic.GHOST_DRAGON, true,7, 3000 );
                default:
                    throw new IllegalArgumentException( EXCEPTION_MESSAGE );
            }
        }
    }
}
