package xyz.telosaddon.yuno.mixin;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.telosaddon.yuno.TelosAddon;
import xyz.telosaddon.yuno.sound.SoundManager;


import java.util.Objects;

import static xyz.telosaddon.yuno.TelosAddon.CONFIG;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {

    // todo: move all of these functionalities into their own features, add listeners n stuff
    @Inject(method = "showFloatingItem", at = @At("HEAD"))
    private void showFloatingItem(ItemStack floatingItem, CallbackInfo ci) {
        if(!floatingItem.getComponents().isEmpty() && !floatingItem.getComponents().contains(DataComponentTypes.CUSTOM_MODEL_DATA)) return;

        // TODO: fix for new component system
        int customModelData = 0;//Objects.requireNonNull(floatingItem.getComponents().get(DataComponentTypes.CUSTOM_MODEL_DATA));

        SoundManager soundManager = TelosAddon.getInstance().getSoundManager();
        boolean soundSetting = CONFIG.soundSetting();
        switch (customModelData) {
            case 11 -> {
                CONFIG.whiteBags(CONFIG.whiteBags() + 1);

                CONFIG.noWhiteRuns(0);

                if(soundSetting)
                    soundManager.playSound("white_bag");

            }
            case 10 -> {
                CONFIG.blackBags(CONFIG.blackBags() + 1);
                CONFIG.noBlackRuns(0);

                if(soundSetting)
                    soundManager.playSound("black_bag");

            }
            case 15 -> CONFIG.goldBags(CONFIG.goldBags() + 1);
            case 12 -> CONFIG.crosses(CONFIG.crosses() + 1);
            case 6,9 -> {CONFIG.eventBags(CONFIG.eventBags() + 1);}
            case 13 -> CONFIG.greenBags(CONFIG.greenBags() + 1);
            case 8 -> CONFIG.relics(CONFIG.relics() + 1);
            case 14 -> CONFIG.runes(CONFIG.runes() + 1);
            default -> {
            }
        }
    }
}
