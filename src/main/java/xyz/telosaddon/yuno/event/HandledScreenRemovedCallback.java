package xyz.telosaddon.yuno.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screen.Screen;

// observer implementation taken from torilhosaddon: https://github.com/Torilhos/TorilhosAddon
public interface HandledScreenRemovedCallback {
    Event<HandledScreenRemovedCallback> EVENT = EventFactory.createArrayBacked(HandledScreenRemovedCallback.class, listeners -> screen -> {
        for (var listener : listeners) {
            listener.onRemoved(screen);
        }
    });

    void onRemoved(Screen screen);
}
