package xyz.telosaddon.yuno.features;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import xyz.telosaddon.yuno.utils.config.ConfigUtils;

import xyz.telosaddon.yuno.TelosAddon;

public class ShowMainRangeFeature extends ShowRangeFeature {

	public ShowMainRangeFeature() {
		super(TelosAddon.CONFIG.keys.showMainRangeFeatureEnabled, PlayerInventory::getMainHandStack);
		this.setRangeType(TelosAddon.CONFIG.showMainRangeFeatureViewType());
		ConfigUtils.addConfigBinding(TelosAddon.CONFIG.keys.showMainRangeFeatureViewType, this::setRangeType);
	}
	@Override()
	public void enable(){
		System.out.println("ShowMainRangeFeature enable");
		this.setRangeType(TelosAddon.CONFIG.showMainRangeFeatureViewType());
	}
	@Override()
	public boolean isEnabled(){
		return TelosAddon.CONFIG.showMainRangeFeatureEnabled();
	}


	@Override
	public void draw(float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, ClientPlayerEntity player, float dy) {
		if (!this.isEnabled()) return;

		this.renderers.forEach(r -> r.draw(tickDelta,
				matrices,
				vertexConsumers,
				player,
				TelosAddon.CONFIG.showMainRangeFeatureColor(),
				(float) (TelosAddon.CONFIG.showMainRangeFeatureHeight() + dy)));

	}
}
