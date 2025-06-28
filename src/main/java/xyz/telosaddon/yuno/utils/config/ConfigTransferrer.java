//package xyz.telosaddon.yuno.utils.config;
//
//import com.google.common.reflect.TypeToken;
//import com.google.gson.*;
//import net.fabricmc.loader.api.FabricLoader;
//import xyz.telosaddon.yuno.TelosAddon;
//import xyz.telosaddon.yuno.features.ShowRangeFeature;
//
//import java.io.*;
//import java.lang.reflect.Type;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
///**
// * Transfers v0.2 configs to the new v0.3 format used by our new config library
// *
// */
//public class ConfigTransferrer {
//
//
//    private final Gson GSON = new GsonBuilder()
//            .registerTypeAdapter(Integer.class, new IntegerSerializer())
//            .registerTypeAdapter(Double.class, new DoubleSerializer())
//            .registerTypeAdapter(Long.class, new LongSerializer())
//            .setPrettyPrinting()
//            .create();
//    private final File configFile ;
//
//    /**
//     * Backup file in case the main file becomes corrupted
//     */
//    private Map<String, Object> configMap;
//
//    public ConfigTransferrer() {
//        configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "telosaddon.json");
//        transfer();
//
//    }
//    public void transfer() {
//        TelosAddon.LOGGER.info( "Attempting to transfer configs" );
//        configMap = new HashMap<>();
//        if (configFile.exists()){
//            try (FileReader reader = new FileReader(configFile)) {
//
//                Type type = new TypeToken<Map<String, Object>>() {}.getType();
//                configMap = GSON.fromJson(reader, type);
//            } catch (Exception e) {
//                TelosAddon.LOGGER.severe("Error loading old config!");
//                e.printStackTrace();
//                return;
//            }
//        }
//        try {
//            if (configMap.containsKey("TotalRuns")) {
//                TelosAddon.CONFIG.totalRuns(getInteger("TotalRuns"));
//            }
//
//            if (configMap.containsKey("DiscordDefaultStatusMessage")) {
//                TelosAddon.CONFIG.discordDefaultStatusMessage(getString("DiscordDefaultStatusMessage"));
//            }
//
////            if (configMap.containsKey("ShowMainRangeFeatureViewType")) {
////                TelosAddon.CONFIG.showMainRangeFeatureViewType(ShowRangeFeature.RangeViewType.valueOf((String) configMap.get("ShowMainRangeFeatureViewType")));
////            }
////            if (configMap.containsKey("ShowOffHandRangeFeatureViewType")) {
////                TelosAddon.CONFIG.showOffHandRangeFeatureViewType(ShowRangeFeature.RangeViewType.valueOf((String) configMap.get("ShowOffHandRangeFeatureViewType")));
////            }
//
//            if (configMap.containsKey("Relics")) {
//                TelosAddon.CONFIG.relics(getInteger("Relics"));
//            }
//            if (configMap.containsKey("GoldBags")) {
//                TelosAddon.CONFIG.goldBags(getInteger("GoldBags"));
//            }
//            if (configMap.containsKey("BlackBags")) {
//                TelosAddon.CONFIG.blackBags(getInteger("BlackBags"));
//            }
//            if (configMap.containsKey("GreenBags")) {
//                TelosAddon.CONFIG.greenBags(getInteger("GreenBags"));
//            }
//            if (configMap.containsKey("WhiteBags")) {
//                TelosAddon.CONFIG.whiteBags(getInteger("WhiteBags"));
//            }
//            if (configMap.containsKey("EventBags")) {
//                TelosAddon.CONFIG.eventBags(getInteger("EventBags"));
//            }
//            if (configMap.containsKey("Crosses")) {
//                TelosAddon.CONFIG.crosses(getInteger("Crosses"));
//            }
//            if (configMap.containsKey("Runes")) {
//                TelosAddon.CONFIG.runes(getInteger("Runes"));
//            }
//            if (configMap.containsKey("NoWhiteRuns")) {
//                TelosAddon.CONFIG.noWhiteRuns(getInteger("NoWhiteRuns"));
//            }
//            if (configMap.containsKey("NoBlackRuns")) {
//                TelosAddon.CONFIG.noBlackRuns(getInteger("NoBlackRuns"));
//            }
//
//            if (configMap.containsKey("MenuColor")) {
//                TelosAddon.CONFIG.menuColor(getInteger("MenuColor"));
//            }
//            if (configMap.containsKey("FillColor")) {
//                TelosAddon.CONFIG.fillColor(getInteger("FillColor"));
//            }
//            if (configMap.containsKey("BorderColor")) {
//                TelosAddon.CONFIG.borderColor(getInteger("BorderColor"));
//            }
//            if (configMap.containsKey("InfoX")) {
//                TelosAddon.CONFIG.infoX(getInteger("InfoX"));
//            }
//            if (configMap.containsKey("InfoY")) {
//                TelosAddon.CONFIG.infoY(getInteger("InfoY"));
//            }
//            if (configMap.containsKey("BagX")) {
//                TelosAddon.CONFIG.bagX(getInteger("BagX"));
//            }
//            if (configMap.containsKey("BagY")) {
//                TelosAddon.CONFIG.bagY(getInteger("BagY"));
//            }
//            if (configMap.containsKey("ShowMainRangeFeatureColor")) {
//                TelosAddon.CONFIG.showMainRangeFeatureColor(getInteger("ShowMainRangeFeatureColor"));
//            }
//            if (configMap.containsKey("ShowOffHandRangeFeatureColor")) {
//                TelosAddon.CONFIG.showOffHandRangeFeatureColor(getInteger("ShowOffHandRangeFeatureColor"));
//            }
//        } catch (Exception e) {
//            TelosAddon.LOGGER.severe("Error transferring config!");
//            e.printStackTrace();
//            return;
//        }
//        TelosAddon.LOGGER.severe("Successfully transferred configs!");
//        configFile.delete();
//
//    }
//
//    public Optional<Object> get(String key) {
//        return Optional.ofNullable(configMap.get(key));
//    }
//
//    public Double getDouble(String key) {
//        Object value = configMap.get(key);
//        if(value instanceof Double)
//            return (Double) value;
//        throw new ClassCastException("Value is not a Double");
//    }
//
//    public int getInteger(String key) {
//        Object value = configMap.get(key);
//        if(value instanceof Number)
//            return ((Number) value).intValue();
//        throw new ClassCastException("Value at key " + key + " is not an Int");
//    }
//
//    public Boolean getBoolean(String key) {
//        Object value = configMap.get(key);
//        if(value instanceof Boolean)
//            return (Boolean) value;
//        throw new ClassCastException("Value is not a Boolean");
//    }
//
//    public String getString(String key) {
//        Object value = configMap.get(key);
//        if(value instanceof String)
//            return (String) value;
//        throw new ClassCastException("Value at key " + key + " is not a String");
//    }
//
//
// }
