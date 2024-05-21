package pl.psi.obstacles;

import pl.psi.Board;
import pl.psi.Point;


public class ObstaclesWithHP implements ObstaclesIF {

    private int maxHp;
    private int currentHp;
    private final Board board;

    public ObstaclesWithHP(int maxHp, Board board) {
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.board = board;
    }

    public void takeDamage(Point aPoint,int damage) {
        currentHp -= damage;
        if (currentHp <= 0) {
            currentHp = 0;
            board.removeFromTheMapObstacleWithHP(aPoint);
        }
    }

    public int getHP() {
        return currentHp;
    }
}
