package net.laserdiamond.serverplugin1201.items.crafting.SmithingTable;


import net.laserdiamond.serverplugin1201.items.management.ItemForger;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class SmithingMenuItems {

    public static List<String> createLore(SmithingItemEnum smithingItemEnum) {

        List<String> lore = new ArrayList<>();

        lore.add(" ");

        if (smithingItemEnum.equals(SmithingItemEnum.EQUIPMENT_ITEM)) {

            lore.add(ChatColor.GRAY + "Insert an equipment item here");

        } else if (smithingItemEnum.equals(SmithingItemEnum.MATERIAL_ITEM)) {

            lore.add(ChatColor.GRAY + "Insert a material here");

        } else if (smithingItemEnum.equals(SmithingItemEnum.TEMPLATE_ITEM)) {

            lore.add(ChatColor.GRAY + "Insert a template here");

        } else if (smithingItemEnum.equals(SmithingItemEnum.SMITH_ITEM)) {

            lore.add(ChatColor.GRAY + "Combine an " + ChatColor.GREEN + "equipment piece" + ChatColor.GRAY + ", " + ChatColor.AQUA + "material" + ChatColor.GRAY + ", and");
            lore.add(ChatColor.DARK_RED + "template" + ChatColor.GRAY + " to upgrade your current item");

            // Combine an equipment piece, material, and
            // template to upgrade your current item

        } else {
            return null;
        }
        lore.add(" ");

        return lore;
    }

    public static ItemForger createEquipmentItem(SmithingItemEnum smithingItemEnum) {

        ItemForger itemForger = new ItemForger(smithingItemEnum.getMaterial(),1,smithingItemEnum.getItemName());
        itemForger.setLore(createLore(smithingItemEnum));
        itemForger.setCustomModelData(smithingItemEnum.getCustomModelData());


        return itemForger;
    }


    public enum SmithingItemEnum {

        RESULT_ITEM (Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "Result Item", 92),
        EQUIPMENT_ITEM (Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "Equipment Piece", 93),
        MATERIAL_ITEM (Material.GREEN_STAINED_GLASS_PANE,ChatColor.AQUA + "Material", 94),
        TEMPLATE_ITEM (Material.GREEN_STAINED_GLASS_PANE,ChatColor.DARK_RED + "Template", 95),
        SMITH_ITEM (Material.SMITHING_TABLE,ChatColor.GOLD + "Smith Items", 96);

        private final Material material;
        private final String itemName;
        private final int customModelData;
        SmithingItemEnum(Material material, String itemName, int customModelData) {
            this.material = material;
            this.itemName = itemName;
            this.customModelData = customModelData;
        }

        public static boolean ofCustomModelData(int input) {
            for (SmithingItemEnum smithingItemEnum : values()) {
                if (smithingItemEnum.getCustomModelData() == input) {
                    return true;
                }
            }
            return false;
        }

        public Material getMaterial() {
            return material;
        }

        public String getItemName() {
            return itemName;
        }

        public int getCustomModelData() {
            return customModelData;
        }


    }
}
