package net.laserdiamond.serverplugin1201.events.mana;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.events.effects.Components.Timers.ManaFreezeTimer;
import net.laserdiamond.serverplugin1201.events.effects.Managers.EffectManager;
import net.laserdiamond.serverplugin1201.stats.Components.Stats;
import net.laserdiamond.serverplugin1201.stats.Manager.StatProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ManaRegen extends BukkitRunnable {

    private ServerPlugin1201 plugin;

    private StatProfileManager statProfileManager;
    private EffectManager effectManager;

    public ManaRegen(ServerPlugin1201 plugin) {
        this.plugin = plugin;
        statProfileManager = plugin.getStatProfileManager();
        effectManager = plugin.getEffectManager();
    }

    @Override
    public void run() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            Stats stats = statProfileManager.getStatProfile(player.getUniqueId()).stats();

            double availableMana = stats.getAvailableMana();
            double maxMana = stats.getMaxMana();
            double baseManaRegen = maxMana/50;

            /*
            EffectDurations effectDurations = effectManager.getEffectProfile(player.getUniqueId()).getEffectDurations();
            int manaFreezeDuration = effectDurations.getManaFreezeDuration();

             */

            if (ManaFreezeTimer.hasNoEffect(player)) {
                if (availableMana < maxMana) {
                    stats.setAvailableMana(availableMana + baseManaRegen);
                }
            }
        }
    }
}
