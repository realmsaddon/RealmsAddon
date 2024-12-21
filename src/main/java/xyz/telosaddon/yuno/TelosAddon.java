package xyz.telosaddon.yuno;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import xyz.telosaddon.yuno.discordrpc.DiscordRPCManager;
import xyz.telosaddon.yuno.hotkey.*;
import xyz.telosaddon.yuno.features.ShowMainRangeFeature;
import xyz.telosaddon.yuno.features.ShowOffHandFeature;
import xyz.telosaddon.yuno.renderer.RangeRenderer;
import xyz.telosaddon.yuno.renderer.waypoints.WaypointRenderer;
import xyz.telosaddon.yuno.sound.SoundManager;

import xyz.telosaddon.yuno.utils.BossBarUtils;
import xyz.telosaddon.yuno.utils.config.ModConfig;
import xyz.telosaddon.yuno.utils.config.TelosConfig;
import xyz.telosaddon.yuno.sound.CustomSound;

import java.util.*;

import java.util.logging.Logger;

import static xyz.telosaddon.yuno.utils.LocalAPI.updateAPI;

public class TelosAddon implements ClientModInitializer  {
    public static final String MOD_NAME = "RealmsAddon";
    public static final String MOD_VERSION = "v0.3";
    public static final String MOD_ID = "realmsaddon";

    public static final ModConfig CONFIG = ModConfig.createAndLoad();

    public static final Logger LOGGER = Logger.getLogger(MOD_NAME);
    private final MinecraftClient mc = MinecraftClient.getInstance();
    public static TelosAddon instance;

    private static final DiscordRPCManager rpcManager = new DiscordRPCManager();
    private SoundManager soundManager;
    private TelosConfig config;
    private Map<String, Integer> bagCounter;
    private long playTime = 0;
    private int tickCounter = 0;
    private List<String> aliveBosses;
    private boolean editMode = false;

    public int infoWidth;
    public int infoHeight;
    public int bagWidth;
    public int bagHeight;

    private ShowMainRangeFeature showMainRangeFeature;

    private ShowOffHandFeature showOffHandFeature;


    public void initHotkeys(){
        NexusHotkey.init();
        MenuHotkey.init();
        TeleportMenuHotkey.init();
        CallHotkey.init();
        TestHotkey.init();
    }
    public void stop() {

        rpcManager.stop();
    }
    public void tick() {

        ClientPlayerEntity player = mc.player;
        if(player == null) return;

        if(mc.options.attackKey.isPressed() && CONFIG.swingSetting() && isOnTelos()) {
            boolean canSwing = !player.getItemCooldownManager().isCoolingDown(player.getMainHandStack());
            if (!CONFIG.swingIfNoCooldown()|| canSwing) {
                player.swingHand(Hand.MAIN_HAND);
            }
        }

        this.showMainRangeFeature.tick();
        this.showOffHandFeature.tick();

        if(isOnTelos()) {
            updateAPI();
            tickCounter++;
            if(tickCounter >= 20) {
                playTime++;
                CONFIG.totalPlayTime(CONFIG.totalPlayTime() + 1);
                tickCounter = 0;

            }
        }

    }

    public void sendMessage(String message) {
        mc.inGameHud.setOverlayMessage(Text.of("§6" + message), false);
    }

    public static TelosAddon getInstance() { return instance; }
    public TelosConfig getConfig() { return config; }
    public SoundManager getSoundManager() { return soundManager; }

    public void toggleGamma(boolean b) {
        Double newGamma = b ? 1500D : 0D;
        mc.options.getGamma().setValue(newGamma);
    }

    private void loadBagCounter() {
        bagCounter = new HashMap<>();
        bagCounter.put("GreenBags", 0);
        bagCounter.put("GoldBags", 0);
        bagCounter.put("WhiteBags", 0);
        bagCounter.put("BlackBags", 0);
        bagCounter.put("EventBags", 0);
        bagCounter.put("Crosses", 0);
        bagCounter.put("Relics", 0);
        bagCounter.put("Runes", 0);
        bagCounter.put("TotalRuns", 0);
        bagCounter.put("NoWhiteRuns", 0);
        bagCounter.put("NoBlackRuns", 0);
    }

    public Map<String, Integer> getBagCounter() {
        return this.bagCounter;
    }

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

        BossBarUtils.init();
        rpcManager.start();
        initHotkeys();
        loadBagCounter();

        soundManager = new SoundManager();

        soundManager.addSound(new CustomSound("button_click"));
        soundManager.addSound(new CustomSound("white_bag"));
        soundManager.addSound(new CustomSound("black_bag"));

        this.showMainRangeFeature = new ShowMainRangeFeature(config);
        this.showOffHandFeature = new ShowOffHandFeature(config);

        RangeRenderer.init();
        WaypointRenderer.init();


        new InitializeCommands().initializeCommands();
    }

    public void run(){
        // todo move to feature
        if(CONFIG.gammaSetting()) {
            toggleGamma(true);
        }
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
