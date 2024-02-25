package net.laserdiamond.serverplugin1201.items.crafting.Recipes;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.items.armor.Vanilla.Components.DiamondArmorManager;
import net.laserdiamond.serverplugin1201.items.armor.Vanilla.Components.NetheriteArmorManager;
import net.laserdiamond.serverplugin1201.items.crafting.SmithingTable.SmithingRecipe;
import net.laserdiamond.serverplugin1201.items.management.armor.ArmorTypes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;


public class SmithingTableRecipes {

    private static final ServerPlugin1201 plugin = ServerPlugin1201.getInstance();
    private static final DiamondArmorManager diamondArmorManager = plugin.getDiamondArmorManager();
    private static final NetheriteArmorManager netheriteArmorManager = plugin.getNetheriteArmorManager();


    public enum Recipes {

        NETHERITE_BOOTS (new SmithingRecipe(new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.NETHERITE_INGOT,2), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), netheriteArmorManager.createArmorPiece(ArmorTypes.BOOTS,0).toItemStack()),
        NETHERITE_LEGGINGS (new SmithingRecipe(new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.NETHERITE_INGOT,2), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), netheriteArmorManager.createArmorPiece(ArmorTypes.LEGGINGS,0).toItemStack()),
        NETHERITE_CHESTPLATE (new SmithingRecipe(new ItemStack(Material.DIAMOND_CHESTPLATE), new ItemStack(Material.NETHERITE_INGOT,2), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), netheriteArmorManager.createArmorPiece(ArmorTypes.CHESTPLATE,0).toItemStack()),
        NETHERITE_HELMET (new SmithingRecipe(new ItemStack(Material.DIAMOND_HELMET), new ItemStack(Material.NETHERITE_INGOT,2), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), netheriteArmorManager.createArmorPiece(ArmorTypes.HELMET,0).toItemStack());

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

                    return result;
                }
            }

            return new ItemStack(Material.AIR);
       }



        public SmithingRecipe getSmithingRecipe() {
            return smithingRecipe;
        }

        public ItemStack getResult() {
            return result;
        }
    }



}
