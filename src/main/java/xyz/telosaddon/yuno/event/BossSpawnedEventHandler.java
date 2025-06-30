package xyz.telosaddon.yuno.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

// called when a world boss is spawned
public interface BossSpawnedEventHandler {
    Event<BossSpawnedEventHandler> EVENT = EventFactory.createArrayBacked(BossSpawnedEventHandler.class, listeners -> bossName -> {
        for (var listener : listeners) {
            listener.onBossSpawned(bossName);
        }
    });

    void onBossSpawned(String bossName);
}
