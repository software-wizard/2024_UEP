package pl.psi.obstacles;

public interface ObstacleObserver {
    void update(ObstaclesWithHPObservable o, Object arg);
}
