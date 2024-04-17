package net.laserdiamond.ventureplugin.stats.Manager;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.enchants.Components.EnchantStats;
import net.laserdiamond.ventureplugin.items.armor.trims.Components.ArmorTrimMaterialStats;
import net.laserdiamond.ventureplugin.items.armor.trims.Components.ArmorTrimPatternStats;
import net.laserdiamond.ventureplugin.items.armor.trims.Manager.ArmorTrimStats;
import net.laserdiamond.ventureplugin.skills.Components.SkillsProfile;
import net.laserdiamond.ventureplugin.skills.Manager.SkillsProfileManager;
import net.laserdiamond.ventureplugin.stats.Components.*;
import net.laserdiamond.ventureplugin.tuning.Components.TuningPoints;
import net.laserdiamond.ventureplugin.tuning.Components.TuningProfile;
import net.laserdiamond.ventureplugin.tuning.Components.TuningStats;
import net.laserdiamond.ventureplugin.tuning.Manager.TuningProfileManager;
import net.laserdiamond.ventureplugin.util.Config.PlayerConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatProfileManager {

    private final PlayerConfig baseStatsConfig;
    private final SkillsProfileManager skillsProfileManager;
    private final TuningProfileManager tuningProfileManager;
    private final Map<UUID, StatProfile> statProfiles = new HashMap<>();
    private final double baseHealth;
    private final double baseSpeed;
    private final double baseStarvation;
    private final double baseLuck;
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
        baseStatsConfig = plugin.getBaseStatsConfig();
        skillsProfileManager = plugin.getSkillsProfileManager();
        tuningProfileManager = plugin.getTunementProfileManager();

        baseHealth = baseStatsConfig.getDouble("baseHealth");
        baseSpeed = baseStatsConfig.getDouble("baseSpeedPoints");
        baseStarvation = baseStatsConfig.getDouble("starvationRate");
        baseLuck = baseStatsConfig.getDouble("baseLuck");


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
        // Skills
        SkillsProfile skillsProfile = skillsProfileManager.getPlayerProfile(player.getUniqueId());
        if (skillsProfile == null)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Player " + player.getName() + " does not have a skills profile");
            skillsProfile = skillsProfileManager.createNewProfile(player);
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "Creating new skills profile for " + player.getName());
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "Player UUID: " + player.getUniqueId());
        } else // Player has skill profile
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "Player " + player.getName() + " already has a skills profile");
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "Player UUID: " + player.getUniqueId());
        }

        // Tuning
        TuningProfile tuningProfile = tuningProfileManager.getPlayerProfile(player.getUniqueId());
        if (tuningProfile == null) // Check if profile is null (doesn't have profile)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Player " + player.getName() + " does not have a tuning profile");
            tuningProfile = tuningProfileManager.createNewProfile(player);
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Creating new tuning profile for " + player.getName());
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Player UUID: " + player.getUniqueId());
        } else // Player has tuning profile
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Player " + player.getName() + " already has a tuning profile");
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Player UUID: " + player.getUniqueId());
        }

        double tuningHealthValue = baseStatsConfig.getDouble("healthValue"),
                tuningDefenseValue = baseStatsConfig.getDouble("defenseValue"),
                tuningSpeedValue = baseStatsConfig.getDouble("speedValue"),
                tuningManaValue = baseStatsConfig.getDouble("manaValue"),
                tuningMeleeValue = baseStatsConfig.getDouble("meleeValue"),
                tuningMagicValue = baseStatsConfig.getDouble("magicValue"),
                tuningRangeValue = baseStatsConfig.getDouble("rangeValue");

        TuningStats tuningStats = tuningProfile.tuningStats();
        TuningPoints tuningPoints = tuningProfile.tuningPoints();

        tuningStats.setHealth(tuningPoints.getHealthPoints() * tuningHealthValue);
        tuningStats.setDefense(tuningPoints.getDefensePoints() * tuningDefenseValue);
        tuningStats.setSpeed(tuningPoints.getSpeedPoints() * tuningSpeedValue);
        tuningStats.setMana(tuningPoints.getManaPoints() * tuningManaValue);
        tuningStats.setMelee(tuningPoints.getMeleePoints() * tuningMeleeValue);
        tuningStats.setMagic(tuningPoints.getMagicPoints() * tuningMagicValue);
        tuningStats.setRange(tuningPoints.getRangePoints() * tuningRangeValue);

        double finalBaseHealth = baseHealth + tuningStats.getHealth();
        double finalBaseDefense = baseDefense + tuningStats.getDefense();
        double finalBaseSpeed = baseSpeed + tuningStats.getSpeed();
        double finalBaseMana = maxMana + tuningStats.getMana();
        double finalBaseMelee = this.baseMelee + tuningStats.getMelee();
        double finalBaseMagic = this.baseMagic + tuningStats.getMagic();
        double finalBaseRange = this.baseRange + tuningStats.getRange();

        Stats stats = new Stats(player, finalBaseHealth, finalBaseSpeed, baseStarvation, baseLuck, availableMana, finalBaseMana);
        ArmorStats armorStats = new ArmorStats(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
        DamageStats damageStats = new DamageStats(finalBaseMelee, finalBaseMagic, finalBaseRange, percentMelee, percentMagic, percentRange,0);
        DefenseStats defenseStats = new DefenseStats(player, finalBaseDefense, baseFireDefense, baseExplosionDefense, baseProjectileDefense, baseMagicDefense, baseToughness, 0);
        LootStats lootStats = new LootStats(0,0,0,0,0);
        EnchantStats enchantStats = new EnchantStats(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);

        ArmorTrimMaterialStats armorTrimMaterialStats = new ArmorTrimMaterialStats(0,0,0,0,0,0,0,0,0,0,0);
        ArmorTrimPatternStats armorTrimPatternStats = new ArmorTrimPatternStats(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
        ArmorTrimStats armorTrimStats = new ArmorTrimStats(armorTrimMaterialStats, armorTrimPatternStats);

        PotionStats potionStats = new PotionStats(0,0);

        StatProfile statProfile = new StatProfile(stats, armorStats, damageStats, defenseStats, lootStats, enchantStats, armorTrimStats, tuningProfile, skillsProfile, potionStats);
        statProfiles.put(player.getUniqueId(), statProfile);

        return statProfile;
    }

    public StatProfile getStatProfile(UUID uuid)
    {
        return statProfiles.get(uuid);
    }

}
