package net.laserdiamond.ventureplugin.entities.mobs;

import net.kyori.adventure.bossbar.BossBarViewer;
import net.kyori.adventure.text.Component;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.mobs.blaze.VentureBlazeRegister;
import net.laserdiamond.ventureplugin.entities.mobs.creeper.VentureCreeperRegister;
import net.laserdiamond.ventureplugin.entities.util.VentureMob;
import net.laserdiamond.ventureplugin.entities.util.VentureMobBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class VentureMobs {

    /**
     * Registers all mobs of this plugin
     * @param plugin VenturePlugin instance
     */
    public static void register(VenturePlugin plugin)
    {
        VentureBlazeRegister.register(plugin);
        VentureCreeperRegister.register(plugin);

        HashMap<String, VentureMob<?>> mobHashMap = plugin.getVentureMobMap();
        for (String name : mobHashMap.keySet())
        {
            Bukkit.getConsoleSender().sendMessage(mobHashMap.get(name).ventureMobType().getDisplayName() + ChatColor.GREEN + " mob registered!");
        }

        //new BossBarDisplay().runTaskTimer(VenturePlugin.getInstance(), 0L, 1L);
    }

    // TODO: If a mob's boss tag is set to true, display a boss bar that displays the mob's name (no notches on the bar)

    private static class BossBarDisplay extends BukkitRunnable
    {

        @Override
        public void run()
        {
            for (Player player : Bukkit.getOnlinePlayers())
            {
                for (Mob mob : player.getLocation().getNearbyEntitiesByType(Mob.class, 90))
                {
                    VentureMobBuilder<Mob> mobBuilder = new VentureMobBuilder<>(mob);
                    if (mobBuilder.isBossMob() && !mob.isDead())
                    {
                        BossBar bossBar = Bukkit.createBossBar(mob.getCustomName(), BarColor.PINK, BarStyle.SOLID);
                        bossBar.setProgress(mob.getHealth() / mob.getMaxHealth());

                        if (!bossBar.getPlayers().contains(player))
                        {
                            bossBar.addPlayer(player);
                        }

                    }
                }
            }
        }
    }

}
