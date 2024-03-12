package net.laserdiamond.serverplugin1201.events.mana;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.events.effects.Components.Timers.ManaFreezeTimer;
import net.laserdiamond.serverplugin1201.events.effects.Managers.EffectManager;
import net.laserdiamond.serverplugin1201.stats.Components.Stats;
import net.laserdiamond.serverplugin1201.stats.Manager.StatProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.scheduler.BukkitRunnable;

public class ManaRegen extends BukkitRunnable implements Listener {

    private final ServerPlugin1201 PLUGIN;

    private final StatProfileManager STAT_PROFILE_MANAGER;
    private final EffectManager EFFECT_MANAGER;

    public ManaRegen(ServerPlugin1201 plugin) {
        this.PLUGIN = plugin;
        STAT_PROFILE_MANAGER = plugin.getStatProfileManager();
        EFFECT_MANAGER = plugin.getEffectManager();
    }

    @Override
    public void run() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            Stats stats = STAT_PROFILE_MANAGER.getStatProfile(player.getUniqueId()).stats();

            double availableMana = stats.getAvailableMana();
            double maxMana = stats.getMaxMana();
            double baseManaRegen = maxMana/50;

            /*
            EffectDurations effectDurations = effectManager.getEffectProfile(player.getUniqueId()).getEffectDurations();
            int manaFreezeDuration = effectDurations.getManaFreezeDuration();

             */

            /*
            if (ManaFreezeTimer.hasNoEffect(player)) {
                if (availableMana < maxMana) {
                    stats.setAvailableMana(availableMana + baseManaRegen);
                }
            }

             */

        }
    }


    @EventHandler
    public void manaRegenEvent(PlayerManaRegenEvent event)
    {
        Player player = event.getPlayer();


        // Cancel event if player has mana freeze
    }
}
