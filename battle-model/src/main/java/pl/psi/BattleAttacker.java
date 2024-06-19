package pl.psi;

import pl.psi.creatures.Creature;
import pl.psi.creatures.Morale;
import pl.psi.enums.AttackTypeEnum;

public class BattleAttacker {

    Board board; //pytanie czy skoro tak czesto tego uzywam to dac jako pole czy za kazdym razem przekazywac jako argument

    public BattleAttacker(Board board) {
        this.board = board;
    }

    public void attack(final Point point, GameEngine gameEngine) {
        Creature attacker = gameEngine.getCreatureToMove();
        attackOnce(point, attacker);
        //If creature gets lucky morale
        Morale currentCreatureMorale = attacker.getMorale();
        if (currentCreatureMorale.shouldAttackAgain()) {
            currentCreatureMorale.setGotLucky(true);

            return;
        }
        gameEngine.pass();
        currentCreatureMorale.setGotLucky(false);
    }

    private void attackOnce(Point point, Creature attacker) {
        AttackTypeEnum attackType = determineAttackType(attacker, point);
        board.getCreature(point)
                .ifPresent(defender -> attacker
                        .attack(defender, attackType));
    }

    private AttackTypeEnum determineAttackType(Creature aCreature, Point enemyLocation) {
        AttackTypeEnum creatureAttackType = aCreature.getAttackType();
        if (creatureAttackType.equals(AttackTypeEnum.RANGE)) {
            if(!isInMeleeRange(aCreature, enemyLocation)) {
                return AttackTypeEnum.RANGE;
            }
        }
        return AttackTypeEnum.MELEE;
    }

    public boolean canAttack(final Point point, Creature creature) {
        //if currentCreature is ranged it can attack a field where a Creature is present
        if(creature.getAttackType().equals(AttackTypeEnum.RANGE) && board.getCreature(point).isPresent()) {
            return true;
        }

        return board.getCreature(point)
                .isPresent()
                && isInMeleeRange(creature, point);
    }

    private boolean isInMeleeRange(Creature aCreature1, Point aCreature2) {
        double distance = board.getPosition(aCreature1).distance(aCreature2);
        return distance < 2 && distance > 0;
    }
}
