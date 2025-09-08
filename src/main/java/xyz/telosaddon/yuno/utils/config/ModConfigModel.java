package xyz.telosaddon.yuno.utils.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import xyz.telosaddon.yuno.features.ShowRangeFeature;

import java.awt.*;

import static xyz.telosaddon.yuno.TelosAddon.MOD_ID;

@Modmenu(modId = MOD_ID)
@Config(name = MOD_ID, wrapperName = "ModConfig")
public class ModConfigModel {
    public String modVersion = "v0.4.1";

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

    public int pitybagX = -2;
    public int pitybagY = 50;

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

    public boolean dungeonTimerSetting = true;

    public boolean healthBarSetting = true;
    //BS pity setting
    public boolean SylvarisSetting=false;
    public boolean VoidedOmnipotentSetting=false;
    public boolean KurvarosSetting=false;
    public boolean ShadowflareSetting=false;
    public boolean ValerionSetting=false;
    public boolean NebulaSetting=false;
    public boolean PrismaraSetting=false;
    public boolean OmnipotentSetting=false;
    public boolean SilexSetting=false;
    public boolean ChronosSetting=false;
    public boolean WardenSetting=false;
    public boolean HeraldSetting=false;
    public boolean ReaperSetting=false;
    public boolean DefenderSetting=false;
    public boolean AsmodeusSetting=false;
    public boolean SeraphimSetting=false;
    public boolean RaphaelSetting=false;
    public boolean OphanimSetting=false;
    public boolean TrueSeraphSetting=false;
    public boolean TrueOphanSetting=false;
    public boolean NihilitySetting=false;

    //BS pity counter
    public int Sylvaris=0;
    public int VoidedOmnipotent=0;
    public int Kurvaros=0;
    public int Shadowflare=0;
    public int Valerion=0;
    public int Nebula=0;
    public int Prismara=0;
    public int Omnipotent=0;
    public int Silex=0;
    public int Chronos=0;
    public int Warden=0;
    public int Herald=0;
    public int Reaper=0;
    public int Defender=0;
    public int Asmodeus=0;
    public int Seraphim=0;
    public int Raphael=0;
    public int Ophanim=0;
    public int TrueSeraph=0;
    public int TrueOphan=0;
    public int Nihility=0;
}
