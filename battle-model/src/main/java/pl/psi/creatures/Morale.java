package pl.psi.creatures;

import lombok.Setter;

import java.util.Random;

public class Morale {
    @Setter
    private int value;
    private Random random;

    public Morale(int value) {
        this.value = value;
    }

    public boolean shouldFreeze() {
        if (value >= 0) {
            return false;
        }
        double chance;
        switch (value) {
            case -1:
                chance = 1.0/12;
                break;
            case -2:
                chance = 1.0/6;
                break;
            case -3:
                chance = 1.0/4;
                break;
            default:
                return false;
        }
        return random.nextDouble() <= chance;
    }

    public boolean shouldAttackAgain() {
        if (value <= 0) {
            return false;
        }
        double chance;
        switch (value) {
            case 1:
                chance = 1.0/12;
                break;
            case 2:
                chance = 1.0/6;
                break;
            case 3:
                chance = 1.0/4;
                break;
            default:
                return false;
        }
        return random.nextDouble() <= chance;
    }
}
