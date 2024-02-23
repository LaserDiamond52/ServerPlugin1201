package net.laserdiamond.serverplugin1201.items.crafting.Recipes;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.items.armor.Vanilla.Components.DiamondArmorManager;
import net.laserdiamond.serverplugin1201.items.armor.Vanilla.Components.NetheriteArmorManager;
import net.laserdiamond.serverplugin1201.items.crafting.SmithingTable.SmithingTableGUI;
import net.laserdiamond.serverplugin1201.items.management.ItemForger;
import net.laserdiamond.serverplugin1201.items.management.armor.ArmorTypes;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SmithingTableRecipes {

    private static final ServerPlugin1201 plugin = ServerPlugin1201.getInstance();
    private static final DiamondArmorManager diamondArmorManager = plugin.getDiamondArmorManager();
    private static final NetheriteArmorManager netheriteArmorManager = plugin.getNetheriteArmorManager();


    public enum Recipes {

        NETHERITE_BOOTS (diamondArmorManager.createArmorPiece(ArmorTypes.BOOTS, 0).toItemStack(), new ItemStack(Material.NETHERITE_INGOT),1, new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE),1, netheriteArmorManager.createArmorPiece(ArmorTypes.BOOTS, 0).toItemStack()),
        NETHERITE_LEGGINGS (diamondArmorManager.createArmorPiece(ArmorTypes.LEGGINGS, 0).toItemStack(), new ItemStack(Material.NETHERITE_INGOT),1, new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE),1, netheriteArmorManager.createArmorPiece(ArmorTypes.LEGGINGS, 0).toItemStack()),
        NETHERITE_CHESTPLATE(diamondArmorManager.createArmorPiece(ArmorTypes.CHESTPLATE, 0).toItemStack(), new ItemStack(Material.NETHERITE_INGOT),1, new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE),1, netheriteArmorManager.createArmorPiece(ArmorTypes.CHESTPLATE, 0).toItemStack()),
        NETHERITE_HELMET (diamondArmorManager.createArmorPiece(ArmorTypes.HELMET, 0).toItemStack(), new ItemStack(Material.NETHERITE_INGOT),1, new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE),1, netheriteArmorManager.createArmorPiece(ArmorTypes.HELMET, 0).toItemStack());

        private final ItemStack equipmentItem, materialItem, templateItem, resultItem;
        private final int materialAmount, templateAmount;
        Recipes(ItemStack equipmentItem, ItemStack materialItem, int materialAmount, ItemStack templateItem, int templateAmount, ItemStack resultItem) {
            this.equipmentItem = equipmentItem;
            this.materialItem = materialItem;
            this.materialAmount = materialAmount;
            this.templateItem = templateItem;
            this.templateAmount = templateAmount;
            this.resultItem = resultItem;
        }

        public static ItemStack createResult(Inventory inventory, ItemStack inputItem) {

            ItemStack equipmentSlot = inventory.getItem(SmithingTableGUI.SmithingTableEnum.EQUIPMENT_ITEM.getInventorySlot());
            ItemStack materialSlot = inventory.getItem(SmithingTableGUI.SmithingTableEnum.MATERIAL_ITEM.getInventorySlot());
            ItemStack templateSlot = inventory.getItem(SmithingTableGUI.SmithingTableEnum.TEMPLATE_ITEM.getInventorySlot());


            ItemMeta inputMeta = inputItem.getItemMeta();
            ItemForger inputItemForger = new ItemForger(inputItem);

            for (Recipes recipes : values()) {

                ItemMeta equipRecipeMeta = recipes.getEquipmentItem().getItemMeta();

                // Check that input is a valid equipment item
                if (materialSlot != null && templateSlot != null) {
                    // Check custom model data for input and recipe items

                    int materialSlotAmt = materialSlot.getAmount();
                    int templateSlotAmt = templateSlot.getAmount();

                    int materialRecipeAmt = recipes.getMaterialAmount();
                    int templateRecipeAmt = recipes.getTemplateAmount();

                    if (inputMeta != null) {
                        if (inputMeta.hasCustomModelData() && equipRecipeMeta.hasCustomModelData()) {
                            int inputEquipCMD = inputItemForger.getCustomModelData();
                            int recipeEquipCMD = equipRecipeMeta.getCustomModelData();

                            // Check if required item types are present
                            if (inputEquipCMD == recipeEquipCMD && materialSlot.equals(recipes.getMaterialItem()) && templateSlot.equals(recipes.getTemplateItem())) {

                                // Check if quantity is correct

                                if (materialSlotAmt <= materialRecipeAmt && templateSlotAmt <= templateRecipeAmt) {
                                    return recipes.getResultItem();
                                }
                            }

                        }
                    }
                }

                // Check that input is a valid material item
                if (equipmentSlot != null && templateSlot != null) {
                    // Check custom model data for input and recipe items
                    ItemMeta equipmentSlotMeta = equipmentSlot.getItemMeta();

                    int inputAmount = inputItem.getAmount();
                    int templateSlotAmt = templateSlot.getAmount();

                    int materialRecipeAmt = recipes.getMaterialAmount();
                    int templateRecipeAmt = recipes.getTemplateAmount();

                    if (equipmentSlotMeta != null) {
                        if (equipmentSlotMeta.hasCustomModelData() && equipRecipeMeta.hasCustomModelData()) {
                            int equipmentSlotCMD = equipmentSlotMeta.getCustomModelData();
                            int equipmentRecipeCMD = equipRecipeMeta.getCustomModelData();

                            if (equipmentSlotCMD == equipmentRecipeCMD && inputItem.equals(recipes.getMaterialItem()) && templateSlot.equals(recipes.getTemplateItem())) {

                                if (inputAmount >= materialRecipeAmt && templateSlotAmt >= templateRecipeAmt) {
                                    return recipes.getResultItem();
                                }
                            }
                        }
                    }
                }

                // Check that input is a valid template item
                if (equipmentSlot != null && materialSlot != null) {
                    // Check custom model data for input and recipe items
                    ItemMeta equipmentSlotMeta = equipmentSlot.getItemMeta();

                    int materialSlotAmt = materialSlot.getAmount();
                    int inputAmount = inputItem.getAmount();

                    int materialRecipeAmt = recipes.getMaterialAmount();
                    int templateRecipeAmt = recipes.getTemplateAmount();

                    if (equipmentSlotMeta != null) {
                        if (equipmentSlotMeta.hasCustomModelData() && equipRecipeMeta.hasCustomModelData()) {
                            int equipmentSlotCMD = equipmentSlotMeta.getCustomModelData();
                            int equipmentRecipeCMD = equipRecipeMeta.getCustomModelData();

                            if (equipmentSlotCMD == equipmentRecipeCMD && materialSlot.equals(recipes.getMaterialItem()) && inputItem.equals(recipes.getTemplateItem())) {

                                if (materialSlotAmt >= materialRecipeAmt && inputAmount >= templateRecipeAmt) {
                                    return recipes.getResultItem();
                                }
                            }
                        }
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

        public int getMaterialAmount() {
            return materialAmount;
        }

        public int getTemplateAmount() {
            return templateAmount;
        }
    }

}
