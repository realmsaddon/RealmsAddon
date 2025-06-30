package xyz.telosaddon.yuno.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

// called when a player gets a loot leaderboard placement. this only applies to bosses that the player has damaged.
public interface BossDefeatedEventHandler {
    Event<BossDefeatedEventHandler> EVENT = EventFactory.createArrayBacked(BossDefeatedEventHandler.class, listeners -> (bossName, placement, damage) -> {
        for (var listener : listeners) {
            listener.onBossDefeated(bossName, placement, damage);
        }
    });

    void onBossDefeated(String bossName, int placement, int damage);
}

