package xyz.telosaddon.yuno.hotkey;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import xyz.telosaddon.yuno.utils.LocalAPI;
import xyz.telosaddon.yuno.utils.waypoints.Waypoint;

import java.util.ArrayList;


@Environment(EnvType.CLIENT)
public class TestHotkey {
    private static KeyBinding keyBinding;

    public static final ArrayList<Waypoint> waypoints = new ArrayList<>();
    public static void init() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.telosaddon.test",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                "category.telosaddon"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.wasPressed()) {
                ClientPlayerEntity player = client.player;
                assert player != null;
                //String bossPos = "new Waypoint(\"" + LocalAPI.getCurrentCharacterFighting() + "\", " + Math.round(player.getX()) + ", " +  Math.round(player.getY()) + ", " + " " + Math.round(player.getZ()) + ")";
                //player.sendMessage(Text.of(bossPos));
                waypoints.add(new Waypoint("test", player.getX(), player.getY(), player.getZ()));
            }
        });


    }
}