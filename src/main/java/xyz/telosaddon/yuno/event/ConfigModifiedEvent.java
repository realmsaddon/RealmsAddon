package xyz.telosaddon.yuno.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface ConfigModifiedEvent {

    // agh is it even worth it
//    Event<ConfigModifiedEvent> EVENT = EventFactory.createArrayBacked(ConfigModifiedEvent.class, listeners -> value -> {
//        for (var listener : listeners) {
//            listener.onConfigModified(value);
//        }
//    });
//
//    <T> void onConfigModified(T value);
}
