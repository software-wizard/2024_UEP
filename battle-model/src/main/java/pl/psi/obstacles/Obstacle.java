package pl.psi.obstacles;

public class Obstacle {
    private static String lastUsedName = "Wall2";


    public String getImagePath() {
        String basePath = "/obstacles/" + getName() + ".png";
        return basePath;

    }

    public String getName() {
        if ("DunePalm".equals(lastUsedName)) {
            lastUsedName = "Stones";
        } else {
            lastUsedName = "DunePalm";
        }
        return lastUsedName;
    }
}
