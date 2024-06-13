package pl.psi.obstacles;

import pl.psi.Point;

public interface ObstacleObserver {
    void update(ObstaclesObservable o, Point point);
}
