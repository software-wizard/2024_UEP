package pl.psi.creatures;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
public class Morale {
    private int value;
    private final Random random;
    @Setter
    private boolean gotLucky;

    public Morale(int value) {
        this.value = validateValue(value);
        this.random = new Random();
    }

    public Morale(int value, Random random) {
        this.value = validateValue(value);
        this.random = random;
    }

    private int validateValue(int aValue) {
        if (aValue < -3) {
            return -3;
        }
        return Math.min(aValue, 3);
    }

    public void setValue(int value) {
        this.value = validateValue(value);
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
        if (value <= 0 || gotLucky) {
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
