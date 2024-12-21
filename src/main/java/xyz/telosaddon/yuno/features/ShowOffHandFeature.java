package xyz.telosaddon.yuno.features;

import xyz.telosaddon.yuno.utils.config.TelosConfig;

public class ShowOffHandFeature extends ShowRangeFeature {
	public ShowOffHandFeature(TelosConfig config) {
		super(config, (inv -> inv.offHand.get(0)), "OffHand");
	}
}
