package xyz.telosaddon.yuno.utils.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import xyz.telosaddon.yuno.features.ShowRangeFeature;

import java.awt.*;

import static xyz.telosaddon.yuno.TelosAddon.MOD_ID;

@Config(name = MOD_ID)
public class ModConfigModel implements ConfigData {

    private static ModConfigModel instance;

    public static ModConfigModel getInstance() {
        if (instance == null)
            instance = AutoConfig.getConfigHolder(ModConfigModel.class).getConfig();
        return instance;
    }
    public String modVersion = "v0.3.21";

    public int greenBags = 0;
    public int goldBags = 0;
    public int whiteBags = 0;
    public int blackBags = 0;
    public int eventBags = 0;
    public int crosses = 0;
    public int relics = 0;
    public int runes = 0;
    public int totalRuns = 0;
    public int noWhiteRuns = 0;
    public int noBlackRuns = 0;
    public long totalPlayTime = 0;

    public double newGamma = 15000;
    public double normalGamma = 1;

    public int menuColor = new Color(255, 215, 0).getRGB();
    public int borderColor = new Color(255, 255, 255, 150).getRGB();
    public int fillColor = new Color(0, 0, 0, 100).getRGB();

    public int infoX = 4;
    public int infoY = 4;

    public int bagX = -1;
    public int bagY = 60;

    public boolean greenSetting = false;
    public boolean goldSetting = false;
    public boolean whiteSetting = false;
    public boolean blackSetting = false;
    public boolean eventSetting = false;
    public boolean crossSetting = false;
    public boolean relicSetting = false;
    public boolean runesSetting = false;
    public boolean totalRunSetting = false;
    public boolean noWhiteRunSetting = false;
    public boolean noBlackRunSetting = false;
    public boolean lifetimeSetting = false;
    public boolean enableJoinText = true;
    public boolean callHotkeyShout = true;

    public boolean swingSetting = false;
    public boolean gammaSetting = false;
    public boolean fpsSetting = false;
    public boolean pingSetting = false;
    public boolean playTimeSetting = false;
    public boolean bossTrackerFeatureEnabled = false;
    public boolean bossWaypointsSetting = false;
    public boolean soundSetting = false;
    public String font = "Default";

    public boolean discordRPCSetting = true;
    public String discordDefaultStatusMessage = " ~Just chillin'";
    public boolean rpcShowLocationSetting = true;
    public boolean rpcShowFightingSetting = false;

    public boolean swingIfNoCooldown = false;

    public boolean showMainRangeFeatureEnabled = true;
    public boolean showOffHandRangeFeatureEnabled = false;
    public double showMainRangeFeatureHeight = 0.5;
    public double showOffHandRangeFeatureHeight = 0.5;
    public int showMainRangeFeatureColor = new Color(255, 0, 0).getRGB();
    public int showOffHandRangeFeatureColor = new Color(0, 0, 255).getRGB();
    public ShowRangeFeature.RangeViewType showMainRangeFeatureViewType = ShowRangeFeature.RangeViewType.LINE;
    public ShowRangeFeature.RangeViewType showOffHandRangeFeatureViewType = ShowRangeFeature.RangeViewType.LINE;


}
