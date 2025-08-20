package xyz.telosaddon.yuno.utils;

import net.minecraft.entity.boss.BossBar;
import xyz.telosaddon.yuno.TelosAddon;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import static xyz.telosaddon.yuno.TelosAddon.LOGGER;
import static xyz.telosaddon.yuno.utils.BossBarUtils.bossBarMap;
import static xyz.telosaddon.yuno.utils.TabListUtils.*;

// place to put stuff that I don't really know where else to put
public class LocalAPI {

    private static String currentCharacterType = "";
    private static String currentCharacterClass = "";
    private static int currentCharacterLevel = -1;
    private static String currentCharacterWorld = "";
    private static String currentCharacterArea = "";
    private static String currentCharacterFighting = "";
    private static String currentClientPing = "";
    private static String lastKnownBoss = "";
    private static boolean countdownLock = false;
    private static String currentPortalCall = "";
    private static int currentPortalCallTime = 0;
    
    public static void updateAPI(){
        CompletableFuture.runAsync(() -> {
                if (!TelosAddon.getInstance().isOnTelos()) return;
                Optional<String> info = TabListUtils.getCharInfo();
                if (info.isEmpty()) return;

                String[] charInfo = info.get().split(" ");
                if (charInfo.length < 4) return;

                // in 1.3 the format is "(MASTERY)(GAMEMODE) (LEVEL) (CLASS)"
                // the spaces are exact, so u have to split the last 2 chars
                switch (charInfo[0].substring(2).hashCode()) {
                    case 880 -> currentCharacterType = "Normal";
                    case 881 -> currentCharacterType = "Hardcore";
                    case 882 -> currentCharacterType = "Seasonal";
                    default -> // 1771717 -> 1771734 inclusive
                            currentCharacterType = "GHardcore";
                }
                try {
                    currentCharacterLevel = Integer.parseInt(charInfo[2]);
                    currentCharacterClass = charInfo[3];

                }catch (Exception e){
                    e.printStackTrace();
                }
                currentClientPing = getPing().isPresent() ? getPing().get() : String.valueOf(-1);
                updateCharacterArea();
                Optional<String> realm = TabListUtils.getServer();
                realm.ifPresent(s -> currentCharacterWorld = s.replaceAll("[\\[\\]]+", ""));
        });
    }

    private static void updateCharacterArea(){

        if (bossBarMap != null) {

            Object[] preArray = bossBarMap.values().toArray();

            if (preArray.length == 5 && preArray[0] instanceof BossBar && preArray[1] instanceof BossBar) {

                BossBar areaBar = (BossBar) preArray[0];
                String area = stripAllFormatting(areaBar.getName().getString());

                currentCharacterArea = area.replaceAll("[^a-zA-z ']+", ""); // idk why but theres numbers at the end so we gotta trim that off

                BossBar bossBar = (BossBar) preArray[1]; // add what boss we're fighting
        
                //LOGGER.info(Level.INFO+ " Bossbar hashcode:" + bossBar.getName().hashCode()  +" " + bossBar.getName().getLiteralString() +" " + bossBar.getName().getString()); // keep this until i can fill out all the bosses
                
               
                lastKnownBoss = currentCharacterFighting;
                switch (bossBar.getName().hashCode()){
                    //if they have a number in comment , it means its updated as of 16th August 2025
                    case -168181711 -> currentCharacterFighting = "Chungus";//-168181711
                    case 1368623635 -> currentCharacterFighting = "Illarius";//1368623635
                    case -1253632898 -> currentCharacterFighting = "Astaroth";//-1253632898
                    case -168176906 -> currentCharacterFighting = "Glumi";//-168176906
                    case -1254008649 -> currentCharacterFighting = "Lotil";//-1254008649
                    case 453134978 -> currentCharacterFighting = "Tidol";//
                    case -1622056066 -> currentCharacterFighting = "Valus";//-1622056066
                    case -1907114029 -> currentCharacterFighting = "Oozul";//-1907114029
                    case 2035818623 -> currentCharacterFighting = "Freddy";//
                    case -342545608 -> currentCharacterFighting = "Anubis";//-342545608
                    case -1240191621 -> currentCharacterFighting = "Hollowbane";
                    case -1048713371 -> currentCharacterFighting = "Claus";
                    case 1824190226 -> currentCharacterFighting = "Shadowflare";//1824190226
                    case 1996713601 -> currentCharacterFighting = "Loa";
                    case -1048545196 -> currentCharacterFighting = "Valerion";
                    //case xxx -> currentCharacterFighting = "Nebula";
                    //case xxx -> currentCharacterFighting = "Ophanim";
                    case -708336010 -> currentCharacterFighting = "Prismara";//-708336010
                    case -1254007688 -> currentCharacterFighting = "Omnipotent";//-1254007688
                    case 1757423534 -> currentCharacterFighting = "Thalassar";
                    case -1643392642 -> currentCharacterFighting = "Silex";//-1643392642
                    case 290925398 -> currentCharacterFighting = "Chronos";//290925398
                    case -1338784736 -> currentCharacterFighting = "Golden Freddy";
                    case -342534076 -> currentCharacterFighting = "Kurvaros";//-342534076
                    case -1370656917 -> currentCharacterFighting = "Warden";//-1370656917
                    case -1370655956 -> currentCharacterFighting = "Herald";//-1370655956
                    case -1370654995 -> currentCharacterFighting = "Reaper";//-1370654995
                    case -1370654034 -> currentCharacterFighting = "Defender";//-1370654034
                    case -1622067598 -> currentCharacterFighting = "Asmodeus";//-1622067598
                    case -1643406096 -> currentCharacterFighting = "Seraphim";//-1643406096
                    case 2131893865 -> currentCharacterFighting = "Raphael's Castle";//2131893865
                    case 254038329 -> currentCharacterFighting = "Raphael";//254038329
                    case -1083171609 -> currentCharacterFighting = "Pirate's Cove";
                    case 230903377 -> currentCharacterFighting = "Sylvaris";//230903377
                    case -1253581965 -> currentCharacterFighting = "Voided Omnipotent";//-1253581965
                    default -> currentCharacterFighting = "";
                }
                //System.out.println("last known boss is: " + lastKnownBoss + ", current boss is: " + currentCharacterFighting + "current portal call is: " + currentPortalCall);
                // this means a boss has died recently.
                if (!lastKnownBoss.equals(currentCharacterFighting) && currentCharacterFighting.equals("")) {
                    LOGGER.info("Boss has died");
                    System.out.println(lastKnownBoss);
                    switch (lastKnownBoss){
                        case "Chungus" -> currentPortalCall = "void";
                        case "Illarius" -> currentPortalCall = "loa";
                        case "Astaroth" -> currentPortalCall = "shatters";
                        case "Glumi" -> currentPortalCall = "fungal";
                        case "Lotil" -> currentPortalCall = "omni";
                        case "Tidol" -> currentPortalCall =  "corsairs";
                        case "Valus" -> currentPortalCall = "cultists";
                        case "Oozul" -> currentPortalCall = "chronos";
                        case "Freddy" -> currentPortalCall = "pizza";
                        case "Anubis" -> currentPortalCall = "alair";
                        case "Defender" -> currentPortalCall = "cprov";
                
                        default -> currentPortalCall = "";
                    }
                    lastKnownBoss = "";
                    if (currentPortalCall.isEmpty() || countdownLock){
                        return;
                    }
                    // a boss portal has dropped, start timer
                    CompletableFuture.runAsync(() -> {
                        countdownLock = true;
                        currentPortalCallTime = 32;
                        while(currentPortalCallTime > 0){
                            currentPortalCallTime--;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        currentPortalCall = "";
                        countdownLock = false;
                
                    });
                }
                
                

            }
        }
    }

    public static String getCurrentCharacterType() {
        return currentCharacterType;
    }
    public static String getCurrentCharacterClass() {
        return currentCharacterClass;
    }

    public static int getCurrentCharacterLevel() {
        return currentCharacterLevel;
    }

    public static String getCurrentCharacterWorld() {
        return currentCharacterWorld;
    }


    public static String getCurrentCharacterFighting() {
        return currentCharacterFighting;
    }

    public static String getCurrentCharacterArea() {
        return currentCharacterArea;
    }

    public static String getCurrentClientPing() {
        return currentClientPing;
    }
    public static String getCurrentPortalCall() {
        return currentPortalCall;
    }

    public static int getCurrentPortalCallTime() {
        return currentPortalCallTime;
    }
}
