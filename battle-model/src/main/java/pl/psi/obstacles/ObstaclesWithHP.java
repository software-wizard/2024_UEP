package pl.psi.obstacles;

import pl.psi.Point;
import pl.psi.creatures.DefenderIf;
import pl.psi.creatures.TargetTypeEnum;

import java.util.ArrayList;


public class ObstaclesWithHP implements  ObstaclesObservable, DefenderIf {

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
    public void notifyObservers(Point point) {
        for (ObstacleObserver o : observers) {
            o.update(this, point);
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

    public String getImagePath() {
        String basePath = "/obstacles/" + getName() + ".png";
        return basePath;

    }

    public String getName() {
        return "Barrel";
    }

    public String toCurrentHPString() {
        return getHP() + System.lineSeparator();
    }

    @Override
    public TargetTypeEnum getType() {
        return TargetTypeEnum.OBSTACLEWITHHP;
    }
}
