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

    private final TunementProfileManager tunementProfileManager;
    private final Map<UUID, StatProfile> statProfiles = new HashMap<>();
    private final double bHealth;
    private final double bSpeed;
    private final double bStarvation;
    private final double bLuck;
    private final double pMelee;
    private final double pMagic;
    private final double pRange;
    private final double availableMana;
    private final double maxMana;
    private final double bMelee;
    private final double bMagic;
    private final double bRange;
    private final double baseDefense;
    private final double baseFireDefense;
    private final double baseExplosionDefense;
    private final double baseProjectileDefense;
    private final double baseMagicDefense;
    private final double baseToughness;

    public StatProfileManager(ServerPlugin1201 plugin) {
        BaseStatsConfig baseStatsConfig = plugin.getBaseStatsConfig();
        tunementProfileManager = plugin.getTunementProfileManager();

        bHealth = baseStatsConfig.getDouble("baseHealth");
        bSpeed = baseStatsConfig.getDouble("baseSpeedPoints");
        bStarvation = baseStatsConfig.getDouble("starvationRate");
        bLuck = baseStatsConfig.getDouble("baseLuck");


        pMelee = baseStatsConfig.getDouble("meleeDamage");
        pMagic = baseStatsConfig.getDouble("magicDamage");
        pRange = baseStatsConfig.getDouble("rangeDamage");
        availableMana = baseStatsConfig.getDouble("availableMana");
        maxMana = baseStatsConfig.getDouble("maxMana");
        bMelee = baseStatsConfig.getDouble("baseMelee");
        bMagic = baseStatsConfig.getDouble("baseMagic");
        bRange = baseStatsConfig.getDouble("baseRange");
        baseDefense = baseStatsConfig.getDouble("baseDefense");
        baseFireDefense = baseStatsConfig.getDouble("baseFireDefense");
        baseExplosionDefense = baseStatsConfig.getDouble("baseExplosionDefense");
        baseProjectileDefense = baseStatsConfig.getDouble("baseProjectileDefense");
        baseMagicDefense = baseStatsConfig.getDouble("baseMagicDefense");
        baseToughness = baseStatsConfig.getDouble("baseToughness");
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
        double health = bHealth + tunementStats.getHealth();
        double defense = baseDefense + tunementStats.getDefense();
        double speed = bSpeed + tunementStats.getSpeed();
        double mana = maxMana + tunementStats.getMana();
        double bMelee = this.bMelee + tunementStats.getMelee();
        double bMagic = this.bMagic + tunementStats.getMagic();
        double bRange = this.bRange + tunementStats.getRange();



        Stats stats = new Stats(player, health, speed, bStarvation, bLuck, availableMana, mana);
        DamageStats damageStats = new DamageStats(bMelee, bMagic, bRange, pMelee, pMagic, pRange);
        DefenseStats defenseStats = new DefenseStats(player, defense, baseFireDefense, baseExplosionDefense, baseProjectileDefense, baseMagicDefense, baseToughness, 0);
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
