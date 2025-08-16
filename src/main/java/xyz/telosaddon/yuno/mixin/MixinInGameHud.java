package xyz.telosaddon.yuno.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.telosaddon.yuno.TelosAddon;
import xyz.telosaddon.yuno.features.DungeonTimerFeature;
import xyz.telosaddon.yuno.features.Features;

import xyz.telosaddon.yuno.utils.FontHelper;
import xyz.telosaddon.yuno.utils.LocalAPI;
import xyz.telosaddon.yuno.utils.data.BossData;

import java.util.*;

import static xyz.telosaddon.yuno.TelosAddon.CONFIG;
import static xyz.telosaddon.yuno.utils.LocalAPI.getCurrentClientPing;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {

    @Shadow @Final
    private MinecraftClient client;

    @Shadow public abstract void clear();

    @Shadow public abstract void render(DrawContext context, RenderTickCounter tickCounter);

    @Shadow private int titleRemainTicks;

    @Shadow private @Nullable Text title;

    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {

        if(client.inGameHud.getDebugHud().shouldShowDebugHud()) return;

        TextRenderer tr = client.textRenderer;

        int width = client.getWindow().getScaledWidth();
        //int height = client.getWindow().getScaledHeight();

        if(CONFIG.bagX() == -1)
            CONFIG.bagX(width - 130);

        boolean isEditMode = TelosAddon.getInstance().isEditMode();
        String fontName = CONFIG.font();

        boolean fpsSetting = CONFIG.fpsSetting();
        boolean pingSetting = CONFIG.pingSetting();
        boolean playtimeSetting = CONFIG.playTimeSetting();
        boolean spawnBossesSetting = CONFIG.bossTrackerFeatureEnabled();

        List<String> pittybagTexts = new ArrayList<>();
        if(CONFIG.SilvarisSetting() || isEditMode)
            pittybagTexts.add("Silvaris§7: §f" + CONFIG.Silvaris());
        if(CONFIG.VoidedOmnipotentSetting() || isEditMode)
            pittybagTexts.add("Voided Omnipotent§7: §f" + CONFIG.Voided_Omnipotent());
        if(CONFIG.KurvarosSetting() || isEditMode)
            pittybagTexts.add("Kurvaros§7: §f" + CONFIG.Kurvaros());
        if(CONFIG.ShadowflareSetting() || isEditMode)
            pittybagTexts.add("Shadowflare§7: §f" + CONFIG.Shadowflare());
        if(CONFIG.ValerionSetting() || isEditMode)
            pittybagTexts.add("Valerion§7: §f" + CONFIG.Valerion());
        if(CONFIG.NebulaSetting() || isEditMode)
            pittybagTexts.add("Nebula§7: §f" + CONFIG.Nebula());
        if(CONFIG.PrismaraSetting() || isEditMode)
            pittybagTexts.add("Prismara§7: §f" + CONFIG.Prismara());
        if(CONFIG.OmnipotentSetting() || isEditMode)
            pittybagTexts.add("Omnipotent§7: §f" + CONFIG.Omnipotent());
        if(CONFIG.SilexSetting() || isEditMode)
            pittybagTexts.add("Silex§7: §f" + CONFIG.Silex());
        if(CONFIG.ChronosSetting() || isEditMode)
            pittybagTexts.add("Chronos§7: §f" + CONFIG.Chronos());
        if(CONFIG.WardenSetting() || isEditMode)
            pittybagTexts.add("Warden§7: §f" + CONFIG.Warden());
        if(CONFIG.HeraldSetting() || isEditMode)
            pittybagTexts.add("Herald§7: §f" + CONFIG.Herald());
        if(CONFIG.ReaperSetting() || isEditMode)
            pittybagTexts.add("Reaper§7: §f" + CONFIG.Reaper());
        if(CONFIG.DefenderSetting() || isEditMode)
            pittybagTexts.add("Defender§7: §f" + CONFIG.Defender());
        if(CONFIG.AsmodeusSetting() || isEditMode)
            pittybagTexts.add("Asmodeus§7: §f" + CONFIG.Asmodeus());
        if(CONFIG.SeraphimSetting() || isEditMode)
            pittybagTexts.add("Seraphim§7: §f" + CONFIG.Seraphim());
        if(CONFIG.RaphaelSetting() || isEditMode)
            pittybagTexts.add("Raphael§7: §f" + CONFIG.Raphael());
        if(CONFIG.OphanimSetting() || isEditMode)
            pittybagTexts.add("Ophanim§7: §f" + CONFIG.Ophanim());

        int pittybagY = CONFIG.pittybagY();
        int pittybagX = CONFIG.pittybagX();

        int ypittyBackground = pittybagY - 25;
        if(!pittybagTexts.isEmpty()) {
            String title = "Blood Shot Pitty";
            context.fill(pittybagX, ypittyBackground, pittybagX + 120, pittybagY + pittybagTexts.size() * 13 + 5, CONFIG.fillColor());
            context.drawBorder(pittybagX, ypittyBackground, 120, pittybagY - ypittyBackground + pittybagTexts.size() * 13 + 5,CONFIG.borderColor());
            context.drawHorizontalLine(pittybagX + 10, pittybagX + 110, pittybagY - 4, CONFIG.borderColor());

            int titleWidth = tr.getWidth(FontHelper.toCustomFont(title, fontName));
            int midX = (pittybagX + (pittybagX + 120)) / 2;

            //context.drawText(tr, toCustomFont(title), midX - titleWidth / 2, pittybagY - 15, config.getInteger("MenuColor"), false);
            context.drawText(tr, FontHelper.toCustomFont(title, fontName), midX - titleWidth / 2, pittybagY - 15, CONFIG.menuColor(), true);
        }
        for(int i = 0; i < pittybagTexts.size(); i++) {
            context.drawText(tr, FontHelper.toCustomFont(pittybagTexts.get(i), fontName), pittybagX + 10, pittybagY + (i * 13), CONFIG.menuColor(), true);
        }


        List<String> bagTexts = new ArrayList<>();
        if(CONFIG.greenSetting() || isEditMode)
            bagTexts.add("Irradiateds§7: §f" + CONFIG.greenBags());
        if(CONFIG.goldSetting() || isEditMode)
            bagTexts.add("Companions§7: §f" + CONFIG.goldBags());
        if(CONFIG.whiteSetting() || isEditMode)
            bagTexts.add("Royals§7: §f" + CONFIG.whiteBags());
        if(CONFIG.blackSetting()|| isEditMode)
            bagTexts.add("Bloodshots§7: §f" + CONFIG.blackBags());
        if(CONFIG.eventSetting() || isEditMode)
            bagTexts.add("Event Bags§7: §f" + CONFIG.eventBags());
        if(CONFIG.crossSetting() || isEditMode)
            bagTexts.add("Unholy§7: §f" + CONFIG.crosses());
        if(CONFIG.relicSetting() || isEditMode)
            bagTexts.add("Voidbounds§7: §f" + CONFIG.relics());
        if(CONFIG.runesSetting() || isEditMode)
            bagTexts.add("Runes§7: §f" + CONFIG.runes());
        if(CONFIG.totalRunSetting() || isEditMode)
            bagTexts.add("Total Runs§7: §f" + CONFIG.totalRuns());
        if(CONFIG.noWhiteRunSetting() || isEditMode)
            bagTexts.add("No Royal Runs§7: §f" + CONFIG.noWhiteRuns());
        if(CONFIG.noBlackRunSetting() || isEditMode)
            bagTexts.add("No BShot Runs§7: §f" + CONFIG.noBlackRuns());

        int bagY = CONFIG.bagY();
        int bagX = CONFIG.bagX();

        int yBackground = bagY - 25;
        if(!bagTexts.isEmpty()) {
            String title = CONFIG.lifetimeSetting() ? "Lifetime Stats" : "Session Stats";
            context.fill(bagX, yBackground, bagX + 120, bagY + bagTexts.size() * 13 + 5, CONFIG.fillColor());
            context.drawBorder(bagX, yBackground, 120, bagY - yBackground + bagTexts.size() * 13 + 5,CONFIG.borderColor());
            context.drawHorizontalLine(bagX + 10, bagX + 110, bagY - 4, CONFIG.borderColor());

            int titleWidth = tr.getWidth(FontHelper.toCustomFont(title, fontName));
            int midX = (bagX + (bagX + 120)) / 2;

            //context.drawText(tr, toCustomFont(title), midX - titleWidth / 2, bagY - 15, config.getInteger("MenuColor"), false);
            context.drawText(tr, FontHelper.toCustomFont(title, fontName), midX - titleWidth / 2, bagY - 15, CONFIG.menuColor(), true);
        }
        for(int i = 0; i < bagTexts.size(); i++) {
            context.drawText(tr, FontHelper.toCustomFont(bagTexts.get(i), fontName), bagX + 10, bagY + (i * 13), CONFIG.menuColor(), true);
        }

        int infoX = CONFIG.infoX();
        int infoY = CONFIG.infoY();
        List<String> infoList = new ArrayList<>();
        if(fpsSetting || isEditMode)
            infoList.add("FPS§7: §f" + client.getCurrentFps());

        if(pingSetting || isEditMode)
            infoList.add("Ping§7: §f" + getCurrentClientPing());

        if(playtimeSetting || isEditMode)
            infoList.add("Playtime§7: §f" + TelosAddon.getInstance().getPlaytimeText());

        if (DungeonTimerFeature.getTimerActive() && CONFIG.dungeonTimerSetting()) {
            infoList.add("Current Dungeon§7: §f" + LocalAPI.getCurrentCharacterArea());
            infoList.add("Current Dungeon Time§7: §f" + DungeonTimerFeature.getTimeStringFormatted());
        }



        if(spawnBossesSetting || isEditMode) {
            for (BossData bossData: Features.BOSS_TRACKER_FEATURE.getCurrentAlive()) {

                infoList.add("Boss Spawned§7: §f" + bossData.getLabel());
            }
            if(isEditMode) {
                infoList.add("Boss Spawned§7: §fNAME");
                infoList.add("Boss Spawned§7: §fNAME");
            }
        }
        int infoHeight = infoList.size() * 10;
        int infoWidth = 150;
        int bagHeight = bagTexts.size() * 13 + 5;
        int bagWidth = 120;
        int pittybagHeight = bagTexts.size() * 13 + 5;
        int pittybagWidth = 120;

        if(isEditMode) {
            if(TelosAddon.getInstance().infoWidth != infoWidth)
                TelosAddon.getInstance().infoWidth = infoWidth;

            if(TelosAddon.getInstance().infoHeight != infoHeight)
                TelosAddon.getInstance().infoHeight = infoHeight;

            if(TelosAddon.getInstance().bagHeight != bagHeight)
                TelosAddon.getInstance().bagHeight = bagHeight;

            if(TelosAddon.getInstance().bagWidth != bagWidth)
                TelosAddon.getInstance().bagWidth = bagWidth;

            if(TelosAddon.getInstance().pittybagHeight != pittybagHeight)
                TelosAddon.getInstance().pittybagHeight = pittybagHeight;

            if(TelosAddon.getInstance().pittybagWidth != pittybagWidth)
                TelosAddon.getInstance().pittybagWidth = pittybagWidth;
        }

        for(int i = 0; i < infoList.size(); i++)
            context.drawText(tr, FontHelper.toCustomFont(infoList.get(i), fontName), infoX, infoY + i * 10, CONFIG.menuColor(), true);
    }


}
