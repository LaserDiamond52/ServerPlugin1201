package net.laserdiamond.serverplugin1201.events.mana;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.events.effects.Managers.EffectManager;
import net.laserdiamond.serverplugin1201.stats.Components.Stats;
import net.laserdiamond.serverplugin1201.stats.Manager.StatProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class ManaRegen extends BukkitRunnable implements Listener {

    private final ServerPlugin1201 plugin;

    private final StatProfileManager statProfileManager;
    private final EffectManager effectManager;

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



            /*
            if (ManaFreezeTimer.hasNoEffect(player)) {
                if (availableMana < maxMana) {
                    stats.setAvailableMana(availableMana + baseManaRegen);
                }
            }

             */

            PlayerManaRegenEvent playerManaRegenEvent = new PlayerManaRegenEvent(player, PlayerManaRegenEvent.ManaRegenReason.REGEN, baseManaRegen);
            Bukkit.getPluginManager().callEvent(playerManaRegenEvent);
            if (!playerManaRegenEvent.isCancelled())
            {
                PlayerManaRegenEvent.run(player, baseManaRegen);
            }

        }
    }

    @EventHandler
    public void manaRegenEvent(PlayerManaRegenEvent event)
    {

    }


}
