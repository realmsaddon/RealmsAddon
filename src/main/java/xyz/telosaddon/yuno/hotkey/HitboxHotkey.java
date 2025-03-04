package xyz.telosaddon.yuno.hotkey;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import xyz.telosaddon.yuno.ui.tabs.TeleportMenuScreen;
import xyz.telosaddon.yuno.utils.LocalAPI;

import java.util.ArrayList;


@Environment(EnvType.CLIENT)
public class HitboxHotkey {
    private static KeyBinding keyBinding;

    public static boolean showHitboxIndicator;

    public static void init() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.telosaddon.hitboxes",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "category.telosaddon"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.wasPressed()) {
                showHitboxIndicator = !showHitboxIndicator;
            }
        });

    }

}