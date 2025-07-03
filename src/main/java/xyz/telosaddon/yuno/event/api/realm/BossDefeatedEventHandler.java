package xyz.telosaddon.yuno.event.api.realm;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import xyz.telosaddon.yuno.utils.data.BossData;

// called when a player gets a loot leaderboard placement. this only applies to bosses that the player has damaged.
public interface BossDefeatedEventHandler {
    Event<BossDefeatedEventHandler> EVENT = EventFactory.createArrayBacked(BossDefeatedEventHandler.class, listeners -> (boss) -> {
        for (var listener : listeners) {
            listener.onBossDefeated(boss);
        }
    });

    void onBossDefeated(BossData boss);
}