package net.laserdiamond.serverplugin1201.tunement.Manager;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.management.Config.ProfileConfigs;
import net.laserdiamond.serverplugin1201.stats.Config.BaseStatsConfig;
import net.laserdiamond.serverplugin1201.tunement.Components.TunementPoints;
import net.laserdiamond.serverplugin1201.tunement.Components.TunementProfile;
import net.laserdiamond.serverplugin1201.tunement.Components.TunementStats;
import net.laserdiamond.serverplugin1201.tunement.Config.TunementConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TunementProfileManager implements ProfileConfigs {

    private final ServerPlugin1201 plugin;
    private final BaseStatsConfig baseStatsConfig;
    private final Map<UUID, TunementProfile> profiles = new HashMap<>();
    private final TunementConfig tunementConfig;
    private final FileConfiguration config;

    public TunementProfileManager(ServerPlugin1201 plugin)
    {
        this.plugin = plugin;
        baseStatsConfig = plugin.getBaseStatsConfig();
        tunementConfig = plugin.getTunementConfig();
        config = tunementConfig.getConfig();
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

            TunementPoints tunementPoints = new TunementPoints(points, healthPoints, defensePoints, speedPoints, manaPoints, meleePoints, magicPoints, rangePoints);
            TunementStats tunementStats = new TunementStats(healthStat, defenseStat, speedStat, manaStat, meleeStat, magicStat, rangeStat);
            TunementProfile tunementProfile = new TunementProfile(tunementPoints, tunementStats);
            profiles.put(uuid, tunementProfile);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Loaded tunement profile for user " + id + "!");
        }
    }

    @Override
    public void saveToConfig() {
        for (UUID uuid : profiles.keySet())
        {
            String id = uuid.toString();
            Player player = Bukkit.getPlayer(uuid);
            TunementProfile tunementProfile = profiles.get(uuid);
            TunementPoints tunementPoints = tunementProfile.tunementPoints();
            TunementStats tunementStats = tunementProfile.tunementStats();
            if (player != null) {
                config.set(id + ".name", player.getName());
            }
            config.set(id + ".points.availablePoints", tunementPoints.getTuningPoints());
            config.set(id + ".points.health", tunementPoints.getHealthPoints());
            config.set(id + ".points.defense", tunementPoints.getDefensePoints());
            config.set(id + ".points.speed", tunementPoints.getSpeedPoints());
            config.set(id + ".points.mana", tunementPoints.getManaPoints());
            config.set(id + ".points.melee", tunementPoints.getMeleePoints());
            config.set(id + ".points.magic", tunementPoints.getMagicPoints());
            config.set(id + ".points.range", tunementPoints.getRangePoints());

            config.set(id + ".stats.health", tunementStats.getHealth());
            config.set(id + ".stats.defense", tunementStats.getDefense());
            config.set(id + ".stats.speed", tunementStats.getSpeed());
            config.set(id + ".stats.mana", tunementStats.getMana());
            config.set(id + ".stats.melee", tunementStats.getMelee());
            config.set(id + ".stats.magic", tunementStats.getMagic());
            config.set(id + ".stats.range", tunementStats.getRange());

        }
    }

    public TunementProfile createNewProfile(Player player)
    {
        int defaultPoints = baseStatsConfig.getInt("baseTunementPoints");
        TunementPoints tunementPoints = new TunementPoints(defaultPoints, 0,0,0,0,0,0,0);
        TunementStats tunementStats = new TunementStats(0,0,0,0,0,0,0);
        TunementProfile tunementProfile = new TunementProfile(tunementPoints, tunementStats);
        profiles.put(player.getUniqueId(), tunementProfile);
        return tunementProfile;
    }

    public TunementProfile getPlayerProfile(UUID uuid)
    {
        return profiles.get(uuid);
    }
}
