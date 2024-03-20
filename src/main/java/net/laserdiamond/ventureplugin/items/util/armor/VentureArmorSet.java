package net.laserdiamond.ventureplugin.items.util.armor;

import com.google.common.collect.Multimap;
import net.laserdiamond.ventureplugin.items.armor.ArmorEquipStats;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.items.util.ItemNameBuilder;
import net.laserdiamond.ventureplugin.items.util.VentureStatItem;
import net.laserdiamond.ventureplugin.util.VentureItemStatKeys;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

/**
 * Abstract class for Venture Armor Sets
 */
public abstract class VentureArmorSet extends VentureStatItem {


    public abstract String armorSetName();

    public abstract ArmorCMD setArmorCMD();

    /**
     * Sets the armor color for leather armor
     * @param armorPieceTypes The armor piece type
     * @return the color for the armor piece
     */
    private Color setArmorColor(ArmorPieceTypes armorPieceTypes)
    {
        String armorPieceString = armorPieceTypes.getName();
        int red = config().getInt(armorPieceString + "Red");
        int green = config().getInt(armorPieceString + "Green");
        int blue = config().getInt(armorPieceString + "Blue");
        return Color.fromRGB(red, green, blue);
    }

    /**
     * The player head skin for player head helmets, if applicable
     * @return The player head skin URL as a string
     */
    public final String playerHeadSkin()
    {
        return config().getString("HelmetURL");
    }

    /**
     * SigBits needed for player head skin
     * @return
     */
    public final int[] sigBits()
    {
        return new int[]{setArmorCMD().getHelmet(), setArmorCMD().getHelmet()};
    }

    /**
     * Method for creating lore for the item
     * @param armorPieceTypes The armor piece type
     * @param stars The amount of stars the item has
     * @return The lore of the item
     */
    public abstract List<String> createLore(@NotNull ArmorPieceTypes armorPieceTypes, int stars);

    /**
     * Method for creating item lore based off the player that has the item in their inventory
     * @param player The player to make the lore for
     * @param armorPieceTypes The armor piece type
     * @param stars The amount of stars the item has
     * @return The lore of the item
     */
    public abstract List<String> createPlayerLore(@NotNull Player player, @NotNull ArmorPieceTypes armorPieceTypes, int stars);

    /**
     * Creates vanilla attributes for the item
     * @param armorPieceTypes The armor piece type
     * @param stars The amount of stars the item has
     * @return The attributes for the item
     */
    public abstract Multimap<Attribute, AttributeModifier> createAttributes(@NotNull ArmorPieceTypes armorPieceTypes, int stars);

    /**
     * Creates Venture Plugin Item Stats for the item
     * @param armorPieceTypes The armor piece type
     * @param stars The amount of stars the item has
     * @return The Venture Item Stats
     */
    public abstract HashMap<VentureItemStatKeys, Double> createItemStats(@NotNull ArmorPieceTypes armorPieceTypes, int stars);

    public final HashMap<VentureItemStatKeys, Double> createVentureStats(@NotNull ArmorPieceTypes armorPieceTypes, int stars)
    {
        String armorPiece = armorPieceTypes.getName();

        double meleeDamage = config().getDouble(armorPiece + "MeleeDamage") * (1 + stars * this.starBonus);
        double rangeDamage = config().getDouble(armorPiece + "RangeDamage") * (1 + stars * this.starBonus);
        double magicDamage = config().getDouble(armorPiece + "MagicDamage") * (1 + stars * this.starBonus);
        double mana = config().getDouble(armorPiece + "Mana") * (1 + stars * this.starBonus);
        double health = config().getDouble(armorPiece + "Health") * (1 + stars * this.starBonus);
        double armor = config().getDouble(armorPiece + "Armor") * (1 + stars * this.starBonus);
        double toughness = config().getDouble("toughness");
        double fortitude = config().getDouble("fortitude");
        double speed = config().getDouble(armorPiece + "Speed") * (1 + stars * this.starBonus);

        HashMap<VentureItemStatKeys, Double> itemStatKeysDoubleHashMap = new HashMap<>();
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.MELEE_DAMAGE_KEY, meleeDamage);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.RANGE_DAMAGE_KEY, rangeDamage);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.MAGIC_DAMAGE_KEY, magicDamage);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.MAX_MANA_KEY, mana);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.HEALTH_KEY, health);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_KEY, armor);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.TOUGHNESS_KEY, toughness);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.FORTITUDE_KEY, fortitude);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.SPEED_KEY, speed);

        return itemStatKeysDoubleHashMap;
    }

    /**
     * Creates the Armor Piece for the item as an ItemForger instance
     * @param armorPieceTypes The armor piece type
     * @param stars The amount of stars the item has
     * @return The Venture armor Piece as an ItemForger instance
     */
    @Deprecated
    public abstract ItemForger createArmorPiece(@NotNull ArmorPieceTypes armorPieceTypes, int stars);

    // TODO: Advanced Armor set piece generator
    public ItemForger createLeatherArmorSet(@NotNull ArmorPieceTypes armorPieceTypes, int stars)
    {
        String armorPieceString = armorPieceTypes.getName();
        ItemForger itemForger = new ItemForger(Material.AIR);

        try {
            String armorName = armorPieceString.substring(0,1).toUpperCase() + armorPieceString.substring(1);

            switch (armorPieceTypes)
            {
                case HELMET -> itemForger = new ItemForger(Material.LEATHER_HELMET)
                        .setCustomModelData(setArmorCMD().getHelmet());
                case CHESTPLATE -> itemForger = new ItemForger(Material.LEATHER_CHESTPLATE)
                        .setCustomModelData(setArmorCMD().getChestplate());
                case LEGGINGS -> itemForger = new ItemForger(Material.LEATHER_LEGGINGS)
                        .setCustomModelData(setArmorCMD().getLeggings());
                case BOOTS -> itemForger = new ItemForger(Material.LEATHER_BOOTS)
                        .setCustomModelData(setArmorCMD().getBoots());
            }

            itemForger.setName(ItemNameBuilder.name(armorSetName() + " " + armorName, stars))
                    .setStars(stars)
                    .setLore(createLore(armorPieceTypes, stars))
                    .setRarity(rarity())
                    .setUnbreakable(isUnbreakable())
                    .setFireResistant(isFireResistant())
                    .setItemStats(createVentureStats(armorPieceTypes, stars));
        } catch (NullPointerException e)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something about this item is null: " + itemForger.getName());
            e.printStackTrace();
        }
        return itemForger;
    }

    private ItemForger createArmorSet(ItemForger itemForger, ArmorPieceTypes armorPieceTypes, Material helmet, Material chestplate, Material leggings, Material boots)
    {
        switch (armorPieceTypes)
        {
            case HELMET -> {
                itemForger = new ItemForger(helmet).setCustomModelData(setArmorCMD().getHelmet());
                switch (helmet)
                {
                    case PLAYER_HEAD -> itemForger.setPlayerHeadSkin(playerHeadSkin(), sigBits()[0], sigBits()[1]);
                    case LEATHER_HELMET -> itemForger.LeatherArmorColor(setArmorColor(armorPieceTypes));
                }
            }
            case CHESTPLATE -> {
                itemForger = new ItemForger(chestplate).setCustomModelData(setArmorCMD().getChestplate());
                if (chestplate.equals(Material.LEATHER_CHESTPLATE))
                {
                    itemForger.LeatherArmorColor(setArmorColor(armorPieceTypes));
                }
            }
            case LEGGINGS -> {
                itemForger = new ItemForger(leggings).setCustomModelData(setArmorCMD().getLeggings());
                if (chestplate.equals(Material.LEATHER_LEGGINGS))
                {
                    itemForger.LeatherArmorColor(setArmorColor(armorPieceTypes));
                }
            }
            case BOOTS -> {
                itemForger = new ItemForger(boots).setCustomModelData(setArmorCMD().getBoots());
                if (chestplate.equals(Material.LEATHER_BOOTS))
                {
                    itemForger.LeatherArmorColor(setArmorColor(armorPieceTypes));
                }
            }
        }
        return itemForger;
    }

    /**
     * Boolean for if the player is wearing the full set
     * @param player The player wearing the armor
     * @return If the player is wearing the full set
     */
    public final boolean isWearingFullSet(Player player)
    {
        return ArmorEquipStats.isWearingFullSet(player, setArmorCMD().getHelmet(), setArmorCMD().getChestplate(), setArmorCMD().getLeggings(), setArmorCMD().getBoots());
    }
}
