package xyz.telosaddon.yuno.hotkey;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.lwjgl.glfw.GLFW;
import xyz.telosaddon.yuno.TelosAddon;
import xyz.telosaddon.yuno.utils.ItemType;

import static xyz.telosaddon.yuno.TelosAddon.LOGGER;

@Environment(EnvType.CLIENT)
public class MountHotkey {
    private static KeyBinding keyBinding;

    public static void init() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.telosaddon.mount",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.telosaddon"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.wasPressed()) {

                useMount();
            }
        });
    }
    public static void useMount(){
        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;
        PlayerInventory inv = client.player.getInventory();
        for (int i = 0; i < 9; i++) {
            ItemStack item = inv.getStack(i);
            LOGGER.info(item.getName().getString().hashCode()+"");
            if (item.getName().getString().hashCode() == 1307673572){ // hacky solution but it works
                inv.setSelectedSlot(i);
            }
        }
        assert client.interactionManager != null;
        client.interactionManager.interactItem(client.player, client.player.getActiveHand());
    }

}
