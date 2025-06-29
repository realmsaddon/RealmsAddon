package xyz.telosaddon.yuno.hotkey;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import xyz.telosaddon.yuno.utils.LocalAPI;

import java.util.ArrayList;

import static xyz.telosaddon.yuno.TelosAddon.LOGGER;


@Environment(EnvType.CLIENT)
public class TestHotkey {
    private static KeyBinding keyBinding;

    public static void init() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.telosaddon.test",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_P,
                "category.telosaddon"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.wasPressed()) {
                testCheckItemInfo(client.player);

           }
        });
    }

    public static void testCheckItemInfo(ClientPlayerEntity player ){
        if (player == null) return;

        var playerMainHandItemstack = player.getInventory().getSelectedStack();
        player.sendMessage(Text.of("this item has these components: " + playerMainHandItemstack.getItem().getName()),false);
        var lorecomponent = playerMainHandItemstack.getComponents().get(DataComponentTypes.LORE);
        LOGGER.info("might have lore");
        if (lorecomponent == null) return;
        LOGGER.info(" have lore?");

        var combinedlines = lorecomponent.lines().stream().reduce((subtotal, elem) -> {

                    return subtotal.copy().append(elem.copy()).append(Text.of(" \n"));
                }
        );
        if (combinedlines.isEmpty()) return;
        player.sendMessage(Text.of("lore: \n{" + combinedlines.get() + "\n }")
        , false);

    }
}