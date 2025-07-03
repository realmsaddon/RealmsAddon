package xyz.telosaddon.yuno.event.api.realm;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface RaphPortalSpawnedEventHandler {
    Event<RaphPortalSpawnedEventHandler> EVENT = EventFactory.createArrayBacked(RaphPortalSpawnedEventHandler.class, listeners -> () -> {
        for (var listener : listeners) {
            listener.onRaphSpawned();
        }
        return;
    });

    void onRaphSpawned();
}
