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
    }
}
