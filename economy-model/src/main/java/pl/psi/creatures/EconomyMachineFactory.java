package pl.psi.creatures;

public class EconomyMachineFactory {
    private static final String EXCEPTION_MESSAGE = "Unsupported war machine name";

    public EconomyCreature create(final String aName) {
        switch (aName) {
            case "First Aid Tent":
                return new EconomyCreature( CreatureStatistic.FIRST_AID_TENT, false,1,2500, 0);
            case "Ballista":
                return new EconomyCreature( CreatureStatistic.BALLISTA, false,1,2500, 0);

            case "Catapult":
                return new EconomyCreature( CreatureStatistic.CATAPULT, false,1,2500, 0);

            default:
                throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }
    }
}
