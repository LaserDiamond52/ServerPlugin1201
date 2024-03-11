package net.laserdiamond.serverplugin1201.stats.Manager;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.enchants.Components.EnchantStats;
import net.laserdiamond.serverplugin1201.items.armor.Trims.Components.ArmorTrimMaterialStats;
import net.laserdiamond.serverplugin1201.items.armor.Trims.Components.ArmorTrimPatternStats;
import net.laserdiamond.serverplugin1201.items.armor.Trims.Manager.ArmorTrimStats;
import net.laserdiamond.serverplugin1201.stats.Components.*;
import net.laserdiamond.serverplugin1201.stats.Config.BaseStatsConfig;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatProfileManager {

    private final ServerPlugin1201 plugin;
    private final BaseStatsConfig baseStatsConfig;
    private final Map<UUID, StatProfile> statProfiles = new HashMap<>();

    private final double[] baseStatsArray = new double[18];

    public StatProfileManager(ServerPlugin1201 plugin) {
        this.plugin = plugin;
        baseStatsConfig = plugin.getBaseStatsConfig();

        baseStatsArray[0] = baseStatsConfig.getDouble("baseHealth");
        baseStatsArray[1] = baseStatsConfig.getDouble("baseSpeedPoints");
        baseStatsArray[2] = baseStatsConfig.getDouble("starvationRate");
        baseStatsArray[3] = baseStatsConfig.getDouble("baseLuck");


        baseStatsArray[4] = baseStatsConfig.getDouble("meleeDamage");
        baseStatsArray[5] = baseStatsConfig.getDouble("magicDamage");
        baseStatsArray[6] = baseStatsConfig.getDouble("rangeDamage");
        baseStatsArray[7] = baseStatsConfig.getDouble("availableMana");
        baseStatsArray[8] = baseStatsConfig.getDouble("maxMana");
        baseStatsArray[9] = baseStatsConfig.getDouble("baseMelee");
        baseStatsArray[10] = baseStatsConfig.getDouble("baseMagic");
        baseStatsArray[11] = baseStatsConfig.getDouble("baseRange");
        baseStatsArray[12] = baseStatsConfig.getDouble("baseDefense");
        baseStatsArray[13] = baseStatsConfig.getDouble("baseFireDefense");
        baseStatsArray[14] = baseStatsConfig.getDouble("baseExplosionDefense");
        baseStatsArray[15] = baseStatsConfig.getDouble("baseProjectileDefense");
        baseStatsArray[16] = baseStatsConfig.getDouble("baseMagicDefense");
        baseStatsArray[17] = baseStatsConfig.getDouble("baseToughness");
    }

    public StatProfile createNewStatProfile(Player player)
    {
        // player, health, speed, starve, luck, pMelee, pMagic, pRange, aMana, mMana, bMelee, bMagic, bRange, def, fireDef, explosionDef, projDef, magicDef, toughness, fortitude
        //Stats stats = new Stats(player, baseStatsArray[0], baseStatsArray[1], baseStatsArray[2], baseStatsArray[3], baseStatsArray[4], baseStatsArray[5], baseStatsArray[6], baseStatsArray[7], baseStatsArray[8], baseStatsArray[9], baseStatsArray[10], baseStatsArray[11], baseStatsArray[12], baseStatsArray[13], baseStatsArray[14], baseStatsArray[15], baseStatsArray[16], baseStatsArray[17], 0);

        Stats stats = new Stats(player, baseStatsArray[0], baseStatsArray[1], baseStatsArray[2], baseStatsArray[3], baseStatsArray[7], baseStatsArray[8]);
        DamageStats damageStats = new DamageStats(baseStatsArray[9], baseStatsArray[10], baseStatsArray[11], baseStatsArray[4], baseStatsArray[5], baseStatsArray[6]);
        DefenseStats defenseStats = new DefenseStats(player, baseStatsArray[12], baseStatsArray[13], baseStatsArray[14], baseStatsArray[15], baseStatsArray[16], baseStatsArray[17],0);
        LootStats lootStats = new LootStats(0,0,0,0);
        EnchantStats enchantStats = new EnchantStats(0,0,0,0,0,0,0,0,0,0,0);

        ArmorTrimMaterialStats armorTrimMaterialStats = new ArmorTrimMaterialStats(0,0,0,0,0,0,0,0,0,0,0);
        ArmorTrimPatternStats armorTrimPatternStats = new ArmorTrimPatternStats(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
        ArmorTrimStats armorTrimStats = new ArmorTrimStats(armorTrimMaterialStats, armorTrimPatternStats);

        StatProfile statProfile = new StatProfile(stats, damageStats, defenseStats, lootStats, enchantStats, armorTrimStats);
        statProfiles.put(player.getUniqueId(), statProfile);

        return statProfile;
    }

    public StatProfile getStatProfile(UUID uuid)
    {
        return statProfiles.get(uuid);
    }

}
