package pl.psi.creatures;

import lombok.Getter;
import pl.psi.enums.AttackTypeEnum;

import java.util.Random;

import pl.psi.obstacles.ObstaclesWithHP;
import pl.psi.obstacles.Wall;


abstract class AbstractCalculateDamageStrategy implements DamageCalculatorIf
{

    public static final int MAX_ATTACK_DIFF = 60;
    public static final int MAX_DEFENCE_DIFF = 12;
    public static final double DEFENCE_BONUS = 0.025;
    public static final double ATTACK_BONUS = 0.05;
    @Getter
    private final Random rand;

    protected AbstractCalculateDamageStrategy( final Random aRand )
    {
        rand = aRand;
    }

    @Override
    public int calculateDamage(final Creature aAttacker, final Creature aDefender, final AttackTypeEnum attackType)
    {
        final int armor = getArmor( aDefender );

        final int randValue = rand.nextInt( aAttacker.getDamage()
            .upperEndpoint()
            - aAttacker.getDamage()
                .lowerEndpoint()
            + 1 ) + aAttacker.getDamage()
                .lowerEndpoint();

        double oneCreatureDamageToDeal;
        if( aAttacker.getAttack() >= armor )
        {
            int attackPoints = aAttacker.getAttack() - armor;
            if( attackPoints > MAX_ATTACK_DIFF )
            {
                attackPoints = MAX_ATTACK_DIFF;
            }
            oneCreatureDamageToDeal = randValue * (1 + attackPoints * ATTACK_BONUS);
        }
        else
        {
            int defencePoints = armor - aAttacker.getAttack();
            if( defencePoints > MAX_DEFENCE_DIFF )
            {
                defencePoints = MAX_DEFENCE_DIFF;
            }
            oneCreatureDamageToDeal = randValue * (1 - defencePoints * DEFENCE_BONUS);
        }

        if( oneCreatureDamageToDeal < 0 )
        {
            oneCreatureDamageToDeal = 0;
        }

        int damage = (int) (aAttacker.getAmount() * oneCreatureDamageToDeal);
        //Melee ranged penalty
        if (aAttacker.getAttackType().equals(AttackTypeEnum.RANGE) && attackType.equals(AttackTypeEnum.MELEE)) {
            damage = damage / 2;
        }
        return damage;
    }

    @Override
    public int calculateDamageToObstacle(Creature attacker, Object obstacleTarget){
        final int randValue = rand.nextInt(attacker.getDamage().upperEndpoint() -
                attacker.getDamage().lowerEndpoint() + 1)
                + attacker.getDamage().lowerEndpoint();

        double oneCreatureDamageToDeal = randValue;

        if (oneCreatureDamageToDeal < 0) {
            oneCreatureDamageToDeal = 0;
        }
        return (int) (attacker.getAmount() * oneCreatureDamageToDeal);
    }

    protected int getArmor(final Creature aDefender )
    {
        return aDefender.getArmor();
    }
}
