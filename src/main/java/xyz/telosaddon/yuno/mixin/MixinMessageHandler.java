package xyz.telosaddon.yuno.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.telosaddon.yuno.TelosAddon;
import xyz.telosaddon.yuno.event.api.realm.BossDefeatedEventHandler;
import xyz.telosaddon.yuno.event.api.realm.BossSpawnedEventHandler;
import xyz.telosaddon.yuno.event.api.realm.RaphPortalSpawnedEventHandler;
import xyz.telosaddon.yuno.event.api.realm.WorldBossDefeatedEventHandler;
import xyz.telosaddon.yuno.features.BagTrackerFeature;
import xyz.telosaddon.yuno.features.Features;
import xyz.telosaddon.yuno.utils.data.BossData;


import java.net.URI;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.List;


import static xyz.telosaddon.yuno.TelosAddon.*;

@Mixin(MessageHandler.class)
public class MixinMessageHandler {
    private boolean trackerBit = false;
    private static final Pattern BOSS_DEFEATED_MESSAGE_PATTERN = Pattern.compile("^(\\w+) has been defeated");
    private static final Pattern BOSS_SPAWNED_MESSAGE_PATTERN = Pattern.compile("^(\\w+) has spawned at");
    private static final Pattern ONYX_PORTAL_OPEN_MESSAGE_PATTERN = Pattern.compile("^A portal to Raph's Castle has opened at");
    private static final List<String> BS_Boss=Arrays.asList("True Seraph","True Ophan","Sylvaris","Voided Omnipotent","Kurvaros","Shadowflare","Valerion","Nebula","Prismara","Omnipotent","Silex","Chronos","Warden","Herald","Reaper","Defender","Asmodeus","Seraphim","Raphael","Ophanim");
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

        if (s.isEmpty()) return;
        if(s.charAt(0) == '\uD814'){ // nexus check
            Features.BOSS_TRACKER_FEATURE.clearAlive();

            if (!CONFIG.enableJoinText()|| TelosAddon.getInstance().getPlayTime() > 15) return; // don't spam this thing
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
                        LOGGER.warn(e.getMessage());
                    }

                });
            }
        }

        if(ONYX_PORTAL_OPEN_MESSAGE_PATTERN.matcher(s).find()){
            RaphPortalSpawnedEventHandler.EVENT.invoker().onRaphSpawned();
        }
        if (BOSS_DEFEATED_MESSAGE_PATTERN.matcher(s).find()){
            WorldBossDefeatedEventHandler.EVENT.invoker().onBossDefeated(s.split(" ")[0]);
        }

        if (BOSS_SPAWNED_MESSAGE_PATTERN.matcher(s).find()){
            BossSpawnedEventHandler.EVENT.invoker().onBossSpawned(s.split(" ")[0]);
        }

        if (trackerBit){
            if (BossData.findByKey(s.trim()).isPresent()){
                LOGGER.info("(BossDefeatedEvent) boss " + s.trim()  + " has been defeated");
                BossDefeatedEventHandler.EVENT.invoker().onBossDefeated(BossData.findByKey(s.trim()).get());
                
                CONFIG.totalRuns(CONFIG.totalRuns() + 1);  //incrementing after killing a boss
                CONFIG.noWhiteRuns(CONFIG.noWhiteRuns() + 1); //increments for any boss
                if(BS_Boss.contains(s.trim())){ //checks if boss can drop BS
                    BagTrackerFeature.blackBagPityCounter(s.trim());
                    //CONFIG.noBlackRuns(CONFIG.noBlackRuns() + 1);
                    }
            }
        }
        if(!s.equals("===============================================")) return;
        if (trackerBit = !trackerBit) return; // there's 2 bars in the kill message, and guess what datatype has 2 states



    }

}
