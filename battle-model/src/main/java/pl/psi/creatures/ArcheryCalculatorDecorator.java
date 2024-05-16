package pl.psi.creatures;

import pl.psi.enums.AttackTypeEnum;

public class ArcheryCalculatorDecorator extends AbstractCalculateDamageStrategy {
    private final int level;
    private final AbstractCalculateDamageStrategy decorated;


    public ArcheryCalculatorDecorator(DamageCalculatorIf aDecorated, int aLevel) {
        super(aDecorated.getRand());
        decorated = (AbstractCalculateDamageStrategy) aDecorated;
        level = aLevel;
    }

    @Override
    public int calculateDamage( final Creature aAttacker, final Creature aDefender )
    {
        return (int) (decorated.calculateDamage(aAttacker, aDefender) * getMultiplier());
    }

    private double getMultiplier() {
        // skad wziac attack type w kalkulatorze
//        if (decorated.getAttackType().equals(AttackTypeEnum.RANGE) ){
//            switch (level) {
//                case 1:
//                    return 1.1;
//                case 2:
//                    return 1.25;
//                case 3:
//                    return 1.5;
//                default:
//                    return 1;
//            }
//        }
        return 1;
    }


}
