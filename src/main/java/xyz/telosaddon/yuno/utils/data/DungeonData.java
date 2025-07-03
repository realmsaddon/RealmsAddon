package xyz.telosaddon.yuno.utils.data;

import java.util.HashMap;
import java.util.Map;

public enum DungeonData {

    PIRATE_COVE("Pirate Cove", DungeonType.LOWLANDS, BossData.JONES),
    THORNWOOD_WARGROVE("Thornwood Wargrove", DungeonType.LOWLANDS, BossData.ZHUM),
    GOBLIN_LAIR("Goblin Lair", DungeonType.LOWLANDS, BossData.DRAYRUK),
    DESERT_TEMPLE("Desert Temple", DungeonType.LOWLANDS, BossData.MIRAJ),
    TOMB_OF_SHADOWS("Tomb of Shadows", DungeonType.LOWLANDS, BossData.KHUFU),
    SAKURA_SHRINE("Sakura Shrine", DungeonType.LOWLANDS, BossData.CHOJI),
    SECLUDED_WOODLAND("Secluded Woodland", DungeonType.LOWLANDS, BossData.FLORA),

    ABYSS_OF_DEMONS("Abyss of Demons", DungeonType.CENTER, BossData.MALFAS),
    UNDEAD_LAIR("Undead Lair", DungeonType.CENTER, BossData.HEPTAVIUS),
    ICE_CAVE("Ice Cave", DungeonType.CENTER, BossData.ARCTIC_COLOSSUS),
    DWARVEN_FROSTKEEP("Dwarven Frostkeep", DungeonType.CENTER, BossData.FROSTGAZE),
    TREASURE_CAVE("Treasure Cave", DungeonType.CENTER, BossData.MAGNUS),
    DEPTHS_OF_PURGATORY("Depths of Purgatory", DungeonType.CENTER, BossData.PYRO),
    FROZEN_RUINS("Frozen Ruins", DungeonType.CENTER, BossData.THALOR),
    KOBOLDS_DEN("Kobold's Den", DungeonType.CENTER, BossData.ASHENCLAW),
    CORVUS_CRYPT("Corvus Crypt", DungeonType.CENTER, BossData.CORVACK),

    OMNIPOTENTS_CITADEL("Omnipotent's Citadel", DungeonType.BOSS, BossData.OMNIPOTENT),
    FUNGAL_CAVERN("Fungal Cavern", DungeonType.BOSS, BossData.PRISMARA),
    CORSAIRS_CONDUCTORIUM("Corsair's Conductorium", DungeonType.BOSS, BossData.THALASSAR),
    FREDDYS_PIZZERIA("Freddy's Pizzeria", DungeonType.BOSS, BossData.GOLDEN_FREDDY),
    CHRONOS("Chronos", DungeonType.BOSS, BossData.CHRONOS),
    ANUBIS_LAIR("Anubis Lair", DungeonType.BOSS, BossData.KURVAROS),
    CULTISTS_HIDEOUT("Cultist's Hideout", DungeonType.BOSS, BossData.SILEX),
    ILLARIUS_HIDEOUT("Illarius' Hideout", DungeonType.BOSS, BossData.LOA),
    VOID("Void", DungeonType.BOSS, BossData.SHADOWFLARE),
    RAPHS_CASTLE("Raphael's Castle", DungeonType.BOSS, BossData.DARK_CHAMPIONS),

    RAPHS_CHAMBER("Raphael's Chamber", DungeonType.ENDGAME, BossData.RAPHAEL),
    TENEBRIS("Tenebris", DungeonType.ENDGAME, BossData.VOIDED_OMNIPOTENT),
    SHATTERED_KINGDOM("Shattered Kingdom", DungeonType.ENDGAME, BossData.OPHANIM),
    DREADWOOD_THICKET("Dreadwood Thicket", DungeonType.ENDGAME, BossData.SYLVARIS),
    CELESTIALS_PROVINCE("Celestial's Province", DungeonType.ENDGAME, BossData.SERAPHIM),
    SERAPHS_DOMAIN("Seraph's Domain", DungeonType.ENDGAME, BossData.TRUE_SERAPH),
    DAWN_OF_CREATION("Dawn of Creation", DungeonType.BOSS, BossData.TRUE_OPHAN);

    public final String areaName;
    public final DungeonType dungeonType;
    public final BossData finalBoss;

    DungeonData(String areaName, DungeonType dungeonType, BossData finalBossName) {
        this.areaName = areaName;
        this.dungeonType = dungeonType;
        this.finalBoss = finalBossName;
    };

    private static final Map<String, DungeonData> areaNameMap;
    static{
        areaNameMap = new HashMap<>();
        for (DungeonData v : DungeonData.values()) {
            areaNameMap.put(v.areaName, v);
        }
    }

    public static DungeonData findByKey(String areaName){
        return areaNameMap.get(areaName);
    }

}
