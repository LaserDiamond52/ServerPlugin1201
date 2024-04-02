package net.laserdiamond.ventureplugin.items.menuItems.misc;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.menuItems.util.MenuItem;
import net.laserdiamond.ventureplugin.items.menuItems.util.VentureMenuItem;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class MiscMenuItems implements Listener {

    private static final VenturePlugin PLUGIN = VenturePlugin.getInstance();

    public MiscMenuItems(VenturePlugin plugin)
    {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static VentureMenuItem BLANK = new VentureMenuItem(PLUGIN)
    {
        @Override
        public MenuItem menuItem() {
            return MenuItem.BLANK;
        }

        @Override
        public List<String> createLore(Player player) {
            return null;
        }
    };

    public static void fillBlankSlotsPlayerInv(Player player, Inventory inventory)
    {
        ItemStack[] contents = inventory.getContents();
        ItemStack itemStack;
        int i = 0;
        while (i < contents.length)
        {
            itemStack = contents[i];
            if (itemStack == null)
            {
                inventory.setItem(i, BLANK.createItem(player).toItemStack());
            }
            i++;
        }
    }

    public static VentureMenuItem EXIT = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.EXIT;
        }

        @Override
        public List<String> createLore(Player player) {
            return null;
        }
    };

    public static VentureMenuItem GO_BACK = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.GO_BACK;
        }

        @Override
        public List<String> createLore(Player player) {
            return null;
        }
    };

    private static final HashMap<Integer, Integer> EXIT_ITEM_INV_LOCATION = new HashMap<>();
    static
    {
        EXIT_ITEM_INV_LOCATION.put(9, 4);
        EXIT_ITEM_INV_LOCATION.put(18, 13);
        EXIT_ITEM_INV_LOCATION.put(27, 22);
        EXIT_ITEM_INV_LOCATION.put(36, 31);
        EXIT_ITEM_INV_LOCATION.put(45, 40);
        EXIT_ITEM_INV_LOCATION.put(54, 49);
    }

    private static final HashMap<Integer, Integer> GO_BACK_ITEM_INV_LOCATION = new HashMap<>();
    static
    {
        GO_BACK_ITEM_INV_LOCATION.put(9, 5);
        GO_BACK_ITEM_INV_LOCATION.put(18, 14);
        GO_BACK_ITEM_INV_LOCATION.put(27, 23);
        GO_BACK_ITEM_INV_LOCATION.put(36, 32);
        GO_BACK_ITEM_INV_LOCATION.put(45, 41);
        GO_BACK_ITEM_INV_LOCATION.put(54, 50);
    }

    /**
     * Places an exit button inside the player GUI inventory
     * <p>
     * Because the exit button's functionality is consistent across all player GUI inventories, its function is already coded in
     * @param player The player viewing the inventory
     * @param inventory The GUI inventory the player is viewing
     */
    public static void placeExitButton(Player player, Inventory inventory)
    {
        int size = inventory.getSize();

        int exitSlot = EXIT_ITEM_INV_LOCATION.get(size);

        inventory.setItem(exitSlot, EXIT.createItem(player).toItemStack());
    }

    /**
     * Places a go back button inside the player GUI inventory
     * <p>
     * Note: This does not include the functionality of the button. That must be coded individually
     * @param player The player viewing the inventory
     * @param inventory The GUI inventory the player is viewing
     */
    public static void placeGoBackButton(Player player, Inventory inventory)
    {
        int size = inventory.getSize();

        int goBackSlot = GO_BACK_ITEM_INV_LOCATION.get(size);

        inventory.setItem(goBackSlot, GO_BACK.createItem(player).toItemStack());
    }

    /**
     * Gets the inventory slot that the go back button is located in
     * @param inventory The inventory to check for a go back button
     * @return The location of where the go back button would be in the inventory
     */
    public static int getGoBackButtonLocation(Inventory inventory)
    {
        int size = inventory.getSize();
        return GO_BACK_ITEM_INV_LOCATION.get(size);
    }

    @EventHandler
    public void manipulateInventory(InventoryClickEvent event)
    {
        HumanEntity humanEntity = event.getWhoClicked();

        if (humanEntity instanceof Player player)
        {
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.getItemMeta() != null && clickedItem.getItemMeta().hasCustomModelData())
            {
                int customModelData = clickedItem.getItemMeta().getCustomModelData();
                if (customModelData == EXIT.menuItem().getCustomModelData())
                {
                    player.closeInventory();
                }
            }
        }
    }
}
