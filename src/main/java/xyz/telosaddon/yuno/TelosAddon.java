package xyz.telosaddon.yuno;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import xyz.telosaddon.yuno.discordrpc.DiscordRPCManager;
import xyz.telosaddon.yuno.hotkey.*;
import xyz.telosaddon.yuno.features.ShowMainRangeFeature;
import xyz.telosaddon.yuno.features.ShowOffHandFeature;
import xyz.telosaddon.yuno.renderer.HitboxRenderer;
import xyz.telosaddon.yuno.renderer.RangeRenderer;
import xyz.telosaddon.yuno.renderer.waypoints.WaypointRenderer;
import xyz.telosaddon.yuno.sound.SoundManager;

import xyz.telosaddon.yuno.utils.BossBarUtils;
//import xyz.telosaddon.yuno.utils.config.ModConfig;
import xyz.telosaddon.yuno.sound.CustomSound;
import xyz.telosaddon.yuno.utils.config.ModConfigModel;

import java.util.logging.Logger;

import static xyz.telosaddon.yuno.utils.LocalAPI.updateAPI;
import static xyz.telosaddon.yuno.utils.TabListUtils.mc;

public class TelosAddon implements ClientModInitializer  {
    public static final String MOD_NAME = "RealmsAddon";
    public static final String MOD_VERSION = "v0.3.3";
    public static final String MOD_ID = "realmsaddon";


    public static ModConfigModel CONFIG;

    public static final Logger LOGGER = Logger.getLogger(MOD_NAME);
    private final MinecraftClient mc = MinecraftClient.getInstance();
    public static TelosAddon instance;

    private static final DiscordRPCManager rpcManager = new DiscordRPCManager();
    private SoundManager soundManager;
    private long playTime = 0;
    private int tickCounter = 0;
    private boolean editMode = false;

    public int infoWidth;
    public int infoHeight;
    public int bagWidth;
    public int bagHeight;

    private ShowMainRangeFeature showMainRangeFeature;

    private ShowOffHandFeature showOffHandFeature;


    // todo: move these to their own features and init them in a feature class
    public void initHotkeys(){
        MountHotkey.init();
        MenuHotkey.init();
        TeleportMenuHotkey.init();
        CallHotkey.init();
        HitboxHotkey.init();
        TestHotkey.init();
    }
    public void stop() {

        rpcManager.stop();
    }
    public void tick() {

        ClientPlayerEntity player = mc.player;
        if(!isOnTelos() || player == null) return;

        this.showMainRangeFeature.tick();
        this.showOffHandFeature.tick();

        updateAPI();

        tickCounter++;
        if(tickCounter >= 20) {
            playTime++;
            CONFIG.totalPlayTime = CONFIG.totalPlayTime + 1;
            tickCounter = 0;

        }


    }

    public void sendMessage(String message) {
        mc.inGameHud.setOverlayMessage(Text.of("§6" + message), false);
    }

    public static TelosAddon getInstance() { return instance; }

    public SoundManager getSoundManager() { return soundManager; }

    public String getPlaytimeText() {
        long hours = this.playTime / 3600;
        long minutes = (this.playTime % 3600) / 60;
        long seconds = this.playTime % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public boolean isOnTelos() {
        String serverIP = mc.getCurrentServerEntry() != null ? mc.getCurrentServerEntry().address : "Null";
        if(mc.world != null && !mc.isPaused() && serverIP.contains("telosrealms.com")) {
            return true;
        }
        return false;
    }

    public boolean isEditMode() { return this.editMode; }
    public void setEditMode(boolean value) { this.editMode = value; }

    @Override
    public void onInitializeClient() {
        instance = this;
        AutoConfig.register(ModConfigModel.class, GsonConfigSerializer::new);
        CONFIG = ModConfigModel.getInstance();
        BossBarUtils.init();
        rpcManager.start();
        initHotkeys();


        soundManager = new SoundManager();

        soundManager.addSound(new CustomSound("button_click"));
        soundManager.addSound(new CustomSound("white_bag"));
        soundManager.addSound(new CustomSound("black_bag"));

        this.showMainRangeFeature = new ShowMainRangeFeature();
        this.showOffHandFeature = new ShowOffHandFeature();

        RangeRenderer.init();
        WaypointRenderer.init();
        HitboxRenderer.init();

        new InitializeCommands().initializeCommands();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientPlayerEntity player = client.player;
            if(mc.options.attackKey.isPressed() && TelosAddon.getInstance().isOnTelos()) {
                assert player != null;
                boolean canSwing = !player.getItemCooldownManager().isCoolingDown(player.getMainHandStack());
                if (!TelosAddon.CONFIG.swingIfNoCooldown|| canSwing) {
                    player.swingHand(Hand.MAIN_HAND);
                }
            }
        });
    }

    public ShowOffHandFeature getShowOffHandFeature() {
        return showOffHandFeature;
    }

    public ShowMainRangeFeature getShowMainRangeFeature() {
        return showMainRangeFeature;
    }

    public DiscordRPCManager getRpcManager () {
        return rpcManager;
    }
    public long getPlayTime() {
        return playTime;
    }

}
