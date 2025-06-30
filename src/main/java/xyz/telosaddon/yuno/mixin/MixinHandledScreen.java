package xyz.telosaddon.yuno.mixin;


import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.telosaddon.yuno.event.HandledScreenRemovedCallback;

import static xyz.telosaddon.yuno.TelosAddon.LOGGER;

@Mixin(HandledScreen.class)
public class MixinHandledScreen {
    @Inject(method = "removed", at = @At("HEAD"))
    private void removed(CallbackInfo info) {
        HandledScreenRemovedCallback.EVENT.invoker().onRemoved(((Screen) (Object) this));
        LOGGER.info("closed screen");

    }
}
