package net.laserdiamond.ventureplugin.items.crafting.Recipes;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.armor.components.NetheriteArmor;
import net.laserdiamond.ventureplugin.items.crafting.SmithingTable.SmithingRecipe;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorPieceTypes;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;


public class SmithingTableRecipes {

    private static final VenturePlugin plugin = VenturePlugin.getInstance();
    private static final NetheriteArmor NETHERITE_ARMOR = plugin.getNetheriteArmorManager();


    public enum Recipes {

        NETHERITE_SWORD (new SmithingRecipe(new ItemStack(Material.DIAMOND_SWORD), new ItemStack(Material.NETHERITE_INGOT,1), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), new ItemStack(Material.NETHERITE_SWORD)),
        NETHERITE_AXE (new SmithingRecipe(new ItemStack(Material.DIAMOND_AXE), new ItemStack(Material.NETHERITE_INGOT,1), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), new ItemStack(Material.NETHERITE_AXE)),
        NETHERITE_PICKAXE (new SmithingRecipe(new ItemStack(Material.DIAMOND_PICKAXE), new ItemStack(Material.NETHERITE_INGOT,1), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), new ItemStack(Material.NETHERITE_PICKAXE)),
        NETHERITE_SHOVEL (new SmithingRecipe(new ItemStack(Material.DIAMOND_SHOVEL), new ItemStack(Material.NETHERITE_INGOT,1), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), new ItemStack(Material.NETHERITE_SHOVEL)),
        NETHERITE_HOE (new SmithingRecipe(new ItemStack(Material.DIAMOND_HOE), new ItemStack(Material.NETHERITE_INGOT,1), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), new ItemStack(Material.NETHERITE_HOE)),
        NETHERITE_BOOTS (new SmithingRecipe(new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.NETHERITE_INGOT,1), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), NETHERITE_ARMOR.createArmorSet(ArmorPieceTypes.BOOTS, 0).toItemStack()),
        NETHERITE_LEGGINGS (new SmithingRecipe(new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.NETHERITE_INGOT,1), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), NETHERITE_ARMOR.createArmorSet(ArmorPieceTypes.LEGGINGS,0).toItemStack()),
        NETHERITE_CHESTPLATE (new SmithingRecipe(new ItemStack(Material.DIAMOND_CHESTPLATE), new ItemStack(Material.NETHERITE_INGOT,1), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), NETHERITE_ARMOR.createArmorSet(ArmorPieceTypes.CHESTPLATE,0).toItemStack()),
        NETHERITE_HELMET (new SmithingRecipe(new ItemStack(Material.DIAMOND_HELMET), new ItemStack(Material.NETHERITE_INGOT,1), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,1)), NETHERITE_ARMOR.createArmorSet(ArmorPieceTypes.HELMET,0).toItemStack());

        private final SmithingRecipe smithingRecipe;
        private final ItemStack result;

        Recipes(SmithingRecipe smithingRecipe, ItemStack result)
        {
            this.smithingRecipe = smithingRecipe;
            this.result = result;
        }


        public static ItemStack createResultTest(ItemStack equipmentInput, ItemStack materialInput, ItemStack templateInput)
        {
            Material equipmentInputType = null, materialInputType = null, templateInputType = null;
            ItemMeta equipmentInputMeta = null, materialInputMeta = null, templateInputMeta = null;

            if (equipmentInput != null)
            {
                equipmentInputType = equipmentInput.getType();
                equipmentInputMeta = equipmentInput.getItemMeta();
            }

            if (materialInput != null)
            {
                materialInputType = materialInput.getType();
                materialInputMeta = materialInput.getItemMeta();
            }

            if (templateInput != null)
            {
                templateInputType = templateInput.getType();
                templateInputMeta = templateInput.getItemMeta();
            }

            ItemStack outputResult = null;

            // Loop through ingredients for matching recipe
            // Once matching ingredients are found for each, break out of loop
            int matchCount;
            for (Recipes recipes : values())
            {
                ItemStack recipeEquipment = recipes.smithingRecipe.equipmentItem(), recipeMaterial = recipes.smithingRecipe.materialItem(), recipeTemplate = recipes.smithingRecipe.templateItem();
                matchCount = 0;
                if (equipmentInputType.equals(recipeEquipment.getType())) // Check for matching equipment piece
                {
                    if (equipmentInputMeta != null && recipeEquipment.getItemMeta() != null)
                    {
                        if (equipmentInputMeta.hasCustomModelData() && recipeEquipment.getItemMeta().hasCustomModelData())
                        {
                            if (equipmentInputMeta.getCustomModelData() == recipeEquipment.getItemMeta().getCustomModelData())
                            {
                                matchCount++; // Matching item found, add 1 to counter
                            }
                        } else if (!equipmentInputMeta.hasCustomModelData() && !recipeEquipment.getItemMeta().hasCustomModelData())
                        {
                            matchCount++; // Matching item found, add 1 to counter
                        }
                    }
                }
                if (matchCount < 1) // If matchCount is less than 1, skip to next iteration
                {
                    continue;
                }
                if ((matchCount == 1) && (materialInputType.equals(recipeMaterial.getType()))) // Check for matching material
                {
                    if (materialInputMeta != null && recipeMaterial.getItemMeta() != null)
                    {
                        if (materialInput.getAmount() >= recipeMaterial.getAmount())
                        {
                            if (materialInputMeta.hasCustomModelData() && recipeMaterial.getItemMeta().hasCustomModelData())
                            {
                                if (materialInputMeta.getCustomModelData() == recipeMaterial.getItemMeta().getCustomModelData())
                                {
                                    matchCount++; // Matching item found, add 1 to counter
                                }
                            } else if (!materialInputMeta.hasCustomModelData() && !recipeMaterial.getItemMeta().hasCustomModelData())
                            {
                                matchCount++; // Matching item found, add 1 to counter
                            }
                        }
                    }
                }
                if (matchCount < 2) // If matchCount is less than 2, skip to next iteration
                {
                    continue;
                }
                if ((matchCount == 2) && templateInputType.equals(recipeTemplate.getType())) // Check for matching template
                {
                    if (templateInputMeta != null && recipeTemplate.getItemMeta() != null)
                    {
                        if (templateInput.getAmount() >= recipeTemplate.getAmount())
                        {
                            if (templateInputMeta.hasCustomModelData() && recipeTemplate.getItemMeta().hasCustomModelData())
                            {
                                if (templateInputMeta.getCustomModelData() == recipeTemplate.getItemMeta().getCustomModelData())
                                {
                                    outputResult = recipes.result; // All three inputs are matching items, generate result
                                    break;
                                }
                            } else if (!templateInputMeta.hasCustomModelData() && !recipeTemplate.getItemMeta().hasCustomModelData())
                            {
                                outputResult = recipes.result; // All three inputs are matching items, generate result
                                break;
                            }
                        }
                    }
                }
            }

            if (outputResult == null) // No match; item is air
            {
                outputResult = new ItemStack(Material.AIR);
            } else { // Matching recipe, output result

                // TODO: Copy over enchantments, etc. from input equipment

                if (equipmentInput.getItemMeta() instanceof ArmorMeta armorMeta) {
                    ArmorTrim armorTrim = armorMeta.getTrim();
                    if (outputResult.getItemMeta() instanceof ArmorMeta armorMeta1) {
                        armorMeta1.setTrim(armorTrim);
                        outputResult.setItemMeta(armorMeta1);
                    }
                }
            }

            return outputResult;
        }

        public SmithingRecipe getSmithingRecipe() {
            return smithingRecipe;
        }


        public ItemStack getResult() {
            return result;
        }


    }



}
