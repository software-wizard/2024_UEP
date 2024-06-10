package pl.psi.obstacles;

import pl.psi.Point;

import java.util.ArrayList;


public class ObstaclesWithHP implements ObstaclesIF, ObstaclesWithHPObservable {

    private int maxHp;
    private int currentHp;
    private final ArrayList<ObstacleObserver> observers = new ArrayList<>();


    public ObstaclesWithHP(int maxHp) {
        this.maxHp = maxHp;
        this.currentHp = maxHp;
    }
    @Override
    public void addObserver(ObstacleObserver o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers(Object arg) {
        for (ObstacleObserver o : observers) {
            o.update(this, arg);
        }
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
