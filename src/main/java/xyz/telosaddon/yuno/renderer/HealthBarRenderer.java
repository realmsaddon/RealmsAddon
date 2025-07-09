package xyz.telosaddon.yuno.renderer;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat;
import io.wispforest.owo.config.Option;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;

import net.minecraft.client.util.math.MatrixStack;

import net.minecraft.util.Identifier;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import xyz.telosaddon.yuno.TelosAddon;

import xyz.telosaddon.yuno.features.ToggleableFeature;

import java.util.Locale;


import static xyz.telosaddon.yuno.TelosAddon.CONFIG;

// ripped from torilhos i just ported it to renderpipeline
public class HealthBarRenderer extends ToggleableFeature {
    private static final Identifier RENDER_IDENTIFIER = Identifier.of("showteloshealth", "health");


    private static final MinecraftClient client  = MinecraftClient.getInstance();
    public HealthBarRenderer() {
        super(CONFIG.keys.healthBarSetting);
        WorldRenderEvents.LAST.addPhaseOrdering(Event.DEFAULT_PHASE, RENDER_IDENTIFIER);
        WorldRenderEvents.LAST.register(RENDER_IDENTIFIER, this::render);
    }

    public void render(WorldRenderContext context) {
        if (!isEnabled()
                || client.player == null
                || client.options.getPerspective().isFirstPerson()) {
            return;
        }

        var renderLayer = HealthRendererPhase.makeLayer();
        var healthPercentage = client.player.getHealth() / client.player.getMaxHealth();

        if (healthPercentage == 1f) {
            return;
        }

        var matrixStack = context.matrixStack();

        if (matrixStack == null) {
            return;
        }

        var camera = context.camera();
        var tickDelta = context.tickCounter().getDynamicDeltaTicks();
        var playerPos = new Vec3d(
                MathHelper.lerp(tickDelta, client.player.lastRenderX, client.player.getX()),
                MathHelper.lerp(tickDelta, client.player.lastRenderY, client.player.getY()),
                MathHelper.lerp(tickDelta, client.player.lastRenderZ, client.player.getZ())
        );
        var renderPos = playerPos.subtract(camera.getPos());
        var barWidth = 1.2f;
        var barHeight = 0.22f;
        var barBorder = 0.02f;
        var scaledBarWidth = barWidth * healthPercentage;
        var healthBarColor = healthPercentage >= 0.75f
                ? 0xB040CC40
                : healthPercentage <= 0.4f
                ? 0xB0CC3030
                : 0xB0FFCC40;

        VertexConsumerProvider.Immediate vertexConsumers = client.getBufferBuilders().getEntityVertexConsumers();
        VertexConsumerProvider provider = context.consumers();
        matrixStack.push();

        // Use the player position as the center of rotation
        matrixStack.translate(renderPos.x, renderPos.y, renderPos.z);

        // Billboarding
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-camera.getYaw() + 180));
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-camera.getPitch()));

        // Place bar slightly below the player
        matrixStack.translate(0, -0.4f, 0);

//        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        RenderSystem.enableBlend();
//        RenderSystem.disableDepthTest();

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();

        var consumer = vertexConsumers.getBuffer(renderLayer);

        drawRectCentered(matrix, consumer, barWidth, barHeight, 0xB0FFFFFF);
        drawRectCentered(matrix, consumer, barWidth - barBorder * 2, barHeight - barBorder * 2, 0xB0000000);
        drawRectLeft(matrix, consumer, scaledBarWidth - barBorder * 2, barWidth - barBorder * 2, barHeight - barBorder * 2, healthBarColor);

        drawTextCentered(matrixStack,vertexConsumers,  client.textRenderer, String.valueOf((int) client.player.getHealth()));

        matrixStack.pop();

        vertexConsumers.draw(renderLayer);

    }

    private void drawRectCentered(Matrix4f matrix, VertexConsumer consumer, float width, float height, int argb) {
        var halfWidth = width * 0.5f;
        var halfHeight = height * 0.5f;
        // TODO: Try Quad draw mode?
        var buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        // Order is top right, top left,  bottom left, bottom right
        consumer.vertex(matrix, halfWidth, halfHeight, 0).color(argb);
        consumer.vertex(matrix, -halfWidth, halfHeight, 0).color(argb);
        consumer.vertex(matrix, -halfWidth, -halfHeight, 0).color(argb);
        consumer.vertex(matrix, halfWidth, -halfHeight, 0).color(argb);

    }

    private void drawRectLeft(Matrix4f matrix, VertexConsumer consumer, float width, float fullWidth, float height, int argb) {
        var halfWidth = fullWidth * 0.5f;
        var halfHeight = height * 0.5f;
        var buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        consumer.vertex(matrix, width - halfWidth, halfHeight, 0).color(argb);
        consumer.vertex(matrix, -halfWidth, halfHeight, 0).color(argb);
        consumer.vertex(matrix, -halfWidth, -halfHeight, 0).color(argb);
        consumer.vertex(matrix, width - halfWidth, -halfHeight, 0).color(argb);
        //BufferRenderer.drawWithGlobalProgram(buffer.end());
    }

    private void drawTextCentered(@NotNull MatrixStack matrixStack, VertexConsumerProvider.Immediate consumerProvider, @NotNull TextRenderer textRenderer, String text) {
        matrixStack.push();
        matrixStack.scale(0.02f, -0.02f, 0.02f);

        textRenderer.draw(
                text,
                -textRenderer.getWidth(text) * 0.5f,
                -textRenderer.fontHeight * 0.4f,
                0xFFFFFF,
                false,
                matrixStack.peek().getPositionMatrix(),
                consumerProvider,
                TextRenderer.TextLayerType.SEE_THROUGH,
                0,
                LightmapTextureManager.pack(15, 15)
        );

        consumerProvider.draw();

        matrixStack.pop();
    }


    private static class HealthRendererPhase extends RenderPhase {
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
                            .withBlend(BlendFunction.OVERLAY)
                            .withLocation(Identifier.of(TelosAddon.MOD_ID, "pipelines/showhealth" ))
                            .build(),
                    RenderLayer.MultiPhaseParameters.builder()
//                            //.program(RenderPhase.POSITION_COLOR_PROGRAM)
//                            //.writeMaskState(ALL_MASK)
//                            //.transparency(RenderPhase.NO_TRANSPARENCY)
//                            .lineWidth(new RenderPhase.LineWidth(OptionalDouble.of(10f)))
                            .layering(RenderPhase.VIEW_OFFSET_Z_LAYERING)
                            .target(ITEM_ENTITY_TARGET)
                            .build(false));
        }

        // required for COLOR_PROGRAM, etc.
        // see #makeLayer
        private HealthRendererPhase(String name, Runnable beginAction, Runnable endAction) {
            super(name, beginAction, endAction);
        }

    }
}



