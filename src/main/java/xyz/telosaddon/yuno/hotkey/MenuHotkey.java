package xyz.telosaddon.yuno.hotkey;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import xyz.telosaddon.yuno.TelosAddon;
import xyz.telosaddon.yuno.ui.TelosMenu;
//import xyz.telosaddon.yuno.ui.tabs.HomeTab;

@Environment(EnvType.CLIENT)
public class MenuHotkey {
    private static KeyBinding keyBinding;

    public static void init() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.telosaddon.menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                "category.telosaddon"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.wasPressed()) {
                assert client.player != null;
                TelosAddon.getInstance().setEditMode(true);
                TelosMenu menu = new TelosMenu();
                menu.getCustomUiManager().editMode();
                client.setScreen(menu);
                client.player.sendMessage(Text.of("TODO"), false);
            }
        });
    }
}
