package net.laserdiamond.serverplugin1201.items.crafting.SmithingTable;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.items.crafting.Recipes.SmithingTableRecipes;
import net.laserdiamond.serverplugin1201.items.management.ItemForger;
import net.laserdiamond.serverplugin1201.items.management.UpdateItem;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.HashMap;

public class SmithingTableCrafting implements Listener
{

    private ServerPlugin1201 plugin;
    public SmithingTableCrafting(ServerPlugin1201 plugin) {
        this.plugin = plugin;
    }

    public static void init()
    {
        //recipeTest();
    }

    // TODO: If using vanilla smithing table, dummy recipe(s) may need to be added in order to allow new items to be inserted in the craft interface
    // TODO: Otherwise, no need :)

    /*
    public static void recipeTest() {

        SmithingTransformRecipe test = new SmithingTransformRecipe(NamespacedKey.minecraft("test"), new ItemStack(Material.AIR), new RecipeChoice.MaterialChoice(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE), new RecipeChoice.MaterialChoice(Material.DIAMOND_BOOTS), new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT), false);
        Bukkit.getServer().addRecipe(test);
    }

     */

    // HashMap for Determining Trim Material
    public final static HashMap<Material, TrimMaterial> trimMaterialMap = new HashMap<>();
    static
    {
        trimMaterialMap.put(Material.COPPER_INGOT, TrimMaterial.COPPER);
        trimMaterialMap.put(Material.GOLD_INGOT, TrimMaterial.GOLD);
        trimMaterialMap.put(Material.IRON_INGOT, TrimMaterial.IRON);
        trimMaterialMap.put(Material.LAPIS_LAZULI, TrimMaterial.LAPIS);
        trimMaterialMap.put(Material.QUARTZ, TrimMaterial.QUARTZ);
        trimMaterialMap.put(Material.REDSTONE, TrimMaterial.REDSTONE);
        trimMaterialMap.put(Material.EMERALD, TrimMaterial.EMERALD);
        trimMaterialMap.put(Material.AMETHYST_SHARD, TrimMaterial.AMETHYST);
        trimMaterialMap.put(Material.DIAMOND, TrimMaterial.DIAMOND);
        trimMaterialMap.put(Material.NETHERITE_INGOT, TrimMaterial.NETHERITE);
    }

    // HashMap for determining trim pattern
    public final static HashMap<Material, TrimPattern> trimPatternMap = new HashMap<>();
    static
    {
        trimPatternMap.put(Material.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.SENTRY);
        trimPatternMap.put(Material.VEX_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.VEX);
        trimPatternMap.put(Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.WILD);
        trimPatternMap.put(Material.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.COAST);
        trimPatternMap.put(Material.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.DUNE);
        trimPatternMap.put(Material.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.WAYFINDER);
        trimPatternMap.put(Material.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.RAISER);
        trimPatternMap.put(Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.SHAPER);
        trimPatternMap.put(Material.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.HOST);
        trimPatternMap.put(Material.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.WARD);
        trimPatternMap.put(Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.SILENCE);
        trimPatternMap.put(Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.TIDE);
        trimPatternMap.put(Material.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.SNOUT);
        trimPatternMap.put(Material.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.RIB);
        trimPatternMap.put(Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.EYE);
        trimPatternMap.put(Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.SPIRE);
    }

    @EventHandler
    public void prepareSmithingEvent(PrepareSmithingEvent event) {

        SmithingInventory smithingInventory = event.getInventory();
        ItemStack equipmentInput = smithingInventory.getInputEquipment();
        ItemStack materialInput = smithingInventory.getInputMineral();
        ItemStack templateInput = smithingInventory.getInputTemplate();

        if (templateInput != null && materialInput != null && equipmentInput != null)
        {
            // TODO: Check if result is correct result, otherwise return air

            ItemStack resultTestStack = SmithingTableRecipes.Recipes.createResultTest(equipmentInput, materialInput, templateInput);
            event.setResult(resultTestStack);
        }

        // Try to make armor trim
        try
        {
            if (templateInput != null && materialInput != null && equipmentInput != null)
            {
                ItemMeta templateInputMeta = templateInput.getItemMeta();
                ItemMeta materialInputMeta = materialInput.getItemMeta();

                // Make sure template DOES NOT have customModelData
                if (templateInputMeta != null && materialInputMeta != null) // Want to apply trim
                {
                    if (!templateInputMeta.hasCustomModelData() && !materialInputMeta.hasCustomModelData())
                    {
                        if (trimPatternMap.containsKey(templateInput.getType()) && trimMaterialMap.containsKey(materialInput.getType())) // Check if HashMaps have key for material
                        {
                            TrimPattern patternToAdd = trimPatternMap.get(templateInput.getType());
                            TrimMaterial materialToAdd = trimMaterialMap.get(materialInput.getType());

                            ArmorTrim trimToAdd = new ArmorTrim(materialToAdd, patternToAdd);
                            ItemStack resultItem = equipmentInput.clone();
                            ItemForger resultForger = new ItemForger(resultItem);

                            resultForger.setArmorTrim(trimToAdd);
                            resultForger.setLore(UpdateItem.renewLore(resultForger.toItemStack()));
                            event.setResult(resultForger.toItemStack());
                        }

                    }
                }
            }
        }
        catch (ClassCastException ignored)
        {
        }
    }

    @EventHandler
    public void smithingTableCollectResult(InventoryClickEvent event)
    {

        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory() instanceof SmithingInventory)
        {

            ItemStack resultItem = event.getClickedInventory().getItem(3);
            ItemStack equipmentItemRawSlot = event.getClickedInventory().getItem(1);
            ItemStack templateItemRawSlot = event.getClickedInventory().getItem(0);
            ItemStack materialItemRawSlot = event.getClickedInventory().getItem(2);
            int slotClicked = event.getSlot();

            if (slotClicked == 3) {
                boolean ready = false;

                // TODO: Optimize more!!!
                // Take more stuff out of for-loop
                for (SmithingTableRecipes.Recipes recipes : SmithingTableRecipes.Recipes.values())
                {
                    ItemStack recipesResult = recipes.getResult();
                    ItemStack equipment = recipes.getSmithingRecipe().equipmentItem();
                    ItemStack material = recipes.getSmithingRecipe().materialItem();
                    ItemStack template = recipes.getSmithingRecipe().templateItem();

                    if (resultItem != null && equipmentItemRawSlot != null && templateItemRawSlot != null && materialItemRawSlot != null)
                    {
                        if (resultItem.getType().equals(recipesResult.getType()))
                        {
                            ItemMeta resultItemMeta = resultItem.getItemMeta();
                            ItemMeta resultMeta = recipesResult.getItemMeta();

                            if (resultItemMeta != null && resultMeta != null)
                            {
                                if (resultItemMeta.hasCustomModelData() && resultMeta.hasCustomModelData())
                                {
                                    if (resultItemMeta.getCustomModelData() == resultMeta.getCustomModelData())
                                    {
                                        ready = true;
                                    }
                                } else if (!resultItemMeta.hasCustomModelData() && !resultMeta.hasCustomModelData())
                                {
                                    ready = true;
                                }

                                if (ready)
                                {
                                    equipmentItemRawSlot.setAmount(equipmentItemRawSlot.getAmount() - equipment.getAmount());
                                    materialItemRawSlot.setAmount(materialItemRawSlot.getAmount() - material.getAmount());
                                    templateItemRawSlot.setAmount(templateItemRawSlot.getAmount() - template.getAmount());

                                    player.playSound(player, Sound.BLOCK_SMITHING_TABLE_USE, 100, 1);

                                    // TODO: Check if result is correct result, otherwise return air

                                    event.setCurrentItem(SmithingTableRecipes.Recipes.createResultTest(equipmentItemRawSlot, materialItemRawSlot, templateItemRawSlot));
                                    event.setCursor(resultItem);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
