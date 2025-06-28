//package xyz.telosaddon.yuno.ui.tabs;
//
//import io.wispforest.owo.ui.base.BaseUIModelScreen;
//import io.wispforest.owo.ui.component.ButtonComponent;
//import io.wispforest.owo.ui.component.CheckboxComponent;
//import io.wispforest.owo.ui.component.TextBoxComponent;
//import io.wispforest.owo.ui.container.FlowLayout;
//import net.minecraft.util.Identifier;
//
//import static xyz.telosaddon.yuno.TelosAddon.CONFIG;
//
//public class HomeTab extends BaseUIModelScreen<FlowLayout> {
//    public HomeTab() {
//        super(FlowLayout.class, DataSource.asset(Identifier.of("telosaddon", "hometab")));
//    }
//
//    @Override
//    protected void build(FlowLayout rootComponent) {
//        //tab buttons
//        rootComponent.childById(ButtonComponent.class, "Gui").onPress(button -> {
//            this.client.setScreen(new GuiTab(this));
//        });
//        rootComponent.childById(ButtonComponent.class, "Settings").onPress(button -> {
//            this.client.setScreen(new SettingsTab(this));
//        });
//
//        rootComponent.childById(ButtonComponent.class, "Range").onPress(button -> {
//            this.client.setScreen(new RangeTab(this));
//        });
//
//        //the other buttons
//        rootComponent.childById(CheckboxComponent.class, "SwingSetting")
//                .checked(CONFIG.swingSetting())
//                .onChanged(CONFIG::swingSetting);
//        rootComponent.childById(CheckboxComponent.class, "SwingIfNoCooldown")
//                .checked(CONFIG.swingIfNoCooldown())
//                .onChanged(CONFIG::swingIfNoCooldown);
//
//        rootComponent.childById(CheckboxComponent.class, "GammaSetting")
//                .checked(CONFIG.gammaSetting())
//                .onChanged(CONFIG::gammaSetting);
//        rootComponent.childById(CheckboxComponent.class, "FPSSetting")
//                .checked(CONFIG.fpsSetting())
//                .onChanged(CONFIG::fpsSetting);
//        rootComponent.childById(CheckboxComponent.class, "PingSetting")
//                .checked(CONFIG.pingSetting())
//                .onChanged(CONFIG::pingSetting);
//        rootComponent.childById(CheckboxComponent.class, "PlaytimeSetting")
//                .checked(CONFIG.playTimeSetting())
//                .onChanged(CONFIG::playTimeSetting);
//        rootComponent.childById(CheckboxComponent.class, "SpawnBossesSetting")
//                .checked(CONFIG.bossTrackerFeatureEnabled())
//                .onChanged(CONFIG::bossTrackerFeatureEnabled);
//
//        rootComponent.childById(CheckboxComponent.class, "SoundSetting")
//                .checked(CONFIG.soundSetting())
//                .onChanged(CONFIG::soundSetting);
//
//        rootComponent.childById(CheckboxComponent.class, "DiscordRPCSetting")
//                .checked(CONFIG.discordRPCSetting())
//                .onChanged(CONFIG::discordRPCSetting);
//
//        rootComponent.childById(TextBoxComponent.class, "DiscordDefaultStatusMessage")
//                .text(CONFIG.discordDefaultStatusMessage())
//                .<TextBoxComponent>configure(textBox -> {
//                    var eventSrc = textBox.onChanged();
//                    eventSrc.subscribe(CONFIG::discordDefaultStatusMessage);
//                });
//
//        rootComponent.childById(CheckboxComponent.class, "RPCShowLocationSetting")
//                .checked(CONFIG.rpcShowLocationSetting())
//                .onChanged(CONFIG::rpcShowLocationSetting);
//        rootComponent.childById(CheckboxComponent.class, "RPCShowFightingSetting")
//                .checked(CONFIG.rpcShowFightingSetting())
//                .onChanged(CONFIG::rpcShowFightingSetting);
//
//        rootComponent.childById(CheckboxComponent.class, "ToggleHotkeyShoutSetting")
//                .checked(CONFIG.callHotkeyShout())
//                .onChanged(CONFIG::callHotkeyShout);
//
//
//        rootComponent.childById(CheckboxComponent.class, "BossWaypointsSetting")
//                .checked(CONFIG.bossWaypointsSetting())
//                .onChanged(CONFIG::bossWaypointsSetting);
//    }
//}
