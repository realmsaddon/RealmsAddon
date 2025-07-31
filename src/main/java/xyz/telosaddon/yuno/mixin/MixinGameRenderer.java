package xyz.telosaddon.yuno.mixin;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.telosaddon.yuno.TelosAddon;
import xyz.telosaddon.yuno.sound.SoundManager;


import java.util.Objects;

import static xyz.telosaddon.yuno.TelosAddon.CONFIG;
import static xyz.telosaddon.yuno.TelosAddon.LOGGER;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {


    @Inject(method = "showFloatingItem", at = @At("HEAD"))
    private void showFloatingItem(ItemStack floatingItem, CallbackInfo ci) {
        if( !floatingItem.getComponents().isEmpty() && !floatingItem.getComponents().contains(DataComponentTypes.ITEM_MODEL)) return;

        var cmd = Objects.requireNonNull(floatingItem.getComponents().get(DataComponentTypes.ITEM_MODEL));
        System.out.println( "item: " + floatingItem.getItem().getTranslationKey()
                + "| path: " + cmd.getPath());

        SoundManager soundManager = TelosAddon.getInstance().getSoundManager();
        boolean soundSetting = CONFIG.soundSetting();
        switch (cmd.getPath()) {
            case "entity/pouch/royal_totem" -> {
                CONFIG.whiteBags(CONFIG.whiteBags() + 1);

                CONFIG.noWhiteRuns(0);

                if(soundSetting)
                    soundManager.playSound("white_bag");

            }
            case "entity/pouch/bloodshot_totem" -> {
                CONFIG.blackBags(CONFIG.blackBags() + 1);
                CONFIG.noBlackRuns(0);

                if(soundSetting)
                    soundManager.playSound("black_bag");

            }
            case "entity/pouch/companion  " -> CONFIG.goldBags(CONFIG.goldBags() + 1);
            case "entity/pouch/unholy_totem"  -> CONFIG.crosses(CONFIG.crosses() + 1);
            case "entity/pouch/halloween_totem","entity/pouch/valentine_totem", "entity/pouch/christmas_totem" -> {CONFIG.eventBags(CONFIG.eventBags() + 1);}
            case "entity/pouch/voidbound_totem" -> CONFIG.relics(CONFIG.relics() + 1);
            case "entity/pouch/rune" -> CONFIG.runes(CONFIG.runes() + 1);
            default -> {
            }
        }
    }
}
