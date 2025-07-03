package xyz.telosaddon.yuno.utils.data;

import java.util.HashMap;
import java.util.Map;

public enum DungeonData {

    PIRATE_COVE(
            "Pirate Cove",
            DungeonType.LOWLANDS,
            "Jones"
    ),
    THORNWOOD_WARGROVE(
            "Thornwood Wargrove",
            DungeonType.LOWLANDS,
            "Zhum"
    ),
    GOBLIN_LAIR(
            "Goblin Lair",
            DungeonType.LOWLANDS,
            "Drayruk"
    ),
    DESERT_TEMPLE(
            "Desert Temple",
            DungeonType.LOWLANDS,
            "Miraj"
    ),
    TOMB_OF_SHADOWS(
            "Tomb of Shadows",
            DungeonType.LOWLANDS,
            "Khufu"
    ),
    SAKURA_SHRINE(
            "Sakura Shrine",
            DungeonType.LOWLANDS,
            "Choji"
    ),
    SECLUDED_WOODLAND(
            "Secluded Woodland",
            DungeonType.LOWLANDS,
            "Flora"
    ),



    ABYSS_OF_DEMONS(
            "Abyss of Demons",
            DungeonType.CENTER,
            "Malfas"
    ),
    UNDEAD_LAIR(
            "Undead Lair",
            DungeonType.CENTER,
            "Heptavius"
    ),
    ICE_CAVE(
            "Ice Cave",
            DungeonType.CENTER,
            "Arctic Colossus"
    ),
    DWARVEN_FROSTKEEP(
            "Dwarven Frostkeep",
            DungeonType.CENTER,
            "Frostgaze"
    ),
    TREASURE_CAVE(
            "Treasure Cave",
            DungeonType.CENTER,
            "Magnus"
    ),
    DEPTHS_OF_PURGATORY(
            "Depths of Purgatory",
            DungeonType.CENTER,
            "Pyro"
    ),
    FROZEN_RUINS(
            "Frozen Ruins",
            DungeonType.CENTER,
            "Thalor"
    ),
    KOBOLDS_DEN(
            "Kobold's Den",
            DungeonType.CENTER,
            "Ashenclaw"
    ),
    CORVUS_CRYPT(
            "Corvus Crypt",
            DungeonType.CENTER,
            "Corvack"
    ),



    OMNIPOTENTS_CITADEL(
            "Omnipotent's Citadel",
            DungeonType.BOSS,
            "Omnipotent"
    ),
    FUNGAL_CAVERN(
            "Fungal Cavern",
            DungeonType.BOSS,
            "Prismara"
    ),
    CORSAIRS_CONDUCTORIUM(
            "Corsair's Conductorium",
            DungeonType.BOSS,
            "Thalassar"
    ),
    FREDDYS_PIZZERIA(
            "Freddy's Pizzeria",
            DungeonType.BOSS,
            "Golden Freddy"
    ),
    CHRONOS(
            "Chronos",
            DungeonType.BOSS,
            "Chronos"
    ),
    ANUBIS_LAIR(
            "Anubis Lair",
            DungeonType.BOSS,
            "Kurvaros"
    ),
    CULTISTS_HIDEOUT(
            "Cultist's Hideout",
            DungeonType.BOSS,
            "Silex"
    ),
    ILLARIUS_HIDEOUT(
            "Illarius' Hideout",
            DungeonType.BOSS,
            "Loa"
    ),
    VOID(
            "Void",
            DungeonType.BOSS,
            "Shadowflare"
    ),
    RAPHS_CASTLE(
            "Raphael's Castle",
            DungeonType.BOSS,
            "Raphael"
    ),



    RAPHS_CHAMBER(
            "Raphael's Chamber",
            DungeonType.ENDGAME,
            "Raphael"
    ),
    TENEBRIS(
            "Tenebris",
            DungeonType.ENDGAME,
            "Voided Omnipotent"
    ),
    SHATTERED_KINGDOM(
            "Shattered Kingdom",
            DungeonType.ENDGAME,
            "Ophanim"
    ),
    DREADWOOD_THICKET(
            "Dreadwood Thicket",
            DungeonType.ENDGAME,
            "Sylvaris"
    ),
    CELESTIALS_PROVINCE(
            "Celestial's Province",
            DungeonType.ENDGAME,
            "Seraphim"
    ),
    SERAPHS_DOMAIN(
            "Seraph's Domain",
            DungeonType.ENDGAME,
            "True Seraph"
    ),
    DAWN_OF_CREATION(
            "Dawn of Creation",
            DungeonType.BOSS,
            "True Ophan"
    );

    public final String areaName;
    public final DungeonType dungeonType;
    public final String finalBossName;

    DungeonData(String areaName, DungeonType dungeonType, String finalBossName) {
        this.areaName = areaName;
        this.dungeonType = dungeonType;
        //this.customModelDataComponent = customModelDataComponent;
        this.finalBossName = finalBossName;
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
