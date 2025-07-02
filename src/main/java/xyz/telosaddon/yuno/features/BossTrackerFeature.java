package xyz.telosaddon.yuno.features;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.text.Text;
import xyz.telosaddon.yuno.TelosAddon;
import xyz.telosaddon.yuno.event.api.realm.WorldBossDefeatedEventHandler;
import xyz.telosaddon.yuno.event.api.realm.BossSpawnedEventHandler;
import xyz.telosaddon.yuno.event.HandledScreenRemovedCallback;
import xyz.telosaddon.yuno.utils.data.BossData;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class BossTrackerFeature extends ToggleableFeature implements WorldBossDefeatedEventHandler, BossSpawnedEventHandler, HandledScreenRemovedCallback {

    private static final Pattern BOSS_DEFEATED_MESSAGE_PATTERN = Pattern.compile("^(\\w+) has been defeated");
    private static final Pattern BOSS_SPAWNED_MESSAGE_PATTERN = Pattern.compile("^(\\w+) has spawned at");
    private static final Pattern ONYX_PORTAL_OPEN_MESSAGE_PATTERN = Pattern.compile("^A portal to Raph's Castle has opened at");
    private static final Pattern BOSS_ITEM_NAME_PATTERN = Pattern.compile("^» \\[(\\w+)] «");

    private final Set<BossData> currentAlive = ConcurrentHashMap.newKeySet();

    BossTrackerFeature() {
        super(TelosAddon.CONFIG.keys.bossTrackerFeatureEnabled);
        WorldBossDefeatedEventHandler.EVENT.register(this);
        BossSpawnedEventHandler.EVENT.register(this);
        HandledScreenRemovedCallback.EVENT.register(this);
    }



//    private void onGameMessage(Text message, boolean overlay) {
//        String s = message.getString();
//
//        if (!s.matches("^(\\w|\\[|\\().+")) {
//            return;
//        }
//
//        if(BOSS_SPAWNED_MESSAGE_PATTERN.matcher(s).find()) {
//            addAlive(s.split(" ")[0]);
//        }
//
//        if(BOSS_DEFEATED_MESSAGE_PATTERN.matcher(s).find()){
//            removeAlive(s.split(" ")[0]);
//        }
//
//
//        if(ONYX_PORTAL_OPEN_MESSAGE_PATTERN.matcher(s).find()){
//
//            CompletableFuture.runAsync(() -> {
//                try {
//                    currentAlive.clear();
//                    currentAlive.add(BossData.ONYX);
//
//                    int countdown = 60;
//                    while (countdown > 0) {
//                        countdown--;
//                        Thread.sleep(1000);
//                    }
//                    currentAlive.remove(BossData.ONYX);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//    }


    public void addAlive(String name){
        var newBoss = BossData.fromString(name);
        newBoss.ifPresent(currentAlive::add);
    }

    public void removeAlive(String name){
        var newBoss = BossData.fromString(name);
        newBoss.ifPresent(currentAlive::remove);
    }

    public void clearAlive(){
        currentAlive.clear();
    }

    public boolean isBossAlive(String name){
        var bossData = BossData.fromString(name);
        return bossData.isPresent() && currentAlive.contains(bossData.get());
    }

    public Set<BossData> getCurrentAlive (){
        return currentAlive;
    }

    @Override
    public void onBossDefeated(String bossName) {
        removeAlive(bossName);
    }

    @Override
    public void onBossSpawned(String bossName) {
        addAlive(bossName);
    }

    @Override
    public void onRemoved(Screen screen) {
        if (!isEnabled()) {
            return;
        }

        if (!(screen instanceof GenericContainerScreen containerScreen)) {
            return;
        }

        var inventory = containerScreen.getScreenHandler().getInventory();

        if (!(inventory instanceof SimpleInventory simpleInventory)) {
            return;
        }

        for (var stack : simpleInventory.getHeldStacks()) {
            System.out.println("Closed inventory contains: " + stack.getName().getString());
            var stackName = stack.getName().getString();
            var bossItemNameMatcher = BOSS_ITEM_NAME_PATTERN.matcher(stackName);

            if (!bossItemNameMatcher.find()) {
                continue;
            }
            var loreData = stack.getComponents().get(DataComponentTypes.LORE);

            if (loreData == null) {
                continue;
            }

            var loreString = loreData
                    .lines()
                    .stream()
                    .map(Text::getString)
                    .collect(Collectors.joining(" "));

            if (loreString.contains("This boss is alive")) {
                addAlive(bossItemNameMatcher.group(1));
            } else if (loreString.contains("This boss has been defeated")) {
                removeAlive(bossItemNameMatcher.group(1));
            } else if (loreString.contains("This boss has not spawned")) {
                removeAlive(bossItemNameMatcher.group(1));
            }
        }
    }
}