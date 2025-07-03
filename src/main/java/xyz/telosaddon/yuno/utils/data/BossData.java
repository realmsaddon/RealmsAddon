package xyz.telosaddon.yuno.utils.data;

import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// credit torilhosaddon for enum object model: https://github.com/Torilhos/TorilhosAddon/blob/main/src/client/java/mdsol/torilhosaddon/feature/model/BossData.java
public enum BossData {
    ANUBIS(
            "Anubis",
            new BlockPos(458, 204, -467),
            BossType.WORLD
    ),
    ASTAROTH(
            "Astaroth",
            new BlockPos(250, 217, 60),
            BossType.WORLD
    ),
    CHUNGUS(
            "Chungus",
            new BlockPos(61, 256, -490),
            BossType.WORLD
    ),
    FREDDY(
            "Freddy",
            new BlockPos(-136, 200, 653),
            BossType.WORLD
    ),
    GLUMI(
            "Glumi",
            new BlockPos(339, 222, 552),
            BossType.WORLD
    ),
    ILLARIUS(
            "Illarius",
            new BlockPos(478, 200, -45),
            BossType.WORLD
    ),
    LOTIL(
            "Lotil",
            new BlockPos(-138, 214, 17),
            BossType.WORLD
    ),
    OOZUL(
            "Oozul",
            new BlockPos(-424, 195, 91),
            BossType.WORLD
    ),
    TIDOL(
            "Tidol",
            new BlockPos(-543, 190, 364),
            BossType.WORLD
    ),
    VALUS(
            "Valus",
            new BlockPos(35, 210, 307),
            BossType.WORLD
    ),
    HOLLOWBANE(
            "Hollowbane",
            new BlockPos(232, 150, 696),
            BossType.WORLD
    ),
    CLAUS(
            "Claus",
            new BlockPos(10, 212, -121),
            BossType.WORLD
    ),
    JONES(
            "Jones",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    ZHUM(
            "Zhum",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    DRAYRUK(
            "Drayruk",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    MIRAJ(
            "Miraj",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    KHUFU(
            "Khufu",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    CHOJI(
            "Choji",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    FLORA(
            "Flora",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    MALFAS(
            "Malfas",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    HEPTAVIUS(
            "Heptavius",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    ARCTIC_COLOSSUS(
            "Arctic Colossus",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    FROSTGAZE(
            "Frostgaze",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    MAGNUS(
            "Magnus",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    PYRO(
            "Pyro",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    THALOR(
            "Thalor",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    ASHENCLAW(
            "Ashenclaw",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    CORVACK(
            "Corvack",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    OMNIPOTENT(
            "Omnipotent",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    PRISMARA(
            "Prismara",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    THALASSAR(
            "Thalassar",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    GOLDEN_FREDDY(
            "Golden Freddy",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    CHRONOS(
            "Chronos",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    KURVAROS(
            "Kurvaros",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    SILEX(
            "Silex",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    LOA(
            "Loa",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    SHADOWFLARE(
            "Shadowflare",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    DARK_CHAMPIONS(
            "Orion and Osiris",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    RAPHAEL(
            "Raphael",
            new BlockPos(-15, 243, 88),
            BossType.DUNGEON
    ),
    VOIDED_OMNIPOTENT(
            "Voided Omnipotent",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    VALERION(
            "Valerion",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    NEBULA(
            "Nebula",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    OPHANIM(
            "Ophanim",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    TRUE_OPHAN(
            "True Ophan",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    WARDEN(
            "Warden",
            new BlockPos(0, 0, 0),
            BossType.WORLD
    ),
    HERALD(
            "Herald",
            new BlockPos(0, 0, 0),
            BossType.WORLD
    ),
    REAPER(
            "Reaper",
            new BlockPos(0, 0, 0),
            BossType.WORLD
    ),
    DEFENDER(
            "Defender",
            new BlockPos(0, 0, 0),
            BossType.WORLD
    ),
    SYLVARIS(
            "Sylvaris",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    ASMODEUS(
            "Asmodeus",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    SERAPHIM(
            "Seraphim",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),
    TRUE_SERAPH(
            "True Seraph",
            new BlockPos(0, 0, 0),
            BossType.DUNGEON
    ),;

    private final String label;

    //TODO: port ts to a decorator
    private final @Nullable BlockPos spawnPosition;

    private final BossType bossType;
    //public final CustomModelDataComponent customModelDataComponent;

    BossData(String label, @Nullable BlockPos spawnPosition, BossType bossType) {
        this.label = label;
        this.spawnPosition = spawnPosition;
        //this.customModelDataComponent = customModelDataComponent;
        this.bossType = bossType;
    }
    public String getLabel() {
        return label;
    }

    private static final Map<String, BossData> bossDataMap;
    static{
        bossDataMap = new HashMap<>();
        for (BossData v : BossData.values()) {
            bossDataMap.put(v.label, v);
        }
    }
    public static Optional<BossData> findByKey(String areaName){
        BossData foundData = bossDataMap.get(areaName);

        if (foundData == null) return Optional.empty();
        return Optional.of(bossDataMap.get(areaName));
    }

    public Optional<BlockPos> getPosition() {
        return Optional.ofNullable(spawnPosition);
    }

    public static Optional<BossData> fromString(@NotNull String name) {
        try {
            return Optional.of(valueOf(name.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
