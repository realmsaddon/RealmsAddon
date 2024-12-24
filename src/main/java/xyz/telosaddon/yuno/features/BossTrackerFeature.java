package xyz.telosaddon.yuno.features;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.text.Text;
import xyz.telosaddon.yuno.TelosAddon;
import xyz.telosaddon.yuno.event.HandledScreenRemovedCallback;
import xyz.telosaddon.yuno.utils.data.BossData;

import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class BossTrackerFeature extends AbstractFeature{

    private static final Pattern BOSS_DEFEATED_MESSAGE_PATTERN = Pattern.compile("^(\\w+) has been defeated");
    private static final Pattern BOSS_SPAWNED_MESSAGE_PATTERN = Pattern.compile("^(\\w+) has spawned at ([0-9.-]+), ([0-9.-]+), ([0-9.-]+)");
    private static final Pattern ONYX_PORTAL_OPEN_MESSAGE_PATTERN = Pattern.compile("^A portal to Onyx's Castle will open in the centre of the realm in 60 seconds at");
    private static final Pattern BOSS_ITEM_NAME_PATTERN = Pattern.compile("^» \\[(\\w+)] «");

    private final HashSet<BossData> currentAlive = new HashSet<>();

    public BossTrackerFeature() {
        super(TelosAddon.CONFIG.keys.bossTrackerFeatureEnabled);
        ClientReceiveMessageEvents.GAME.register(this::onGameMessage);
        HandledScreenRemovedCallback.EVENT.register(this::onScreenClosed);
    }

    private void onGameMessage(Text message, boolean overlay) {
        String s = message.getString();

        if (!s.matches("^(\\w|\\[|\\().+")) {
            return;
        }

        if(BOSS_SPAWNED_MESSAGE_PATTERN.matcher(s).find()) {
            addAlive(s.split(" ")[0]);
        }

        if(BOSS_DEFEATED_MESSAGE_PATTERN.matcher(s).find()){
            removeAlive(s.split(" ")[0]);
        }
        // todo: add onyx timer hud item

        if(ONYX_PORTAL_OPEN_MESSAGE_PATTERN.matcher(s).find()){

            CompletableFuture.runAsync(() -> {
                try {
                    currentAlive.clear();
                    currentAlive.add(BossData.ONYX);

                    int countdown = 90;
                    while (countdown > 0) {
                        countdown--;
                        if (!currentAlive.contains(BossData.ONYX)) return;
                        Thread.sleep(1000);
                    }
                    currentAlive.clear();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }


    private void onScreenClosed(Screen screen) {
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
            var stackName = stack.getName().getString();
            var bossItemNameMatcher = BOSS_ITEM_NAME_PATTERN.matcher(stackName);

            // Match boss items.
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

    public HashSet<BossData> getCurrentAlive (){
        return currentAlive;
    }

}
