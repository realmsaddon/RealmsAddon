package xyz.telosaddon.yuno.features;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;

import static xyz.telosaddon.yuno.TelosAddon.CONFIG;
import xyz.telosaddon.yuno.utils.config.ConfigUtils;

public class ShowOffHandFeature extends ShowRangeFeature {
	public ShowOffHandFeature() {
		super(CONFIG.keys.showOffHandRangeFeatureEnabled, (inv -> inv.offHand.get(0)));
		this.setRangeType(CONFIG.showOffHandRangeFeatureViewType());
		ConfigUtils.addConfigBinding(CONFIG.keys.showOffHandRangeFeatureViewType, this::setRangeType);
	}

	@Override()
	public void enable(){
		super.enable();
		this.setRangeType(CONFIG.showOffHandRangeFeatureViewType());
	}

	@Override()
	public boolean isEnabled(){
		return CONFIG.showOffHandRangeFeatureEnabled();
	}

	@Override
	public void draw(float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, ClientPlayerEntity player, float dy) {
		if (!this.isEnabled()) return;

		this.renderers.forEach(r -> r.draw(tickDelta,
				matrices,
				vertexConsumers,
				player,
				CONFIG.showOffHandRangeFeatureColor(),
				(float) (CONFIG.showOffHandRangeFeatureHeight() + dy)));

	}
}
