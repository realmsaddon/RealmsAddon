package xyz.telosaddon.yuno.utils;

import net.minecraft.client.render.entity.ArmorStandEntityRenderer;
import net.minecraft.entity.decoration.ArmorStandEntity;

public class MathUtil {
    public static float clamp(float val, float min, float max) {
        return val < min ? min : Math.min(val, max);
    }

}
