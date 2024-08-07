package net.laserdiamond.ventureplugin.items.util;

import com.google.common.collect.Multimap;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.commands.view_profiles.TuningMenu;
import net.laserdiamond.ventureplugin.commands.view_profiles.ViewSkills;
import net.laserdiamond.ventureplugin.commands.view_profiles.ViewStats;
import net.laserdiamond.ventureplugin.enchants.Components.VentureEnchants;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.items.armor.armor_sets.SoulFireBlazeArmor;
import net.laserdiamond.ventureplugin.items.armor.trims.Components.TrimLore;
import net.laserdiamond.ventureplugin.items.armor.ArmorPieceTypes;
import net.laserdiamond.ventureplugin.items.armor.VentureArmorSet;
import net.laserdiamond.ventureplugin.items.menuItems.util.VentureMenuItem;
import net.laserdiamond.ventureplugin.items.menuItems.util.VentureSkillProgressItem;
import net.laserdiamond.ventureplugin.items.misc.VentureMiscItem;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ItemRegistry implements Listener {

    private static final VenturePlugin PLUGIN = VenturePlugin.getInstance();
    private static final HashMap<String, VentureMiscItem> MISC_ITEM_MAP = PLUGIN.getMiscItemMap();
    private static final HashMap<String, VentureArmorSet> ARMOR_SET_ITEM_MAP = PLUGIN.getArmorSetItemMap();
    private static final HashMap<String, VentureMenuItem> VENTURE_MENU_ITEMS_MAP = PLUGIN.getVentureMenuItems();
    private static final HashMap<String, VentureSkillProgressItem> VENTURE_SKILL_PROGRESS_ITEMS = PLUGIN.getVentureSkillProgressItems();

    @EventHandler
    private void refreshItemOnJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        for (ItemStack itemStack : player.getInventory().getContents())
        {
            // TODO: Update item to have player-defined lore
            renewItem(itemStack, player);
        }
    }


    @EventHandler
    private void refreshSwitchMainHand(PlayerItemHeldEvent event)
    {
        Player player = event.getPlayer();

        for (ItemStack itemStack : player.getInventory().getContents())
        {
            renewItem(itemStack, player);
        }
    }

    private static final List<String> PLAYER_INV_TITLES = new ArrayList<>();
    static
    {
        PLAYER_INV_TITLES.add(ViewStats.STAT_INV_TITLE);
        PLAYER_INV_TITLES.add(ViewStats.DEFENSE_STAT_INV_TITLE);
        PLAYER_INV_TITLES.add(ViewStats.DAMAGE_STAT_INV_TITLE);
        PLAYER_INV_TITLES.add(ViewStats.FORTUNE_STAT_INV_TITLE);
        PLAYER_INV_TITLES.add(ViewStats.POTION_STAT_INV_TITLE);

        PLAYER_INV_TITLES.add(TuningMenu.TUNING_INV_TITLE);
        PLAYER_INV_TITLES.add(ViewSkills.SKILL_INV_TITLE);
        PLAYER_INV_TITLES.add(ViewSkills.COMBAT_INV_TITLE);
        PLAYER_INV_TITLES.add(ViewSkills.MINING_INV_TITLE);
        PLAYER_INV_TITLES.add(ViewSkills.FORAGING_INV_TITLE);
        PLAYER_INV_TITLES.add(ViewSkills.FARMING_INV_TITLE);
        PLAYER_INV_TITLES.add(ViewSkills.ENCHANTING_INV_TITLE);
        PLAYER_INV_TITLES.add(ViewSkills.FISHING_INV_TITLE);
        PLAYER_INV_TITLES.add(ViewSkills.BREWING_INV_TITLE);
    }

    private static boolean isAnyPlayerInvTitle(String invTitle)
    {
        for (String playerInvTitle : PLAYER_INV_TITLES)
        {
            if (invTitle.contains(playerInvTitle))
            {
                return true;
            }
        }
        return false;
    }

    @EventHandler
    private void refreshOnClick(InventoryClickEvent event)
    {
        Inventory eventInv = event.getClickedInventory();

        HumanEntity humanEntity = event.getWhoClicked();

        if (humanEntity instanceof Player player)
        {
            if (eventInv != null)
            {
                if (eventInv.equals(player.getInventory()))
                {
                    //renewItem(event.getCursor(), player);
                    for (ItemStack itemStack : player.getInventory().getContents())
                    {
                        renewItem(itemStack, player);
                    }

                } else
                {
                    if (isAnyPlayerInvTitle(event.getView().getTitle()))
                    {
                        renewItem(event.getCurrentItem(), player);
                    } else {
                        renewItem(event.getCurrentItem());
                    }
                }

            }
        }
    }

    @EventHandler
    private void refreshOpenInventory(InventoryOpenEvent event)
    {
        HumanEntity humanEntity = event.getPlayer();
        Inventory inventory = event.getInventory();

        if (humanEntity instanceof Player player)
        {
            for (ItemStack itemStack : inventory.getContents())
            {
                String title = event.getView().getTitle();
                if (isAnyPlayerInvTitle(title))
                {
                    renewItem(itemStack, player);
                    //player.sendMessage("player gui");
                } else {
                    renewItem(itemStack);
                    //player.sendMessage("not player gui");
                }
            }
            for (ItemStack itemStack : player.getInventory().getContents())
            {
                renewItem(itemStack, player);
            }
        }
    }

    @EventHandler
    private void refreshCloseInventory(InventoryCloseEvent event)
    {
        HumanEntity humanEntity = event.getPlayer();
        Inventory inventory = event.getInventory();

        if (humanEntity instanceof Player player)
        {
            for (ItemStack itemStack : inventory.getContents())
            {
                if (isAnyPlayerInvTitle(event.getView().getTitle()))
                {
                    renewItem(itemStack, player);
                } else {
                    renewItem(itemStack);
                }

            }

            for (ItemStack itemStack : player.getInventory().getContents())
            {
                renewItem(itemStack, player);
            }
        }
    }

    @EventHandler
    private void refreshItemPickUp(PlayerAttemptPickupItemEvent event)
    {
        Player player = event.getPlayer();
        ItemStack itemToPickUp = event.getItem().getItemStack();

        // TODO: Update item to have player-defined lore
        renewItem(itemToPickUp, player);

    }

    @EventHandler
    private void refreshManipulateArmorStand(PlayerArmorStandManipulateEvent event)
    {
        Player player = event.getPlayer();
        ItemStack armorStandItem = event.getArmorStandItem();
        ItemStack playerItem = event.getPlayerItem();

        // TODO: Update both items to have player-defined lore
        renewItem(armorStandItem, player);
        renewItem(playerItem, player);

    }

    public static ItemStack renewItem(ItemStack itemStack)
    {
        LinkedList<String> newLore = new LinkedList<>();

        if (itemStack != null && itemStack.getType().isItem())
        {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null)
            {
                VentureItemBuilder ventureItemBuilder = new VentureItemBuilder(itemStack);
                if (itemMeta instanceof ArmorMeta armorMeta)
                {
                    List<String> trimLore = TrimLore.createLore(armorMeta);
                    newLore.addAll(trimLore);
                }

                if (itemMeta.hasEnchants())
                {
                    newLore.addAll(VentureEnchants.enchantLore(ventureItemBuilder.getEnchantments()));
                }

                String itemKeyValue = ventureItemBuilder.getItemKey();
                int stars = ventureItemBuilder.getStars();
                VentureArmorSet ventureArmorSet = ARMOR_SET_ITEM_MAP.get(itemKeyValue);
                if (ventureArmorSet != null && ARMOR_SET_ITEM_MAP.containsKey(itemKeyValue))
                {
                    ArmorPieceTypes armorPieceTypes = null;
                    if (itemKeyValue.contains("helmet"))
                    {
                        armorPieceTypes = ArmorPieceTypes.HELMET;
                    } else if (itemKeyValue.contains("chestplate"))
                    {
                        armorPieceTypes = ArmorPieceTypes.CHESTPLATE;
                    } else if (itemKeyValue.contains("leggings"))
                    {
                        armorPieceTypes = ArmorPieceTypes.LEGGINGS;
                    } else if (itemKeyValue.contains("boots"))
                    {
                        armorPieceTypes = ArmorPieceTypes.BOOTS;
                    }

                    if (armorPieceTypes != null)
                    {
                        LinkedList<String> lore = ventureArmorSet.createLore(armorPieceTypes, stars);
                        VentureItemBuilder armorItem = ventureArmorSet.createArmorSet(armorPieceTypes, stars);
                        VentureItemRarity.Rarity rarity = armorItem.getRarity();
                        HashMap<VentureItemStatKeys, Double> itemStats = armorItem.getItemStats();
                        Multimap<Attribute, AttributeModifier> attributes = armorItem.getAttributes();

                        if (lore != null)
                        {
                            newLore.addAll(lore);
                            SoulFireBlazeArmor.itemSpecificLore(ventureItemBuilder, newLore);
                        }

                        if (rarity != null) // Set rarity for item
                        {
                            itemMeta.setDisplayName(armorItem.getName());
                            itemMeta.getPersistentDataContainer().set(ItemKeys.RARITY_KEY, VentureItemRarity.STRING, rarity.getRarity());
                        }

                        if (itemStats != null) // Set VentureItemStats for item
                        {
                            for (VentureItemStatKeys ventureItemStatKeys : itemStats.keySet())
                            {
                                NamespacedKey itemStatKey = ventureItemStatKeys.getKey();
                                Double statKeyValue = itemStats.get(ventureItemStatKeys);
                                if (itemMeta.getPersistentDataContainer().get(itemStatKey, PersistentDataType.DOUBLE) != null)
                                {
                                    itemMeta.getPersistentDataContainer().set(itemStatKey, PersistentDataType.DOUBLE, statKeyValue);
                                }
                            }
                        }
                        if (attributes != null) // Set Attributes for item
                        {
                            itemMeta.setAttributeModifiers(attributes);
                        }

                    }
                }

                VentureMiscItem ventureMiscItem = MISC_ITEM_MAP.get(itemKeyValue);
                if (ventureMiscItem != null && MISC_ITEM_MAP.containsKey(itemKeyValue))
                {
                    List<String> lore = ventureMiscItem.createLore();
                    VentureItemRarity.Rarity rarity = ventureMiscItem.rarity();
                    VentureItemBuilder miscItem = ventureMiscItem.createItem();

                    if (!lore.isEmpty())
                    {
                        newLore.addAll(lore);
                    }

                    if (rarity != null)
                    {
                        itemMeta.setDisplayName(miscItem.getName());
                        itemMeta.getPersistentDataContainer().set(ItemKeys.RARITY_KEY, VentureItemRarity.STRING, rarity.getRarity());
                    }

                }

                itemMeta.setLore(newLore);
                itemStack.setItemMeta(itemMeta);

            }
        }
        return itemStack;
    }

    public static ItemStack renewItem(ItemStack itemStack, Player player)
    {
        LinkedList<String> newLore = new LinkedList<>();

        if (itemStack != null && itemStack.getType().isItem())
        {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null)
            {
                VentureItemBuilder ventureItemBuilder = new VentureItemBuilder(itemStack);
                if (itemMeta instanceof ArmorMeta armorMeta)
                {
                    List<String> trimLore = TrimLore.createLore(armorMeta);
                    newLore.addAll(trimLore);
                }

                if (itemMeta.hasEnchants())
                {
                    newLore.addAll(VentureEnchants.enchantLore(ventureItemBuilder.getEnchantments()));
                }

                int itemMatchCount = 0;
                final String itemKeyValue = ventureItemBuilder.getItemKey();

                int stars = ventureItemBuilder.getStars();
                VentureArmorSet ventureArmorSet = ARMOR_SET_ITEM_MAP.get(itemKeyValue);
                if (ventureArmorSet != null && ARMOR_SET_ITEM_MAP.containsKey(itemKeyValue))
                {
                    ArmorPieceTypes armorPieceTypes = null;
                    if (itemKeyValue.contains("helmet"))
                    {
                        armorPieceTypes = ArmorPieceTypes.HELMET;
                    } else if (itemKeyValue.contains("chestplate"))
                    {
                        armorPieceTypes = ArmorPieceTypes.CHESTPLATE;
                    } else if (itemKeyValue.contains("leggings"))
                    {
                        armorPieceTypes = ArmorPieceTypes.LEGGINGS;
                    } else if (itemKeyValue.contains("boots"))
                    {
                        armorPieceTypes = ArmorPieceTypes.BOOTS;
                    }

                    if (armorPieceTypes != null)
                    {
                        LinkedList<String> defaultLore = ventureArmorSet.createLore(armorPieceTypes, stars);
                        LinkedList<String> playerLore = ventureArmorSet.createPlayerLore(player, armorPieceTypes, stars);
                        VentureItemBuilder armorItem = ventureArmorSet.createArmorSet(armorPieceTypes, stars);
                        VentureItemRarity.Rarity rarity = armorItem.getRarity();
                        HashMap<VentureItemStatKeys, Double> itemStats = armorItem.getItemStats();
                        Multimap<Attribute, AttributeModifier> attributes = armorItem.getAttributes();

                        if (ventureArmorSet.isPlayerArmorSet()) // Check if item is part of a player-defined armor set
                        {
                            if (playerLore != null)
                            {
                                newLore.addAll(playerLore);
                            }

                        } else // Item is not a player-defined armor set
                        {
                            if (defaultLore != null)
                            {
                                newLore.addAll(defaultLore);
                                SoulFireBlazeArmor.itemSpecificLore(ventureItemBuilder, newLore);
                            }
                        }

                        if (rarity != null) // Set rarity for item
                        {
                            itemMeta.setDisplayName(armorItem.getName());
                            itemMeta.getPersistentDataContainer().set(ItemKeys.RARITY_KEY, VentureItemRarity.STRING, rarity.getRarity());
                        }

                        if (itemStats != null) // Set VentureItemStats for item
                        {
                            for (VentureItemStatKeys ventureItemStatKeys : itemStats.keySet())
                            {
                                NamespacedKey itemStatKey = ventureItemStatKeys.getKey();
                                Double statKeyValue = itemStats.get(ventureItemStatKeys);
                                if (itemMeta.getPersistentDataContainer().get(itemStatKey, PersistentDataType.DOUBLE) != null)
                                {
                                    itemMeta.getPersistentDataContainer().set(itemStatKey, PersistentDataType.DOUBLE, statKeyValue);
                                }
                            }
                        }
                        if (attributes != null) // Set Attributes for item
                        {
                            itemMeta.setAttributeModifiers(attributes);
                        }
                    }
                } else
                {
                    itemMatchCount++;
                }

                VentureMiscItem ventureMiscItem = MISC_ITEM_MAP.get(itemKeyValue);
                if (ventureMiscItem != null && MISC_ITEM_MAP.containsKey(itemKeyValue))
                {
                    List<String> lore = ventureMiscItem.createLore(); // Default Lore
                    List<String> playerLore = ventureMiscItem.createPlayerLore(player); // Player Lore
                    VentureItemRarity.Rarity rarity = ventureMiscItem.rarity(); // Rarity
                    VentureItemBuilder miscItem = ventureMiscItem.createItem();

                    if (!playerLore.isEmpty())
                    {
                        newLore.addAll(playerLore); // If Player lore is not empty, add player lore to new lore
                    } else
                    {
                        newLore.addAll(lore); // Otherwise, add default lore
                    }

                    if (rarity != null)
                    {
                        itemMeta.setDisplayName(miscItem.getName());
                        itemMeta.getPersistentDataContainer().set(ItemKeys.RARITY_KEY, VentureItemRarity.STRING, rarity.getRarity());
                    }

                } else
                {
                    itemMatchCount++;
                }

                final String menuItemKeyValue = ventureItemBuilder.getMenuItemKey();
                VentureMenuItem ventureMenuItem = VENTURE_MENU_ITEMS_MAP.get(menuItemKeyValue);

                if (ventureMenuItem != null && VENTURE_MENU_ITEMS_MAP.containsKey(menuItemKeyValue))
                {
                    List<String> lore = ventureMenuItem.createLore(player);
                    if (lore != null)
                    {
                        newLore.addAll(lore);
                    }
                } else
                {
                    itemMatchCount++;
                }

                String skillProgressKeyValue = ventureItemBuilder.getSkillProgressSkillKey();
                VentureSkillProgressItem skillProgressItem = VENTURE_SKILL_PROGRESS_ITEMS.get(skillProgressKeyValue);
                if (skillProgressItem != null && VENTURE_SKILL_PROGRESS_ITEMS.containsKey(skillProgressKeyValue))
                {
                    int skillLvl = ventureItemBuilder.getSkillProgressLvl();
                    List<String> lore = skillProgressItem.skillProgressItem(skillLvl, new StatPlayer(player).getSkillsProfile()).getLore();
                    if (lore != null)
                    {
                        newLore.addAll(lore);
                    }
                } else
                {
                    itemMatchCount++;
                }

                if (itemMatchCount == 4) // Item is not an armor set item or a menu item
                {
                    return itemStack;
                }

                itemMeta.setLore(newLore);
                itemStack.setItemMeta(itemMeta);

            }
        }
        return itemStack;
    }
}
