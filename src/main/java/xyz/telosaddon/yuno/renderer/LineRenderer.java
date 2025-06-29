package xyz.telosaddon.yuno.renderer;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import xyz.telosaddon.yuno.TelosAddon;

import java.util.Locale;
import java.util.OptionalDouble;

public class LineRenderer implements IRenderer{

	private float radius = Float.NaN;
	private float offset = 0;

	@Override
	public void draw(float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, int color, float height) {
		float dy = (entity.isInSneakingPose() ? 0.125f : 0) + height;

		RenderLayer layer = LineRendererPhases.LINE_LAYER;

		VertexConsumer vertices = vertexConsumers.getBuffer(layer);

		matrices.push();
		matrices.translate(0, 0, offset);
		drawLine(tickDelta, matrices, vertices, dy, color);
		matrices.pop();
	}

	private void drawLine(float tickDelta, MatrixStack matrices, VertexConsumer vertexConsumer, float dy, int argb){
		if(Float.isNaN(radius))
			return;
		MatrixStack.Entry entry = matrices.peek();
		float width = 0.2f;

		vertexConsumer.vertex(entry, width/2, dy, 0f).color(argb).normal(entry, 0.0f, 0.0f, 0);
		vertexConsumer.vertex(entry, width/2, dy, this.radius).color(argb).normal(entry, 0.0f, 0.0f, 0);
		vertexConsumer.vertex(entry, -width/2, dy, this.radius).color(argb).normal(entry, 0.0f, 0.0f, 0);
		vertexConsumer.vertex(entry, -width/2, dy, 0f).color(argb).normal(entry, 0.0f, 0.0f, 0);
	}

	@Override
	public void setRadius(float radius) {
		this.radius = radius;
	}

	@Override
	public void setOffset(float offset) {
		this.offset = offset;
	}

	private static class LineRendererPhases extends RenderPhase {
		static final RenderLayer.MultiPhase LINE_LAYER = makeLayer();

		private static RenderLayer.MultiPhase makeLayer() {
			String name = "showtelosrange_line_" + VertexFormat.DrawMode.LINES.name().toLowerCase(Locale.ROOT);

			return RenderLayer.of(
					name,
					1536,
					false,
					true,

					RenderPipeline.builder(RenderPipelines.POSITION_COLOR_SNIPPET)
							.withCull(false)
							.withVertexFormat(VertexFormats.POSITION_COLOR, VertexFormat.DrawMode.QUADS)
							.withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
							.withLocation(Identifier.of(TelosAddon.MOD_ID, "pipelines/showrange_line" ))
							.build(),
					RenderLayer.MultiPhaseParameters.builder()
							.lineWidth(new RenderPhase.LineWidth(OptionalDouble.of(10f)))
							.layering(RenderPhase.VIEW_OFFSET_Z_LAYERING)
							.target(ITEM_ENTITY_TARGET)
							.build(false));
		}

		// required for COLOR_PROGRAM, etc.
		// see #makeLayer
		private LineRendererPhases(String name, Runnable beginAction, Runnable endAction) {
			super(name, beginAction, endAction);
		}

	}


}
