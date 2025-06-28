package xyz.telosaddon.yuno.hotkey;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import xyz.telosaddon.yuno.TelosAddon;
import xyz.telosaddon.yuno.utils.LocalAPI;

import java.util.Locale;
import java.util.Objects;

import static xyz.telosaddon.yuno.TelosAddon.CONFIG;
import static xyz.telosaddon.yuno.utils.BossBarUtils.bossBarMap;


@Environment(EnvType.CLIENT)

public class CallHotkey{
    private static KeyBinding keyBinding;
    private static int callCooldown = 0;

    public static void init() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.telosaddon.call",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                "category.telosaddon"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (callCooldown > 0) {
                callCooldown--;
            }

            if (keyBinding.wasPressed()) {
                if (callCooldown <=0 ) {

                    String callString = "";

                    if (!LocalAPI.getCurrentCharacterFighting().equals("")){
                        callString = LocalAPI.getCurrentCharacterFighting().toLowerCase(Locale.ROOT) + " tp";
                    }
                    else if (!LocalAPI.getCurrentPortalCall().equals("")){
                        String portalToCall = LocalAPI.getCurrentPortalCall();
                        int timeLeft = LocalAPI.getCurrentPortalCallTime();
                        callString = "tp "  + portalToCall.toLowerCase(Locale.ROOT) + " " + timeLeft + "s";
                    }
                    else{
                        if (client.player == null) return;
                        client.player.sendMessage(Text.of("§cYou can only use this hotkey near a boss!"), false);
                        return;
                    }

                    if (CONFIG.callHotkeyShout) {
                        Objects.requireNonNull(client.getNetworkHandler()).sendChatCommand("shout " + callString);
                    }
                    else{
                        Objects.requireNonNull(client.getNetworkHandler()).sendChatMessage(callString);
                    }
                    callCooldown = 20 * 30; // 30s cd
                }
                else{
                    if (client.player == null) return;
                    client.player.sendMessage(Text.of("§cYou can't call a boss for another " + callCooldown/20 + " seconds!"), false);
                }
            }
        });
    }
}
