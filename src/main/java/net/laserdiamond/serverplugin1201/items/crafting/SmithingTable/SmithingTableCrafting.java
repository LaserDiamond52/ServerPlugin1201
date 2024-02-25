package net.laserdiamond.serverplugin1201.items.crafting.SmithingTable;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.items.crafting.Recipes.SmithingTableRecipes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.*;

import java.util.HashMap;

public class SmithingTableCrafting implements Listener {

    private ServerPlugin1201 plugin;
    public SmithingTableCrafting(ServerPlugin1201 plugin) {
        this.plugin = plugin;
    }

    public static void init() {
        //recipeTest();
    }

    /*
    public static void recipeTest() {

        SmithingTransformRecipe test = new SmithingTransformRecipe(NamespacedKey.minecraft("test"), new ItemStack(Material.AIR), new RecipeChoice.MaterialChoice(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE), new RecipeChoice.MaterialChoice(Material.DIAMOND_BOOTS), new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT), false);
        Bukkit.getServer().addRecipe(test);
    }

     */

    private HashMap<SmithingRecipe, ItemStack> recipe = new HashMap<>();
    {
        //recipe.put(new SmithingRecipe(new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.NETHERITE_INGOT), new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE)), new ItemStack(Material.IRON_BOOTS));
    }


    @EventHandler
    public void prepareSmithingEvent(PrepareSmithingEvent event) {

        SmithingInventory smithingInventory = event.getInventory();
        ItemStack equipmentInput = smithingInventory.getInputEquipment();
        ItemStack materialInput = smithingInventory.getInputMineral();
        ItemStack templateInput = smithingInventory.getInputTemplate();

        ItemStack resultItemStack = SmithingTableRecipes.Recipes.createResult(equipmentInput, materialInput, templateInput);

        event.setResult(resultItemStack);
    }

    @EventHandler
    public void smithingTableClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory() instanceof SmithingInventory smithingInventory) {

            ItemStack resultItem = event.getClickedInventory().getItem(3);
            ItemStack equipmentItemRawSlot = event.getClickedInventory().getItem(1);
            ItemStack templateItemRawSlot = event.getClickedInventory().getItem(0);
            ItemStack materialItemRawSlot = event.getClickedInventory().getItem(2);
            int slotClicked = event.getSlot();

            ItemStack equipmentSlot = smithingInventory.getInputEquipment();
            ItemStack templateSlot = smithingInventory.getInputTemplate();
            ItemStack materialSlot = smithingInventory.getInputMineral();
            ItemStack resultSlot = smithingInventory.getResult();

            player.sendMessage("Clicked slot: " + slotClicked);

            if (slotClicked == 3) {
                player.sendMessage("result");
            } else if (slotClicked == 2) {
                player.sendMessage("material");
            } else if (slotClicked == 1) {
                player.sendMessage("equipment");
            } else if (slotClicked == 0) {
                player.sendMessage("template");
            }
        }
    }







}
