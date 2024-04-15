package net.laserdiamond.ventureplugin.events;

import net.laserdiamond.ventureplugin.items.menuItems.misc.MiscMenuItems;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

// TODO: Move this over to misc menu items
@Deprecated
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
            if (clickedItem.getItemMeta() != null)
            {
                ItemForger clickedItemForger = new ItemForger(clickedItem);
                String menuKeyName = clickedItemForger.getMenuItemKey();
                // Cancel Clicking on Blank Menu item for ALL inventory GUIs
                if (menuKeyName == null)
                {
                    return;
                }
                if (menuKeyName.equals(MiscMenuItems.BLANK.menuItem().getKeyName()))
                {
                    event.setCancelled(true);
                }
            }

            /*
            if (clickedItem.equals(MiscMenuItems.BLANK.createItem(player).toItemStack())) {
                event.setCancelled(true);
            }
             */
        }
    }
}
