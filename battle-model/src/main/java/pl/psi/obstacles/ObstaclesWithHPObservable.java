package pl.psi.obstacles;

public interface ObstaclesWithHPObservable {
    void addObserver(ObstacleObserver o);
    void notifyObservers(Object arg);
}
