package net.laserdiamond.ventureplugin.tuning.Manager;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.util.Config.PlayerConfig;
import net.laserdiamond.ventureplugin.util.Config.PlayerSaveConfig;
import net.laserdiamond.ventureplugin.util.Config.ProfileConfigs;
import net.laserdiamond.ventureplugin.stats.Config.BaseStatsConfig;
import net.laserdiamond.ventureplugin.tuning.Components.TuningPoints;
import net.laserdiamond.ventureplugin.tuning.Components.TuningProfile;
import net.laserdiamond.ventureplugin.tuning.Components.TuningStats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TuningProfileManager implements ProfileConfigs {

    private final PlayerConfig baseStatsConfig;
    private final Map<UUID, TuningProfile> profiles = new HashMap<>();
    private final FileConfiguration config;

    public TuningProfileManager(VenturePlugin plugin)
    {
        baseStatsConfig = plugin.getBaseStatsConfig();
        PlayerSaveConfig tuningConfig = plugin.getTuningConfig();
        config = tuningConfig.getConfig();
    }
    @Override
    public void loadFromConfig() {
        for (String id : config.getConfigurationSection("").getKeys(false))
        {
            UUID uuid = UUID.fromString(id);
            int points = config.getInt(id + ".points.availablePoints");
            int healthPoints = config.getInt(id + ".points.health");
            int defensePoints = config.getInt(id + ".points.defense");
            int speedPoints = config.getInt(id + ".points.speed");
            int manaPoints = config.getInt(id + ".points.mana");
            int meleePoints = config.getInt(id + ".points.melee");
            int magicPoints = config.getInt(id + ".points.magic");
            int rangePoints = config.getInt(id + ".points.range");

            double healthStat = config.getDouble(id + ".stats.health");
            double defenseStat = config.getDouble(id + ".stats.defense");
            double speedStat = config.getDouble(id + ".stats.speed");
            double manaStat = config.getDouble(id + ".stats.mana");
            double meleeStat = config.getDouble(id + ".stats.melee");
            double magicStat = config.getDouble(id + ".stats.magic");
            double rangeStat = config.getDouble(id + ".stats.range");

            TuningPoints tuningPoints = new TuningPoints(points, healthPoints, defensePoints, speedPoints, manaPoints, meleePoints, magicPoints, rangePoints);
            TuningStats tuningStats = new TuningStats(healthStat, defenseStat, speedStat, manaStat, meleeStat, magicStat, rangeStat);
            TuningProfile tuningProfile = new TuningProfile(tuningPoints, tuningStats);
            profiles.put(uuid, tuningProfile);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Loaded tuning profile for user " + id + "!");
        }
    }

    @Override
    public void saveToConfig() {
        for (UUID uuid : profiles.keySet())
        {
            String id = uuid.toString();
            Player player = Bukkit.getPlayer(uuid);
            TuningProfile tuningProfile = profiles.get(uuid);
            TuningPoints tuningPoints = tuningProfile.tuningPoints();
            TuningStats tuningStats = tuningProfile.tuningStats();
            if (player != null) {
                config.set(id + ".name", player.getName());
            }
            config.set(id + ".points.availablePoints", tuningPoints.getTuningPoints());
            config.set(id + ".points.health", tuningPoints.getHealthPoints());
            config.set(id + ".points.defense", tuningPoints.getDefensePoints());
            config.set(id + ".points.speed", tuningPoints.getSpeedPoints());
            config.set(id + ".points.mana", tuningPoints.getManaPoints());
            config.set(id + ".points.melee", tuningPoints.getMeleePoints());
            config.set(id + ".points.magic", tuningPoints.getMagicPoints());
            config.set(id + ".points.range", tuningPoints.getRangePoints());

            config.set(id + ".stats.health", tuningStats.getHealth());
            config.set(id + ".stats.defense", tuningStats.getDefense());
            config.set(id + ".stats.speed", tuningStats.getSpeed());
            config.set(id + ".stats.mana", tuningStats.getMana());
            config.set(id + ".stats.melee", tuningStats.getMelee());
            config.set(id + ".stats.magic", tuningStats.getMagic());
            config.set(id + ".stats.range", tuningStats.getRange());

        }
    }

    public TuningProfile createNewProfile(Player player)
    {
        int defaultPoints = baseStatsConfig.getInt("baseTuningPoints");
        TuningPoints tuningPoints = new TuningPoints(defaultPoints, 0,0,0,0,0,0,0);
        TuningStats tuningStats = new TuningStats(0,0,0,0,0,0,0);
        TuningProfile tuningProfile = new TuningProfile(tuningPoints, tuningStats);
        profiles.put(player.getUniqueId(), tuningProfile);
        return tuningProfile;
    }

    public TuningProfile getPlayerProfile(UUID uuid)
    {
        return profiles.get(uuid);
    }
}
