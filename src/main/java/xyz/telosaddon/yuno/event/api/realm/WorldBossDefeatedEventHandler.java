package xyz.telosaddon.yuno.event.api.realm;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface WorldBossDefeatedEventHandler {
    Event<WorldBossDefeatedEventHandler> EVENT = EventFactory.createArrayBacked(WorldBossDefeatedEventHandler.class, listeners -> bossName -> {
        for (var listener : listeners) {
            listener.onBossDefeated(bossName);
        }
    });

    void onBossDefeated(String bossName);
}
