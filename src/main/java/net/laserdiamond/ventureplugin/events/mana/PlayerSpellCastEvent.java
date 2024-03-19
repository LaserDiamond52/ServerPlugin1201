package net.laserdiamond.ventureplugin.events.mana;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerSpellCastEvent extends PlayerEvent implements Cancellable {

    private double manaCost;
    private boolean isCancelled;
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public PlayerSpellCastEvent(Player player, double manaCost)
    {
        super(player);
        this.manaCost = manaCost;
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

    public static HandlerList getHandlerList()
    {
        return HANDLER_LIST;
    }

    public double getManaCost() {
        return manaCost;
    }

    public void setManaCost(double manaCost) {
        this.manaCost = manaCost;
    }
}
