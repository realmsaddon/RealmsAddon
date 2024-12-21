package xyz.telosaddon.yuno.features;

import net.minecraft.entity.player.PlayerInventory;
import xyz.telosaddon.yuno.utils.config.TelosConfig;

public class ShowMainRangeFeature extends ShowRangeFeature {

	public ShowMainRangeFeature(TelosConfig config) {
		super(config, PlayerInventory::getMainHandStack, "Main");
	}
}
