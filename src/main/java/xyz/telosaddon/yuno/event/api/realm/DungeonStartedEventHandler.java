package xyz.telosaddon.yuno.event.api.realm;


import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import xyz.telosaddon.yuno.utils.data.DungeonData;

public interface DungeonStartedEventHandler {
    Event<DungeonStartedEventHandler> EVENT = EventFactory.createArrayBacked(DungeonStartedEventHandler.class, listeners -> dungeonType -> {
        for (var listener : listeners) {
            listener.onDungeonSpawned(dungeonType);
        }
    });

    void onDungeonSpawned(DungeonData dungeonType);
}
