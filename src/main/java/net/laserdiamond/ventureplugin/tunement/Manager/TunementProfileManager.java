package net.laserdiamond.ventureplugin.tunement.Manager;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.util.Config.PlayerSaveConfig;
import net.laserdiamond.ventureplugin.util.Config.ProfileConfigs;
import net.laserdiamond.ventureplugin.stats.Config.BaseStatsConfig;
import net.laserdiamond.ventureplugin.tunement.Components.TunementPoints;
import net.laserdiamond.ventureplugin.tunement.Components.TunementProfile;
import net.laserdiamond.ventureplugin.tunement.Components.TunementStats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TunementProfileManager implements ProfileConfigs {

    private final BaseStatsConfig baseStatsConfig;
    private final Map<UUID, TunementProfile> profiles = new HashMap<>();
    private final FileConfiguration config;

    public TunementProfileManager(VenturePlugin plugin)
    {
        baseStatsConfig = plugin.getBaseStatsConfig();
        PlayerSaveConfig tunementConfig = plugin.getTunementConfig();
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
