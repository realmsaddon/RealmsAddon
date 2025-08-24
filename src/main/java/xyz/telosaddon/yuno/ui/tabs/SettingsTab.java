package xyz.telosaddon.yuno.ui.tabs;

import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.DropdownComponent;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.component.TextBoxComponent;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.Color;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import net.minecraft.client.gui.screen.Screen;
import xyz.telosaddon.yuno.TelosAddon;
import xyz.telosaddon.yuno.ui.TelosMenu;
import xyz.telosaddon.yuno.utils.config.SerializeUtils;

import static xyz.telosaddon.yuno.TelosAddon.CONFIG;

public class SettingsTab extends BaseUIModelScreen<FlowLayout> {

    private final Screen parent;

    public SettingsTab(@Nullable Screen parent) {

        super(FlowLayout.class, DataSource.asset(Identifier.of("telosaddon", "settingstab")));
        this.parent = parent;
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent.childById(LabelComponent.class, "version").text(Text.of(TelosAddon.MOD_NAME + TelosAddon.MOD_VERSION));
        //tab buttons
        rootComponent.childById(ButtonComponent.class, "Gui").onPress(button -> {
            this.client.setScreen(new GuiTab(this));
        });
        rootComponent.childById(ButtonComponent.class, "Home").onPress(button -> {
            this.client.setScreen(new HomeTab());
        });
        rootComponent.childById(ButtonComponent.class, "Range").onPress(button -> {
            this.client.setScreen(new RangeTab(this));
        });


        rootComponent.childById(ButtonComponent.class, "EditDisplayPosition").onPress(button -> {
                TelosAddon.getInstance().setEditMode(true);
                TelosMenu menu = new TelosMenu();
                menu.getCustomUiManager().editMode(this);
                this.client.setScreen(menu);

        });

        rootComponent.childById(ButtonComponent.class, "ResetDisplayPosition").onPress(button -> {
                    CONFIG.infoX(4);
                    CONFIG.infoY(4);
                    CONFIG.bagX(-1);
                    CONFIG.bagY(60);
                    CONFIG.pitybagX(-1);
                    CONFIG.pitybagY(20);
        });

        rootComponent.childById(TextBoxComponent.class, "ChangeGUIColorField")
                .text(Color.ofRgb(CONFIG.menuColor()).asHexString(false))
                .<TextBoxComponent>configure(textBox -> {
                    var eventSrc = textBox.onChanged();
                    eventSrc.subscribe((input)->{
                        try {
                            int color = 0xFF000000 | SerializeUtils.parseHexRGB(input);
                            CONFIG.menuColor(color);
                        } catch (Exception e) {
                            TelosAddon.LOGGER.error(e.getLocalizedMessage());
                            TelosAddon.getInstance().sendMessage("Wrong Format! Use #AARRGGBB!");
                        }
                    });
                });

        rootComponent.childById(DropdownComponent.class, "ChangeFontDropdown")

                .button(Text.of("Nokia CF").getWithStyle(Style.EMPTY).getFirst(),
                        (button)->{
                            CONFIG.font("nokiacf") ;
                        })
                .button(Text.of("Roboto").getWithStyle(Style.EMPTY).getFirst(),
                        (dropdown)->{
                            CONFIG.font("roboto") ;
                })
                .button(Text.of("Silkscreen").getWithStyle(Style.EMPTY).getFirst(),
                        (dropdown)->{
                            CONFIG.font("silkscreen");
                })
                .button(Text.of("Arial").getWithStyle(Style.EMPTY).getFirst(),
                        (dropdown)->{
                            CONFIG.font("arial");
                        })
                .button(Text.of("Comic Sans").getWithStyle(Style.EMPTY).getFirst(),
                        (dropdown)->{
                            CONFIG.font("comic_sans");
                        })
                .button(Text.of("Default").getWithStyle(Style.EMPTY).getFirst(),
                        (dropdown)->{
                            CONFIG.font("default");
                        });;


    }
}