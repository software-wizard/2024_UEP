package pl.psi.obstacles;

import lombok.Getter;
import lombok.Setter;
import pl.psi.Point;
import pl.psi.creatures.DefenderIf;
import pl.psi.creatures.TargetTypeEnum;

import java.util.ArrayList;

@Getter
@Setter
public class Wall implements ObstaclesObservable, DefenderIf {

    private int levelOneHP = 1000;
    private int levelTwoHP = 35;
    private int levelThreeHP = 20;
    private int currentHP;
    private int currentLevel;
    private Point point;
    private TargetTypeEnum targetType = TargetTypeEnum.WALL;

    private final ArrayList<ObstacleObserver> observers = new ArrayList<>();
    private String name;




    public Wall() {
        this.currentHP = levelOneHP;
        this.currentLevel = 1;

    }


     public void takeDamageFromCatapult(int damage, Point aPoint) {
        if (currentHP <=0 && currentLevel == 3){
            currentHP = 0;
            notifyObservers(aPoint);
        }else{
            if (currentLevel == 1){
                currentHP -= damage;
                if (currentHP <= 0) {
                    currentHP = levelTwoHP;
                    currentLevel = 2;
                }
            }
            else if (currentLevel == 2) {
                currentHP -= damage;
                if (currentHP <= 0) {
                    currentHP = levelThreeHP;
                    currentLevel = 3;
                }

            } else if (currentLevel == 3) {
                currentHP -= damage;
                if (currentHP <= 0) {
                    notifyObservers(aPoint);
                    currentHP = 0;

                }
            }
        }

    }

    public void takeDamageFromCreature(int damage, Point aPoint) {
        if (currentHP <=0 && currentLevel == 3){
            currentHP = 0;
            notifyObservers(aPoint);
        }else{
             if (currentLevel == 2) {
                currentHP -= damage;
                if (currentHP <= 0) {
                    currentHP = levelThreeHP;
                    currentLevel = 3;
                }

            } else if (currentLevel == 3) {
                currentHP -= damage;
                if (currentHP <= 0) {
                    notifyObservers(aPoint);
                    currentHP = 0;

                }
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

    @Override
    public TargetTypeEnum getType() {
        return TargetTypeEnum.WALL;
    }

    public String getImagePath() {
        String basePath = "/obstacles/" + getName() + ".png";
        return basePath;
    }


    public void setName(String aName) {
        this.name = aName;
    }

    public String getName() {
        return name;
    }

    public String toStringHP() {
        return String.valueOf(getHP());
    }
}
