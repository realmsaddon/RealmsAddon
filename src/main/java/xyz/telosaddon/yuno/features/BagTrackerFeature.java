package xyz.telosaddon.yuno.features;
import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.Text;
import net.minecraft.world.event.GameEvent.Message;
import xyz.telosaddon.yuno.utils.LocalAPI;
import xyz.telosaddon.yuno.utils.data.BossData;
import static xyz.telosaddon.yuno.TelosAddon.LOGGER;
import static xyz.telosaddon.yuno.TelosAddon.CONFIG;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;


public class BagTrackerFeature {
    private static String boss="";
    private static String lastboss="";
    //public static void track_white_bag(){
    //    LOGGER.info("Inside white function");
    //    boss=LocalAPI.getCurrentCharacterFighting();
    //    LOGGER.info(boss);
    //    LOGGER.info("(BossDefeatedEvent) boss " + boss  + " Dropped a bag");
    //    CONFIG.noWhiteRuns(0);
    //}
    public static void resetBlackBagPity(){
        boss=LocalAPI.getCurrentCharacterFighting();
        LOGGER.info("(Bag event counter) boss " + boss  + " Dropped a bag");
        switch(boss){
            case "Sylvaris" -> CONFIG.Sylvaris(0) ;
            case "Voided Omnipotent" -> CONFIG.VoidedOmnipotent(0) ;
            case "Kurvaros" -> CONFIG.Kurvaros(0) ;
            case "Shadowflare" -> CONFIG.Shadowflare(0) ;
            case "Valerion" -> CONFIG.Valerion(0) ;
            case "Nebula" -> CONFIG.Nebula(0) ;
            case "Prismara" -> CONFIG.Prismara(0) ;
            case "Omnipotent" -> CONFIG.Omnipotent(0) ;
            case "Silex" -> CONFIG.Silex(0) ;
            case "Chronos" -> CONFIG.Chronos(0) ;
            case "Warden" -> CONFIG.Warden(0) ;
            case "Herald" -> CONFIG.Herald(0) ;
            case "Reaper" -> CONFIG.Reaper(0) ;
            case "Defender" -> CONFIG.Defender(0) ;
            case "Asmodeus" -> CONFIG.Asmodeus(0) ;
            case "Seraphim" -> CONFIG.Seraphim(0) ;
            case "Raphael" -> CONFIG.Raphael(0) ;
            case "Ophanim" -> CONFIG.Ophanim(0) ;
            default -> {}
        }
        CONFIG.noBlackRuns(0);
        return;
    }
    public static void blackBagPityCounter(String boss_name){ // also increases unholy counter
        LOGGER.info("(BagPityEvent) boss " + boss_name  + " did not drop a bag");
        switch(boss_name){
            case "Sylvaris" -> CONFIG.Sylvaris(CONFIG.Sylvaris()+1) ;
            case "Voided Omnipotent" -> {
                CONFIG.VoidedOmnipotent(CONFIG.VoidedOmnipotent()+1) ;
                CONFIG.Nihility(CONFIG.Nihility()+1);
            }
            case "Kurvaros" -> CONFIG.Kurvaros(CONFIG.Kurvaros()+1) ;
            case "Shadowflare" -> CONFIG.Shadowflare(CONFIG.Shadowflare()+1) ;
            case "Valerion" -> CONFIG.Valerion(CONFIG.Valerion()+1) ;
            case "Nebula" -> CONFIG.Nebula(CONFIG.Nebula()+1) ;
            case "Prismara" -> CONFIG.Prismara(CONFIG.Prismara()+1) ;
            case "Omnipotent" -> CONFIG.Omnipotent(CONFIG.Omnipotent()+1) ;
            case "Silex" -> CONFIG.Silex(CONFIG.Silex()+1) ;
            case "Chronos" -> CONFIG.Chronos(CONFIG.Chronos()+1) ;
            case "Warden" -> CONFIG.Warden(CONFIG.Warden()+1) ;
            case "Herald" -> CONFIG.Herald(CONFIG.Herald()+1) ;
            case "Reaper" -> CONFIG.Reaper(CONFIG.Reaper()+1) ;
            case "Defender" -> CONFIG.Defender(CONFIG.Defender()+1) ;
            case "Asmodeus" -> CONFIG.Asmodeus(CONFIG.Asmodeus()+1) ;
            case "Seraphim" -> CONFIG.Seraphim(CONFIG.Seraphim()+1) ;
            case "Raphael" -> CONFIG.Raphael(CONFIG.Raphael()+1) ;
            case "Ophanim" -> CONFIG.Ophanim(CONFIG.Ophanim()+1) ;
            //unholy 
            case "True Seraph" -> CONFIG.TrueSeraph(CONFIG.TrueSeraph()+1);
            case "True Ophan" -> CONFIG.TrueOphan(CONFIG.TrueOphan()+1) ;
            default -> {}
        }
        CONFIG.noBlackRuns(CONFIG.noBlackRuns()+1);
        return;
    }
    public static void resetUnholyPity(){
        String boss=LocalAPI.getCurrentCharacterFighting();
        LOGGER.info("(BagPityEvent) boss " + boss  + " Dropped a Unholy / Voidbound Bag");
        switch(boss){
            case "True Seraph" -> CONFIG.TrueSeraph(0);
            case "True Ophan" -> CONFIG.TrueOphan(0) ;
            case "Voided Omnipotent" -> CONFIG.Nihility(0);
            default -> {}
        }
        return;
    }
}


