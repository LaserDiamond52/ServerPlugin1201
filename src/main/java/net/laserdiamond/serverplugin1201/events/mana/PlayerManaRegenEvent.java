package net.laserdiamond.serverplugin1201.events.mana;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.stats.Components.DamageStats;
import net.laserdiamond.serverplugin1201.stats.Components.Stats;
import net.laserdiamond.serverplugin1201.stats.Manager.StatProfileManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerManaRegenEvent extends PlayerEvent implements Cancellable {

    private final ServerPlugin1201 plugin = ServerPlugin1201.getInstance();
    private final StatProfileManager statProfileManager = plugin.getStatProfileManager();
    private double manaAmount;
    private boolean isCancelled;
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public PlayerManaRegenEvent(Player player, double manaAmount) {
        super(player);
        this.manaAmount = manaAmount;

        Stats stats = statProfileManager.getStatProfile(player.getUniqueId()).stats();
        double availableMana = stats.getAvailableMana();
        double maxMana = stats.getMaxMana();

        if (availableMana < maxMana)
        {
            stats.setAvailableMana(availableMana + manaAmount);
        }

        if (availableMana + manaAmount > maxMana)
        {
            stats.setAvailableMana(maxMana);
        }
    }


    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public double getManaAmount() {
        return manaAmount;
    }

    public void setManaAmount(double manaAmount) {
        this.manaAmount = manaAmount;
    }
}
