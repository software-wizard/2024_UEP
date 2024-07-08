package pl.psi;

import pl.psi.creatures.Creature;
import pl.psi.creatures.MachineCalculatorDecorator;
import pl.psi.creatures.Morale;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.CreatureTypeEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttackEngine {

    Board board; //pytanie czy skoro tak czesto tego uzywam to dac jako pole czy za kazdym razem przekazywac jako argument

    public AttackEngine(Board board) {
        this.board = board;
    }

    public void attack(final Point enemyLocation, final Creature attacker) {
        attackOnce(enemyLocation, attacker);
        //If creature gets lucky morale
        Morale currentCreatureMorale = attacker.getMorale();
        if (currentCreatureMorale.shouldAttackAgain()) {
            currentCreatureMorale.setGotLucky(true);
            return;
        }
        currentCreatureMorale.setGotLucky(false);
    }


    public boolean canAttack(final Point point, Creature attacker) {
        if (board.getCreature(point).isEmpty()) {
            return false;
        }

        Creature defender = board.getCreature(point).get();

        if (attacker.getCreatureType().equals(CreatureTypeEnum.MACHINE)) {
            return defender.getCreatureType().equals(CreatureTypeEnum.MACHINE);
        }

        if (attacker.getAttackType().equals(AttackTypeEnum.RANGE)) {
            return true;
        }

        return isInMeleeRange(attacker, point);
    }

    public void shootRandomEnemyMachine(Creature attacker, List<Creature> aEnemyCreatures) {
        ArrayList<Creature> enemyCreatures = new ArrayList<>(aEnemyCreatures);
        Collections.shuffle(enemyCreatures);
        for (Creature c : enemyCreatures) {
            if (c.getCreatureType().equals(CreatureTypeEnum.MACHINE)) {
                attack(board.getPosition(c), attacker);
                System.out.println("machine shot enemy machine");
                return;
            }
        }
    }

    public boolean shouldFireRandomly(Creature machine) {
        MachineCalculatorDecorator machineCalculator = (MachineCalculatorDecorator) machine.getCalculator();
        return machineCalculator.getLevel() < 2;
    }

    private void attackOnce(Point enemyLocation, Creature attacker) {
        AttackTypeEnum attackType = determineAttackType(attacker, enemyLocation);
        board.getCreature(enemyLocation)
                .ifPresent(defender -> attacker
                        .attack(defender, attackType));
    }

    private AttackTypeEnum determineAttackType(Creature aCreature, Point enemyLocation) {
        AttackTypeEnum creatureAttackType = aCreature.getAttackType();
        if (creatureAttackType.equals(AttackTypeEnum.RANGE)) {
            if (!isInMeleeRange(aCreature, enemyLocation)) {
                return AttackTypeEnum.RANGE;
            }
        }
        return AttackTypeEnum.MELEE;
    }

    private boolean isInMeleeRange(Creature aCreature1, Point aCreature2) {
        double distance = board.getPosition(aCreature1).distance(aCreature2);
        return distance < 2 && distance > 0;
    }
}
