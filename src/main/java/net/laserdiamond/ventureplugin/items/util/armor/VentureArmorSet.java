package net.laserdiamond.ventureplugin.items.util.armor;

import com.google.common.collect.Multimap;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.armor.util.ArmorEquipStats;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.items.util.ItemNameBuilder;
import net.laserdiamond.ventureplugin.items.util.VentureStatItem;
import net.laserdiamond.ventureplugin.util.File.ArmorConfig;
import net.laserdiamond.ventureplugin.util.VentureItemStatKeys;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * An abstract class that represents a Venture plugin armor set
 */
public abstract class VentureArmorSet extends VentureStatItem {

    public final VenturePlugin plugin;

    public VentureArmorSet(VenturePlugin plugin)
    {
        this.plugin = plugin;
        registerArmorSet();
    }

    /**
     * The name of the armor set
     * @return The name of the armor set
     */
    public abstract String armorSetName();

    public abstract ArmorConfig config();

    /**
     * Sets the custom model data for the armor set
     * @return ArmorCMD enum object containing the custom model data for each armor piece
     */
    public abstract ArmorCMD getArmorCMD();

    /**
     * Gets the material for all armor pieces
     * @param armorPieceTypes The armor piece type
     * @return The material for the armor piece type
     */
    public abstract Material armorPieceMaterials(ArmorPieceTypes armorPieceTypes);

    /**
     * Sets the armor color for leather armor
     * @param armorPieceTypes The armor piece type
     * @return the color for the armor piece
     */
    public final Color armorColors(ArmorPieceTypes armorPieceTypes)
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
        String url = config().getString("HelmetURL");
        if (url != null)
        {
            return url;
        } else {
            return " ";
        }
    }

    /**
     * SigBits needed for player head skin
     * @return
     */
    private int[] sigBits()
    {
        return new int[]{getArmorCMD().getHelmet(), getArmorCMD().getHelmet()};
    }

    /**
     * Creates the lore for the item
     * @param armorPieceTypes The armor piece type
     * @param stars The amount of stars the item has
     * @return The lore of the item
     */
    public List<String> createLore(@NotNull ArmorPieceTypes armorPieceTypes, int stars)
    {
        List<String> lore = new ArrayList<>();
        lore.addAll(createStatLore(createVentureStats(armorPieceTypes, stars)));
        return lore;
    }

    /**
     * Method for creating item lore based off the player that has the item in their inventory
     * @param player The player to make the lore for
     * @param armorPieceTypes The armor piece type
     * @param stars The amount of stars the item has
     * @return The lore of the item
     */
    public List<String> createPlayerLore(@NotNull Player player, @NotNull ArmorPieceTypes armorPieceTypes, int stars)
    {
        List<String> lore = new ArrayList<>();
        lore.addAll(createStatLore(createVentureStats(armorPieceTypes, stars)));
        return lore;
    }

    /**
     * Creates Venture Plugin Item Stats for the item
     * @param armorPieceTypes The armor piece type
     * @param stars The amount of stars the item has
     * @return The Venture Item Stats
     */
    public HashMap<VentureItemStatKeys, Double> createVentureStats(@NotNull ArmorPieceTypes armorPieceTypes, int stars)
    {
        String armorPiece = armorPieceTypes.getName();

        /*
        double meleeDamage = config().getDouble(armorPiece + "MeleeDamage") * (1 + stars * this.starBonus);
        double rangeDamage = config().getDouble(armorPiece + "RangeDamage") * (1 + stars * this.starBonus);
        double magicDamage = config().getDouble(armorPiece + "MagicDamage") * (1 + stars * this.starBonus);
        double mana = config().getDouble(armorPiece + "Mana") * (1 + stars * this.starBonus);
        double health = config().getDouble(armorPiece + "Health") * (1 + stars * this.starBonus);
        double armor = config().getDouble(armorPiece + "Armor") * (1 + stars * this.starBonus);
        double toughness = config().getDouble("toughness");
        double fortitude = config().getDouble("fortitude");
        double speed = config().getDouble(armorPiece + "Speed") * (1 + stars * this.starBonus);

         */
        double meleeDamage = config().getStat(armorPieceTypes, ArmorConfig.StatType.MELEE_DAMAGE, stars);
        double rangeDamage = config().getStat(armorPieceTypes, ArmorConfig.StatType.RANGE_DAMAGE, stars);
        double magicDamage = config().getStat(armorPieceTypes, ArmorConfig.StatType.MAGIC_DAMAGE, stars);
        double mana = config().getStat(armorPieceTypes, ArmorConfig.StatType.MANA, stars);
        double health = config().getStat(armorPieceTypes, ArmorConfig.StatType.HEALTH, stars);
        double armor = config().getStat(armorPieceTypes, ArmorConfig.StatType.ARMOR, stars);
        double toughness = config().getStat(armorPieceTypes, ArmorConfig.StatType.TOUGHNESS, stars);
        double fortitude = config().getStat(armorPieceTypes, ArmorConfig.StatType.FORTITUDE, stars);
        double speed = config().getStat(armorPieceTypes, ArmorConfig.StatType.SPEED, stars);

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

    public final Multimap<Attribute, AttributeModifier> createArmorAttributes(@NotNull ArmorPieceTypes armorPieceTypes, int stars)
    {
        String armorPiece = armorPieceTypes.getName();
        double damage = config().getDouble(armorPiece + "MeleeDamage") * 0.01 * (1 + stars * this.starBonus);
        double armor = config().getDouble(armorPiece + "Armor") * (1 + stars * this.starBonus);
        double toughness = config().getDouble("toughness");
        ItemForger itemForger = new ItemForger(Material.STONE_SWORD);

        EquipmentSlot equipmentSlot = null;

        switch (armorPieceTypes)
        {
            case HELMET -> equipmentSlot = EquipmentSlot.HEAD;
            case CHESTPLATE -> equipmentSlot = EquipmentSlot.CHEST;
            case LEGGINGS -> equipmentSlot = EquipmentSlot.LEGS;
            case BOOTS -> equipmentSlot = EquipmentSlot.FEET;
        }

        AttributeModifier damageModifier = new AttributeModifier(UUID.randomUUID(),"generic.attack_damage", damage, AttributeModifier.Operation.ADD_SCALAR, equipmentSlot);
        AttributeModifier armorModifier = new AttributeModifier(UUID.randomUUID(),"generic.armor", armor, AttributeModifier.Operation.ADD_NUMBER, equipmentSlot);
        AttributeModifier toughnessModifier = new AttributeModifier(UUID.randomUUID(),"generic.armor_toughness", toughness, AttributeModifier.Operation.ADD_NUMBER, equipmentSlot);

        itemForger.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);
        itemForger.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);
        itemForger.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, toughnessModifier);

        return itemForger.getAttributes();
    }

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
        ItemForger itemForger = new ItemForger(armorPieceMaterials(armorPieceTypes))
                .setName(ItemNameBuilder.name(armorSetName() + " " + armorName, stars))
                //.setStars(stars)
                .setLore(createLore(armorPieceTypes, stars))
                .setRarity(rarity())
                .setUnbreakable(isUnbreakable())
                .setFireResistant(isFireResistant())
                .setAttributeModifiers(createArmorAttributes(armorPieceTypes, stars), false)
                .setItemStats(createVentureStats(armorPieceTypes, stars));

        switch (armorPieceMaterials(armorPieceTypes))
        {
            case PLAYER_HEAD -> itemForger.setPlayerHeadSkin(playerHeadSkin(), sigBits()[0], sigBits()[1]);
            case LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS -> itemForger.LeatherArmorColor(armorColors(armorPieceTypes));
        }

        switch (armorPieceTypes)
        {
            case HELMET -> itemForger.setCustomModelData(getArmorCMD().getHelmet());
            case CHESTPLATE -> itemForger.setCustomModelData(getArmorCMD().getChestplate());
            case LEGGINGS -> itemForger.setCustomModelData(getArmorCMD().getLeggings());
            case BOOTS -> itemForger.setCustomModelData(getArmorCMD().getBoots());
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
    public final ItemForger createPlayerArmorSet(Player player, @NotNull ArmorPieceTypes armorPieceTypes, int stars)
    {
        String armorPieceTypesName = armorPieceTypes.getName();
        String armorName = armorPieceTypesName.substring(0,1).toUpperCase() + armorPieceTypesName.substring(1);
        String keyName = (armorSetName() + "_" + armorPieceTypesName + "_" + stars).toLowerCase().replace(" ", "_");
        ItemForger itemForger = new ItemForger(armorPieceMaterials(armorPieceTypes))
                .setName(ItemNameBuilder.name(armorSetName() + " " + armorName, stars))
                .setLore(createPlayerLore(player, armorPieceTypes, stars))
                .setRarity(rarity())
                .setUnbreakable(isUnbreakable())
                .setFireResistant(isFireResistant())
                .setAttributeModifiers(createArmorAttributes(armorPieceTypes, stars), true)
                .setItemStats(createVentureStats(armorPieceTypes, stars))
                .setItemKey(keyName);

        switch (armorPieceMaterials(armorPieceTypes))
        {
            case PLAYER_HEAD -> itemForger.setPlayerHeadSkin(playerHeadSkin(), sigBits()[0], sigBits()[1]);
            case LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS -> itemForger.LeatherArmorColor(armorColors(armorPieceTypes));
        }

        switch (armorPieceTypes)
        {
            case HELMET -> itemForger.setCustomModelData(getArmorCMD().getHelmet());
            case CHESTPLATE -> itemForger.setCustomModelData(getArmorCMD().getChestplate());
            case LEGGINGS -> itemForger.setCustomModelData(getArmorCMD().getLeggings());
            case BOOTS -> itemForger.setCustomModelData(getArmorCMD().getBoots());
        }
        return itemForger;
    }

    /**
     * Boolean for if the player is wearing the full set
     * @param player The player wearing the armor
     * @return If the player is wearing the full set
     */
    public boolean isWearingFullSet(Player player)
    {
        return ArmorEquipStats.isWearingFullSet(player, getArmorCMD().getHelmet(), getArmorCMD().getChestplate(), getArmorCMD().getLeggings(), getArmorCMD().getBoots());
    }

    /**
     * Checks if the Item Stack is an armor piece of this class
     * @param itemStack The item
     * @return True if an armor piece of this class, false if not
     */
    public boolean isArmorPiece(ItemStack itemStack)
    {
        if (itemStack != null && itemStack.getItemMeta() != null && itemStack.getItemMeta().hasCustomModelData())
        {
            int customModelData = itemStack.getItemMeta().getCustomModelData();
            if (customModelData == getArmorCMD().getHelmet() ||
                customModelData == getArmorCMD().getChestplate() ||
                customModelData == getArmorCMD().getLeggings() ||
                customModelData == getArmorCMD().getBoots())
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Registers the armor set to be automatically refreshed when updated and to the giveitem command
     */
    public void registerArmorSet()
    {
        HashMap<String, ItemForger> itemRegistryMap = plugin.getItemRegistryMap();
        List<VentureArmorSet> playerItemMap = plugin.getPlayerArmorItemMap();

        int maxStars = plugin.getConfig().getInt("maxStars");

        for (int i = 0; i < maxStars + 1; i++)
        {
            for (ArmorPieceTypes armorPieceTypes : ArmorPieceTypes.values())
            {
                String armorPieceTypeName = armorPieceTypes.getName();
                String commandName = (armorSetName() + "_" + armorPieceTypeName + "_" + i).toLowerCase().replace(" ", "_");
                ItemForger armorItem = createArmorSet(armorPieceTypes, i).setItemKey(commandName);
                itemRegistryMap.put(commandName, armorItem);

            }
        }
        if (registerPlayerArmorSet())
        {
            playerItemMap.add(this);
        }
    }

    /**
     * Determines if the armor set should be registered as a player-based armor set
     * <p>
     * Player-based armor sets have unique properties to them, in that their lore and/or stats and other properties may be determined by the player that is currently holding them
     * <p>
     * If createPlayerLore or other player-based methods are overridden in the child class, and this is false, they will not be in use
     * @return True if this armor set should be player-based, false if not (false by default)
     */
    public boolean registerPlayerArmorSet()
    {
        return false;
    }
}
