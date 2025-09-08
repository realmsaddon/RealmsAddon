package xyz.telosaddon.yuno.ui.tabs;

import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.CheckboxComponent;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.container.FlowLayout;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import net.minecraft.client.gui.screen.Screen;
import xyz.telosaddon.yuno.TelosAddon;

import static xyz.telosaddon.yuno.TelosAddon.CONFIG;

public class GuiTab extends BaseUIModelScreen<FlowLayout> {

    private final Screen parent;

    public GuiTab(@Nullable Screen parent) {
        super(FlowLayout.class, DataSource.asset(Identifier.of("telosaddon", "guitab")));
        this.parent = parent;
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent.childById(LabelComponent.class, "version").text(Text.of(TelosAddon.MOD_NAME + TelosAddon.MOD_VERSION));


        //tab buttons
        rootComponent.childById(ButtonComponent.class, "Home").onPress(button -> {
            this.client.setScreen(new HomeTab());
        });
        rootComponent.childById(ButtonComponent.class, "Settings").onPress(button -> {
            this.client.setScreen(new SettingsTab(this));
        });
        rootComponent.childById(ButtonComponent.class, "Range").onPress(button -> {
            this.client.setScreen(new RangeTab(this));
        });

        rootComponent.childById(CheckboxComponent.class, "GreenSetting")
                .checked(CONFIG.greenSetting())
                .onChanged(CONFIG::greenSetting);

        rootComponent.childById(CheckboxComponent.class, "GoldSetting")
                .checked(CONFIG.goldSetting())
                .onChanged(CONFIG::goldSetting);

        rootComponent.childById(CheckboxComponent.class, "WhiteSetting")
                .checked(CONFIG.whiteSetting())
                .onChanged(CONFIG::whiteSetting);

        rootComponent.childById(CheckboxComponent.class, "BlackSetting")
                .checked(CONFIG.blackSetting())
                .onChanged(CONFIG::blackSetting);


        rootComponent.childById(CheckboxComponent.class, "EventSetting")
                .checked(CONFIG.eventSetting())
                .onChanged(CONFIG::eventSetting);

        rootComponent.childById(CheckboxComponent.class, "CrossSetting")
                .checked(CONFIG.crossSetting())
                .onChanged(CONFIG::crossSetting);

        rootComponent.childById(CheckboxComponent.class, "RelicSetting")
                .checked(CONFIG.relicSetting())
                .onChanged(CONFIG::relicSetting);


        rootComponent.childById(CheckboxComponent.class, "RunesSetting")
                .checked(CONFIG.runesSetting())
                .onChanged(CONFIG::runesSetting);

        rootComponent.childById(CheckboxComponent.class, "TotalRunSetting")
                .checked(CONFIG.totalRunSetting())
                .onChanged(CONFIG::totalRunSetting);

        rootComponent.childById(CheckboxComponent.class, "NoWhiteRunSetting")
                .checked(CONFIG.noWhiteRunSetting())
                .onChanged(CONFIG::noWhiteRunSetting);

        rootComponent.childById(CheckboxComponent.class, "NoBlackRunSetting")
                .checked(CONFIG.noBlackRunSetting())
                .onChanged(CONFIG::noBlackRunSetting);

        rootComponent.childById(CheckboxComponent.class, "LifetimeSetting")
                .checked(CONFIG.lifetimeSetting())
                .onChanged(CONFIG::lifetimeSetting);
        
        
        //Pity counting
        rootComponent.childById(CheckboxComponent.class, "Sylvaris")
                .checked(CONFIG.SylvarisSetting())
                .onChanged(CONFIG::SylvarisSetting);
        rootComponent.childById(CheckboxComponent.class, "VoidedOmnipotent")
                        .checked(CONFIG.VoidedOmnipotentSetting())
                        .onChanged(CONFIG::VoidedOmnipotentSetting);
        rootComponent.childById(CheckboxComponent.class, "Kurvaros")
                        .checked(CONFIG.KurvarosSetting())
                        .onChanged(CONFIG::KurvarosSetting);
        rootComponent.childById(CheckboxComponent.class, "Shadowflare")
                        .checked(CONFIG.ShadowflareSetting())
                        .onChanged(CONFIG::ShadowflareSetting);
        rootComponent.childById(CheckboxComponent.class, "Valerion")
                        .checked(CONFIG.ValerionSetting())
                        .onChanged(CONFIG::ValerionSetting);
        rootComponent.childById(CheckboxComponent.class, "Nebula")
                        .checked(CONFIG.NebulaSetting())
                        .onChanged(CONFIG::NebulaSetting);
        rootComponent.childById(CheckboxComponent.class, "Prismara")
                        .checked(CONFIG.PrismaraSetting())
                        .onChanged(CONFIG::PrismaraSetting);
        rootComponent.childById(CheckboxComponent.class, "Omnipotent")
                        .checked(CONFIG.OmnipotentSetting())
                        .onChanged(CONFIG::OmnipotentSetting);
        rootComponent.childById(CheckboxComponent.class, "Silex")
                        .checked(CONFIG.SilexSetting())
                        .onChanged(CONFIG::SilexSetting);
        rootComponent.childById(CheckboxComponent.class, "Chronos")
                        .checked(CONFIG.ChronosSetting())
                        .onChanged(CONFIG::ChronosSetting);
        rootComponent.childById(CheckboxComponent.class, "Warden")
                        .checked(CONFIG.WardenSetting())
                        .onChanged(CONFIG::WardenSetting);
        rootComponent.childById(CheckboxComponent.class, "Herald")
                        .checked(CONFIG.HeraldSetting())
                        .onChanged(CONFIG::HeraldSetting);
        rootComponent.childById(CheckboxComponent.class, "Reaper")
                        .checked(CONFIG.ReaperSetting())
                        .onChanged(CONFIG::ReaperSetting);
        rootComponent.childById(CheckboxComponent.class, "Defender")
                        .checked(CONFIG.DefenderSetting())
                        .onChanged(CONFIG::DefenderSetting);
        rootComponent.childById(CheckboxComponent.class, "Asmodeus")
                        .checked(CONFIG.AsmodeusSetting())
                        .onChanged(CONFIG::AsmodeusSetting);
        rootComponent.childById(CheckboxComponent.class, "Seraphim")
                        .checked(CONFIG.SeraphimSetting())
                        .onChanged(CONFIG::SeraphimSetting);
        rootComponent.childById(CheckboxComponent.class, "Raphael")
                        .checked(CONFIG.RaphaelSetting())
                        .onChanged(CONFIG::RaphaelSetting);
        rootComponent.childById(CheckboxComponent.class, "Ophanim")
                        .checked(CONFIG.OphanimSetting())
                        .onChanged(CONFIG::OphanimSetting);

        //Unholy and Nihility 
        rootComponent.childById(CheckboxComponent.class, "TrueSeraph")
                        .checked(CONFIG.TrueSeraphSetting())
                        .onChanged(CONFIG::TrueSeraphSetting);
        rootComponent.childById(CheckboxComponent.class, "TrueOphan")
                        .checked(CONFIG.TrueOphanSetting())
                        .onChanged(CONFIG::TrueOphanSetting);
        rootComponent.childById(CheckboxComponent.class, "Nihility")
                        .checked(CONFIG.NihilitySetting())
                        .onChanged(CONFIG::NihilitySetting);
    }
}
