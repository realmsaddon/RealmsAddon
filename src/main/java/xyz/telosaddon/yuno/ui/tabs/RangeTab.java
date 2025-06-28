//package xyz.telosaddon.yuno.ui.tabs;
//
//import io.wispforest.owo.ui.base.BaseUIModelScreen;
//import io.wispforest.owo.ui.component.*;
//import io.wispforest.owo.ui.container.FlowLayout;
//import io.wispforest.owo.ui.core.Color;
//import net.minecraft.text.Text;
//import net.minecraft.util.Identifier;
//import org.jetbrains.annotations.Nullable;
//import net.minecraft.client.gui.screen.Screen;
//import xyz.telosaddon.yuno.TelosAddon;
//import xyz.telosaddon.yuno.features.ShowRangeFeature;
//import xyz.telosaddon.yuno.utils.config.SerializeUtils;
//
//import static xyz.telosaddon.yuno.TelosAddon.CONFIG;
//
//public class RangeTab extends BaseUIModelScreen<FlowLayout> {
//
//    private final Screen parent;
//
//    public RangeTab(@Nullable Screen parent) {
//        super(FlowLayout.class, DataSource.asset(Identifier.of("telosaddon", "rangetab")));
//        this.parent = parent;
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
//        rootComponent.childById(ButtonComponent.class, "Home").onPress(button -> {
//            this.client.setScreen(new HomeTab());
//        });
//
//        rootComponent.childById(CheckboxComponent.class, "MainHandShowRangeSetting")
//                .checked(CONFIG.showMainRangeFeatureEnabled())
//                .onChanged(CONFIG::showMainRangeFeatureEnabled);
//        rootComponent.childById(DropdownComponent.class, "MainHandTypeDropdown")
//
//                .button(Text.of("Circle"),
//                        (button)->{
//                            CONFIG.showMainRangeFeatureViewType(ShowRangeFeature.RangeViewType.CIRCLE);
//                })
//                .button(Text.of("Line"),
//                        (dropdown)->{
//                            CONFIG.showMainRangeFeatureViewType(ShowRangeFeature.RangeViewType.LINE);
//                })
//                .button(Text.of("Both"),
//                        (dropdown)->{
//                            CONFIG.showMainRangeFeatureViewType(ShowRangeFeature.RangeViewType.BOTH);
//                        });
//        rootComponent.childById(TextBoxComponent.class, "MainHandColorField")
//                .text(Color.ofRgb(CONFIG.showMainRangeFeatureColor()).asHexString(true))
//                .<TextBoxComponent>configure(textBox -> {
//                    var eventSrc = textBox.onChanged();
//                    eventSrc.subscribe((input)->{
//                        try {
//                            int color = 0xFF000000 | SerializeUtils.parseHexRGB(input);
//                            CONFIG.showMainRangeFeatureColor(color);
//                        } catch (Exception e) {
//                            TelosAddon.getInstance().sendMessage("Wrong Format! Use #AARRGGBB!");
//                        }
//                    });
//                });
//
//
////        rootComponent.childById(ColorPickerComponent.class, "MainHandColorPicker")
////                .selectedColor(Color.ofArgb(CONFIG.showMainRangeFeatureColor()))
////                .onChanged().subscribe((color) -> {
////                    CONFIG.showMainRangeFeatureColor(color.argb());
////                });
//
//
//        rootComponent.childById(CheckboxComponent.class, "OffHandShowRangeSetting")
//                .checked(CONFIG.showOffHandRangeFeatureEnabled())
//                .onChanged(CONFIG::showOffHandRangeFeatureEnabled);
//        rootComponent.childById(DropdownComponent.class, "OffHandTypeDropdown")
//                .button(Text.of("Circle"),
//                        (dropdown)->{
//
//                            CONFIG.showOffHandRangeFeatureViewType(ShowRangeFeature.RangeViewType.CIRCLE);
//
//                        })
//                .button(Text.of("Line"),
//                        (dropdown)->{
//
//                            CONFIG.showOffHandRangeFeatureViewType(ShowRangeFeature.RangeViewType.LINE);
//                        })
//                .button(Text.of("Both"),
//                        (dropdown)->{
//
//                            CONFIG.showOffHandRangeFeatureViewType(ShowRangeFeature.RangeViewType.BOTH);
//                        });
//        rootComponent.childById(TextBoxComponent.class, "OffHandColorField")
//                .text(Color.ofRgb(CONFIG.showOffHandRangeFeatureColor()).asHexString(false))
//                .<TextBoxComponent>configure(textBox -> {
//                    var eventSrc = textBox.onChanged();
//                    eventSrc.subscribe((input)->{
//                        try {
//                            int color = 0xFF000000 | SerializeUtils.parseHexRGB(input);
//                            CONFIG.showOffHandRangeFeatureColor(color);
//                        } catch (Exception e) {
//                            TelosAddon.getInstance().sendMessage("Wrong Format! Use #AARRGGBB!");
//                        }
//                    });
//                });
////        rootComponent.childById(ColorPickerComponent.class, "OffHandColorPicker")
////                .selectedColor(Color.ofArgb(CONFIG.showOffHandRangeFeatureColor()))
////                .onChanged().subscribe((color) -> {
////                    CONFIG.showOffHandRangeFeatureColor(color.argb());
////                });
//
//
//
//    }
//
//
//}