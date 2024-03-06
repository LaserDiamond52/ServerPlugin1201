package net.laserdiamond.serverplugin1201.stats.Manager;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.enchants.Components.EnchantStats;
import net.laserdiamond.serverplugin1201.items.armor.Trims.Components.ArmorTrimMaterialStats;
import net.laserdiamond.serverplugin1201.items.armor.Trims.Components.ArmorTrimPatternStats;
import net.laserdiamond.serverplugin1201.items.armor.Trims.Manager.ArmorTrimStats;
import net.laserdiamond.serverplugin1201.stats.Components.StatProfile;
import net.laserdiamond.serverplugin1201.stats.Components.Stats;
import net.laserdiamond.serverplugin1201.stats.Config.BaseStatsConfig;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatProfileManager {

    private ServerPlugin1201 plugin;
    private BaseStatsConfig baseStatsConfig;
    private Map<UUID, StatProfile> statProfiles = new HashMap<>();

    private final double[] baseStatsArray = new double[17];

    public StatProfileManager(ServerPlugin1201 plugin) {
        this.plugin = plugin;
        baseStatsConfig = plugin.getBaseStatsConfig();

        baseStatsArray[0] = baseStatsConfig.getDouble("baseHealth");
        baseStatsArray[1] = baseStatsConfig.getDouble("baseSpeed");
        baseStatsArray[2] = baseStatsConfig.getDouble("starvationRate");

        baseStatsArray[3] = baseStatsConfig.getDouble("meleeDamage");
        baseStatsArray[4] = baseStatsConfig.getDouble("magicDamage");
        baseStatsArray[5] = baseStatsConfig.getDouble("rangeDamage");
        baseStatsArray[6] = baseStatsConfig.getDouble("availableMana");
        baseStatsArray[7] = baseStatsConfig.getDouble("maxMana");
        baseStatsArray[8] = baseStatsConfig.getDouble("baseMelee");
        baseStatsArray[9] = baseStatsConfig.getDouble("baseMagic");
        baseStatsArray[10] = baseStatsConfig.getDouble("baseRange");
        baseStatsArray[11] = baseStatsConfig.getDouble("baseDefense");
        baseStatsArray[12] = baseStatsConfig.getDouble("baseFireDefense");
        baseStatsArray[13] = baseStatsConfig.getDouble("baseExplosionDefense");
        baseStatsArray[14] = baseStatsConfig.getDouble("baseProjectileDefense");
        baseStatsArray[15] = baseStatsConfig.getDouble("baseMagicDefense");
        baseStatsArray[16]= baseStatsConfig.getDouble("baseToughness");

    }

    public StatProfile createNewStatProfile(Player player)
    {

        Stats stats = new Stats(0,baseStatsArray[3],baseStatsArray[4], baseStatsArray[5], baseStatsArray[6], baseStatsArray[7], baseStatsArray[8], baseStatsArray[9], baseStatsArray[10], baseStatsArray[11], baseStatsArray[12], baseStatsArray[13], baseStatsArray[14], baseStatsArray[15],baseStatsArray[16],0);
        EnchantStats enchantStats = new EnchantStats(0,0,0,0,0,0,0,0,0,0,0);

        ArmorTrimMaterialStats armorTrimMaterialStats = new ArmorTrimMaterialStats(0,0,0,0,0,0,0,0,0,0,0);
        ArmorTrimPatternStats armorTrimPatternStats = new ArmorTrimPatternStats(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
        ArmorTrimStats armorTrimStats = new ArmorTrimStats(armorTrimMaterialStats, armorTrimPatternStats);

        StatProfile statProfile = new StatProfile(stats, enchantStats, armorTrimStats);
        statProfiles.put(player.getUniqueId(), statProfile);

        AttributeInstance healthInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        healthInstance.setBaseValue(baseStatsArray[0]);

        AttributeInstance speedInstance = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        speedInstance.setBaseValue(baseStatsArray[1]);

        player.setStarvationRate((int) baseStatsArray[2]);

        return statProfile;
    }

    public StatProfile getStatProfile(UUID uuid)
    {
        return statProfiles.get(uuid);
    }

}
