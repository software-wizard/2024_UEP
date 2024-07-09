package pl.psi.creatures;

import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.CreatureTypeEnum;

import java.util.Random;

public class MachineFactory {

    private static final String EXCEPTION_MESSAGE = "Name of war machine not found";


    public Creature create(final String aName) {
        switch (aName) {
            case "First Aid Tent":
                return new FirstAidTent.Builder().statistic(CreatureStatistic.FIRST_AID_TENT)
                        .build();
            case "Ballista":
                return new Ballista.Builder().statistic(CreatureStatistic.BALLISTA)
                        .creatureType(CreatureTypeEnum.BALLISTA)
                        .attackType(AttackTypeEnum.RANGE)
                        .calculator(new MachineCalculatorDecorator(new DefaultDamageCalculator(new Random()), 0))
                        .morale(new Morale(0))
                        .build();
            case "Catapult":
                return new Catapult.Builder().statistic(CreatureStatistic.CATAPULT)
                        .creatureType(CreatureTypeEnum.CATAPULT)
                        .attackType(AttackTypeEnum.RANGE)
                        .calculator(new MachineCalculatorDecorator(new DefaultDamageCalculator(new Random()), 0))
                        .morale(new Morale(0))
                        .build();
            default:
                throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }
    }
}