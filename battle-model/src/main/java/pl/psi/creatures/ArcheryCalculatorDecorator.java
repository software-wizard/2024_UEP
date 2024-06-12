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
    public int calculateDamage(final Creature aAttacker, final Creature aDefender, AttackTypeEnum attackType)
    {
        int damage = decorated.calculateDamage(aAttacker, aDefender, attackType);
        if (attackType.equals(AttackTypeEnum.RANGE)) {
            return (int) (damage * getMultiplier());
        }
        //damage calculator has melee penalty built in
        return damage;
    }

    private double getMultiplier() {
            switch (level) {
                case 1:
                    return 1.1;
                case 2:
                    return 1.25;
                case 3:
                    return 1.5;
                default:
                    return 1;
            }
    }


}
