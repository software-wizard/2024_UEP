package pl.psi.obstacles;

public class Obstacle {
    private static String lastUsedName = "Tree";


    public String getImagePath() {
        String basePath = "/obstacles/" + getName() + ".png";
        return basePath;

    }

    public String getName() {
        if ("Tree".equals(lastUsedName)) {
            lastUsedName = "Stones";
        } else {
            lastUsedName = "Tree";
        }
        return lastUsedName;
    }
}
