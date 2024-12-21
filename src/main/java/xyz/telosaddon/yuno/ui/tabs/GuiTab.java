//package xyz.telosaddon.yuno.ui.tabs;
//
//import xyz.telosaddon.yuno.TelosAddon;
//import xyz.telosaddon.yuno.ui.CustomElement;
//import xyz.telosaddon.yuno.ui.elements.CustomButton;
//import xyz.telosaddon.yuno.ui.CustomUiManager;
//import xyz.telosaddon.yuno.utils.config.TelosConfig;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static xyz.telosaddon.yuno.TelosAddon.CONFIG;
//
//public class GuiTab {
//
//    private final TelosConfig config;
//    private final CustomUiManager uiManager;
//    private List<CustomElement> elements;
//
//    public GuiTab(CustomUiManager uiManager) {
//        config = TelosAddon.getInstance().getConfig();
//        this.uiManager = uiManager;
//    }
//
//    public void loadButtons() {
//        boolean greenSetting = CONFIG.greenSetting();
//        boolean goldSetting = CONFIG.goldSetting();
//        boolean whiteSetting = CONFIG.whiteSetting();
//        boolean blackSetting = CONFIG.blackSetting();
//        boolean xMasSetting = CONFIG.eventSetting();
//        boolean crossSetting = CONFIG.crossSetting();
//        boolean relicSetting = CONFIG.relicSetting();
//        boolean runesSetting = CONFIG.runesSetting();
//        boolean totalRunSetting = CONFIG.totalRunSetting();
//        boolean noWhiteRunSetting = CONFIG.noWhiteRunSetting();
//        boolean noBlackRunSetting = CONFIG.noBlackRunSetting();
//        boolean lifetimeSetting = CONFIG.lifetimeSetting();
//
//        this.elements = Arrays.asList(
//
//                new CustomButton(8, 83, 150, 20, "GreenBag Counter", (button, toggled) -> {
//                    CONFIG.noWhiteRunSetting(!CONFIG.noWhiteRunSetting());
//                }).setToggled(CONFIG.greenSetting()),
//
//                new CustomButton(8, 106, 150, 20, "GoldBag Counter", (button, toggled) -> {
//                    CONFIG.goldSetting(!CONFIG.goldSetting());
//                }).setToggled(CONFIG.goldSetting()),
//
//                new CustomButton(8, 129, 150, 20, "WhiteBag Counter", (button, toggled) -> {
//                    CONFIG.whiteSetting(!CONFIG.whiteSetting());
//                }).setToggled(CONFIG.whiteSetting()),
//
//                new CustomButton(8, 152, 150, 20, "BlackBag Counter", (button, toggled) -> {
//                    CONFIG.blackSetting(!CONFIG.blackSetting());
//                }).setToggled(CONFIG.blackSetting()),
//
//                new CustomButton(8, 175, 150, 20, "EventBag Counter", (button, toggled) -> {
//                    CONFIG.eventSetting(!CONFIG.eventSetting());
//                }).setToggled(CONFIG.eventSetting()),
//
//                new CustomButton(8, 198, 150, 20, "Cross Counter", (button, toggled) -> {
//                    CONFIG.crossSetting(!CONFIG.crossSetting());
//                }).setToggled(CONFIG.crossSetting()),
//
//                new CustomButton(8, 221, 150, 20, "Relic Counter", (button, toggled) -> {
//                    CONFIG.relicSetting(!CONFIG.relicSetting());
//                }).setToggled(CONFIG.relicSetting()),
//                new CustomButton(8, 244, 150, 20, "Runes Counter", (button, toggled) -> {
//                    toggle("RunesSetting", button.getText(), toggled);
//                }).setToggled(CONFIG.runesSetting()),
//
//                new CustomButton(8, 267, 150, 20, "Total Boss Runs", (button, toggled) -> {
//                    toggle("TotalRunSetting", button.getText(), toggled);
//                }).setToggled(CONFIG.totalRunSetting()),
//
//                new CustomButton(175, 83, 150, 20, "No Whites Runs", (button, toggled) -> {
//                    toggle("NoWhiteRunSetting", button.getText(), toggled);
//                }).setToggled(CONFIG.noWhiteRunSetting()),
//
//                new CustomButton(175, 106, 150, 20, "No Black Runs", (button, toggled) -> {
//                    toggle("NoBlackRunSetting", button.getText(), toggled);
//                }).setToggled(CONFIG.noBlackRunSetting()),
//
//                new CustomButton(175, 129, 150, 20, "Lifetime Counter", (button, toggled) -> {
//                    toggle("LifetimeSetting", button.getText(), toggled);
//                }).setToggled(CONFIG.lifetimeSetting())
//
//        );
//
//        uiManager.getCustomElements().addAll(this.elements);
//    }
//
//    public void toggle(String name, String btnName, boolean toggled) {
//
//    }
//
//}
