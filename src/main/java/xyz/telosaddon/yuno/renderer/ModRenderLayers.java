package xyz.telosaddon.yuno.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import net.minecraft.util.TriState;
import net.minecraft.util.Util;

import java.util.function.Function;

import static net.minecraft.client.render.RenderPhase.ARMOR_CUTOUT_NO_CULL_PROGRAM;

public class ModRenderLayers {
    public static final Function<Identifier, RenderLayer> PLAYER_TRANSLUCENT_NO_CULL = Util.memoize(texture -> {
        var params = RenderLayer.MultiPhaseParameters.builder()
                .program(RenderPhase.ENTITY_TRANSLUCENT_PROGRAM)
                .texture(new RenderPhase.Texture(texture, TriState.FALSE, false))
                .transparency(RenderLayer.TRANSLUCENT_TRANSPARENCY)
                .writeMaskState(new RenderPhase.WriteMaskState(true, false))
                .lightmap(RenderLayer.ENABLE_LIGHTMAP)
                .overlay(RenderLayer.ENABLE_OVERLAY_COLOR)
                .build(true);
        return RenderLayer.of(
                "player_translucent_no_cull", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
                VertexFormat.DrawMode.QUADS, 262144, true, true, params
        );
    });

}
