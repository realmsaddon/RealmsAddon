package xyz.telosaddon.yuno.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import xyz.telosaddon.yuno.TelosAddon;
import xyz.telosaddon.yuno.event.api.realm.BossDefeatedEventHandler;
import xyz.telosaddon.yuno.event.api.realm.DungeonStartedEventHandler;
import xyz.telosaddon.yuno.utils.data.DungeonData;

import java.util.concurrent.CompletableFuture;

import static xyz.telosaddon.yuno.TelosAddon.LOGGER;

public class DungeonTimerFeature extends ToggleableFeature implements DungeonStartedEventHandler, BossDefeatedEventHandler {
    private static boolean timerActive = false;
    private static boolean bossBeaten = false;
    private static String currentDungeon = "";
    private static String currentFinalBoss = "";
    private static int timer = 0;

    protected DungeonTimerFeature() {
        super(TelosAddon.CONFIG.keys.dungeonTimerSetting);
        DungeonStartedEventHandler.EVENT.register(this);
        BossDefeatedEventHandler.EVENT.register(this);
    }

    @Override
    public void onDungeonSpawned(DungeonData dungeonType) {
        if (!isEnabled()) return;
        if (timerActive){
            LOGGER.warn("Attempted to start new timer while one was active!");
            return;
        }

        // make only 1 timer active at a time
        timerActive = true;

        CompletableFuture.runAsync(()->{
            currentDungeon = dungeonType.areaName;
            currentFinalBoss = dungeonType.finalBossName;

            timer = 0;
            LOGGER.info("dungeon " + dungeonType.areaName + " has spawned");
            while(!bossBeaten) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    LOGGER.error(e.getLocalizedMessage());
                }
                timer++;

            }

            var player = MinecraftClient.getInstance().player;
            if (player != null) player.sendMessage(Text.of("§aDefeated §e" + currentDungeon + "§a in §e" + getTimeStringFormatted()),false);
            bossBeaten = false;
            currentFinalBoss = "";
            timerActive = false;
        });

    }

    public static boolean getTimerActive(){
        return timerActive;
    }

    public static int getTime(){
        return timer;
    }

    public static String getTimeStringFormatted(){
        return String.format("%02d:%02d:%02d", timer/3600, (timer%3600)/60, timer%60);
    }
    @Override
    public void onBossDefeated(String bossName) {
        if (bossName.equals(currentFinalBoss)){
            bossBeaten = true;
        }
    }
}
