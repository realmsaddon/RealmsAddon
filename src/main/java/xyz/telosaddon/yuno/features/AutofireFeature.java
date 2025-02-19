package xyz.telosaddon.yuno.features;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Hand;
import xyz.telosaddon.yuno.TelosAddon;

import static xyz.telosaddon.yuno.utils.TabListUtils.mc;


public class AutofireFeature extends ToggleableFeature{
    AutofireFeature() {
        super(TelosAddon.CONFIG.keys.swingSetting);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // todo: move to toggleablefeature
            ClientPlayerEntity player = client.player;
            if(mc.options.attackKey.isPressed() && isEnabled() && TelosAddon.getInstance().isOnTelos()) {
                boolean canSwing = !player.getItemCooldownManager().isCoolingDown(player.getMainHandStack());
                if (!TelosAddon.CONFIG.swingIfNoCooldown()|| canSwing) {
                    player.swingHand(Hand.MAIN_HAND);
                }
            }
        });
    }


    @Override
    public void enable(){

    }

    @Override
    public void disable(){
    }
}


