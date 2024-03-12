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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatProfileManager {

    private final ServerPlugin1201 plugin;
    private final BaseStatsConfig baseStatsConfig;
    private final TunementProfileManager tunementProfileManager;
    private final Map<UUID, StatProfile> statProfiles = new HashMap<>();

    private final double[] baseStatsArray = new double[18];

    public StatProfileManager(ServerPlugin1201 plugin) {
        this.plugin = plugin;
        baseStatsConfig = plugin.getBaseStatsConfig();
        tunementProfileManager = plugin.getTunementProfileManager();

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
        TunementProfile tunementProfile = tunementProfileManager.getPlayerProfile(player.getUniqueId());
        if (tunementProfile == null) // Check if profile is null (doesn't have profile)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Player " + player.getName() + " does not have a tunement profile");
            tunementProfile = tunementProfileManager.createNewProfile(player);
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Creating new profile for " + player.getName());
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Player UUID: " + player.getUniqueId());
        } else // Player has profile
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Player " + player.getName() + " already has a tunement profile");
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Player UUID: " + player.getUniqueId());
        }

        TunementStats tunementStats = tunementProfile.tunementStats();
        double health = baseStatsArray[0] + tunementStats.getHealth();
        double defense = baseStatsArray[12] + tunementStats.getDefense();
        double speed = baseStatsArray[1] + tunementStats.getSpeed();
        double mana = baseStatsArray[8] + tunementStats.getMana();
        double bMelee = baseStatsArray[9] + tunementStats.getMelee();
        double bMagic = baseStatsArray[10] + tunementStats.getMagic();
        double bRange = baseStatsArray[11] + tunementStats.getRange();

        Stats stats = new Stats(player, health, speed, baseStatsArray[2], baseStatsArray[3], baseStatsArray[7], mana);
        DamageStats damageStats = new DamageStats(bMelee, bMagic, bRange, baseStatsArray[4], baseStatsArray[5], baseStatsArray[6]);
        DefenseStats defenseStats = new DefenseStats(player, defense, baseStatsArray[13], baseStatsArray[14], baseStatsArray[15], baseStatsArray[16], baseStatsArray[17],0);
        LootStats lootStats = new LootStats(0,0,0,0);
        EnchantStats enchantStats = new EnchantStats(0,0,0,0,0,0,0,0,0,0,0);

        ArmorTrimMaterialStats armorTrimMaterialStats = new ArmorTrimMaterialStats(0,0,0,0,0,0,0,0,0,0,0);
        ArmorTrimPatternStats armorTrimPatternStats = new ArmorTrimPatternStats(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
        ArmorTrimStats armorTrimStats = new ArmorTrimStats(armorTrimMaterialStats, armorTrimPatternStats);

        StatProfile statProfile = new StatProfile(stats, damageStats, defenseStats, lootStats, enchantStats, armorTrimStats, tunementProfile);
        statProfiles.put(player.getUniqueId(), statProfile);

        return statProfile;
    }

    public StatProfile getStatProfile(UUID uuid)
    {
        return statProfiles.get(uuid);
    }

}
