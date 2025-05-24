package xyz.telosaddon.yuno.renderer.waypoints;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.ArmorStandEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import xyz.telosaddon.yuno.features.Features;
import xyz.telosaddon.yuno.hotkey.TestHotkey;
import xyz.telosaddon.yuno.renderer.LineRenderer;
import xyz.telosaddon.yuno.renderer.RangeRenderer;
import xyz.telosaddon.yuno.utils.MathUtil;
import xyz.telosaddon.yuno.utils.data.BossData;


import java.awt.*;
import java.util.Locale;


import static xyz.telosaddon.yuno.TelosAddon.CONFIG;

public class WaypointRenderer{
    private static final Identifier RENDER_IDENTIFIER = Identifier.of("showteloswaypoints", "waypoints");
    public static void init(){
        WorldRenderEvents.END.addPhaseOrdering(Event.DEFAULT_PHASE, RENDER_IDENTIFIER);
        WorldRenderEvents.END.register(RENDER_IDENTIFIER, WaypointRenderer::render);
    }


    public static Vec3d calculateRenderCoords(BlockPos waypoint, Camera camera, float distance) {
        double cameraX = (float)camera.getPos().x;
        double cameraY = (float)camera.getPos().y;
        double cameraZ = (float)camera.getPos().z;
        double wx = waypoint.getX();
        double wy = waypoint.getY();
        double wz = waypoint.getZ();

        // local displacement
        double vx = wx - cameraX;
        double vy = wy - cameraY;
        double vz = wz - cameraZ;

        float vector_len = (float)Math.sqrt((vx*vx) + (vy*vy) + (vz*vz));
        float radius = 32;
        double scx = radius / vector_len * vx;
        double scy = radius / vector_len * vy;
        double scz = radius / vector_len * vz;
        double prx, pry, prz;
        if (distance > 32) {
            prx = scx + cameraX;
            pry = scy + cameraY;
            prz = scz + cameraZ;
        }
        else {
            prx = wx;
            pry = wy;
            prz = wz;
        }
        return new Vec3d(prx,pry + 1,prz).subtract(camera.getPos());
    }
    public static void render(WorldRenderContext context){

        MatrixStack matrixStack = context.matrixStack();
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;
        Camera camera = context.camera();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_TEXTURE_COLOR);

        VertexConsumerProvider.Immediate consumerProvider = client.getBufferBuilders().getEntityVertexConsumers();
        for(BossData bossData : Features.BOSS_TRACKER_FEATURE.getCurrentAlive()) {
            if(!CONFIG.bossWaypointsSetting()) return;
            BlockPos waypoint = bossData.spawnPosition;

            Vec3d position = new Vec3d(waypoint.getX(), waypoint.getY(), waypoint.getZ());
            double squaredDistance = camera.getPos().squaredDistanceTo(position);

            double distance = Math.sqrt(squaredDistance);

            //System.out.println("local displacement: " + x + " " + y + " " + z);
            Vec3d renderCoords = calculateRenderCoords(waypoint, camera, (int) distance);

            matrixStack.push();

            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0F));
            matrixStack.translate(renderCoords.x, renderCoords.y, renderCoords.z);
            float scale = (0.1F);

            matrixStack.multiply(camera.getRotation());


            matrixStack.scale(scale, -scale, scale);


            int tooclosedist = 30;
            var lerp = clamp((float) (distance - 15)/tooclosedist , 0.1F , 1F);
            var fade = (int)(lerp * 255F);
            //System.out.println(fade);
            int colorcode = new Color(255,255,255, (fade)).getRGB();
            int backgroundColorCode = new Color(0,0,0, (int) (fade * 0.3)).getRGB();

            String waypointString = bossData.label + " (" +  (int) distance + "m" + ")";
//            RenderSystem.depthMask(false);
//            RenderSystem.disableCull();
//            RenderSystem.disableBlend();
            textRenderer.draw(
                    Text.of(waypointString),
                    -textRenderer.getWidth(bossData.label) / 2.0F,
                    (float) 0,
                    colorcode,
                    false,
                    matrixStack.peek().getPositionMatrix(),
                    consumerProvider,
                    TextRenderer.TextLayerType.SEE_THROUGH ,
                    backgroundColorCode,
                    LightmapTextureManager.MAX_LIGHT_COORDINATE
            );
            if (distance > 30 ) {
                textRenderer.draw(
                        Text.of(waypointString),
                        -textRenderer.getWidth(bossData.label) / 2.0F,
                        (float) 0,
                        -1,
                        false,
                        matrixStack.peek().getPositionMatrix(),
                        consumerProvider,
                        TextRenderer.TextLayerType.NORMAL,
                        0,
                        LightmapTextureManager.MAX_LIGHT_COORDINATE
                );
            }

            //todo: render the boss icon
//            RenderSystem.depthMask(true);
//            RenderSystem.enableCull();
//            RenderSystem.enableBlend();
            matrixStack.pop();

        }
        consumerProvider.draw();

    }


    public static float clamp(float value, float min, float max) {
        return max > value ? Math.max(value, min) : max;
    }
//    public static final RenderLayer WAYPOINT_TEXT_LAYER = RenderLayer.of(
//            "waypoint_text_layer",
//            VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
//            VertexFormat.DrawMode.QUADS,
//            256,
//            RenderLayer.MultiPhaseParameters.builder()
//                    .program(TEXT_PROGRAM)
//                    .transparency(RenderLayer.Transparency.TRANSLUCENT_TRANSPARENCY)
//                    .depthTest(RenderLayer.DepthTest.ALWAYS_DEPTH_TEST)
//                    .cull(DISABLE_CULLING)
//                    .lightmap(RenderLayer.Lightmap.ENABLE_LIGHTMAP)
//                    .build(true)
//    );
}
