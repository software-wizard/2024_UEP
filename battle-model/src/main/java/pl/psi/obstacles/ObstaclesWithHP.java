package pl.psi.obstacles;

import pl.psi.Point;


public class ObstaclesWithHP extends ObstaclesWithHPObserver implements ObstaclesIF {

    private int maxHp;
    private int currentHp;

    public ObstaclesWithHP(int maxHp) {
        this.maxHp = maxHp;
        this.currentHp = maxHp;
    }

    public void takeDamage(Point aPoint, int damage) {
        currentHp -= damage;
        if (currentHp <= 0) {
            currentHp = 0;
            notifyObservers(aPoint);
        }
    }

    public int getHP() {
        return currentHp;
    }
}
