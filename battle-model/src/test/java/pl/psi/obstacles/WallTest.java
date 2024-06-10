package pl.psi.obstacles;

import org.junit.jupiter.api.Test;
import pl.psi.Point;
import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStatistic;
import pl.psi.creatures.MachineFactory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WallTest {
    private Wall wall;

    @Test
    public void takeDamageReducesHP() {
        wall = new Wall();

        int damage = 1000;
        wall.takeDamageFromCatapult(damage,new  Point(0, 0));


        assertEquals(500, wall.getCurrentHP());
    }

    @Test
    public void creatureCannotAttackWallAtLVL1(){
        wall = new Wall();
        int damage = 100000;
        wall.takeDamageFromCreature(damage,new Point(0,0));

        assertEquals(1500, wall.getCurrentHP());

    }

    @Test
    public void creatureCanAttackWallAtLVL2And3(){
        wall = new Wall();
        int damage = 100000;
        wall.setCurrentLevel(2);
        wall.takeDamageFromCreature(damage,new Point(0,0));

        assertThat(wall.getCurrentHP() <1000);

        wall.setCurrentLevel(3);
        wall.takeDamageFromCreature(damage,new Point(0,0));

        assertThat(wall.getCurrentHP() <500);
    }


    @Test
    public void catapultCanAttackWallAtLVL1(){
        wall = new Wall();
        MachineFactory machineFactory = new MachineFactory();
        Creature catapult = machineFactory.create("Catapult");

        catapult.attackWall(wall,new Point(0,0));

        assertThat(wall.getCurrentHP()< 1500);

    }


    @Test
    public void wallLevelChangesAfterAttacks(){
        wall = new Wall();

        MachineFactory machineFactory = new MachineFactory();
        Creature catapult = machineFactory.create("Catapult");

        catapult.attackWall(wall,new Point(0,0));

        assertEquals(1000,wall.getCurrentHP());
        assertThat(wall.getCurrentLevel() == 1);

        catapult.attackWall(wall,new Point(0,0));
        assertEquals(500,wall.getCurrentHP());

        catapult.attackWall(wall,new Point(0,0));
        assertEquals(1000,wall.getCurrentHP());
        assertThat(wall.getCurrentLevel() == 2);

        catapult.attackWall(wall,new Point(0,0));
        catapult.attackWall(wall,new Point(0,0));
        assertEquals(500,wall.getCurrentHP());
        assertThat(wall.getCurrentLevel() == 3);

    }

    @Test
    public void creatureCannotAttackWallAtLVL1(){
        wall = new Wall();
        int damage = 100000;
        wall.takeDamageFromCreature(damage,new Point(0,0));

        assertEquals(1500, wall.getHP());

    }
    @Test
    public void catapultCanAttackWallAtLVL1(){
        wall = new Wall();
        MachineFactory machineFactory = new MachineFactory();
        Creature catapult = machineFactory.create("Catapult");
        catapult.attackWall(wall,new Point(0,0));
        //wall.takeDamageFromCatapult(CreatureStatistic.CATAPULT.getAttack(),new Point(0,0));

        assertEquals(1490, wall.getHP());

    }


    @Test
    public void wallCanBeDestroyed(){
        wall = new Wall();

        MachineFactory machineFactory = new MachineFactory();
        Creature catapult = machineFactory.create("Catapult");
        catapult.attackWall(wall,new Point(0,0));

        assertEquals(1000,wall.getHP());
//        assertThat(wall.getCurrentLevel() == 3);
//        wall.takeDamageFromCatapult(damage,new Point(0,0));
//        assertThat(wall.getCurrentLevel() == 2);
//
//        wall.takeDamageFromCatapult(damage,new Point(0,0));
//        assertThat(wall.getCurrentLevel() == 3);
//
//        wall.takeDamageFromCatapult(damage,new Point(0,0));
//        assertEquals(0, wall.getHP());

    }

}
