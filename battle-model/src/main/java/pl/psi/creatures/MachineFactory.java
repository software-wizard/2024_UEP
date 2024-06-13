package pl.psi.creatures;

public class MachineFactory
{

    private static final String EXCEPTION_MESSAGE = "Name of war machine not found";



    public Creature create( final String aName)
    {
            switch( aName )
            {
                case "First Aid Tent":
                    return new FirstAidTent.Builder().statistic( CreatureStatistic.FIRST_AID_TENT )
                            .build();
                case "Ballista":
                    return new Ballista.Builder().statistic( CreatureStatistic.BALLISTA )
                            .build();
                case "Catapult":
                    return new Catapult.Builder().statistic( CreatureStatistic.CATAPULT )
                            .build();
                default:
                    throw new IllegalArgumentException( EXCEPTION_MESSAGE );
            }
    }





}