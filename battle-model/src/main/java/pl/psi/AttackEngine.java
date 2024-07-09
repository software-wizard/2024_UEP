package pl.psi;

import pl.psi.creatures.Creature;
import pl.psi.creatures.DamageCalculatorIf;
import pl.psi.creatures.MachineCalculatorDecorator;
import pl.psi.creatures.Morale;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.CreatureTypeEnum;
import pl.psi.obstacles.ObstaclesWithHP;
import pl.psi.obstacles.Wall;

import java.util.*;

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


    public boolean canAttack(final Point point, Creature attacker, List<Creature> enemyCreatures) {

        if (board.isWall(point)) {
            return canAttackWall(point, attacker);
        }

        if (attacker.getCreatureType().equals(CreatureTypeEnum.CATAPULT)) {
            return false;
        }

        if (board.isObstacleWithHP(point)) {
            return canAttackObstacle(point, attacker);
        }


        if (board.getCreature(point).isEmpty()) {
            return false;
        }

        if (!enemyCreatures.contains(board.getCreature(point).get())) {
            return false;
        }

        if (attacker.getAttackType().equals(AttackTypeEnum.RANGE) || attacker.getCreatureType().equals(CreatureTypeEnum.BALLISTA)) {
            Creature enemy = board.getCreature(point).get();
            return enemy.getCreatureType().equals(CreatureTypeEnum.GROUND);
        }

        return isInMeleeRange(attacker, point);
    }

    private boolean canAttackWall(Point point, Creature attacker) {
        if (attacker.getCreatureType().equals(CreatureTypeEnum.BALLISTA)) {
            return false;
        }
        Wall wall = board.getWall(point).orElse(null);
        if (wall != null) {
            if (attacker.getCreatureType().equals(CreatureTypeEnum.CATAPULT)) {
                return true;
            }
            if (wall.getCurrentLevel() == 2 || wall.getCurrentLevel() == 3){
                if (attacker.getAttackType().equals(AttackTypeEnum.MELEE)) {
                    return isInMeleeRange(attacker, point);
                }
                if (attacker.getAttackType().equals(AttackTypeEnum.RANGE)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canAttackObstacle(Point point, Creature attacker) {
        if (attacker.getCreatureType().equals(CreatureTypeEnum.GROUND)) {
            if (attacker.getAttackType().equals(AttackTypeEnum.MELEE)) {
                return isInMeleeRange(attacker, point);
            }
            if (attacker.getAttackType().equals(AttackTypeEnum.RANGE)) {
                return true;
            }
        }
        return false;
    }

    public boolean canHeal(final Point point, Creature attacker, List<Creature> enemyCreatures) {
        if (board.getCreature(point).isEmpty()) {
            return false;
        }
        if (enemyCreatures.contains(board.getCreature(point).get())) {
            return false;
        }
        if (attacker.getCreatureType().equals(CreatureTypeEnum.HEALING_TENT)) {
            if (board.getCreature(point).isPresent() &&
                    !board.getCreature(point).get().getCreatureType().equals(CreatureTypeEnum.HEALING_TENT)) {
                return board.getCreature(point).get().getCurrentHp() < board.getCreature(point).get().getMaxHp();
            };
        }
        return false;
    }

    public void shootRandomWall(Creature attacker) {
        List<Point> walls = new ArrayList<>(board.getWallHashMap().keySet());
        Collections.shuffle(walls);
        attackOnce(walls.get(0), attacker);
    }

    public boolean shouldFireRandomly(Creature machine) {
        DamageCalculatorIf calculator = machine.getCalculator();
//        if (!(calculator instanceof MachineCalculatorDecorator)) {
//            return false;
//        }
        MachineCalculatorDecorator machineCalculator = (MachineCalculatorDecorator) machine.getCalculator();
        return machineCalculator.getLevel() < 2;
    }

    private void attackOnce(Point enemyLocation, Creature attacker) {
        AttackTypeEnum attackType = determineAttackType(attacker, enemyLocation);

        if (board.isObstacleWithHP(enemyLocation)) {
            Optional<ObstaclesWithHP> obstacle = board.getObstacleWithHP(enemyLocation);
            obstacle.ifPresent(o -> attacker.attack(o, attackType,enemyLocation));
        } else if (board.isWall(enemyLocation)) {
            Optional<Wall> wall = board.getWall(enemyLocation);
            wall.ifPresent(w -> attacker.attack(w, attackType,enemyLocation));
        } else {
            board.getCreature(enemyLocation)
                    .ifPresent(defender -> attacker.attack(defender, attackType));
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
        if (firstAidTent.getCreatureType().equals(CreatureTypeEnum.HEALING_TENT)) {
                board.getCreature(creatureToHealLocation)
                        .ifPresent(firstAidTent::restoreCurrentHpToPartHP);
        }
    }
}
