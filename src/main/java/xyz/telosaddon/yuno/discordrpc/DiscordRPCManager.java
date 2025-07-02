package xyz.telosaddon.yuno.discordrpc;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import xyz.telosaddon.yuno.TelosAddon;
import xyz.telosaddon.yuno.utils.LocalAPI;

import java.time.OffsetDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import static xyz.telosaddon.yuno.TelosAddon.CONFIG;
import static xyz.telosaddon.yuno.TelosAddon.LOGGER;

public class DiscordRPCManager implements IPCListener {

    private static final long APPLICATION_ID = 1290485474452045865L;


    private IPCClient client;
    private OffsetDateTime startTimestamp;

    private boolean connected;

    public void start() {
        try{
            LOGGER.info("Starting Discord RPC...");
            if (isActive()) {
                return;
            }
            startTimestamp = OffsetDateTime.now();
            client = new IPCClient(APPLICATION_ID);
            client.setListener(this);
            try {
                client.connect();
            } catch (Exception e) {
                LOGGER.warn("Failed to connect to Discord RPC!");
                LOGGER.warn(e.toString());
            }
        } catch (Throwable e) {
            LOGGER.warn("Discord RPC has thrown an unexpected error while trying to start...");
            LOGGER.warn(e.toString());
        }

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            CompletableFuture<Void> future = CompletableFuture.runAsync(()->{
                if ((System.currentTimeMillis()/50) % 100 == 0){
                    updatePresence();
                }
            });
        });
    }


    public void stop() {
        LOGGER.info("Attempting to disconnect RPC \nConnectedStatus: " + connected);
        if (isActive()) {
            connected = false;
            client.close();
        }
    }

    public void updatePresence() {
        if (!isActive()) return;
        if (!TelosAddon.getInstance().isOnTelos() || !CONFIG.discordRPCSetting()){
            client.sendRichPresence(null);
            return;
        }


        String largeImageDescription = "telosrealms.com";
        String smallImageDescription = LocalAPI.getCurrentCharacterType() + " Lv" + LocalAPI.getCurrentCharacterLevel() + " " + LocalAPI.getCurrentCharacterClass();
        RichPresence presence = new RichPresence.Builder()
                .setState(getStateString())
                .setDetails(getDetailsString())
                .setStartTimestamp(startTimestamp)
                .setLargeImage("teloslogo", largeImageDescription)
                .setSmallImage(LocalAPI.getCurrentCharacterType().toLowerCase(), smallImageDescription)
                .build();
        client.sendRichPresence(presence);
    }

    private String getDetailsString(){
        if (CONFIG.rpcShowLocationSetting()){
            return LocalAPI.getCurrentCharacterWorld() + " | " + LocalAPI.getCurrentCharacterArea();
        } else{
            return "In an Unknown Place";
        }
    }

    private String getStateString(){
        return (LocalAPI.getCurrentCharacterFighting().length() > 0 && CONFIG.rpcShowLocationSetting())
                ? ("Fighting " + LocalAPI.getCurrentCharacterFighting())
                : CONFIG.discordDefaultStatusMessage();
    }
    @Override
    public void onReady(IPCClient client) {
        LOGGER.info("Discord RPC started.");
        connected = true;
    }

    @Override
    public void onDisconnect(IPCClient client, Throwable t) {
        LOGGER.info("Discord RPC disconnected.");
        this.client = null;
        connected = false;
    }
    public boolean isActive() {
        return client != null && connected;
    }

    public IPCClient getClient() {
        return client;
    }

}
