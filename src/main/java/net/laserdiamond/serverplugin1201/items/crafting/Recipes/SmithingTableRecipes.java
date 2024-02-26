package net.laserdiamond.serverplugin1201.items.crafting.Recipes;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.items.armor.Vanilla.Components.DiamondArmorManager;
import net.laserdiamond.serverplugin1201.items.armor.Vanilla.Components.NetheriteArmorManager;
import net.laserdiamond.serverplugin1201.items.crafting.SmithingTable.SmithingRecipe;
import net.laserdiamond.serverplugin1201.items.management.ItemForger;
import net.laserdiamond.serverplugin1201.items.management.UpdateItem;
import net.laserdiamond.serverplugin1201.items.management.armor.ArmorTypes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.SmithingInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;

import java.util.HashMap;
import java.util.Map;


public class SmithingTableRecipes {

    private static final ServerPlugin1201 plugin = ServerPlugin1201.getInstance();
    private static final DiamondArmorManager diamondArmorManager = plugin.getDiamondArmorManager();
    private static final NetheriteArmorManager netheriteArmorManager = plugin.getNetheriteArmorManager();


    public enum Recipes {

        NETHERITE_SWORD (new SmithingRecipe(new ItemStack(Material.DIAMOND_SWORD), new ItemStack(Material.NETHERITE_INGOT,1), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), new ItemStack(Material.NETHERITE_SWORD)),
        NETHERITE_AXE (new SmithingRecipe(new ItemStack(Material.DIAMOND_AXE), new ItemStack(Material.NETHERITE_INGOT,1), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), new ItemStack(Material.NETHERITE_AXE)),
        NETHERITE_PICKAXE (new SmithingRecipe(new ItemStack(Material.DIAMOND_PICKAXE), new ItemStack(Material.NETHERITE_INGOT,1), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), new ItemStack(Material.NETHERITE_PICKAXE)),
        NETHERITE_SHOVEL (new SmithingRecipe(new ItemStack(Material.DIAMOND_SHOVEL), new ItemStack(Material.NETHERITE_INGOT,1), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), new ItemStack(Material.NETHERITE_SHOVEL)),
        NETHERITE_HOE (new SmithingRecipe(new ItemStack(Material.DIAMOND_SWORD), new ItemStack(Material.NETHERITE_INGOT,1), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), new ItemStack(Material.NETHERITE_HOE)),
        NETHERITE_BOOTS (new SmithingRecipe(new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.NETHERITE_INGOT,1), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), netheriteArmorManager.createArmorPiece(ArmorTypes.BOOTS,0).toItemStack()),
        NETHERITE_LEGGINGS (new SmithingRecipe(new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.NETHERITE_INGOT,1), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), netheriteArmorManager.createArmorPiece(ArmorTypes.LEGGINGS,0).toItemStack()),
        NETHERITE_CHESTPLATE (new SmithingRecipe(new ItemStack(Material.DIAMOND_CHESTPLATE), new ItemStack(Material.NETHERITE_INGOT,1), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), netheriteArmorManager.createArmorPiece(ArmorTypes.CHESTPLATE,0).toItemStack()),
        NETHERITE_HELMET (new SmithingRecipe(new ItemStack(Material.DIAMOND_HELMET), new ItemStack(Material.NETHERITE_INGOT,1), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), netheriteArmorManager.createArmorPiece(ArmorTypes.HELMET,0).toItemStack());

        private final SmithingRecipe smithingRecipe;
        private final ItemStack result;

        Recipes(SmithingRecipe smithingRecipe, ItemStack result) {
            this.smithingRecipe = smithingRecipe;
            this.result = result;
        }

        public static final HashMap<SmithingRecipe, ItemStack> ingredientsResultMap = new HashMap<>();
        static {
            for (Recipes recipes : values()) {
                SmithingRecipe smithingRecipe = recipes.smithingRecipe;
                ItemStack result = recipes.result;

                ingredientsResultMap.put(smithingRecipe, result);
            }
        }

       public static ItemStack createResult(ItemStack equipmentInput, ItemStack materialInput, ItemStack templateInput) {

            boolean correctEquipment = false;
            boolean correctMaterial = false;
            boolean correctTemplate = false;

            int inputMaterialAmount;
            int inputTemplateAmount;

            for (Recipes recipes : values()) {
                ItemStack recipeEquipment = recipes.smithingRecipe.getEquipmentItem();
                ItemStack recipeMaterial = recipes.smithingRecipe.getMaterialItem();
                ItemStack recipeTemplate = recipes.smithingRecipe.getTemplateItem();
                ItemStack result = recipes.result;

                int requiredMaterialAmount;
                int requiredTemplateAmount;

                if (equipmentInput != null && recipeEquipment != null) {

                    ItemMeta inputMeta = equipmentInput.getItemMeta();
                    ItemMeta recipeMeta = recipeEquipment.getItemMeta();

                    if (equipmentInput.getType().equals(recipeEquipment.getType())) {

                        if (inputMeta != null && recipeMeta != null) {

                            if (inputMeta.hasCustomModelData() && recipeMeta.hasCustomModelData()) {

                                if (inputMeta.getCustomModelData() == recipeMeta.getCustomModelData()) {
                                    correctEquipment = true;
                                }
                            } else if (!inputMeta.hasCustomModelData() && !recipeMeta.hasCustomModelData()) {
                                correctEquipment = true;
                            }
                        }
                    }
                }

                if (materialInput != null && recipeMaterial != null) {

                    ItemMeta inputMeta = materialInput.getItemMeta();
                    ItemMeta recipeMeta = recipeMaterial.getItemMeta();

                    inputMaterialAmount = materialInput.getAmount();
                    requiredMaterialAmount = recipeMaterial.getAmount();

                    if (materialInput.getType().equals(recipeMaterial.getType())) {

                        if (inputMeta != null && recipeMeta != null) {

                            if (inputMaterialAmount >= requiredMaterialAmount) {

                                if (inputMeta.hasCustomModelData() && recipeMeta.hasCustomModelData()) {

                                    if (inputMeta.getCustomModelData() == recipeMeta.getCustomModelData()) {
                                        correctMaterial = true;
                                    }
                                } else if (!inputMeta.hasCustomModelData() && !recipeMeta.hasCustomModelData()) {
                                    correctMaterial = true;
                                }
                            }


                        }
                    }
                }

                if (templateInput != null && recipeTemplate != null) {

                    ItemMeta inputMeta = templateInput.getItemMeta();
                    ItemMeta recipeMeta = recipeTemplate.getItemMeta();

                    inputTemplateAmount = templateInput.getAmount();
                    requiredTemplateAmount = recipeTemplate.getAmount();

                    if (templateInput.getType().equals(recipeTemplate.getType())) {

                        if (inputMeta != null && recipeMeta != null) {

                            if (inputTemplateAmount >= requiredTemplateAmount) {

                                if (inputMeta.hasCustomModelData() && recipeMeta.hasCustomModelData()) {

                                    if (inputMeta.getCustomModelData() == recipeMeta.getCustomModelData()) {
                                        correctTemplate = true;
                                    }
                                } else if (!inputMeta.hasCustomModelData() && !recipeMeta.hasCustomModelData()) {
                                    correctTemplate = true;
                                }
                            }

                        }
                    }
                }

                if (correctEquipment && correctMaterial && correctTemplate) {
                    // Return result, etc.

                    ItemForger resultForger = new ItemForger(result);
                    ItemForger inputEquipmentForger = new ItemForger(equipmentInput);

                    Map<Enchantment, Integer> enchantsToAdd = inputEquipmentForger.getEnchantments();
                    int starsToAdd = inputEquipmentForger.getStars();

                    if (inputEquipmentForger.hasEnchants()) {
                        resultForger.removeEnchantments();
                        resultForger.addEnchantments(enchantsToAdd);
                    } else {
                        resultForger.removeEnchantments();
                    }

                    resultForger.setStars(starsToAdd);

                    try {
                        ArmorTrim trimToAdd = inputEquipmentForger.getArmorTrim();
                        if (inputEquipmentForger.hasArmorTrim()) {
                            resultForger.removeArmorTrim();
                            resultForger.setArmorTrim(trimToAdd);
                        } else {
                            resultForger.removeArmorTrim();
                        }

                    } catch (ClassCastException ignored) {
                    }
                    resultForger.setLore(UpdateItem.renewLore(resultForger.toItemStack()));

                    return resultForger.toItemStack();
                }
            }

            return new ItemStack(Material.AIR);
       }

        public static void consumeMaterials(SmithingInventory smithingInventory) {

            ItemStack equipmentItem = smithingInventory.getInputEquipment();
            ItemStack materialItem = smithingInventory.getInputMineral();
            ItemStack templateItem = smithingInventory.getInputTemplate();
            ItemStack resultItem = smithingInventory.getResult();

            // Make sure none of the slots in the inventory are null
            if (resultItem != null && equipmentItem != null && materialItem != null && templateItem != null) {

                // Determine what recipe is being made to remove proper amount of ingredients
                for (Recipes recipes : values()) {

                    ItemStack result = recipes.result;
                    ItemStack equipment = recipes.smithingRecipe.getEquipmentItem();
                    ItemStack material = recipes.smithingRecipe.getMaterialItem();
                    ItemStack template = recipes.smithingRecipe.getTemplateItem();

                    int recipeEquipmentAmt = equipment.getAmount();
                    int recipeMaterialAmt = material.getAmount();
                    int recipeTemplateAmt = template.getAmount();

                    if (resultItem.getType().equals(result.getType())) {

                        ItemMeta resultItemMeta = resultItem.getItemMeta();
                        ItemMeta resultMeta = result.getItemMeta();

                        if (resultItemMeta != null && resultMeta != null) {

                            if (resultItemMeta.hasCustomModelData() && resultMeta.hasCustomModelData()) {

                                if (resultItemMeta.getCustomModelData() == resultMeta.getCustomModelData()) {
                                    equipmentItem.setAmount(equipmentItem.getAmount() - recipeEquipmentAmt);
                                    materialItem.setAmount(materialItem.getAmount() - recipeMaterialAmt);
                                    templateItem.setAmount(templateItem.getAmount() - recipeTemplateAmt);
                                }
                            } else if (!resultItemMeta.hasCustomModelData() && !resultMeta.hasCustomModelData()) {
                                equipmentItem.setAmount(equipmentItem.getAmount() - recipeEquipmentAmt);
                                materialItem.setAmount(materialItem.getAmount() - recipeMaterialAmt);
                                templateItem.setAmount(templateItem.getAmount() - recipeTemplateAmt);
                            }

                        }
                    }


                }
            }
        }

        public SmithingRecipe getSmithingRecipe() {
            return smithingRecipe;
        }

        public ItemStack getResult() {
            return result;
        }
    }



}
