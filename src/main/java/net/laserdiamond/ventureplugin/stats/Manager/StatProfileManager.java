package net.laserdiamond.ventureplugin.stats.Manager;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.enchants.Components.EnchantStats;
import net.laserdiamond.ventureplugin.items.armor.trims.Components.ArmorTrimMaterialStats;
import net.laserdiamond.ventureplugin.items.armor.trims.Components.ArmorTrimPatternStats;
import net.laserdiamond.ventureplugin.items.armor.trims.Manager.ArmorTrimStats;
import net.laserdiamond.ventureplugin.stats.Components.*;
import net.laserdiamond.ventureplugin.stats.Config.BaseStatsConfig;
import net.laserdiamond.ventureplugin.tunement.Components.TunementProfile;
import net.laserdiamond.ventureplugin.tunement.Components.TunementStats;
import net.laserdiamond.ventureplugin.tunement.Manager.TunementProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatProfileManager {

    private final TunementProfileManager tunementProfileManager;
    private final Map<UUID, StatProfile> statProfiles = new HashMap<>();
    private final double baseHealth;
    private final double bSpeed;
    private final double bStarvation;
    private final double bLuck;
    private final double percentMelee;
    private final double percentMagic;
    private final double percentRange;
    private final double availableMana;
    private final double maxMana;
    private final double baseMelee;
    private final double baseMagic;
    private final double baseRange;
    private final double baseDefense;
    private final double baseFireDefense;
    private final double baseExplosionDefense;
    private final double baseProjectileDefense;
    private final double baseMagicDefense;
    private final double baseToughness;

    public StatProfileManager(VenturePlugin plugin) {
        BaseStatsConfig baseStatsConfig = plugin.getBaseStatsConfig();
        tunementProfileManager = plugin.getTunementProfileManager();

        baseHealth = baseStatsConfig.getDouble("baseHealth");
        bSpeed = baseStatsConfig.getDouble("baseSpeedPoints");
        bStarvation = baseStatsConfig.getDouble("starvationRate");
        bLuck = baseStatsConfig.getDouble("baseLuck");


        percentMelee = baseStatsConfig.getDouble("meleeDamage");
        percentMagic = baseStatsConfig.getDouble("magicDamage");
        percentRange = baseStatsConfig.getDouble("rangeDamage");
        availableMana = baseStatsConfig.getDouble("availableMana");
        maxMana = baseStatsConfig.getDouble("maxMana");
        baseMelee = baseStatsConfig.getDouble("baseMelee");
        baseMagic = baseStatsConfig.getDouble("baseMagic");
        baseRange = baseStatsConfig.getDouble("baseRange");
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
        double finalBaseHealth = baseHealth + tunementStats.getHealth();
        double finalBaseDefense = baseDefense + tunementStats.getDefense();
        double finalBaseSpeed = bSpeed + tunementStats.getSpeed();
        double finalBaseMana = maxMana + tunementStats.getMana();
        double finalBaseMelee = this.baseMelee + tunementStats.getMelee();
        double finalBaseMagic = this.baseMagic + tunementStats.getMagic();
        double finalBaseRange = this.baseRange + tunementStats.getRange();



        Stats stats = new Stats(player, finalBaseHealth, finalBaseSpeed, bStarvation, bLuck, availableMana, finalBaseMana);
        ArmorStats armorStats = new ArmorStats(0,0,0,0,0,0,0,0,0,0,0,0,0);
        DamageStats damageStats = new DamageStats(finalBaseMelee, finalBaseMagic, finalBaseRange, percentMelee, percentMagic, percentRange,0);
        DefenseStats defenseStats = new DefenseStats(player, finalBaseDefense, baseFireDefense, baseExplosionDefense, baseProjectileDefense, baseMagicDefense, baseToughness, 0);
        LootStats lootStats = new LootStats(0,0,0,0,0);
        EnchantStats enchantStats = new EnchantStats(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);

        ArmorTrimMaterialStats armorTrimMaterialStats = new ArmorTrimMaterialStats(0,0,0,0,0,0,0,0,0,0,0);
        ArmorTrimPatternStats armorTrimPatternStats = new ArmorTrimPatternStats(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
        ArmorTrimStats armorTrimStats = new ArmorTrimStats(armorTrimMaterialStats, armorTrimPatternStats);

        StatProfile statProfile = new StatProfile(stats, armorStats, damageStats, defenseStats, lootStats, enchantStats, armorTrimStats, tunementProfile);
        statProfiles.put(player.getUniqueId(), statProfile);

        return statProfile;
    }

    public StatProfile getStatProfile(UUID uuid)
    {
        return statProfiles.get(uuid);
    }

}
