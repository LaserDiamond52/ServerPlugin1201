package net.laserdiamond.ventureplugin.items.menuItems.misc;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.menuItems.util.MenuItem;
import net.laserdiamond.ventureplugin.items.menuItems.util.VentureMenuItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class MiscMenuItems {

    private static final VenturePlugin PLUGIN = VenturePlugin.getInstance();

    public static HashMap<Integer, List<String>> miscMenuItemsMap(Player player)
    {
        HashMap<Integer, List<String>> menuItemMap = new HashMap<>();

        menuItemMap.put(BLANK.menuItem().getCustomModelData(), BLANK.createLore(player));


        return menuItemMap;
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
}
