package pl.psi.creatures;

public class OffenseCalculatorDecorator extends AbstractCalculateDamageStrategy {
    private final int level;
    private final AbstractCalculateDamageStrategy decorated;


    public OffenseCalculatorDecorator(DamageCalculatorIf aDecorated, int aLevel) {
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
        switch (level) {
            case 1:
                return 1.1;
            case 2:
                return 1.2;
            case 3:
                return 1.3;
            default:
                return 1;
        }
    }


}
