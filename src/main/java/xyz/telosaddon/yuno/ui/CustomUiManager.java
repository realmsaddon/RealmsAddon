package xyz.telosaddon.yuno.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import xyz.telosaddon.yuno.TelosAddon;
import xyz.telosaddon.yuno.ui.tabs.SettingsTab;
import xyz.telosaddon.yuno.ui.elements.CustomButton;

import java.util.ArrayList;
import java.util.List;

public class CustomUiManager {

    private final MinecraftClient mc;
    private MinecraftClient client = MinecraftClient.getInstance();
    private List<CustomElement> customElements;


    public CustomUiManager() {
        this.customElements = new ArrayList<>();
        this.mc = MinecraftClient.getInstance();

    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        for(CustomElement element : this.customElements) {
            element.render(context, mouseX, mouseY, delta);
        }

    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        for(CustomElement element : this.customElements) {
            if(element.mouseClicked(mouseX, mouseY, button))
                return true;
        }
        return false;
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {

        for(CustomElement element : this.customElements) {
            if(element.isFocused() && element.mouseDragged(mouseX, mouseY, button, deltaX, deltaY))
                return true;
        }
        return false;

    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {

        for(CustomElement element : this.customElements) {
            if(element.isFocused()) {
                element.keyPressed(keyCode, scanCode, modifiers);
            }
        }

    }

    public void charTyped(char chr, int modifiers) {

        for(CustomElement element : this.customElements) {

            if(element.isFocused()) {
                element.charTyped(chr, modifiers);
            }

        }
    }


    public void addCustomElement(CustomElement customElement) {
        this.customElements.add(customElement);
    }

    public void editMode(Screen currentTab) {

        this.addCustomElement(new CustomButton(8, mc.getWindow().getScaledHeight() - 28, 150, 20, "Done", (button) -> {
            TelosAddon.getInstance().setEditMode(false);
            this.client.setScreen(new SettingsTab(currentTab));
        }).setTextInMiddle(true));
    }


}
