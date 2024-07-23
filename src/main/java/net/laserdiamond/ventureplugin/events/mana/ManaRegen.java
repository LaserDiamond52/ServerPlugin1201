package net.laserdiamond.ventureplugin.events.mana;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.events.effects.Managers.EffectManager;
import net.laserdiamond.ventureplugin.stats.Components.Stats;
import net.laserdiamond.ventureplugin.stats.Manager.StatProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class ManaRegen extends BukkitRunnable implements Listener {

    private final VenturePlugin plugin;
    private final StatProfileManager statProfileManager;
    private final EffectManager effectManager;

    public ManaRegen(VenturePlugin plugin) {
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

            PlayerManaRegenEvent playerManaRegenEvent = new PlayerManaRegenEvent(player, PlayerManaRegenEvent.ManaRegenReason.REGEN, baseManaRegen);
            Bukkit.getPluginManager().callEvent(playerManaRegenEvent);

        }
    }

    @EventHandler
    public void manaRegenEvent(PlayerManaRegenEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }
        StatPlayer statPlayer = new StatPlayer(event.getPlayer());
        Stats stats = statPlayer.getStats();
        double availableMana = stats.getAvailableMana();
        double maxMana = stats.getMaxMana();

        stats.setAvailableMana(Math.max(0, Math.min(availableMana + event.getManaRegenAmount(), maxMana)));
    }


}
