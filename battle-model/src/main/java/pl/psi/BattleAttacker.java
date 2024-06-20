package pl.psi;

import pl.psi.creatures.Creature;
import pl.psi.creatures.MachineCalculatorDecorator;
import pl.psi.creatures.Morale;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.CreatureTypeEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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

    public void machineAttack(GameEngine gameEngine) {
        MachineCalculatorDecorator machineCalculatorDecorator = (MachineCalculatorDecorator) gameEngine.getCreatureToMove().getCalculator();
        //niebezpieczne, wiem. chlopaki od maszyn musieliby zrobic klase extendujaca creature, albo mozna w builderze jakos zabezpieczyc
        if (machineCalculatorDecorator.getLevel() < 2) {
            shootRandomEnemyMachine(gameEngine);
            gameEngine.pass();
        }
    }

    private void shootRandomEnemyMachine(GameEngine gameEngine) {
        ArrayList<Creature> enemyCreatures = new ArrayList<>(gameEngine.getEnemyCreatures());
        Collections.shuffle(enemyCreatures);
        for (Creature c : enemyCreatures) {
            if (c.getCreatureType().equals(CreatureTypeEnum.MACHINE)) {
                attack(board.getPosition(c), gameEngine);
                System.out.println("machine shot enemy machine");
                return;
            }
        }
    }
}
