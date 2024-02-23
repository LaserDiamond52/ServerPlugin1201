package net.laserdiamond.serverplugin1201.events;

import net.laserdiamond.serverplugin1201.items.management.misc.MenuItems;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CancelInventoryMovementMenus implements Listener {

    @EventHandler
    public void cancelClick(InventoryClickEvent event) {

        HumanEntity humanEntity = event.getWhoClicked();
        String inventoryName = event.getView().getTitle();

        if (humanEntity instanceof Player player) {

            if (event.getCurrentItem() == null) {
                return;
            }

            if (inventoryName.contains(player.getName() + "'s Stats")) {
                event.setCancelled(true);
            }

            ItemStack clickedItem = event.getCurrentItem();

            // Cancel Clicking on Blank Menu item for ALL inventory GUIs
            if (clickedItem.equals(MenuItems.createMenuItem(MenuItems.MenuItemEnum.BLANK_ITEM).toItemStack())) {
                event.setCancelled(true);
            }
        }
    }
}
