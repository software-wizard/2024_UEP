package pl.psi.obstacles;

import pl.psi.Point;

public interface ObstaclesObservable {
    void addObserver(ObstacleObserver o);
    void notifyObservers(Point point);
}
