package pl.psi;

import pl.psi.creatures.Creature;
import pl.psi.creatures.MachineCalculatorDecorator;
import pl.psi.creatures.Morale;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.CreatureTypeEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AttackEngine {

    Board board; //pytanie czy skoro tak czesto tego uzywam to dac jako pole czy za kazdym razem przekazywac jako argument

    public AttackEngine(Board board) {
        this.board = board;
    }

    public void attack(final Point enemyLocation, Creature attacker) {
        System.out.println("AttackEngine attack");
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
        if (board.getCreature(point).isEmpty() &&
                board.getWall(point).isEmpty()) {
            return false;
        }
        if (attacker.getCreatureType().equals(CreatureTypeEnum.MACHINE)) {
            if (attacker.getStats().getName().equals("Catapult")) {
                return board.getWall(point).isPresent();
            } else if (attacker.getStats().getName().equals("Ballista")) {
                return board.getCreature(point).isPresent() &&
                        !board.getCreature(point).get().getCreatureType().equals(CreatureTypeEnum.MACHINE);
            }
        }

        if (attacker.getAttackType().equals(AttackTypeEnum.RANGE)) {
            return true;
        }

        return isInMeleeRange(attacker, point);
    }

    public boolean canHeal(final Point point, Creature attacker) {
        if (board.getCreature(point).isEmpty()) {
            return false;
        }
        if (attacker.getCreatureType().equals(CreatureTypeEnum.GROUND)) {
            if (attacker.getStats().getName().equals("First Aid Tent")) {
                return board.getCreature(point).isPresent() &&
                        !board.getCreature(point).get().getCreatureType().equals(CreatureTypeEnum.MACHINE) &&
                        !board.getCreature(point).get().getStats().getName().equals("First Aid Tent");
            }
        }
        return false;
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
        if (attacker.getCreatureType().equals(CreatureTypeEnum.MACHINE)) {
            if (attacker.getStats().getName().equals("Catapult")) {
                board.getWall(enemyLocation)
                        .ifPresent(defender -> attacker
                                .attack(defender, attackType));
                return;
            } else if (attacker.getStats().getName().equals("Ballista")) {
                board.getCreature(enemyLocation)
                        .ifPresent(defender -> attacker
                                .attack(defender, attackType));
                return;
            }
        }
        Optional<Creature> creature = board.getCreature(enemyLocation);
        if (creature.isPresent()) {
            creature.ifPresent(defender -> attacker.attack(defender, attackType));
        } else {
            board.getWall(enemyLocation).ifPresent(defender -> attacker.attack(defender, attackType));
        }
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

    public void heal(Point creatureToHealLocation, Creature firstAidTent) {
        if (firstAidTent.getCreatureType().equals(CreatureTypeEnum.GROUND)) {
            if (firstAidTent.getStats().getName().equals("First Aid Tent")) {
                board.getCreature(creatureToHealLocation)
                        .ifPresent(firstAidTent::restoreCurrentHpToPartHP);
            }
        }
    }
}
