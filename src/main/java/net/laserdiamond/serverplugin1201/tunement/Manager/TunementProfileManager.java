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

    private final ServerPlugin1201 PLUGIN;
    private final BaseStatsConfig BASE_STATS_CONFIG;
    private final Map<UUID, TunementProfile> profiles = new HashMap<>();
    private final TunementConfig TUNEMENT_CONFNIG;
    private final FileConfiguration CONFIG;

    public TunementProfileManager(ServerPlugin1201 plugin)
    {
        this.PLUGIN = plugin;
        BASE_STATS_CONFIG = plugin.getBaseStatsConfig();
        TUNEMENT_CONFNIG = plugin.getTunementConfig();
        CONFIG = TUNEMENT_CONFNIG.getConfig();
    }
    @Override
    public void loadFromConfig() {
        for (String id : CONFIG.getConfigurationSection("").getKeys(false))
        {
            UUID uuid = UUID.fromString(id);
            int points = CONFIG.getInt(id + ".points.availablePoints");
            int healthPoints = CONFIG.getInt(id + ".points.health");
            int defensePoints = CONFIG.getInt(id + ".points.defense");
            int speedPoints = CONFIG.getInt(id + ".points.speed");
            int manaPoints = CONFIG.getInt(id + ".points.mana");
            int meleePoints = CONFIG.getInt(id + ".points.melee");
            int magicPoints = CONFIG.getInt(id + ".points.magic");
            int rangePoints = CONFIG.getInt(id + ".points.range");

            double healthStat = CONFIG.getDouble(id + ".stats.health");
            double defenseStat = CONFIG.getDouble(id + ".stats.defense");
            double speedStat = CONFIG.getDouble(id + ".stats.speed");
            double manaStat = CONFIG.getDouble(id + ".stats.mana");
            double meleeStat = CONFIG.getDouble(id + ".stats.melee");
            double magicStat = CONFIG.getDouble(id + ".stats.magic");
            double rangeStat = CONFIG.getDouble(id + ".stats.range");

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
                CONFIG.set(id + ".name", player.getName());
            }
            CONFIG.set(id + ".points.availablePoints", tunementPoints.getTuningPoints());
            CONFIG.set(id + ".points.health", tunementPoints.getHealthPoints());
            CONFIG.set(id + ".points.defense", tunementPoints.getDefensePoints());
            CONFIG.set(id + ".points.speed", tunementPoints.getSpeedPoints());
            CONFIG.set(id + ".points.mana", tunementPoints.getManaPoints());
            CONFIG.set(id + ".points.melee", tunementPoints.getMeleePoints());
            CONFIG.set(id + ".points.magic", tunementPoints.getMagicPoints());
            CONFIG.set(id + ".points.range", tunementPoints.getRangePoints());

            CONFIG.set(id + ".stats.health", tunementStats.getHealth());
            CONFIG.set(id + ".stats.defense", tunementStats.getDefense());
            CONFIG.set(id + ".stats.speed", tunementStats.getSpeed());
            CONFIG.set(id + ".stats.mana", tunementStats.getMana());
            CONFIG.set(id + ".stats.melee", tunementStats.getMelee());
            CONFIG.set(id + ".stats.magic", tunementStats.getMagic());
            CONFIG.set(id + ".stats.range", tunementStats.getRange());

        }
    }

    public TunementProfile createNewProfile(Player player)
    {
        int defaultPoints = BASE_STATS_CONFIG.getInt("baseTunementPoints");
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
