package net.laserdiamond.serverplugin1201.stats.Manager;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.enchants.Components.EnchantStats;
import net.laserdiamond.serverplugin1201.items.armor.Trims.Components.ArmorTrimMaterialStats;
import net.laserdiamond.serverplugin1201.items.armor.Trims.Components.ArmorTrimPatternStats;
import net.laserdiamond.serverplugin1201.items.armor.Trims.Manager.ArmorTrimStats;
import net.laserdiamond.serverplugin1201.stats.Components.*;
import net.laserdiamond.serverplugin1201.stats.Config.BaseStatsConfig;
import net.laserdiamond.serverplugin1201.tunement.Components.TunementProfile;
import net.laserdiamond.serverplugin1201.tunement.Components.TunementStats;
import net.laserdiamond.serverplugin1201.tunement.Manager.TunementProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatProfileManager {

    private final ServerPlugin1201 PLUGIN;
    private final BaseStatsConfig BASE_STATS_CONFIG;
    private final TunementProfileManager TUNEMENT_PROFILE_MANAGER;
    private final Map<UUID, StatProfile> STAT_PROFILES = new HashMap<>();

    private final double[] BASE_STATS_ARRAY = new double[18];

    public StatProfileManager(ServerPlugin1201 plugin) {
        this.PLUGIN = plugin;
        BASE_STATS_CONFIG = plugin.getBaseStatsConfig();
        TUNEMENT_PROFILE_MANAGER = plugin.getTunementProfileManager();

        BASE_STATS_ARRAY[0] = BASE_STATS_CONFIG.getDouble("baseHealth");
        BASE_STATS_ARRAY[1] = BASE_STATS_CONFIG.getDouble("baseSpeedPoints");
        BASE_STATS_ARRAY[2] = BASE_STATS_CONFIG.getDouble("starvationRate");
        BASE_STATS_ARRAY[3] = BASE_STATS_CONFIG.getDouble("baseLuck");


        BASE_STATS_ARRAY[4] = BASE_STATS_CONFIG.getDouble("meleeDamage");
        BASE_STATS_ARRAY[5] = BASE_STATS_CONFIG.getDouble("magicDamage");
        BASE_STATS_ARRAY[6] = BASE_STATS_CONFIG.getDouble("rangeDamage");
        BASE_STATS_ARRAY[7] = BASE_STATS_CONFIG.getDouble("availableMana");
        BASE_STATS_ARRAY[8] = BASE_STATS_CONFIG.getDouble("maxMana");
        BASE_STATS_ARRAY[9] = BASE_STATS_CONFIG.getDouble("baseMelee");
        BASE_STATS_ARRAY[10] = BASE_STATS_CONFIG.getDouble("baseMagic");
        BASE_STATS_ARRAY[11] = BASE_STATS_CONFIG.getDouble("baseRange");
        BASE_STATS_ARRAY[12] = BASE_STATS_CONFIG.getDouble("baseDefense");
        BASE_STATS_ARRAY[13] = BASE_STATS_CONFIG.getDouble("baseFireDefense");
        BASE_STATS_ARRAY[14] = BASE_STATS_CONFIG.getDouble("baseExplosionDefense");
        BASE_STATS_ARRAY[15] = BASE_STATS_CONFIG.getDouble("baseProjectileDefense");
        BASE_STATS_ARRAY[16] = BASE_STATS_CONFIG.getDouble("baseMagicDefense");
        BASE_STATS_ARRAY[17] = BASE_STATS_CONFIG.getDouble("baseToughness");
    }

    public StatProfile createNewStatProfile(Player player)
    {
        TunementProfile tunementProfile = TUNEMENT_PROFILE_MANAGER.getPlayerProfile(player.getUniqueId());
        if (tunementProfile == null) // Check if profile is null (doesn't have profile)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Player " + player.getName() + " does not have a tunement profile");
            tunementProfile = TUNEMENT_PROFILE_MANAGER.createNewProfile(player);
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Creating new profile for " + player.getName());
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Player UUID: " + player.getUniqueId());
        } else // Player has profile
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Player " + player.getName() + " already has a tunement profile");
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Player UUID: " + player.getUniqueId());
        }

        TunementStats tunementStats = tunementProfile.tunementStats();
        double health = BASE_STATS_ARRAY[0] + tunementStats.getHealth();
        double defense = BASE_STATS_ARRAY[12] + tunementStats.getDefense();
        double speed = BASE_STATS_ARRAY[1] + tunementStats.getSpeed();
        double mana = BASE_STATS_ARRAY[8] + tunementStats.getMana();
        double bMelee = BASE_STATS_ARRAY[9] + tunementStats.getMelee();
        double bMagic = BASE_STATS_ARRAY[10] + tunementStats.getMagic();
        double bRange = BASE_STATS_ARRAY[11] + tunementStats.getRange();

        Stats stats = new Stats(player, health, speed, BASE_STATS_ARRAY[2], BASE_STATS_ARRAY[3], BASE_STATS_ARRAY[7], mana);
        DamageStats damageStats = new DamageStats(bMelee, bMagic, bRange, BASE_STATS_ARRAY[4], BASE_STATS_ARRAY[5], BASE_STATS_ARRAY[6]);
        DefenseStats defenseStats = new DefenseStats(player, defense, BASE_STATS_ARRAY[13], BASE_STATS_ARRAY[14], BASE_STATS_ARRAY[15], BASE_STATS_ARRAY[16], BASE_STATS_ARRAY[17],0);
        LootStats lootStats = new LootStats(0,0,0,0);
        EnchantStats enchantStats = new EnchantStats(0,0,0,0,0,0,0,0,0,0,0);

        ArmorTrimMaterialStats armorTrimMaterialStats = new ArmorTrimMaterialStats(0,0,0,0,0,0,0,0,0,0,0);
        ArmorTrimPatternStats armorTrimPatternStats = new ArmorTrimPatternStats(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
        ArmorTrimStats armorTrimStats = new ArmorTrimStats(armorTrimMaterialStats, armorTrimPatternStats);

        StatProfile statProfile = new StatProfile(stats, damageStats, defenseStats, lootStats, enchantStats, armorTrimStats, tunementProfile);
        STAT_PROFILES.put(player.getUniqueId(), statProfile);

        return statProfile;
    }

    public StatProfile getStatProfile(UUID uuid)
    {
        return STAT_PROFILES.get(uuid);
    }

}
