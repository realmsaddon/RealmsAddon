package xyz.telosaddon.yuno.utils.data;

import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

// credit torilhosaddon for enum object model: https://github.com/Torilhos/TorilhosAddon/blob/main/src/client/java/mdsol/torilhosaddon/feature/model/BossData.java
public enum BossData {
    ANUBIS(
            "Anubis",
            new BlockPos(458, 204, -467)
    ),
    ASTAROTH(
            "Astaroth",
            new BlockPos(250, 217, 60)
    ),
    CHUNGUS(
            "Chungus",
            new BlockPos(61, 256, -490)
    ),
    FREDDY(
            "Freddy",
            new BlockPos(-136, 200, 653)
    ),
    GLUMI(
            "Glumi",
            new BlockPos(339, 222, 552)
    ),
    ILLARIUS(
            "Illarius",
            new BlockPos(478, 200, -45)
    ),
    LOTIL(
            "Lotil",
            new BlockPos(-138, 214, 17)
    ),
    OOZUL(
            "Oozul",
            new BlockPos(-424, 195, 91)
    ),
    TIDOL(
            "Tidol",
            new BlockPos(-543, 190, 364)
    ),
    VALUS(
            "Valus",
            new BlockPos(35, 210, 307)
    ),
    HOLLOWBANE(
            "Hollowbane",
            new BlockPos(232, 150, 696)
    ),
    CLAUS(
            "Claus",
            new BlockPos(10, 212, -121)
    ),
    ONYX(
            "Raphael",
            new BlockPos(-15, 243, 88)
    );

    public final String label;
    public final BlockPos spawnPosition;
    //public final CustomModelDataComponent customModelDataComponent;

    BossData(String label,  BlockPos spawnPosition) {
        this.label = label;
        this.spawnPosition = spawnPosition;
        //this.customModelDataComponent = customModelDataComponent;
    }

    public static Optional<BossData> fromString(@NotNull String name) {
        try {
            return Optional.of(valueOf(name.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
