package net.laserdiamond.ventureplugin.items.armor;

import com.google.common.collect.Multimap;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.util.VentureItemBuilder;
import net.laserdiamond.ventureplugin.items.util.ItemStringBuilder;
import net.laserdiamond.ventureplugin.items.misc.VentureStatItem;
import net.laserdiamond.ventureplugin.items.util.VentureItemEnchantabilities;
import net.laserdiamond.ventureplugin.util.File.ArmorConfig;
import net.laserdiamond.ventureplugin.items.util.VentureItemStatKeys;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * An abstract class that represents a full armor set of this plugin
 */
public abstract class VentureArmorSet extends VentureStatItem {

    private final ArmorConfig armorConfig;

    public VentureArmorSet(VenturePlugin plugin)
    {
        armorConfig = new ArmorConfig(plugin, armorName().toLowerCase().replace(" ", "_"));
        armorConfig.loadConfig();
        registerArmorSet();
    }

    /**
     * The name of the armor set
     * @return The name of the armor set
     */
    protected abstract String armorName();

    /**
     * The config file that stores the armor set's stats and other information
     * @return ArmorConfig
     */
    protected ArmorConfig getArmorConfig()
    {
        return armorConfig;
    }

    /**
     * Sets the custom model data and materials for the armor set
     * @return VentureArmorMaterial enum object containing the custom model data for each armor piece
     */
    public abstract VentureArmorMaterial ventureArmorMaterial();

    /**
     * Sets the armor color for leather armor
     * @param armorPieceTypes The armor piece type
     * @return the color for the armor piece
     */
    private Color armorColors(ArmorPieceTypes armorPieceTypes)
    {
        String armorPieceString = armorPieceTypes.getName();
        int red = armorConfig.getInt(armorPieceString + "Red");
        int green = armorConfig.getInt(armorPieceString + "Green");
        int blue = armorConfig.getInt(armorPieceString + "Blue");
        return Color.fromRGB(red, green, blue);
    }

    /**
     * The player head skin for player head helmets, if applicable
     * @return The player head skin URL as a string
     */
    private String playerHeadSkin()
    {
        String url = armorConfig.getString("HelmetURL");
        if (url != null)
        {
            return url;
        } else {
            return "";
        }
    }

    /**
     * SigBits needed for player head skin
     * @return an Array of integers containing the SigBits
     */
    private int[] sigBits()
    {
        return new int[]{ventureArmorMaterial().getArmorCustomModelData()[0], ventureArmorMaterial().getArmorCustomModelData()[0]};
    }

    /**
     * Creates the lore for the item. If an item is to have lore based off the condition of the item, a static method must be made and be invoked in the ItemRegistry renewItem methods.
     *
     * @param armorPieceTypes The armor piece type
     * @param stars The amount of stars the item has
     * @return The lore of the item
     */
    public LinkedList<String> createLore(@NotNull ArmorPieceTypes armorPieceTypes, int stars)
    {
        return new LinkedList<>(createStatLore(createVentureStats(armorPieceTypes, stars)));
    }

    /**
     * Method for creating item lore based off the player that has the item in their inventory. If an item is to have lore based off the condition of the item, a static method must be made and be invoked in the ItemRegistry renewItem methods.
     *
     * @param player          The player to make the lore for
     * @param armorPieceTypes The armor piece type
     * @param stars           The amount of stars the item has
     * @return The lore of the item
     */
    public LinkedList<String> createPlayerLore(@NotNull Player player, @NotNull ArmorPieceTypes armorPieceTypes, int stars)
    {
        return new LinkedList<>(createStatLore(createVentureStats(armorPieceTypes, stars)));
    }

    /**
     * Creates Venture Plugin Item Stats for the item
     * @param armorPieceTypes The armor piece type
     * @param stars The amount of stars the item has
     * @return The Venture Item Stats
     */
    protected HashMap<VentureItemStatKeys, Double> createVentureStats(@NotNull ArmorPieceTypes armorPieceTypes, int stars)
    {
        double meleeDamage = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.MELEE_DAMAGE, stars);
        double rangeDamage = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.RANGE_DAMAGE, stars);
        double magicDamage = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.MAGIC_DAMAGE, stars);
        double mana = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.MANA, stars);
        double health = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.HEALTH, stars);
        double defense = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.DEFENSE, stars);
        double fireDefense = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.FIRE_DEFENSE, stars);
        double projectileDefense = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.PROJECTILE_DEFENSE, stars);
        double explosionDefense = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.EXPLOSION_DEFENSE, stars);
        double magicDefense = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.MAGIC_DEFENSE, stars);
        double toughness = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.TOUGHNESS, stars);
        double fortitude = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.FORTITUDE, stars);
        double speed = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.SPEED, stars);
        double mobFortune = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.MOB_FORTUNE, stars);
        double miningFortune = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.MINING_FORTUNE, stars);
        double foragingFortune = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.FORAGING_FORTUNE, stars);
        double farmingFortune = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.FARMING_FORTUNE, stars);
        double fishingLuck = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.FISHING_LUCK, stars);
        double luck = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.LUCK, stars);
        double longevity = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.LONGEVITY, stars);
        double caffeination = armorConfig.getStat(armorPieceTypes, ArmorConfig.StatType.CAFFEINATION, stars);

        HashMap<VentureItemStatKeys, Double> itemStatKeysDoubleHashMap = new HashMap<>();
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_MELEE_DAMAGE_KEY, meleeDamage);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_RANGE_DAMAGE_KEY, rangeDamage);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_MAGIC_DAMAGE_KEY, magicDamage);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_MAX_MANA_KEY, mana);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_HEALTH_KEY, health);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_DEFENSE_KEY, defense);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_FIRE_DEFENSE_KEY, fireDefense);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_PROJECTILE_DEFENSE_KEY, projectileDefense);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_EXPLOSION_DEFENSE_KEY, explosionDefense);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_MAGIC_DEFENSE_KEY, magicDefense);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_TOUGHNESS_KEY, toughness);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_FORTITUDE_KEY, fortitude);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_SPEED_KEY, speed);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_MOB_FORTUNE_KEY, mobFortune);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_MINING_FORTUNE_KEY, miningFortune);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_FORAGING_FORTUNE_KEY, foragingFortune);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_FARMING_FORTUNE_KEY, farmingFortune);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_FISHING_LUCK_KEY, fishingLuck);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_LUCK_KEY, luck);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_LONGEVITY, longevity);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_CAFFEINATION, caffeination);

        return itemStatKeysDoubleHashMap;
    }

    protected final Multimap<Attribute, AttributeModifier> createArmorAttributes(@NotNull ArmorPieceTypes armorPieceTypes, int stars)
    {
        String armorPiece = armorPieceTypes.getName();
        double damage = armorConfig.getDouble(armorPiece + "MeleeDamage") * 0.01 * (1 + stars * this.starBonus);
        double armor = armorConfig.getDouble(armorPiece + "Defense") * (1 + stars * this.starBonus);
        double toughness = armorConfig.getDouble("toughness");
        VentureItemBuilder ventureItemBuilder = new VentureItemBuilder(Material.STONE_SWORD);

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

        ventureItemBuilder.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);
        ventureItemBuilder.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);
        ventureItemBuilder.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, toughnessModifier);

        return ventureItemBuilder.getAttributes();
    }

    /**
     * Determines the enchantability type of the armor piece
     * @param armorPieceTypes The armor piece type
     * @return The venture enchantability type
     */
    public VentureItemEnchantabilities enchantabilityType(ArmorPieceTypes armorPieceTypes)
    {
        return switch (armorPieceTypes)
        {
            case HELMET -> VentureItemEnchantabilities.HELMET;
            case CHESTPLATE -> VentureItemEnchantabilities.CHESTPLATE;
            case LEGGINGS -> VentureItemEnchantabilities.LEGGINGS;
            case BOOTS -> VentureItemEnchantabilities.BOOTS;
        };
    }

    /**
     * Creates an instance of the armor piece
     * @param armorPieceTypes The armor piece type
     * @param stars The amount of stars the item has
     * @return The armor piece as a VentureItemBuilder instance
     */
    public VentureItemBuilder createArmorSet(@NotNull ArmorPieceTypes armorPieceTypes, int stars)
    {
        String armorPieceTypesName = armorPieceTypes.getName();
        String armorName = armorPieceTypesName.substring(0,1).toUpperCase() + armorPieceTypesName.substring(1);
        String keyName = (armorName() + "_" + armorPieceTypesName).toLowerCase().replace(" ", "_");
        VentureItemBuilder ventureArmorPiece = new VentureItemBuilder(ventureArmorMaterial().getArmorMaterials()[armorPieceTypes.ordinal()])
                .setName(ItemStringBuilder.name(armorName() + " " + armorName, stars)) // Sets the name of the item
                .forceSetStars(stars) // Sets the stars on the item
                .setLore(createLore(armorPieceTypes, stars)) // Sets the lore of the item
                .setRarity(rarity()) // Sets the venture item rarity of the item
                .setUnbreakable(isUnbreakable()) // Sets whether the item is unbreakable
                .setFireResistant(isFireResistant()) // Sets whether the item is fire-resistant
                .setAttributeModifiers(createArmorAttributes(armorPieceTypes, stars), false) // Sets the attribute modifiers
                .setItemStats(createVentureStats(armorPieceTypes, stars)) // Sets the venture item stats on the item
                .setItemKey(keyName) // Sets the item key
                .setCustomModelData(ventureArmorMaterial().getArmorCustomModelData()[armorPieceTypes.ordinal()]) // Sets the custom model data on the item
                .setEnchantmentType(enchantabilityType(armorPieceTypes)); // Sets the enchantability type

        switch (ventureArmorPiece.getMaterial()) // Check if armor material is player head or leather armor
        {
            case PLAYER_HEAD -> ventureArmorPiece.setPlayerHeadSkin(playerHeadSkin(), sigBits()[0], sigBits()[1]); // Apply player head skin
            case LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS -> ventureArmorPiece.LeatherArmorColor(armorColors(armorPieceTypes)); // Apply leather armor color
        }

        return ventureArmorPiece;
    }

    /**
     * Creates a player instance of the armor piece
     * @param player The player that will determine the armor's properties
     * @param armorPieceTypes The armor piece type
     * @param stars The stars the armor piece will have
     * @return The armor piece as a VentureItemBuilder instance
     */
    @Deprecated
    public VentureItemBuilder createPlayerArmorSet(Player player, @NotNull ArmorPieceTypes armorPieceTypes, int stars)
    {
        String armorPieceTypesName = armorPieceTypes.getName();
        String armorName = armorPieceTypesName.substring(0,1).toUpperCase() + armorPieceTypesName.substring(1);
        String keyName = (armorName() + "_" + armorPieceTypesName + "_" + stars).toLowerCase().replace(" ", "_");
        VentureItemBuilder ventureItemBuilder = new VentureItemBuilder(ventureArmorMaterial().getArmorMaterials()[armorPieceTypes.ordinal()])
                .setName(ItemStringBuilder.name(armorName() + " " + armorName, stars))
                .setLore(createPlayerLore(player, armorPieceTypes, stars))
                .setRarity(rarity())
                .setUnbreakable(isUnbreakable())
                .setFireResistant(isFireResistant())
                .setAttributeModifiers(createArmorAttributes(armorPieceTypes, stars), true)
                .setItemStats(createVentureStats(armorPieceTypes, stars))
                .setItemKey(keyName)
                .setCustomModelData(ventureArmorMaterial().getArmorCustomModelData()[armorPieceTypes.ordinal()]);

        switch (ventureItemBuilder.getMaterial()) // Check if armor material is player head or leather armor
        {
            case PLAYER_HEAD -> ventureItemBuilder.setPlayerHeadSkin(playerHeadSkin(), sigBits()[0], sigBits()[1]); // Apply player head skin
            case LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS -> ventureItemBuilder.LeatherArmorColor(armorColors(armorPieceTypes)); // Apply leather armor color
        }

        return ventureItemBuilder;
    }

    /**
     * Boolean for if the player is wearing the full set
     * @param player The player wearing the armor
     * @return If the player is wearing the full set
     */
    protected boolean isWearingFullSet(Player player)
    {
        return ArmorEquipStats.isWearingFullSet(player, ventureArmorMaterial());
    }

    /**
     * Checks if the Item Stack is an armor piece of this class
     * @param itemStack The item
     * @return True if an armor piece of this class, false if not
     */
    protected boolean isArmorPiece(ItemStack itemStack)
    {
        if (itemStack != null && itemStack.getItemMeta() != null && itemStack.getItemMeta().hasCustomModelData())
        {
            int customModelData = itemStack.getItemMeta().getCustomModelData();
            for (Integer armorCMD : ventureArmorMaterial().getArmorCustomModelData())
            {
                if (customModelData == armorCMD)
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Registers the armor set to be automatically refreshed when updated and to the giveitem command
     */
    private void registerArmorSet()
    {
        HashMap<String, VentureArmorSet> armorSetItemMap = plugin.getArmorSetItemMap();
        HashMap<String, VentureItemBuilder> itemRegistryMap = plugin.getItemRegistryMap();

        int maxStars = plugin.getConfig().getInt("maxStars");

        for (ArmorPieceTypes armorPieceTypes : ArmorPieceTypes.values())
        {
            String armorPieceTypeName = armorPieceTypes.getName();
            String armorMapName = (armorName() + "_" + armorPieceTypeName).toLowerCase().replace(" ", "_");
            // Per armor piece in here
            armorSetItemMap.put(armorMapName, this);
            for (int i = 0; i < maxStars + 1; i++)
            {
                // Per armor piece and star in here
                String commandName = (armorMapName + "_" + i).toLowerCase().replace(" ", "_");
                VentureItemBuilder armorItem = createArmorSet(armorPieceTypes, i);
                itemRegistryMap.put(commandName, armorItem);
            }
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
    public boolean isPlayerArmorSet()
    {
        return false;
    }
}
