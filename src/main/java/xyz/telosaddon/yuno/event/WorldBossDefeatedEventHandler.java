package xyz.telosaddon.yuno.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

// called when a boss that the player has been fighting is defeated. This only fires on world boss deaths.
public interface WorldBossDefeatedEventHandler {
    Event<WorldBossDefeatedEventHandler> EVENT = EventFactory.createArrayBacked(WorldBossDefeatedEventHandler.class, listeners -> bossName -> {
        for (var listener : listeners) {
            listener.onBossDefeated(bossName);
        }
    });

    void onBossDefeated(String bossName);
}
