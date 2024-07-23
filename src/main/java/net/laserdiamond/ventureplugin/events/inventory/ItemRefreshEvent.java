package net.laserdiamond.ventureplugin.events.inventory;

import net.laserdiamond.ventureplugin.VenturePlugin;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Event that handles the refreshing/updation of items. Cannot be cancelled
 */
public class ItemRefreshEvent extends Event {

    private final ItemStack refreshedItem;
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public ItemRefreshEvent(final ItemStack itemStack)
    {
        this.refreshedItem = itemStack;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList()
    {
        return HANDLER_LIST;
    }

    public ItemStack getRefreshedItem() {
        return refreshedItem;
    }
}
