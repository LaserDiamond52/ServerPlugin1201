package net.laserdiamond.ventureplugin.enchants.anvil;

import net.laserdiamond.ventureplugin.VenturePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AnvilInvetoryGUI implements Listener {

    private VenturePlugin plugin;
    private Inventory anvilGUI;

    public AnvilInvetoryGUI(VenturePlugin plugin) {
        this.plugin = plugin;
        anvilGUI = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "Anvil");
        initGUI();
    }

    public void initGUI() {

        // Set Blank Items in inventory

        ItemStack[] contents = anvilGUI.getContents();
        int i = 0;
        for (ItemStack itemStack : contents) {
            if (itemStack == null) {
                if (!Slots.isInventorySlot(i)) {
                    //anvilGUI.setItem(i, MenuItems.createMenuItem(MenuItems.MenuItemEnum.BLANK_ITEM).toItemStack());
                }
            }
            i++;
        }
    }

    @EventHandler
    public void openGUI(InventoryOpenEvent event) {

        Player player = (Player) event.getPlayer();

        if (event.getInventory().getType().equals(InventoryType.ANVIL)) {
            event.setCancelled(true);
            player.openInventory(anvilGUI);
        }

    }

    @EventHandler
    public void clickGUI(InventoryClickEvent event) {


    }

    @EventHandler
    public void closeGUI(InventoryCloseEvent event) {


    }

    public enum Slots {

        FIRST_ITEM (29),
        SECOND_ITEM (33),
        ANVIL_INFO (22),
        RESULT_ITEM (13);

        private final int slot;

        Slots(int slot) {
            this.slot = slot;
        }

        public static boolean isInventorySlot(int input) {

            for (Slots slots : values()) {
                int slot = slots.getSlot();
                if (slot == input) {
                    return true;
                }
            }
            return false;
        }

        public int getSlot() {
            return slot;
        }
    }
}
