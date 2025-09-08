package xyz.telosaddon.yuno;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.telosaddon.yuno.discordrpc.DiscordRPCManager;
import xyz.telosaddon.yuno.event.api.realm.DungeonStartedEventHandler;
import xyz.telosaddon.yuno.features.DungeonTimerFeature;
import xyz.telosaddon.yuno.hotkey.*;
import xyz.telosaddon.yuno.features.ShowMainRangeFeature;
import xyz.telosaddon.yuno.features.ShowOffHandFeature;
import xyz.telosaddon.yuno.renderer.HitboxRenderer;
import xyz.telosaddon.yuno.renderer.RangeRenderer;
import xyz.telosaddon.yuno.renderer.waypoints.WaypointRenderer;
import xyz.telosaddon.yuno.sound.SoundManager;

import xyz.telosaddon.yuno.utils.BossBarUtils;
import xyz.telosaddon.yuno.utils.LocalAPI;

import xyz.telosaddon.yuno.utils.config.ModConfig;
import xyz.telosaddon.yuno.sound.CustomSound;
import xyz.telosaddon.yuno.utils.data.DungeonData;


import static xyz.telosaddon.yuno.utils.LocalAPI.updateAPI;

public class TelosAddon implements ClientModInitializer  {
    public static final String MOD_NAME = "RealmsAddon";
    public static final String MOD_VERSION = "v0.4.2";
    public static final String MOD_ID = "realmsaddon";

    public static final ModConfig CONFIG = ModConfig.createAndLoad();

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
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
    public int pitybagWidth;
    public int pitybagHeight;
    private ShowMainRangeFeature showMainRangeFeature;

    private ShowOffHandFeature showOffHandFeature;

    private DungeonData previousDungeonState;

    public void initHotkeys(){
        MountHotkey.init();
        MenuHotkey.init();
        TeleportMenuHotkey.init();
        CallHotkey.init();
        HitboxHotkey.init();
    }
    public void stop() {

        rpcManager.stop();
    }
    public void tick() {

        ClientPlayerEntity player = mc.player;
        if(player == null) return;
        if(!isOnTelos()) return;

        this.showMainRangeFeature.tick();
        this.showOffHandFeature.tick();

        updateAPI();

        tickCounter++;
        if(tickCounter >= 20) {
            playTime++;
            CONFIG.totalPlayTime(CONFIG.totalPlayTime() + 1);
            tickCounter = 0;

        }

        DungeonData currentDungeonState = DungeonData.findByKey(LocalAPI.getCurrentCharacterArea());

        if (currentDungeonState != null && currentDungeonState != previousDungeonState){
            LOGGER.info("user entered dungeon: {}", currentDungeonState.areaName);
            DungeonStartedEventHandler.EVENT.invoker().onDungeonSpawned(currentDungeonState);
            previousDungeonState = currentDungeonState;
        } else if (previousDungeonState != null && currentDungeonState == null){
            DungeonTimerFeature.disableTimer();
            LOGGER.info("user exited dungeon unexpectedly, stopping timer");
            previousDungeonState = null;
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
        if(mc.world != null && !mc.isPaused() && serverIP.toLowerCase().contains("telosrealms")) {
            return true;
        }
        return false;
    }

    public boolean isEditMode() { return this.editMode; }
    public void setEditMode(boolean value) { this.editMode = value; }

    @Override
    public void onInitializeClient() {
        instance = this;

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
