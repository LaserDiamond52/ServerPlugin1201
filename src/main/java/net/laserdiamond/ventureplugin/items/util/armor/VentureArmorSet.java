package net.laserdiamond.ventureplugin.items.util.armor;

import com.google.common.collect.Multimap;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.armor.ArmorEquipStats;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.items.util.ItemForgerRegistry;
import net.laserdiamond.ventureplugin.items.util.ItemNameBuilder;
import net.laserdiamond.ventureplugin.items.util.VentureStatItem;
import net.laserdiamond.ventureplugin.util.ItemRegistry;
import net.laserdiamond.ventureplugin.util.ItemRegistryKey;
import net.laserdiamond.ventureplugin.util.VentureItemStatKeys;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

/**
 * An abstract class that represents a Venture plugin armor set
 */
public abstract class VentureArmorSet extends VentureStatItem {

    private VenturePlugin instance()
    {
        return VenturePlugin.getInstance();
    }

    /**
     * The name of the armor set
     * @return The name of the armor set
     */
    public abstract String armorSetName();

    /**
     * Sets the custom model data for the armor set
     * @return ArmorCMD enum object containing the custom model data for each armor piece
     */
    public abstract ArmorCMD setArmorCMD();

    /**
     * Sets the material for all armor pieces
     * @param armorPieceTypes The armor piece type
     * @return The material for the armor piece type
     */
    public abstract Material setArmorPieceMaterial(ArmorPieceTypes armorPieceTypes);

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
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_MELEE_DAMAGE_KEY, meleeDamage);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_RANGE_DAMAGE_KEY, rangeDamage);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_MAGIC_DAMAGE_KEY, magicDamage);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_MAX_MANA_KEY, mana);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_HEALTH_KEY, health);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_DEFENSE_KEY, armor);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_TOUGHNESS_KEY, toughness);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_FORTITUDE_KEY, fortitude);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_SPEED_KEY, speed);

        return itemStatKeysDoubleHashMap;
    }

    /**
     * Creates the Armor Piece for the item as an ItemForger instance
     * @param armorPieceTypes The armor piece type
     * @param stars The amount of stars the item has
     * @return The Venture armor piece as an ItemForger instance
     */
    @Deprecated
    public abstract ItemForger createArmorPiece(@NotNull ArmorPieceTypes armorPieceTypes, int stars);

    // TODO: Advanced Armor set piece generator

    /**
     * Creates an instance of the armor piece
     * @param armorPieceTypes The armor piece type
     * @param stars The amount of stars the item has
     * @return The armor piece as an ItemForger instance
     */
    public ItemForger createArmorSet(@NotNull ArmorPieceTypes armorPieceTypes, int stars)
    {
        String armorPieceString = armorPieceTypes.getName();
        String armorName = armorPieceString.substring(0,1).toUpperCase() + armorPieceString.substring(1);
        ItemForger itemForger = new ItemForger(setArmorPieceMaterial(armorPieceTypes))
                .setName(ItemNameBuilder.name(armorSetName() + " " + armorName, stars))
                .setStars(stars)
                .setLore(createLore(armorPieceTypes, stars))
                .setRarity(rarity())
                .setUnbreakable(isUnbreakable())
                .setFireResistant(isFireResistant())
                .setItemStats(createVentureStats(armorPieceTypes, stars))
                .setAttributeModifiers(createAttributes(armorPieceTypes, stars), true);

        switch (setArmorPieceMaterial(armorPieceTypes))
        {
            case PLAYER_HEAD -> itemForger.setPlayerHeadSkin(playerHeadSkin(), sigBits()[0], sigBits()[1]);
            case LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS -> itemForger.LeatherArmorColor(setArmorColor(armorPieceTypes));
        }

        switch (armorPieceTypes)
        {
            case HELMET -> itemForger.setCustomModelData(setArmorCMD().getHelmet());
            case CHESTPLATE -> itemForger.setCustomModelData(setArmorCMD().getChestplate());
            case LEGGINGS -> itemForger.setCustomModelData(setArmorCMD().getLeggings());
            case BOOTS -> itemForger.setCustomModelData(setArmorCMD().getBoots());
        }
        return itemForger;
    }

    /**
     * Creates a player instance of the armor piece
     * @param player The player that will determine the armor's properties
     * @param armorPieceTypes The armor piece type
     * @param stars The stars the armor piece will have
     * @return The armor piece as an ItemForger instance
     */
    public ItemForger createPlayerArmorSet(Player player, @NotNull ArmorPieceTypes armorPieceTypes, int stars)
    {
        String armorPieceString = armorPieceTypes.getName();
        String armorName = armorPieceString.substring(0,1).toUpperCase() + armorPieceString.substring(1);
        ItemForger itemForger = new ItemForger(setArmorPieceMaterial(armorPieceTypes))
                .setName(ItemNameBuilder.name(armorSetName() + " " + armorName, stars))
                .setStars(stars)
                .setLore(createPlayerLore(player, armorPieceTypes, stars))
                .setRarity(rarity())
                .setUnbreakable(isUnbreakable())
                .setFireResistant(isFireResistant())
                .setItemStats(createVentureStats(armorPieceTypes, stars))
                .setAttributeModifiers(createAttributes(armorPieceTypes, stars), true);

        switch (setArmorPieceMaterial(armorPieceTypes))
        {
            case PLAYER_HEAD -> itemForger.setPlayerHeadSkin(playerHeadSkin(), sigBits()[0], sigBits()[1]);
            case LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS -> itemForger.LeatherArmorColor(setArmorColor(armorPieceTypes));
        }

        switch (armorPieceTypes)
        {
            case HELMET -> itemForger.setCustomModelData(setArmorCMD().getHelmet());
            case CHESTPLATE -> itemForger.setCustomModelData(setArmorCMD().getChestplate());
            case LEGGINGS -> itemForger.setCustomModelData(setArmorCMD().getLeggings());
            case BOOTS -> itemForger.setCustomModelData(setArmorCMD().getBoots());
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

    /**
     * Registers the armor set to be automatically refreshed when updated and to the giveitem command
     */
    public void registerArmorSet()
    {
        ItemRegistry registry = new ItemRegistry(instance());
        HashMap<ItemRegistryKey, ItemForger> registryMap = registry.getItemRegistryMap();

        int maxStars = instance().getConfig().getInt("maxStars");


        for (int i = 0; i < maxStars; i++)
        {
            for (ArmorPieceTypes armorPieceTypes : ArmorPieceTypes.values())
            {
                ItemRegistryKey key = new ItemRegistryKey(createArmorSet(armorPieceTypes, i).getCustomModelData(), i);
                registryMap.put(key, createArmorSet(armorPieceTypes, i));
            }
        }

        HashMap<String, ItemForger> nameMap = registry.getItemCommandNameMap();
        for (ArmorPieceTypes armorPieceTypes : ArmorPieceTypes.values())
        {
            ItemForger armorItem = createArmorSet(armorPieceTypes, 0);
            String armorPieceTypeName = armorPieceTypes.getName();
            String commandName = (armorSetName() + armorPieceTypeName).toLowerCase().replace(" ", "_");

            nameMap.put(commandName, armorItem);
        }

    }

    // TODO: Finish player armor registry (or figure out at least)
    @Deprecated
    public void registerArmorSetPlayer(Player player)
    {
        ItemForgerRegistry registry = new ItemForgerRegistry(instance());
        HashMap<ItemRegistryKey, ItemForger> registryPlayerMap = registry.getPlayerItemForgerRegistryMap();

        int maxStars = instance().getConfig().getInt("maxStars");


        for (int i = 0; i < maxStars; i++)
        {
            for (ArmorPieceTypes armorPieceTypes : ArmorPieceTypes.values())
            {
                ItemRegistryKey key = new ItemRegistryKey(createArmorSet(armorPieceTypes, i).getCustomModelData(), i);
                registryPlayerMap.put(key, createPlayerArmorSet(player, armorPieceTypes, i));
            }
        }
    }
}
