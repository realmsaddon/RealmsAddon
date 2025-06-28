package xyz.telosaddon.yuno.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screen.Screen;

// called when a boss that the player has been fighting is defeated. This will not fire for world boss deaths.
public interface BossDefeatedEvent {
    Event<BossDefeatedEvent> EVENT = EventFactory.createArrayBacked(BossDefeatedEvent.class, listeners -> bossName -> {
        for (var listener : listeners) {
            listener.onBossDefeated(bossName);
        }
    });

    void onBossDefeated(String bossName);
}
