package net.laserdiamond.serverplugin1201.items.management.misc;

import net.laserdiamond.serverplugin1201.items.management.ItemForger;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class MenuItems {

    public static ItemForger createMenuItem(MenuItemEnum menuItemEnum) {

        ItemForger itemForger = new ItemForger(menuItemEnum.getMaterial(), 1, menuItemEnum.getItemName());
        itemForger.setCustomModelData(menuItemEnum.getCmd());
        itemForger.hideAllItemFlags();

        return itemForger;
    }

    public enum MenuItemEnum {

        RESULT_ITEM (Material.LIGHT_BLUE_STAINED_GLASS_PANE, 97, ChatColor.GREEN + "Result"),
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
