package net.laserdiamond.serverplugin1201.items.crafting.SmithingTable;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.items.crafting.Recipes.SmithingTableRecipes;
import net.laserdiamond.serverplugin1201.items.equipment.EquipmentItems;
import net.laserdiamond.serverplugin1201.items.management.misc.MenuItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SmithingTableGUI implements Listener {

    private ServerPlugin1201 plugin;

    public SmithingTableGUI(ServerPlugin1201 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void cancelSmithingTableInv(InventoryOpenEvent event) {

        HumanEntity humanEntity = event.getPlayer();

        if (humanEntity instanceof Player player) {
            if (event.getInventory().getType().equals(InventoryType.SMITHING)) {

                event.setCancelled(true);

                Inventory smithingInv = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "Smithing Table");

                for (SmithingTableEnum smithingTableEnum : SmithingTableEnum.values()) {
                    ItemStack smithingItemGUI = smithingTableEnum.getItemStack();
                    int slot = smithingTableEnum.getInventorySlot();
                    smithingInv.setItem(slot, smithingItemGUI);
                }

                ItemStack[] contents = smithingInv.getContents();
                int i = 0;
                for (ItemStack itemStack : contents) {
                    if (itemStack == null) {
                        if (!SmithingTableEnum.isInputSlot(i) && i != SmithingTableEnum.RESULT_ITEM.getInventorySlot()) {
                            smithingInv.setItem(i, MenuItems.createMenuItem(MenuItems.MenuItemEnum.BLANK_ITEM).toItemStack());
                        }

                    }
                    i++;
                }

                player.openInventory(smithingInv);
            }
        }
    }

    @EventHandler
    public void clickSmithingTable(InventoryClickEvent event) {

        HumanEntity humanEntity = event.getWhoClicked();

        if (humanEntity instanceof Player player) {
            if (event.getView().getTitle().contains("Smithing Table")) {

                ItemStack clickedItem = event.getCurrentItem();

                int clickedSlot = event.getSlot();

                ItemStack cursorItem = event.getCursor();
                Inventory clickedInv = event.getClickedInventory();

                // Cancel clicking in input slots
                if (clickedInv != null && !clickedInv.equals(player.getInventory())) {
                    event.setCancelled(true);

                    // Allow players to input items into appropriate slots


                    if (clickedSlot == SmithingTableEnum.EQUIPMENT_ITEM.getInventorySlot()) {
                        if (SmithingTableEnum.EQUIPMENT_ITEM.possibleItemTypes.contains(cursorItem.getType()) || cursorItem.getType().equals(Material.AIR)) {
                            //player.sendMessage("equipment slot");
                            event.setResult(Event.Result.ALLOW);

                        }

                    } else if (clickedSlot == SmithingTableEnum.MATERIAL_ITEM.getInventorySlot()) {
                        if (SmithingTableEnum.MATERIAL_ITEM.possibleItemTypes.contains(cursorItem.getType()) || cursorItem.getType().equals(Material.AIR)) {
                            //player.sendMessage("material slot");
                            event.setResult(Event.Result.ALLOW);

                        }

                    } else if (clickedSlot == SmithingTableEnum.TEMPLATE_ITEM.getInventorySlot()) {
                        if (SmithingTableEnum.TEMPLATE_ITEM.possibleItemTypes.contains(cursorItem.getType()) || cursorItem.getType().equals(Material.AIR)) {
                            //player.sendMessage("template slot");
                            event.setResult(Event.Result.ALLOW);

                        }

                    }

                    // TODO: Create Result Item


                }

            }
        }
    }

    private void inputItemResource(int clickedSlot, SmithingTableEnum smithingTableEnum, ItemStack inputItem, Player player) {

        if (clickedSlot == smithingTableEnum.getInventorySlot()) {
            if (smithingTableEnum.getPossibleItemTypes().contains(inputItem.getType())) {
                player.sendMessage("put resource into slot: " + smithingTableEnum.getInventorySlot());

            }
        }
    }

    @EventHandler
    public void closeSmithingTableInv(InventoryCloseEvent event) {

        HumanEntity humanEntity = event.getPlayer();

        if (humanEntity instanceof Player player) {
            if (event.getView().getTitle().contains("Smithing Table")) {
                player.sendMessage("Closed smithing inventory");

                // Give player input items back to player
                Inventory eventInv = event.getInventory();

                /*
                ItemStack inputEquipment = eventInv.getItem(SmithingTableEnum.EQUIPMENT_ITEM.getInventorySlot());
                ItemStack inputMaterial = eventInv.getItem(SmithingTableEnum.MATERIAL_ITEM.getInventorySlot());
                ItemStack inputTemplate = eventInv.getItem(SmithingTableEnum.TEMPLATE_ITEM.getInventorySlot());

                 */

                for (SmithingTableEnum smithingTableEnum : SmithingTableEnum.values()) {
                    int enumSlot = smithingTableEnum.getInventorySlot();
                    if (SmithingTableEnum.isInputSlot(enumSlot)) {
                        ItemStack inputItem = eventInv.getItem(enumSlot);
                        if (SmithingTableEnum.isSlotItem(inputItem)) {
                            player.sendMessage("no input items were present; return no items");
                        } else {
                            if (inputItem != null) {
                                player.sendMessage("adding input item to inventory");
                                player.getInventory().addItem(inputItem);

                            }
                        }

                    }
                }

            }
        }

    }

    public enum SmithingTableEnum {

        EQUIPMENT_ITEM (38, new ItemStack(Material.AIR), EquipmentItems.any),
        MATERIAL_ITEM (40, new ItemStack(Material.AIR), SmithingPossibleIngredients.materials),
        TEMPLATE_ITEM (42, new ItemStack(Material.AIR), SmithingPossibleIngredients.templates),
        SMITH_ITEM (22, SmithingMenuItems.createEquipmentItem(SmithingMenuItems.SmithingItemEnum.SMITH_ITEM).toItemStack(), null),
        RESULT_ITEM (13, new ItemStack(Material.AIR), null);

        private final int InventorySlot;
        private final ItemStack itemStack;
        private final List<Material> possibleItemTypes;
        SmithingTableEnum(int InventorySlot, ItemStack itemStack, List<Material> possibleItemTypes) {
            this.InventorySlot = InventorySlot;
            this.itemStack = itemStack;
            this.possibleItemTypes = possibleItemTypes;
        }

        public static boolean isInputSlot(int slot) {
            for (SmithingTableEnum smithingTableEnum : values()) {
                if (smithingTableEnum.getInventorySlot() == slot) {
                    if (smithingTableEnum.getInventorySlot() != SMITH_ITEM.getInventorySlot() &&
                        smithingTableEnum.getInventorySlot() != RESULT_ITEM.getInventorySlot()) {
                        return true;
                    }
                }
            }
            return false;
        }

        public static boolean isSlotItem(ItemStack inputItem) {
            for (SmithingTableEnum smithingTableEnum : values()) {
                if (smithingTableEnum.getItemStack().equals(inputItem)) {
                    return true;
                }
            }
            return false;
        }

        public static final List<ItemStack> slotItem = new ArrayList<>();
        static {
            for (SmithingTableEnum smithingTableEnum : values()) {
                slotItem.add(smithingTableEnum.getItemStack());
            }
        }

        public int getInventorySlot() {
            return InventorySlot;
        }

        public ItemStack getItemStack() {
            return itemStack;
        }

        public List<Material> getPossibleItemTypes() {
            return possibleItemTypes;
        }
    }
}
