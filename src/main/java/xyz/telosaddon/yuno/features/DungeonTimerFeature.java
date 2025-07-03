package xyz.telosaddon.yuno.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import xyz.telosaddon.yuno.TelosAddon;
import xyz.telosaddon.yuno.event.api.realm.BossDefeatedEventHandler;
import xyz.telosaddon.yuno.event.api.realm.DungeonStartedEventHandler;
import xyz.telosaddon.yuno.utils.data.BossData;
import xyz.telosaddon.yuno.utils.data.DungeonData;

import java.util.concurrent.CompletableFuture;

import static xyz.telosaddon.yuno.TelosAddon.LOGGER;

public class DungeonTimerFeature extends ToggleableFeature implements DungeonStartedEventHandler, BossDefeatedEventHandler {
    private static boolean timerActive = false;
    private static boolean bossBeaten = false;

    private static DungeonData currentDungeon;
    private static BossData currentFinalBoss;
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
            currentDungeon = dungeonType;
            currentFinalBoss = dungeonType.finalBoss;

            timer = 0;
            bossBeaten = false;
            LOGGER.info("dungeon " + dungeonType.areaName + " has spawned");
            while(!bossBeaten) {
                //LOGGER.info("Hi from thread: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    LOGGER.error(e.getLocalizedMessage());
                }
                timer++;

            }
            bossBeaten = false;
            currentFinalBoss = null;
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

    public static void disableTimer(){
        bossBeaten = true;

    }
    @Override
    public void onBossDefeated(BossData bossName) {
        if (currentDungeon == null) return;

        if (bossName.equals(currentFinalBoss)){
            bossBeaten = true;
            var player = MinecraftClient.getInstance().player;
            if (player != null) player.sendMessage(Text.of("§aDefeated §e" + currentDungeon.areaName + "§a in §e" + getTimeStringFormatted()),false);

            // todo: make some decorator shit for multi stage dungeons
        } else if (currentDungeon == DungeonData.SHATTERED_KINGDOM || currentDungeon == DungeonData.CELESTIALS_PROVINCE ){
            var player = MinecraftClient.getInstance().player;
            if (player != null) player.sendMessage(Text.of("§2Split: §aDefeated §e" + bossName.getLabel() + "§a in §e" + getTimeStringFormatted()),false);

        }
    }
}
