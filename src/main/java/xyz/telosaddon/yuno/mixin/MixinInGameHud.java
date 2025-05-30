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
import xyz.telosaddon.yuno.features.Features;

import xyz.telosaddon.yuno.utils.FontHelper;
import xyz.telosaddon.yuno.utils.data.BossData;

import java.util.*;
import java.util.List;

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


        List<String> bagTexts = new ArrayList<>();
        if(CONFIG.greenSetting() || isEditMode)
            bagTexts.add("Green Bags§7: §f" + CONFIG.greenBags());
        if(CONFIG.goldSetting() || isEditMode)
            bagTexts.add("Gold Bags§7: §f" + CONFIG.goldBags());
        if(CONFIG.whiteSetting() || isEditMode)
            bagTexts.add("White Bags§7: §f" + CONFIG.whiteBags());
        if(CONFIG.blackSetting()|| isEditMode)
            bagTexts.add("Black Bags§7: §f" + CONFIG.blackBags());
        if(CONFIG.eventSetting() || isEditMode)
            bagTexts.add("Event Bags§7: §f" + CONFIG.eventBags());
        if(CONFIG.crossSetting() || isEditMode)
            bagTexts.add("Crosses§7: §f" + CONFIG.crosses());
        if(CONFIG.relicSetting() || isEditMode)
            bagTexts.add("Voidbounds§7: §f" + CONFIG.relics());
        if(CONFIG.runesSetting() || isEditMode)
            bagTexts.add("Runes§7: §f" + CONFIG.runes());
        if(CONFIG.totalRunSetting() || isEditMode)
            bagTexts.add("Total Runs§7: §f" + CONFIG.totalRuns());
        if(CONFIG.noWhiteRunSetting() || isEditMode)
            bagTexts.add("No Whites Runs§7: §f" + CONFIG.noWhiteRuns());
        if(CONFIG.noBlackRunSetting() || isEditMode)
            bagTexts.add("No Black Runs§7: §f" + CONFIG.noBlackRuns());

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

        if(spawnBossesSetting || isEditMode) {
            for (BossData bossData: Features.BOSS_TRACKER_FEATURE.getCurrentAlive()) {

                infoList.add("Boss Spawned§7: §f" + bossData.label);
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

        if(isEditMode) {
            if(TelosAddon.getInstance().infoWidth != infoWidth)
                TelosAddon.getInstance().infoWidth = infoWidth;

            if(TelosAddon.getInstance().infoHeight != infoHeight)
                TelosAddon.getInstance().infoHeight = infoHeight;

            if(TelosAddon.getInstance().bagHeight != bagHeight)
                TelosAddon.getInstance().bagHeight = bagHeight;

            if(TelosAddon.getInstance().bagWidth != bagWidth)
                TelosAddon.getInstance().bagWidth = bagWidth;
        }

        for(int i = 0; i < infoList.size(); i++)
            context.drawText(tr, FontHelper.toCustomFont(infoList.get(i), fontName), infoX, infoY + i * 10, CONFIG.menuColor(), true);
    }







}
