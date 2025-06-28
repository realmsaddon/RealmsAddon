package xyz.telosaddon.yuno.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.telosaddon.yuno.TelosAddon;
import xyz.telosaddon.yuno.event.BossDefeatedEvent;
import xyz.telosaddon.yuno.event.HandledScreenRemovedCallback;
import xyz.telosaddon.yuno.features.Features;


import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

import static xyz.telosaddon.yuno.TelosAddon.*;

@Mixin(MessageHandler.class)
public class MixinMessageHandler {
    private boolean trackerBit = false;
    private static final Pattern BOSS_DEFEATED_MESSAGE_PATTERN = Pattern.compile("^(\\w+) has been defeated!");


    @Inject(method = "method_45745", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;addMessage(Lnet/minecraft/text/Text;)V"))
    private void onDisguisedChatLambda(MessageType.Parameters parameters, Text text, Instant instant, CallbackInfoReturnable<Boolean> cir) {
        onChat(text);
    }

    @Inject(method = "processChatMessageInternal", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V"))
    private void onChatMessage(MessageType.Parameters params, SignedMessage message, Text decorated, GameProfile sender, boolean onlyShowSecureChat, Instant receptionTimestamp, CallbackInfoReturnable<Boolean> cir) {
        onChat(decorated);
    }

    @Inject(method =  "onGameMessage", at = @At("HEAD"))
    private void onGameMessage(Text message, boolean overlay, CallbackInfo ci) {
        onChat(message);
    }

    @Unique
    private void onChat(Text text) {
        String s = text.getString().trim();

        if(s.contains("discord.telosrealms.com")){ // nexus check
            Features.BOSS_TRACKER_FEATURE.clearAlive();

            if (!CONFIG.enableJoinText|| TelosAddon.getInstance().getPlayTime() > 15) return; // don't spam this thing
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null) {
                CompletableFuture.runAsync(() -> {
                    try {
                        Thread.sleep(10); // make this display after server join message
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    client.player.sendMessage(Text.of("§6[§e" + MOD_NAME + MOD_VERSION + "§6]" +
                            "\n§ePlease note this is a third party mod and is not affiliated with Telos Realms. "), false);
                    try {
                        client.player.sendMessage(Text.literal("§eFor bugs, support, and other questions, please join our discord: §f§nhttps://discord.gg/2pa42RxuaF")
                                .setStyle(Style.EMPTY
                                        // Set the click event to open the URL
                                        .withClickEvent(new ClickEvent.OpenUrl(new URI("https://discord.gg/2pa42RxuaF")))
                                        // Optional: Set a hover text when the player hovers over the link
                                        .withHoverEvent(new HoverEvent.ShowText(Text.literal("Join our discord!")))
                                ), false);
                    } catch (Exception e) {
                        LOGGER.warning(e.getMessage());
                    }

                });
            }
        }

        if (BOSS_DEFEATED_MESSAGE_PATTERN.matcher(s).find()){
            BossDefeatedEvent.EVENT.invoker().onBossDefeated(s.split(" ")[0]);

        }

        if(!s.equals("===============================================")) return;
        if (trackerBit = !trackerBit) return; // there's 2 bars in the kill message


        CONFIG.totalRuns++;
        CONFIG.noWhiteRuns++;
        CONFIG.noBlackRuns++;



    }

}
