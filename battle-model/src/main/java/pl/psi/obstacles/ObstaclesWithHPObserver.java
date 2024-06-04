package pl.psi.obstacles;

import java.util.ArrayList;
import java.util.List;

public class ObstaclesWithHPObserver {
    private final List<ObstaclesWithHPObserverIF> observers = new ArrayList<>();

    public void addObserver(ObstaclesWithHPObserverIF o) {
        observers.add(o);
    }

    public void notifyObservers(Object arg) {
        for (ObstaclesWithHPObserverIF o : observers) {
            o.update(this, arg);
        }
    }
}
