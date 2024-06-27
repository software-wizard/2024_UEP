package pl.psi.obstacles;

import lombok.Getter;
import lombok.Setter;
import pl.psi.Point;
import java.util.ArrayList;

@Getter
@Setter
public class Wall implements ObstaclesIF, ObstaclesObservable {

    private int levelOneHP = 1500;
    private int levelTwoHP = 1000;
    private int levelThreeHP = 500;
    private int currentHP;
    private int currentLevel;
    private Point point;

    private final ArrayList<ObstacleObserver> observers = new ArrayList<>();



    public Wall() {
        this.currentHP = levelOneHP;
        this.currentLevel = 1;

    }

     public void takeDamageFromCatapult(int damage, Point aPoint) {
        if (currentLevel == 1) {
            currentHP -= damage;
            if (currentHP <= 0) {
                currentLevel = 2;
                currentHP = levelTwoHP;
            }
        } else if (currentLevel == 2) {
            currentHP -= damage;
            if (currentHP <= 0) {
                currentLevel = 3;
                currentHP = levelThreeHP;
            }
        } else if (currentLevel == 3) {
            currentHP -= damage;
            if (currentHP <= 0) {
                currentHP = 0;
                notifyObservers(aPoint);

            }
        }
    }

    public void takeDamageFromCreature(int damage, Point aPoint) {

        if (currentLevel == 2) {
            currentHP -= damage;
            if (currentHP <= 0) {
                currentLevel = 3;
                currentHP = levelThreeHP;
            }
        } else if (currentLevel == 3) {
            currentHP -= damage;
            if (currentHP <= 0) {
                currentHP = 0;
                notifyObservers(aPoint);
            }
        }
    }

    @Override
    public void addObserver(ObstacleObserver o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers(Point point) {
        for (ObstacleObserver o : observers) {
            o.update(this, point);
        }
    }


    public int getHP() {
        return currentHP;
    }
}
