package net.laserdiamond.serverplugin1201.items.crafting.Recipes;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.items.armor.Vanilla.Components.DiamondArmorManager;
import net.laserdiamond.serverplugin1201.items.armor.Vanilla.Components.NetheriteArmorManager;
import net.laserdiamond.serverplugin1201.items.crafting.SmithingTable.SmithingTableGUI;
import net.laserdiamond.serverplugin1201.items.management.armor.ArmorTypes;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SmithingTableRecipes {

    private static final ServerPlugin1201 plugin = ServerPlugin1201.getInstance();
    private static final DiamondArmorManager diamondArmorManager = plugin.getDiamondArmorManager();
    private static final NetheriteArmorManager netheriteArmorManager = plugin.getNetheriteArmorManager();


    public enum Recipes {

        NETHERITE_BOOTS (diamondArmorManager.createArmorPiece(ArmorTypes.BOOTS, 0).toItemStack(), new ItemStack(Material.NETHERITE_INGOT), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE), netheriteArmorManager.createArmorPiece(ArmorTypes.BOOTS, 0).toItemStack()),
        NETHERITE_LEGGINGS (diamondArmorManager.createArmorPiece(ArmorTypes.LEGGINGS, 0).toItemStack(), new ItemStack(Material.NETHERITE_INGOT), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE), netheriteArmorManager.createArmorPiece(ArmorTypes.LEGGINGS, 0).toItemStack()),
        NETHERITE_CHESTPLATE(diamondArmorManager.createArmorPiece(ArmorTypes.CHESTPLATE, 0).toItemStack(), new ItemStack(Material.NETHERITE_INGOT), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE), netheriteArmorManager.createArmorPiece(ArmorTypes.CHESTPLATE, 0).toItemStack()),
        NETHERITE_HELMET (diamondArmorManager.createArmorPiece(ArmorTypes.HELMET, 0).toItemStack(), new ItemStack(Material.NETHERITE_INGOT), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE), netheriteArmorManager.createArmorPiece(ArmorTypes.HELMET, 0).toItemStack());

        private final ItemStack equipmentItem, materialItem, templateItem, resultItem;

        Recipes(ItemStack equipmentItem, ItemStack materialItem, ItemStack templateItem, ItemStack resultItem) {
            this.equipmentItem = equipmentItem;
            this.materialItem = materialItem;
            this.templateItem = templateItem;
            this.resultItem = resultItem;
        }

        public static ItemStack createResult(Inventory inventory) {

            ItemStack equipmentSlot = inventory.getItem(SmithingTableGUI.SmithingTableEnum.EQUIPMENT_ITEM.getInventorySlot());
            ItemStack materialSlot = inventory.getItem(SmithingTableGUI.SmithingTableEnum.MATERIAL_ITEM.getInventorySlot());
            ItemStack templateSlot = inventory.getItem(SmithingTableGUI.SmithingTableEnum.TEMPLATE_ITEM.getInventorySlot());

            for (Recipes recipes : values()) {
                if (equipmentSlot != null && materialSlot != null && templateSlot != null) {
                    if (equipmentSlot.equals(recipes.getEquipmentItem()) && materialSlot.equals(recipes.getMaterialItem()) && templateSlot.equals(recipes.getTemplateItem())) {
                        return recipes.getResultItem();
                    }
                }
            }

            return new ItemStack(Material.AIR);
        }

        public static boolean isResultItem(ItemStack itemStack) {

            for (Recipes recipes : values()) {
                if (itemStack.equals(recipes.getResultItem())) {
                    return true;
                }
            }
            return false;
        }

        public ItemStack getEquipmentItem() {
            return equipmentItem;
        }

        public ItemStack getMaterialItem() {
            return materialItem;
        }

        public ItemStack getTemplateItem() {
            return templateItem;
        }

        public ItemStack getResultItem() {
            return resultItem;
        }
    }

}
