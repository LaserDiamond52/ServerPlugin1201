package net.laserdiamond.serverplugin1201.items.management;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.enchants.Components.EnchantsClass;
import net.laserdiamond.serverplugin1201.items.armor.StormLord.Components.StormLordArmorManager;
import net.laserdiamond.serverplugin1201.items.armor.Vanilla.Components.DiamondArmorManager;
import net.laserdiamond.serverplugin1201.items.armor.Vanilla.Components.NetheriteArmorManager;
import net.laserdiamond.serverplugin1201.items.management.armor.ArmorTypes;
import net.laserdiamond.serverplugin1201.items.management.misc.MenuItems;
import net.laserdiamond.serverplugin1201.stats.Components.StatsItemManager;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemMappings implements Listener {

    private ServerPlugin1201 plugin;
    private static DiamondArmorManager diamondArmorManager;
    private static NetheriteArmorManager netheriteArmorManager;
    private static StormLordArmorManager stormLordArmorManager;

    public ItemMappings(ServerPlugin1201 plugin) {
        this.plugin = plugin;
        diamondArmorManager = plugin.getDiamondArmorManager();
        netheriteArmorManager = plugin.getNetheriteArmorManager();
        stormLordArmorManager = plugin.getStormArmorManager();
    }
    public static HashMap<Integer, ItemForger> itemForgerHashMap(int stars) {

        // Map item's custom model data to the item forger item it is associated with
        HashMap<Integer, ItemForger> itemForgerHashMap = new HashMap<>();

        // Menu Items
        for (MenuItems.MenuItemEnum menuItemEnum : MenuItems.MenuItemEnum.values()) {
            itemForgerHashMap.put(menuItemEnum.getCmd(), MenuItems.createMenuItem(menuItemEnum));
        }

        // Armor Items
        for (ArmorTypes armorTypes : ArmorTypes.values()) {

            itemForgerHashMap.put(diamondArmorManager.createArmorPiece(armorTypes, stars).getCustomModelData(), diamondArmorManager.createArmorPiece(armorTypes, stars));
            itemForgerHashMap.put(netheriteArmorManager.createArmorPiece(armorTypes, stars).getCustomModelData(), netheriteArmorManager.createArmorPiece(armorTypes, stars));
            itemForgerHashMap.put(stormLordArmorManager.createArmorPiece(armorTypes, stars).getCustomModelData(), stormLordArmorManager.createArmorPiece(armorTypes, stars));
        }


        // Weapons/Tools

        // Misc

        return itemForgerHashMap;
    }

    public static HashMap<Integer, List<String>> itemLoreHashMapPlayer(int stars, Player player) {

        HashMap<Integer, List<String>> itemLoreHashMapPlayer = new HashMap<>();

        for (MenuItems.MenuItemEnum menuItemEnum : MenuItems.MenuItemEnum.values()) {
            itemLoreHashMapPlayer.put(menuItemEnum.getCmd(), null);
        }

        // Stat items
        for (StatsItemManager.StatsItem statsItem : StatsItemManager.StatsItem.values()) {
            itemLoreHashMapPlayer.put(statsItem.getCMD(), StatsItemManager.createLore(player, statsItem));
        }

        // Armor Items
        for (ArmorTypes armorTypes : ArmorTypes.values()) {
            itemLoreHashMapPlayer.put(stormLordArmorManager.createArmorPiece(armorTypes, stars).getCustomModelData(), stormLordArmorManager.createPlayerLore(player, armorTypes, stars));
        }

        // Weapons/Tools

        // Misc


        return itemLoreHashMapPlayer;
    }

    public static String enchantString(Enchantment enchantment, ItemMeta itemMeta) {


        String enchantString;
        String enchantmentName = EnchantsClass.EnchantmentNames.get(enchantment);
        int enchantLvl = itemMeta.getEnchantLevel(enchantment);

        if (enchantLvl == enchantment.getMaxLevel()) {
            // Enchant is Max Level

            // Check if is curse enchantment
            if (enchantment.isCursed()) {
                enchantString = ChatColor.RED + enchantmentName + " " + enchantLvl;
            } else {
                enchantString = ChatColor.GOLD + enchantmentName + " " + enchantLvl;
            }

        } else if (enchantLvl > enchantment.getMaxLevel()) {
            // Enchant is over Max Level

            // Check if is curse enchantment
            if (enchantment.isCursed()) {
                enchantString = ChatColor.RED + enchantmentName + " " + enchantLvl;
            } else {
                enchantString = ChatColor.LIGHT_PURPLE + enchantmentName + " " + enchantLvl;
            }

        } else if (enchantLvl > enchantment.getStartLevel() && enchantLvl < enchantment.getMaxLevel()) {
            // Enchant is greater than start level, but lower than the max level

            // Check if is curse enchantment
            if (enchantment.isCursed()) {
                enchantString = ChatColor.RED + enchantmentName + " " + enchantLvl;
            } else {
                enchantString = ChatColor.DARK_PURPLE + enchantmentName + " " + enchantLvl;
            }

        } else {
            // Enchantment is the start level

            // Check if is curse enchantment
            if (enchantment.isCursed()) {
                enchantString = ChatColor.RED + enchantmentName + " " + enchantLvl;
            } else {
                enchantString = ChatColor.GRAY + enchantmentName + " " + enchantLvl;
            }
        }
        return enchantString;
    }
    public static List<String> enchantStrings(Map<Enchantment, Integer> enchants) {

        List<String> enchantLore = new ArrayList<>();
        String enchantString;
        for (Enchantment enchantment : enchants.keySet()) {
            String enchantmentName = EnchantsClass.EnchantmentNames.get(enchantment);
            int enchantLvl = enchants.get(enchantment);

            if (enchantLvl == enchantment.getMaxLevel()) {
                // Enchant is Max Level

                // Check if is curse enchantment
                if (enchantment.isCursed()) {
                    enchantString = ChatColor.RED + enchantmentName + " " + enchantLvl;
                } else {
                    enchantString = ChatColor.GOLD + enchantmentName + " " + enchantLvl;
                }

            } else if (enchantLvl > enchantment.getMaxLevel()) {
                // Enchant is over Max Level

                // Check if is curse enchantment
                if (enchantment.isCursed()) {
                    enchantString = ChatColor.RED + enchantmentName + " " + enchantLvl;
                } else {
                    enchantString = ChatColor.LIGHT_PURPLE + enchantmentName + " " + enchantLvl;
                }

            } else if (enchantLvl > enchantment.getStartLevel() && enchantLvl < enchantment.getMaxLevel()) {
                // Enchant is greater than start level, but lower than the max level

                // Check if is curse enchantment
                if (enchantment.isCursed()) {
                    enchantString = ChatColor.RED + enchantmentName + " " + enchantLvl;
                } else {
                    enchantString = ChatColor.DARK_PURPLE + enchantmentName + " " + enchantLvl;
                }

            } else {
                // Enchantment is the start level

                // Check if is curse enchantment
                if (enchantment.isCursed()) {
                    enchantString = ChatColor.RED + enchantmentName + " " + enchantLvl;
                } else {
                    enchantString = ChatColor.GRAY + enchantmentName + " " + enchantLvl;
                }
            }
            enchantLore.add(enchantString);
        }

        return enchantLore;
    }

    /*
    private void refreshItem(ItemStack itemStack, Player player) {

        if (itemStack != null) {
            if (itemStack.getType() != Material.AIR) {
                if (itemStack.getItemMeta() != null) {
                    ItemMeta itemMeta = itemStack.getItemMeta();

                    List<String> enchantLore = new ArrayList<>();

                    for (Enchantment enchantment : itemMeta.getEnchants().keySet()) {
                        enchantLore.add(enchantString(enchantment, itemMeta));
                    }

                    if (itemMeta.hasCustomModelData()) {

                        // Default Lore (Not Player Determined)
                        Integer stars = ItemForger.getItemStars(itemStack);
                        //String itemTypeString = ItemForger.getItemTypeKey(itemStack);
                        HashMap<Integer, ItemForger> itemForgerProfiles = itemForgerHashMap(stars);

                        try {
                            ItemForger itemForger = itemForgerProfiles.get(itemMeta.getCustomModelData());
                            List<String> lore = itemForger.getLore();
                            Multimap<Attribute, AttributeModifier> attributes = itemForger.getAttributes();

                            // Get Stat values for the items

                            HashMap<itemStatKeys, Double> itemStatMap = itemForger.getItemStats();
                            ItemForger.setItemStats(itemStack, itemStatMap);

                            HashMap<Integer, List<String>> playerLoreMap = itemLoreHashMapPlayer(stars, player);
                            List<String> playerLore = playerLoreMap.get(itemMeta.getCustomModelData());

                            if (lore != null) {
                                for (String l : lore) {

                                    if (itemForgerProfiles.containsKey(itemMeta.getCustomModelData()) && !playerLoreMap.containsKey(itemMeta.getCustomModelData())) {
                                        enchantLore.add(l);
                                    }
                                }
                            }

                            if (playerLore != null) {
                                for (String l : playerLore) {

                                    if (itemForgerProfiles.containsKey(itemMeta.getCustomModelData()) && playerLoreMap.containsKey(itemMeta.getCustomModelData())) {
                                        enchantLore.add(l);
                                    }

                                }
                            }

                            if (attributes != null) {
                                itemMeta.setAttributeModifiers(attributes);
                            }
                        } catch (NullPointerException exception) {
                            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Item found with no mapping! :(");
                            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Custom model data: " + itemMeta.getCustomModelData());
                        }



                    }

                    itemMeta.setLore(enchantLore);

                    itemStack.setItemMeta(itemMeta);

                } else {
                    itemStack.setLore(null);
                }
            }
        }
    }
    public static void refreshItem(ItemStack itemStack) {

        if (itemStack != null) {
            if (itemStack.getType() != Material.AIR) {
                if (itemStack.getItemMeta() != null) {
                    ItemMeta itemMeta = itemStack.getItemMeta();

                    List<String> enchantLore = new ArrayList<>();

                    for (Enchantment enchantment : itemMeta.getEnchants().keySet()) {
                        enchantLore.add(enchantString(enchantment, itemMeta));
                    }

                    // Check if item has custom model data to add item's lore and attributes
                    if (itemMeta.hasCustomModelData()) {

                        Integer stars = ItemForger.getItemStars(itemStack);
                        //String itemStringType = ItemForger.getItemTypeKey(itemStack);
                        HashMap<Integer, ItemForger> itemProfiles = itemForgerHashMap(stars);

                        try {
                            ItemForger itemForger = itemProfiles.get(itemMeta.getCustomModelData());
                            List<String> lore = itemForger.getLore();
                            Multimap<Attribute, AttributeModifier> attributes = itemForger.getAttributes();
                            HashMap<itemStatKeys, Double> itemStatMap = itemForger.getItemStats();
                            ItemForger.setItemStats(itemStack, itemStatMap);

                            if (lore != null) {
                                for (String l : lore) {
                                    enchantLore.add(l);
                                }
                            }
                            if (attributes != null) {
                                itemMeta.setAttributeModifiers(attributes);
                            }
                        } catch (NullPointerException exception) {
                            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Item found with no mapping! :(");
                            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Custom model data: " + itemMeta.getCustomModelData());
                        }



                    }

                    itemMeta.setLore(enchantLore);

                    itemStack.setItemMeta(itemMeta);

                } else {
                    itemStack.setLore(null);
                }
            }
        }
    }


     */
    @EventHandler
    public void refreshOnJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        for (ItemStack itemStack : player.getInventory().getContents()) {
            //refreshItem(itemStack, player);
            UpdateItem.renewItem(itemStack, player);
        }
    }

    @EventHandler
    public void refreshOnClick(InventoryClickEvent event) {

        Inventory eventInv = event.getClickedInventory();

        HumanEntity humanEntity = event.getWhoClicked();

        if (humanEntity instanceof Player player) {

            PlayerInventory playerInventory = player.getInventory();
            if (eventInv != null) {
                if (eventInv.equals(playerInventory)) {
                    //player.sendMessage("Clicked in own inventory");
                } else {
                    //player.sendMessage("Clicked not in own inventory");
                }
            }
        }
    }

    @EventHandler
    public void RefreshOpenInventory(InventoryOpenEvent event) {

        HumanEntity humanEntity = event.getPlayer();
        Inventory inventory = event.getInventory();
        String inventoryTitle = event.getView().getTitle();

        for (ItemStack itemStack : inventory.getContents()) {

            if (humanEntity instanceof Player player) {
                if (!event.getView().getTitle().contains(player.getName() + "'s Stats") &&
                        !event.getView().getTitle().equals(ChatColor.GOLD + "Skill Tree"))
                {
                    //refreshItem(itemStack);
                    UpdateItem.renewItem(itemStack);
                }
            }


        }

        if (humanEntity instanceof Player player) {

            Inventory playerInventory = player.getInventory();

            for (ItemStack itemStack : playerInventory.getContents()) {
                //refreshItem(itemStack);
                UpdateItem.renewItem(itemStack);
            }
        }
    }

    @EventHandler
    public void RefreshCloseInventory(InventoryCloseEvent event) {

        HumanEntity humanEntity = event.getPlayer();
        Inventory inventory = event.getInventory();
        String inventoryTitle = event.getView().getTitle();

        for (ItemStack itemStack : inventory.getContents()) {

            if (humanEntity instanceof Player player) {
                if (!event.getView().getTitle().contains(player.getName() + "'s Stats") &&
                        !event.getView().getTitle().equals(ChatColor.GOLD + "Skill Tree"))
                {
                    UpdateItem.renewItem(itemStack);
                }
            }
        }

        if (humanEntity instanceof Player player) {

            Inventory playerInventory = player.getInventory();

            for (ItemStack itemStack : playerInventory.getContents()) {
                //refreshItem(itemStack, player);
                UpdateItem.renewItem(itemStack, player);
            }
        }

    }

    @EventHandler
    public void RefreshPickUpItem(EntityPickupItemEvent event) {

        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player player) {
            ItemStack itemStack = event.getItem().getItemStack();
            //refreshItem(itemStack, player);
            UpdateItem.renewItem(itemStack, player);
        }

    }

    @EventHandler
    public void RefreshManipulateArmorStand(PlayerArmorStandManipulateEvent event) {

        Player player = event.getPlayer();
        ItemStack armorStandItem = event.getArmorStandItem();
        ItemStack playerItem = event.getPlayerItem();

        //refreshItem(armorStandItem, player);
        //refreshItem(playerItem, player);

        UpdateItem.renewItem(armorStandItem, player);
        UpdateItem.renewItem(playerItem, player);

    }

    private List<String> blackListInventoriesNames(Player player) {

        List<String> blackList = new ArrayList<>();
        blackList.add(player.getName() + "'stats");
        blackList.add("Skill Tree");
        return blackList;
    }

    // TODO: Item maps for /giveitem command


    // Item Mappings for "plugingive" command
    public enum ItemMaps {


        NETHERITE_BOOTS (netheriteArmorManager.createArmorPiece(ArmorTypes.BOOTS, 0), "netherite_boots"),
        NETHERITE_LEGGINGS (netheriteArmorManager.createArmorPiece(ArmorTypes.LEGGINGS, 0), "netherite_leggings"),
        NETHERITE_CHESTPLATE (netheriteArmorManager.createArmorPiece(ArmorTypes.CHESTPLATE, 0), "netherite_chestplate"),
        NETHERITE_HELMET (netheriteArmorManager.createArmorPiece(ArmorTypes.HELMET, 0), "netherite_helmet"),

        STORM_LORD_BOOTS (stormLordArmorManager.createArmorPiece(ArmorTypes.BOOTS, 0), "storm_lord_boots"),
        STORM_LORD_LEGGINGS (stormLordArmorManager.createArmorPiece(ArmorTypes.LEGGINGS, 0),"storm_lord_leggings"),
        STORM_LORD_CHESTPLATE (stormLordArmorManager.createArmorPiece(ArmorTypes.CHESTPLATE, 0), "storm_lord_chestplate"),
        STORM_LORD_HELMET (stormLordArmorManager.createArmorPiece(ArmorTypes.HELMET,0), "storm_lord_helmet");

        private final ItemForger itemForger;
        private final String name;

        ItemMaps(ItemForger itemForger, String name) {
            this.itemForger = itemForger;
            this.name = name;
        }

        public ItemForger getItemForger() {
            return itemForger;
        }
        public String getName() {
            return name;
        }

        public static ItemForger of(String inputName) {
            for (ItemMaps itemMaps : values()) {
                if (itemMaps.name.equals(inputName)) {
                    return itemMaps.itemForger;
                }
            }
            throw new IllegalArgumentException(ChatColor.RED + "Not an item: " + inputName);
        }

    }

}
