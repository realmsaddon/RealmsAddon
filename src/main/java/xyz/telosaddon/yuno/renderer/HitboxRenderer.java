package xyz.telosaddon.yuno.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;
import xyz.telosaddon.yuno.hotkey.TestHotkey;
import xyz.telosaddon.yuno.renderer.waypoints.WaypointRenderer;

import static xyz.telosaddon.yuno.hotkey.HitboxHotkey.showHitboxIndicator;

public class HitboxRenderer {


    private static final Identifier RENDER_IDENTIFIER = Identifier.of("showteloshitboxes", "hitboxes");
    public static void init(){
        WorldRenderEvents.AFTER_ENTITIES.addPhaseOrdering(Event.DEFAULT_PHASE, RENDER_IDENTIFIER);
        WorldRenderEvents.AFTER_ENTITIES.register(RENDER_IDENTIFIER, HitboxRenderer::render);
    }

    public static void render(WorldRenderContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!showHitboxIndicator || client.options.getPerspective() != Perspective.THIRD_PERSON_BACK) return;


        MatrixStack matrices = context.matrixStack();


        VertexConsumerProvider.Immediate vertexConsumers = client.getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getLines());
        renderHitbox(matrices, vertexConsumer,  1, 1, 1);

        vertexConsumers.draw();

    }


    private static void renderHitbox(MatrixStack matrices, VertexConsumer vertices,  float red, float green, float blue) {
        MinecraftClient client = MinecraftClient.getInstance();
        Entity entity = client.player;



        if (entity == null ) return;
        Vec3d lookVector = Vec3d.fromPolar(entity.prevPitch, entity.prevYaw).normalize().multiply(4.0F);
        Box box = entity.getBoundingBox().offset(-entity.getX() + lookVector.x, -entity.getY() + lookVector.y - 1.5F, -entity.getZ() + lookVector.z);
        VertexRendering.drawBox(matrices, vertices, box, red, green, blue, 1.0F);
    }
}
