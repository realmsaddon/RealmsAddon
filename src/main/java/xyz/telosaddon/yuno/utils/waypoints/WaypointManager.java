package xyz.telosaddon.yuno.utils.waypoints;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import xyz.telosaddon.yuno.renderer.waypoints.WaypointRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WaypointManager {
    private static WaypointManager manager;

    private final HashMap<String, Waypoint> waypoints = new HashMap<>();
    private final HashMap<String, Waypoint> currentAlive = new HashMap<>();

    public static WaypointManager getInstance(){
        if (manager == null)
            manager = new WaypointManager();
        return manager;
    }

    private WaypointManager(){

        waypoints.put("Oozul", new Waypoint("Oozul",-423, 195,  90));
        waypoints.put("Freddy", new Waypoint("Freddy",-134, 203,  654));
        waypoints.put("Chungus", new Waypoint("Chungus", 62, 256,  -490));
        waypoints.put("Freddy", new Waypoint("Freddy",-134, 203,  654));
        waypoints.put("Astaroth",  new Waypoint("Astaroth", 251, 219,  62));
        waypoints.put("Glumi", new Waypoint("Glumi", 316, 223,  557));
        waypoints.put("Hollowbane", new Waypoint("Hollowbane", 234, 200,  702));
        waypoints.put("Illarious", new Waypoint("Illarious", 480, 211,  -43));
        waypoints.put("Valus", new Waypoint("Valus", 34, 210,  309));
        waypoints.put("Tidol", new Waypoint("Tidol", -544, 189,  364));
        waypoints.put("Anubis", new Waypoint("Anubis", 459, 204,  -467));
        waypoints.put("Onyx", new Waypoint("Onyx", -14, 243.0,  88));
    }

    public void addAlive(String name){
        if (!waypoints.containsKey(name)) return;
        Waypoint waypoint = waypoints.get(name);
        currentAlive.put(name, waypoint);
    }

    public void removeAlive(String name){
        if (!currentAlive.containsKey(name)) return;
        currentAlive.remove(name);
    }

    public void clearAlive(){
        currentAlive.clear();
    }

    public List<Waypoint> getAliveWaypoints() {
        return new ArrayList<>(currentAlive.values());
    }

}
