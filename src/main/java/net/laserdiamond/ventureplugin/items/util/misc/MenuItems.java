package net.laserdiamond.ventureplugin.items.util.misc;

import net.laserdiamond.ventureplugin.items.util.ItemForger;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class MenuItems {

    public static ItemForger createMenuItem(MenuItemEnum menuItemEnum) {

        ItemForger itemForger = new ItemForger(menuItemEnum.getMaterial(), 1, menuItemEnum.getItemName());
        itemForger.setCustomModelData(menuItemEnum.getCmd());
        itemForger.hideAllItemFlags();

        return itemForger;
    }

    public static List<String> createLore(MenuItemEnum menuItemEnum) {

        List<String> lore = new ArrayList<>();

        if (menuItemEnum.equals(MenuItemEnum.ANVIL_INFO)) {

        }

        return lore;
    }

    public enum MenuItemEnum {

        ANVIL_INFO (Material.ANVIL, 96, ChatColor.GRAY + "Anvil"),

        @Deprecated
        RESULT_ITEM (Material.LIGHT_BLUE_STAINED_GLASS_PANE, 97, ChatColor.GREEN + "Result"),
        @Deprecated
        NOT_RECIPE_ITEM (Material.BARRIER,98, ChatColor.RED + "Not a Valid Recipe!"),
        BLANK_ITEM (Material.BLACK_STAINED_GLASS_PANE,99, " ");

        private final Material material;
        private final int cmd;
        private final String itemName;

        MenuItemEnum(Material material, int cmd, String itemName) {
            this.material = material;
            this.cmd = cmd;
            this.itemName = itemName;
        }

        public Material getMaterial() {
            return material;
        }

        public int getCmd() {
            return cmd;
        }

        public String getItemName() {
            return itemName;
        }
    }
}
