package xyz.telosaddon.yuno.utils.waypoints;

public class Waypoint {
    private final String name;
    private final double x;
    private final double y;
    private final double z;

    public Waypoint(String name, double x, double y, double z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
