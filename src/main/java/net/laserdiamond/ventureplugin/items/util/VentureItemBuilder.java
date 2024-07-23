package net.laserdiamond.ventureplugin.items.util;

import com.google.common.collect.Multimap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.laserdiamond.ventureplugin.enchants.Components.VentureEnchants;
import net.laserdiamond.ventureplugin.events.skills.SkillsExpGainEvent;
import net.laserdiamond.ventureplugin.items.attributes.AttributeLoreNameMap;
import org.apache.commons.codec.binary.Base64;
import net.laserdiamond.ventureplugin.VenturePlugin;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Class used to help create items of this plugin. Each method from this class that modifies the item will set the item meta to the new item meta, so calling the setItemMeta method is redundant. Any major modifications to items should be done through this class.
 */
public class VentureItemBuilder {

    private final VenturePlugin plugin = VenturePlugin.getInstance();

    private final ItemStack itemStack;

    public VentureItemBuilder(Material material) {
        itemStack = new ItemStack(material, 1);
    }

    public VentureItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public VentureItemBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
    }

    public VentureItemBuilder(Material material, int amount, String name) {
        itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
    }

    public VentureItemBuilder(Material material, int amount, byte durability) {
        itemStack = new ItemStack(material, amount, durability);
    }

    /**
     * Sets the material of the item
     * @param material The new material for the item
     * @return Instance of this class
     */
    public VentureItemBuilder setMaterial(Material material)
    {
        itemStack.setType(material);
        return this;
    }

    /**
     * Gets the material of the item
     * @return The material of the item
     */
    public Material getMaterial()
    {
        return itemStack.getType();
    }

    /**
     * Sets the amount of the item in the item stack
     * @param amount The new amount of the item
     * @return Instance of this class
     */
    public VentureItemBuilder setAmount(int amount)
    {
        itemStack.setAmount(amount);
        return this;
    }

    /**
     * Gets the amount of the item in the item stack
     * @return The amount of the item in the item stack
     */
    public int getAmount()
    {
        return itemStack.getAmount();
    }

    /**
     * Clones the item
     * @return Instance of this class
     */
    @Override
    public VentureItemBuilder clone() {
        return new VentureItemBuilder(itemStack);
    }

    /**
     * Gets the item meta of the item
     * @return The item meta of the item
     */
    public ItemMeta getItemMeta()
    {
        return itemStack.getItemMeta();
    }

    /**
     * Determines if the item is an armor item
     * @return True if an armor item, false otherwise
     */
    public boolean isArmorItem()
    {
        if (itemStack.getItemMeta() instanceof ArmorMeta)
        {
            return true;
        }
        return false;
    }

    /**
     * Gets the armor meta of the item
     * @return The armor meta of the item. If the item is not an armor item, returns null
     */
    public ArmorMeta getArmorMeta()
    {
        if (itemStack.getItemMeta() instanceof ArmorMeta armorMeta)
        {
            return armorMeta;
        }
        return null;
    }

    /**
     * Determines if the item is a leather armor item
     * @return True if a leather armor item, false otherwise
     */
    public boolean isLeatherArmorItem()
    {
        if (itemStack.getItemMeta() instanceof LeatherArmorMeta)
        {
            return true;
        }
        return false;
    }

    /**
     * Gets the leather armor meta of the item
     * @return The leather armor meta of the item. If the item is not a leather armor item, returns null
     */
    public LeatherArmorMeta getLeatherArmorMeta()
    {
        if (itemStack.getItemMeta() instanceof LeatherArmorMeta leatherArmorMeta)
        {
            return leatherArmorMeta;
        }
        return null;
    }

    /**
     * Determines if the item is a head item (ex: player head, creeper head, etc.)
     * @return True if a head item, false otherwise
     */
    public boolean isHead()
    {
        if (itemStack.getItemMeta() instanceof SkullMeta)
        {
            return true;
        }
        return false;
    }

    /**
     * Gets the skull meta of the item
     * @return The skull meta of the item. If the item is not a head item, returns null
     */
    public SkullMeta getSkullMeta()
    {
        if (itemStack.getItemMeta() instanceof SkullMeta skullMeta)
        {
            return skullMeta;
        }
        return null;
    }

    /**
     * Determines if the item is an enchanted book item
     * @return True if the item is an enchanted book item, false otherwise
     */
    public boolean isEnchantedBook()
    {
        if (itemStack.getItemMeta() instanceof EnchantmentStorageMeta)
        {
            return true;
        }
        return false;
    }

    /**
     * Gets the enchantment storage meta of the item
     * @return The enchantment storage meta if the item is an enchanted book item. If the item is not an enchanted book item, returns null
     */
    public EnchantmentStorageMeta getEnchantmentStorageMeta()
    {
        if (itemStack.getItemMeta() instanceof EnchantmentStorageMeta enchantmentStorageMeta)
        {
            return enchantmentStorageMeta;
        }
        return null;
    }

    /**
     * Gets the name of the item
     * @return A String of the item's name
     */
    public String getName() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getDisplayName();
    }

    /**
     * Sets the name of the item
     * @param name The new name of the item as a string
     * @return Instance of this class
     */
    public VentureItemBuilder setName(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Forces an enchantment to be added to the item. Ignores enchantment restrictions, and will replace the previous version of the enchantment
     * @param enchantment The enchantment to add
     * @param level The level of the enchantment
     * @return Instance of this class
     */
    public VentureItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Removes an enchantment from the item
     * @param enchantment The enchantment to remove
     * @return Instance of this class
     */
    public VentureItemBuilder removeEnchantment(Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    public VentureItemBuilder setEnchantmentType(VentureItemEnchantabilities enchantmentType)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(ItemKeys.ENCHANTMENT_CATEGORY_KEY, PersistentDataType.STRING, enchantmentType.name());
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public VentureItemEnchantabilities getEnchantmentType()
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        String value = itemMeta.getPersistentDataContainer().get(ItemKeys.ENCHANTMENT_CATEGORY_KEY, PersistentDataType.STRING);
        for (VentureItemEnchantabilities enchantmentType : VentureItemEnchantabilities.values())
        {
            if (value != null && value.equals(enchantmentType.name()))
            {
                return enchantmentType;
            }
        }
        return null;
    }

    /**
     * Sets the item to be unstackable (max stack size is one) by adding a UUID tag to the item
     * @return Instance of this class
     */
    public VentureItemBuilder setUnstackable() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("uuid"), PersistentDataType.STRING, UUID.randomUUID().toString());
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the player head skin of the item. The item must be a player head item, else this method will do nothing
     * @param url The url of the skin as a string
     * @param mostSigBits Most significant 64 bits of the UUID
     * @param leastSigBits Least significant 64 bits of the UUID
     * @return Instance of this class
     */
    public VentureItemBuilder setPlayerHeadSkin(@NotNull String url, int mostSigBits, int leastSigBits) {

        if (itemStack.getItemMeta() instanceof SkullMeta skullMeta)
        {

            UUID GameProfile = new UUID(mostSigBits,leastSigBits);

            com.mojang.authlib.GameProfile gameProfile = new GameProfile(GameProfile,null);
            byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
            gameProfile.getProperties().put("textures", new Property("textures", new String(encodedData)));
            Field profileField = null;

            try {
                profileField = skullMeta.getClass().getDeclaredField("profile");
            } catch (NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
            profileField.setAccessible(true);
            try {
                profileField.set(skullMeta, gameProfile);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }

            itemStack.setItemMeta(skullMeta);
        }
        return this;
    }

    /**
     * Sets the custom model data of the item
     * @param CustomModelData The new custom model data of the item
     * @return Instance of this class
     */
    public VentureItemBuilder setCustomModelData(int CustomModelData) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(CustomModelData);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Gets the custom model data on the item
     * @return The custom model data on the item. If the item does not have custom model data, returns null
     */
    public Integer getCustomModelData() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasCustomModelData()) {
            return itemMeta.getCustomModelData();
        }
        return null;
    }

    /**
     * Checks if the item has custom model data
     * @return True if custom model data is present on the item, false otherwise
     */
    public boolean hasCustomModelData() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasCustomModelData()) {
            return true;
        }
        return false;
    }

    /**
     * Safely adds an enchantment to the item (ignores level restriction)
     * @param enchantment The enchantment to add to the item
     * @param level The level of the enchantment
     * @return Instance of this class
     */
    public VentureItemBuilder addEnchant(Enchantment enchantment, int level) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(enchantment, level, true);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Adds enchantments to the item
     * @param enchantments The enchantments to add
     * @return Instance of this class
     */
    public VentureItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        itemStack.addEnchantments(enchantments);
        return this;
    }

    /**
     * Removes all enchantments from the item
     * @return Instance of this class
     */
    public VentureItemBuilder removeEnchantments() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Map<Enchantment, Integer> enchants = itemMeta.getEnchants();
        for (Enchantment enchantment : enchants.keySet()) {
            itemMeta.removeEnchant(enchantment);
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Gets the enchantments stored on the item
     * @return The enchantments stored on the item
     */
    public Map<Enchantment, Integer> getEnchantments() {

        if (isEnchantedBook())
        {
            return getEnchantmentStorageMeta().getStoredEnchants();
        } else {
            return itemStack.getItemMeta().getEnchants();
        }
        //return itemStack.getItemMeta().getEnchants();
    }

    /**
     * Determines if the item has enchantments
     * @return True if enchantments are stored on the item, false otherwise
     */
    public boolean hasEnchants() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasEnchants()) {
            return true;
        }
        return false;
    }

    /**
     * Adds an attribute modifier to the item
     * @param name The name of the attribute
     * @param value The value of the attribute
     * @param attribute The attribute to added
     * @param operation The operation of the attribute
     * @param equipmentSlot The equipment slot the attribute becomes active
     * @return Instance of this class
     */
    public VentureItemBuilder addAttributeModifier(String name, double value, Attribute attribute, AttributeModifier.Operation operation, EquipmentSlot equipmentSlot)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), name, value, operation, equipmentSlot);
        addAttributeModifier(attribute, attributeModifier);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Adds an attribute modifier to the item
     * @param attribute The attribute to add
     * @param attributeModifier The attribute modifier to add
     * @return Instance of this class
     */
    public VentureItemBuilder addAttributeModifier(Attribute attribute, AttributeModifier attributeModifier) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addAttributeModifier(attribute, attributeModifier);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Gets the attributes stored on the item
     * @return The attributes stored on the item
     */
    public Multimap<Attribute, AttributeModifier> getAttributes() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getAttributeModifiers();
    }

    /**
     * Sets the attributes on the item. Wipes any previous attributes on the item
     * @param attributeModifiers The attribute modifiers to set on the item
     * @param hideAttributes Determines if the attributes should be hidden on the item. Set to true to hide attributes
     * @return Instance of this class
     */
    public VentureItemBuilder setAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers, boolean hideAttributes) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setAttributeModifiers(attributeModifiers);
        if (hideAttributes) itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Removes an attribute modifier from the item based on the attribute passed through
     * @param attribute The attribute of the attribute modifier
     * @return Instance of this class
     */
    public VentureItemBuilder removeAttributeModifier(Attribute attribute)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.removeAttributeModifier(attribute);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the item to be unbreakable
     * @param unbreakable True to make the item unbreakable, false otherwise
     * @return Instance of this class
     */
    public VentureItemBuilder setUnbreakable(boolean unbreakable) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(unbreakable);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the durability of the item to be infinite
     * @return Instance of this class
     */
    public VentureItemBuilder setInfiniteDurability() {
        itemStack.setDurability(Short.MAX_VALUE);
        return this;
    }

    /**
     * Gets the lore of the item
     * @return A List of Strings containing the lore of the item
     */
    public List<String> getLore() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getLore();
    }

    /**
     * Sets the lore of the item
     * @param lore The new lore for the item
     * @return
     */
    public VentureItemBuilder setLore(String... lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the lore of the item
     * @param lore The new lore for the item
     * @return Instance of this class
     */
    public VentureItemBuilder setLore(List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Removes a line from the lore. If the lore doesn't contain this string, returns this method
     * @param line The line to search for in the lore and remove
     * @return Instance of this class
     */
    public VentureItemBuilder removeLoreLine(String line) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>(itemMeta.getLore());
        if (!lore.contains(line)) return this;
        lore.remove(line);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Removes a line from the lore. If the index is less than 0 or greater than the size of the lore, returns this method
     * @param index The index in the lore
     * @return Instance of this class
     */
    public VentureItemBuilder removeLoreLine(int index) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>(itemMeta.getLore());
        if (index < 0 || index > lore.size()) return this;
        lore.remove(index);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Adds a new line to the end of the lore.
     * @param line The string to add to the lore
     * @return Instance of this class
     */
    public VentureItemBuilder addLoreLine(String line) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (itemMeta.hasLore()) {
            lore = itemMeta.getLore();
        }
        lore.add(line);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Adds a new line to the index of the lore
     * @param line The string to add to the lore
     * @param index The index to add the line to in the lore
     * @return Instance of this class
     */
    public VentureItemBuilder addLoreLine(String line, int index) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>(itemMeta.getLore());
        lore.set(index, line);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the color of the item if the item is leather armor
     * @param color The color to set the leather armor item
     * @return Instance of this class
     */
    public VentureItemBuilder LeatherArmorColor(Color color) {
        if (itemStack.getItemMeta() instanceof LeatherArmorMeta leatherArmorMeta)
        {
            leatherArmorMeta.setColor(color);
            itemStack.setItemMeta(leatherArmorMeta);
        }
        return this;
    }

    /**
     * Sets the color of the item if the item is leather armor
     * @param R The red value of the color
     * @param G The green value of the color
     * @param B The blue value of the color
     * @return Instance of this class
     */
    public VentureItemBuilder LeatherArmorColor(int R, int G, int B) {
        if (itemStack.getItemMeta() instanceof LeatherArmorMeta leatherArmorMeta)
        {
            leatherArmorMeta.setColor(Color.fromRGB(R, G, B));
            itemStack.setItemMeta(leatherArmorMeta);
        }
        return this;
    }

    /**
     * Gets the color of the leather armor item. Returns null if the item is not a leather armor item
     * @return The color of the leather armor item. If the item is not leather armor, returns null
     */
    public Color getLeatherArmorColor() {
        if (itemStack.getItemMeta() instanceof LeatherArmorMeta leatherArmorMeta)
        {
            return leatherArmorMeta.getColor();
        }
        return null;
    }

    /**
     * Gets the armor trim of the armor item
     * @return The armor trim of the armor item. Returns null if the item is not an armor item
     */
    public ArmorTrim getArmorTrim() {
        if (itemStack.getItemMeta() instanceof ArmorMeta armorMeta)
        {
            return armorMeta.getTrim();
        }
        return null;
    }

    /**
     * Gets the armor trim material of the armor item.
     * @return The armor trim material of the armor item. Returns null if the item is not an armor item.
     */
    public TrimMaterial getArmorTrimMaterial() {
        if (itemStack.getItemMeta() instanceof ArmorMeta armorMeta)
        {
            if (armorMeta.getTrim() != null) {
                return armorMeta.getTrim().getMaterial();
            }
        }
        return null;
    }

    /**
     * Gets the armor trim pattern of the armor item
     * @return The armor trim patter of the armor item. Returns null if the item is not an armor item.
     */
    public TrimPattern getArmorTrimPattern() {
        if (itemStack.getItemMeta() instanceof ArmorMeta armorMeta)
        {
            if (armorMeta.getTrim() != null) {
                return armorMeta.getTrim().getPattern();
            }
        }
        return null;
    }

    /**
     * Sets the armor trim on the item if the item is an armor item. Method does nothing if the item is not an armor item.
     * @param armorTrim The new armor trim for the item
     * @return Instance of this class
     */
    public VentureItemBuilder setArmorTrim(ArmorTrim armorTrim) {
        if (itemStack.getItemMeta() instanceof ArmorMeta armorMeta)
        {
            armorMeta.setTrim(armorTrim);
            itemStack.setItemMeta(armorMeta);
        }
        return this;
    }

    /**
     * Sets the armor trim on the item if the item is an armor item. Method does nothing if the item is not an armor item.
     * @param trimMaterial The new trim material for the item
     * @param trimPattern The new trim pattern for the item
     * @return Instance of this class
     */
    public VentureItemBuilder setArmorTrim(TrimMaterial trimMaterial, TrimPattern trimPattern)
    {
        if (itemStack.getItemMeta() instanceof ArmorMeta armorMeta)
        {
            ArmorTrim trim = new ArmorTrim(trimMaterial, trimPattern);
            armorMeta.setTrim(trim);
            itemStack.setItemMeta(armorMeta);
        }
        return this;
    }

    /**
     * Checks if the item has an armor trim
     * @return True if the item has an armor trim, false otherwise
     */
    public boolean hasArmorTrim() {
        if (itemStack.getItemMeta() instanceof ArmorMeta armorMeta)
        {
            if (armorMeta.hasTrim()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the armor trim from the item
     * @return Instance of this class
     */
    public VentureItemBuilder removeArmorTrim() {
        if (itemStack.getItemMeta() instanceof ArmorMeta armorMeta)
        {
            armorMeta.setTrim(null);
            itemStack.setItemMeta(armorMeta);
        }
        return this;
    }

    /**
     * Gets the venture item rarity of the item
     * @return The venture item rarity of the item. If the item does not have a rarity, returns null
     */
    public VentureItemRarity.Rarity getRarity() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        VentureItemRarity rarityInst = new VentureItemRarity();

        for (VentureItemRarity.Rarity rarity : VentureItemRarity.Rarity.values()) {
            if (rarity != null) {

                PersistentDataAdapterContext persistentDataAdapterContext = new PersistentDataAdapterContext() {
                    @Override
                    public @NotNull PersistentDataContainer newPersistentDataContainer() {
                        return itemMeta.getPersistentDataContainer();
                    }
                };

                String rarityFromStringPDC = itemMeta.getPersistentDataContainer().get(ItemKeys.RARITY_KEY, VentureItemRarity.STRING);
                if (rarityFromStringPDC != null) {
                    return rarityInst.fromPrimitive(rarityFromStringPDC, persistentDataAdapterContext);
                } else {
                    return null;
                }

            }
        }
        return null;
    }

    /**
     * Sets the venture item rarity of the item. The color of the item's name is also changed (does not include the stars in the item's name)
     * @param itemRarity The venture item rarity to set the item
     * @return Instance of this class
     */
    public VentureItemBuilder setRarity(VentureItemRarity.Rarity itemRarity) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = itemMeta.getDisplayName();
        itemMeta.setDisplayName(itemRarity.getRarityColor() + displayName);
        itemMeta.getPersistentDataContainer().set(ItemKeys.RARITY_KEY, VentureItemRarity.STRING, itemRarity.getRarity());
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Gets the venture item rarity of the item from the item meta passed through
     * @param itemMeta The item meta of the item
     * @return The venture item rarity of the item. If the item does not have a rarity, returns null
     */
    public static VentureItemRarity.Rarity getItemRarity(ItemMeta itemMeta) {
        VentureItemRarity rarityInst = new VentureItemRarity();

        for (VentureItemRarity.Rarity rarity : VentureItemRarity.Rarity.values()) {
            if (rarity != null) {

                PersistentDataAdapterContext persistentDataAdapterContext = new PersistentDataAdapterContext() {
                    @Override
                    public @NotNull PersistentDataContainer newPersistentDataContainer() {
                        return itemMeta.getPersistentDataContainer();
                    }
                };

                String rarityFromStringPDC = itemMeta.getPersistentDataContainer().get(ItemKeys.RARITY_KEY, VentureItemRarity.STRING);
                if (rarityFromStringPDC != null) {
                    return rarityInst.fromPrimitive(rarityFromStringPDC, persistentDataAdapterContext);
                } else {
                    return null;
                }

            }
        }
        return null;
    }

    /**
     * Sets the venture item rarity of the item from the item meta passed through
     * @param itemMeta The item meta of the item
     * @param itemRarity The new rarity of the item
     */
    public static void setItemRarity(ItemMeta itemMeta, VentureItemRarity.Rarity itemRarity) {

        String displayName = itemMeta.getDisplayName();
        itemMeta.setDisplayName(itemRarity.getRarityColor() + displayName);

        if (itemMeta.getPersistentDataContainer().get(ItemKeys.RARITY_KEY, VentureItemRarity.STRING) != null)
        {
            itemMeta.getPersistentDataContainer().remove(ItemKeys.RARITY_KEY);
        }
        itemMeta.getPersistentDataContainer().set(ItemKeys.RARITY_KEY, VentureItemRarity.STRING, itemRarity.getRarity());
    }

    /**
     * Gets the stars on the item
     * @return The amount of stars if applicable, otherwise 0
     */
    public Integer getStars()
    {
        //String itemKey = this.getItemKey();
        Integer stars = 0;

        if (getItemMeta().getPersistentDataContainer().get(ItemKeys.STARS_KEY, PersistentDataType.INTEGER) != null)
        {
            stars = getItemMeta().getPersistentDataContainer().get(ItemKeys.STARS_KEY, PersistentDataType.INTEGER);
        }
        /*
        StringBuilder starStringBuilder = new StringBuilder();
        if (itemKey != null)
        {
            for (int i = 0; i < itemKey.length(); i++)
            {
                char letter = itemKey.charAt(i);
                if (Character.isDigit(letter)) // Check if the character at the current index is an int
                {
                    starStringBuilder.append(letter); // Add the int to the string builder
                }
                if (!starStringBuilder.isEmpty() && !Character.isDigit(i)) // If the string builder already has an int in it, and the next character is not an int, break out of the loop
                {
                    break;
                }
            }
            String starString = starStringBuilder.toString();
            if (!starString.isEmpty()) // Check if final string is not empty
            {
                try
                {
                    stars = Integer.parseInt(starString);
                } catch (NumberFormatException exception)
                {
                    Bukkit.getConsoleSender().sendMessage("Could not find stars on item!");
                    return 0;
                }
            } else
            {
                return stars;
            }
        }

         */
        return stars;
    }

    /**
     * Sets the amount of stars on the item. The item must be starrable, otherwise nothing will happen to the item
     * @param stars The amount of stars the item should have. Stars cannot be less than 0 or greater than the max stars set in the config.yml
     * @return Instance of this class
     */
    public VentureItemBuilder setStars(int stars)
    {
        //String itemKey = this.getItemKey();
        stars = Math.min(Math.max(stars, 0), plugin.getConfig().getInt("maxStars")); // Stars cannot be less than 0, or greater than the max
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer().get(ItemKeys.STARS_KEY, PersistentDataType.INTEGER) != null)
        {
            itemMeta.getPersistentDataContainer().set(ItemKeys.STARS_KEY, PersistentDataType.INTEGER, stars);
        }
        itemStack.setItemMeta(itemMeta);
        /*
        String newStar = Integer.toString(Math.max(0, stars));
        if (itemKey != null)
        {
            //StringBuilder starBuilder = new StringBuilder();
            for (int i = 0; i < itemKey.length(); i++)
            {
                // TODO: Change way this works to set all the digits of the star count (only gets first) so that double digits+ stars can be possible to manage and maintain
                char letter = itemKey.charAt(i);
                if (Character.isDigit(letter))
                {
                    //starBuilder.append(letter);
                    this.setItemKey(itemKey.replace(String.valueOf(letter), newStar));
                    break;
                }
            }
        }

         */
        return this;
    }

    /**
     * Force sets the amount of stars on the item. Should only be used when the items are initially created
     * @param stars The amount of stars the item should have. Stars cannot be less than 0 or greater than the max stars set in the config.yml
     * @return Instance of this class
     */
    public VentureItemBuilder forceSetStars(int stars)
    {
        stars = Math.min(Math.max(stars, 0), plugin.getConfig().getInt("maxStars"));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(ItemKeys.STARS_KEY, PersistentDataType.INTEGER, stars);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Gets the item stat value stored on the item
     * @param itemStatKey The state to get the value from
     * @return A double representing the stat value of the stat
     */
    public Double getItemStat(VentureItemStatKeys itemStatKey) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer().get(itemStatKey.getKey(), PersistentDataType.DOUBLE) != null) {
            return itemMeta.getPersistentDataContainer().get(itemStatKey.getKey(), PersistentDataType.DOUBLE);
        }
        return 0.0;
    }

    /**
     * Sets the item stat value on the item
     * @param itemStatKey The item stat to modify
     * @param value The value to set to the stat
     * @return Instance of this class
     */
    public VentureItemBuilder setItemStat(VentureItemStatKeys itemStatKey, double value) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(itemStatKey.getKey(), PersistentDataType.DOUBLE, value);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the item stat value on the item stack passed through
     * @param itemStack The item stack to modify
     * @param key The item stat to modify
     * @param value the value to set to the stat
     */
    public static void setItemStat(ItemStack itemStack, VentureItemStatKeys key, double value) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(key.getKey(), PersistentDataType.DOUBLE, value);
        itemStack.setItemMeta(itemMeta);
    }

    /**
     * Sets the item stats on the item
     * @param itemStatsKeyMap The item stats to set on the item
     * @return Instance of this class
     */
    public VentureItemBuilder setItemStats(HashMap<VentureItemStatKeys, Double> itemStatsKeyMap) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        for (VentureItemStatKeys ventureItemStatKeys : itemStatsKeyMap.keySet()) {
            NamespacedKey itemStatKey = ventureItemStatKeys.getKey();
            Double keyValue = itemStatsKeyMap.get(ventureItemStatKeys);

            itemMeta.getPersistentDataContainer().set(itemStatKey, PersistentDataType.DOUBLE, keyValue);
        }

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the item stats on the item stack passed through
     * @param itemStack The item stack to modify
     * @param itemStatMap The item stats to set on the item
     */
    public static void setItemStats(ItemStack itemStack, HashMap<VentureItemStatKeys, Double> itemStatMap) {

        ItemMeta itemMeta = itemStack.getItemMeta();
        for (VentureItemStatKeys ventureItemStatKeys : itemStatMap.keySet()) {
            NamespacedKey itemStatKey = ventureItemStatKeys.getKey();
            Double keyValue = itemStatMap.get(ventureItemStatKeys);
            if (itemMeta.getPersistentDataContainer().get(itemStatKey, PersistentDataType.DOUBLE) != null) {
                itemMeta.getPersistentDataContainer().remove(itemStatKey);
            }
            itemMeta.getPersistentDataContainer().set(itemStatKey, PersistentDataType.DOUBLE, keyValue);
        }

        itemStack.setItemMeta(itemMeta);
    }

    /**
     * Gets the item stats on the item
     * @return The item stats of the item
     */
    public HashMap<VentureItemStatKeys, Double> getItemStats() {

        HashMap<VentureItemStatKeys, Double> itemStatsMap = new HashMap<>();
        for (VentureItemStatKeys ventureItemStatKeys : VentureItemStatKeys.values()) {
            Double itemStatKeyValue = getItemStat(ventureItemStatKeys);
            itemStatsMap.put(ventureItemStatKeys, itemStatKeyValue);
        }

        return itemStatsMap;
    }

    /**
     * Sets the persistent data integer value for a key on an item
     * @param dataKey The key to set the value on the item
     * @param value The value to be set for the key (int)
     * @return Instance of this class
     */
    public VentureItemBuilder setUniqueItemDataKeyInt(UniqueVentureItemDataKey dataKey, int value)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(dataKey.getKey(), PersistentDataType.INTEGER, value);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the persistent data integer value for a key on the ItemStack passed through
     * @param itemStack The item stack to modify
     * @param dataKey The key to set the value on the item
     * @param value The value to be set for the key (int)
     */
    public static void setUniqueItemDataKeyInt(ItemStack itemStack, UniqueVentureItemDataKey dataKey, int value)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(dataKey.getKey(), PersistentDataType.INTEGER, value);
        itemStack.setItemMeta(itemMeta);
    }

    /**
     * Sets the persistent data double value for a key on an item
     * @param dataKey The key to set the value on the item
     * @param value The value to be set for the key (double)
     * @return Instance of this class
     */
    public VentureItemBuilder setUniqueItemDataKeyDouble(UniqueVentureItemDataKey dataKey, double value)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(dataKey.getKey(), PersistentDataType.DOUBLE, value);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the persistent data double value of a key on the ItemStack passed through
     * @param itemStack The item stack to modify
     * @param dataKey The key to set the value on the item
     * @param value The value to be set for the key (double)
     */
    public static void setUniqueItemDataKeyDouble(ItemStack itemStack, UniqueVentureItemDataKey dataKey, double value)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(dataKey.getKey(), PersistentDataType.DOUBLE, value);
        itemStack.setItemMeta(itemMeta);
    }

    /**
     * Gets the integer stored for the key on the item
     * @param dataKey The key to retrieve the value from (Integer)
     * @return The integer stored to the key on the item
     */
    public Integer getUniqueItemDataKeyInt(UniqueVentureItemDataKey dataKey)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer().get(dataKey.getKey(), PersistentDataType.INTEGER) != null)
        {
            return itemMeta.getPersistentDataContainer().get(dataKey.getKey(), PersistentDataType.INTEGER);
        }
        return 0;
    }

    /**
     * Gets the integer stored for the key on the Item Stack passed through
     * @param itemStack The item stack to retrieve the data from
     * @param dataKey The key to retrieve the data from (Integer)
     * @return The integer stored to the key on the item
     */
    public static Integer getUniqueItemDataKeyInt(ItemStack itemStack, UniqueVentureItemDataKey dataKey)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer().get(dataKey.getKey(), PersistentDataType.INTEGER) != null)
        {
            return itemMeta.getPersistentDataContainer().get(dataKey.getKey(), PersistentDataType.INTEGER);
        }
        return 0;
    }

    /**
     * Gets the double stored for the key on the item
     * @param dataKey The key to retrieve the data from
     * @return The double stored to the key on the item
     */
    public Double getUniqueItemDataKeyDouble(UniqueVentureItemDataKey dataKey)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer().get(dataKey.getKey(), PersistentDataType.DOUBLE) != null)
        {
            return itemMeta.getPersistentDataContainer().get(dataKey.getKey(), PersistentDataType.DOUBLE);
        }
        return 0.0;
    }

    /**
     * Gets the double stored for the key on the Item Stack passed through
     * @param itemStack The item stack to retrieve the data from
     * @param dataKey The key to retrieve the data from
     * @return The double stored to the key on the item
     */
    public static Double getUniqueItemDataKeyDouble(ItemStack itemStack, UniqueVentureItemDataKey dataKey)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer().get(dataKey.getKey(), PersistentDataType.DOUBLE) != null)
        {
            return itemMeta.getPersistentDataContainer().get(dataKey.getKey(), PersistentDataType.DOUBLE);
        }
        return 0.0;
    }

    /**
     * Creates lore based on the VentureItemStats of the item
     * @param itemStatsKeyMap The VentureItemStats of the item
     * @return A LinkedList of Strings displaying the VentureItemStats of the item
     */
    public static LinkedList<String> createStatLore(HashMap<VentureItemStatKeys, Double> itemStatsKeyMap) {

        LinkedList<String> statLore = new LinkedList<>();

        DecimalFormat doubleDecimalFormat = new DecimalFormat("0.00");

        // Create Lore for item
        for (VentureItemStatKeys ventureItemStatKeys : VentureItemStatKeys.values())
        {
            String displayName = ventureItemStatKeys.getDisplayName();
            double statValue = 0;
            if (itemStatsKeyMap.get(ventureItemStatKeys) != null)
            {
                statValue = itemStatsKeyMap.get(ventureItemStatKeys);
            }

            String displayString = displayName + ventureItemStatKeys.getDisplayColor() + doubleDecimalFormat.format(statValue);
            StringBuilder displayStringBuilder = new StringBuilder(displayString);

            if (VentureItemStatKeys.isPercentageStat(ventureItemStatKeys))
            {
                displayStringBuilder.append("%");

            }
            displayStringBuilder.append(ventureItemStatKeys.getStatSymbol().getSymbol());
            if (statValue != 0)
            {
                statLore.add(displayStringBuilder.toString());
            }
        }
        return statLore;
    }

    /**
     * Creates lore based on the VentureItemStats and Attributes of the item
     * @param itemStatsKeyMap The VentureItemStats of the item
     * @param attributes The attributes of the item
     * @return A LinkedList of Strings displaying the VentureItemStats and Attributes of the item
     */
    public static LinkedList<String> createStatLore(HashMap<VentureItemStatKeys, Double> itemStatsKeyMap, Multimap<Attribute, AttributeModifier> attributes) {

        LinkedList<String> statLore = new LinkedList<>();

        DecimalFormat doubleDecimalFormat = new DecimalFormat("0.00");

        // Create Lore for item
        for (VentureItemStatKeys ventureItemStatKeys : VentureItemStatKeys.values()) {
            String displayName = ventureItemStatKeys.getDisplayName();
            double statValue = 0;
            if (itemStatsKeyMap.get(ventureItemStatKeys) != null) {
                statValue = itemStatsKeyMap.get(ventureItemStatKeys);
            }

            String displayString = displayName + ventureItemStatKeys.getDisplayColor() + doubleDecimalFormat.format(statValue);
            StringBuilder displayStringBuilder = new StringBuilder(displayString);

            if (VentureItemStatKeys.isPercentageStat(ventureItemStatKeys)) {
                displayStringBuilder.append("%");

            }
            if (statValue > 0) {
                statLore.add(displayStringBuilder.toString());
            }
        }

        if (attributes != null) {
            LinkedList<String> attributeLore = createAttributeLore(attributes);
            statLore.addAll(attributeLore);
        }

        return statLore;
    }

    /**
     * Creates lore based on the attributes of the item
     * @param attributes The attributes of the item
     * @return A LinkedList of Strings displaying the attributes of the item
     */
    public static LinkedList<String> createAttributeLore(Multimap<Attribute, AttributeModifier> attributes) {

        LinkedList<String> attributeLore = new LinkedList<>();

        DecimalFormat doubleDecimalFormat = new DecimalFormat("0.00");

        for (Attribute attribute : attributes.keys()) {
            for (AttributeModifier attributeModifier : attributes.get(attribute)) {
                String name = AttributeLoreNameMap.ofDisplayName(attribute);
                ChatColor displayColor = AttributeLoreNameMap.ofDisplayColor(attribute);
                double amount = attributeModifier.getAmount();
                if (attribute.equals(Attribute.GENERIC_KNOCKBACK_RESISTANCE)) {
                    amount *= 10;
                }
                attributeLore.add(name + displayColor + doubleDecimalFormat.format(amount));
            }
        }

        return attributeLore;
    }

    /**
     * Sets if the item should be fire-resistant
     * @param fireResistant True if fire-resistant, false if not
     * @return Instance of this class
     */
    public VentureItemBuilder setFireResistant(boolean fireResistant) {
        if (fireResistant)
        {
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.getPersistentDataContainer().set(ItemKeys.FIRE_RESISTANCE_KEY, PersistentDataType.BOOLEAN, true);
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    /**
     * Returns if the item is fire resistance
     * @return True if fire-resistant, false if not
     */
    public boolean isFireResistant() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer().get(ItemKeys.FIRE_RESISTANCE_KEY, PersistentDataType.BOOLEAN).equals(true)) {
            return true;
        }
        return false;
    }

    /**
     * Hides all the item flags on the item
     * @return Instance of this class
     */
    public VentureItemBuilder hideAllItemFlags() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        for (ItemFlag itemFlag : ItemFlag.values()) {
            itemMeta.addItemFlags(itemFlag);
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the item key String on the item. Should only be used when initially creating the item
     * @param value The item key String to set on the item
     * @return Instance of this class
     */
    public VentureItemBuilder setItemKey(String value)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(ItemKeys.ITEM_MAP_KEY, PersistentDataType.STRING, value);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Gets the item key of the item as a String. The item key String stored on the item is used for updating the item
     * @return The item key String
     */
    public String getItemKey()
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(ItemKeys.ITEM_MAP_KEY, PersistentDataType.STRING);
    }

    /**
     * Sets the menu item key String on the item. Should only be used when initially creating the menu item
     * @param value The menu item key String to set on the item
     * @return Instance of this class
     */
    public VentureItemBuilder setMenuItemKey(String value)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(ItemKeys.MENU_ITEM_MAP_KEY, PersistentDataType.STRING, value);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Gets the menu item key of the item as a String. The menu item key String stored on the item is used for updating the item
     * @return The menu item key String
     */
    public String getMenuItemKey()
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(ItemKeys.MENU_ITEM_MAP_KEY, PersistentDataType.STRING);
    }

    public VentureItemBuilder setSkillProgressSkill(SkillsExpGainEvent.Skill skill)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(ItemKeys.SKILL_PROGRESS_SKILL_KEY, PersistentDataType.STRING, skill.name().toLowerCase() + "_progress");
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public String getSkillProgressSkillKey()
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(ItemKeys.SKILL_PROGRESS_SKILL_KEY, PersistentDataType.STRING);
    }

    public VentureItemBuilder setSkillProgressLvl(Integer lvl)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(ItemKeys.SKILL_PROGRESS_LEVEL_KEY, PersistentDataType.INTEGER, lvl);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public Integer getSkillProgressLvl()
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(ItemKeys.SKILL_PROGRESS_LEVEL_KEY, PersistentDataType.INTEGER);
    }

    /**
     * Sets if a potion is ready for brewing exp collection. If true, the potion exp hasn't been collected yet, and claiming it will grant the brewing exp. If false, no exp will be granted
     * @param ready Whether the potion is ready for exp collection
     * @return Instance of this class
     */
    public VentureItemBuilder setPotionReady(boolean ready)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta instanceof PotionMeta potionMeta)
        {
            potionMeta.getPersistentDataContainer().set(ItemKeys.POTION_CLAIMED_KEY, PersistentDataType.BOOLEAN, ready);
            itemStack.setItemMeta(potionMeta);
        }
        return this;
    }

    /**
     * Gets if a potion is ready for brewing exp collection
     * @return True if ready, false if not
     */
    public Boolean getPotionReady()
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta instanceof PotionMeta potionMeta)
        {
            if (potionMeta.getPersistentDataContainer().get(ItemKeys.POTION_CLAIMED_KEY, PersistentDataType.BOOLEAN) != null &&
                potionMeta.getPersistentDataContainer().has(ItemKeys.POTION_CLAIMED_KEY, PersistentDataType.BOOLEAN))
            {
                return potionMeta.getPersistentDataContainer().get(ItemKeys.POTION_CLAIMED_KEY, PersistentDataType.BOOLEAN);
            } else
            {
                Bukkit.broadcastMessage("not ready :(");
            }
        }
        return false;
    }

    /**
     * Sets if an item stack is ready for brewing exp collection. The item stack's item meta must be an instance of a potion meta, otherwise the item stack is returned. If the item stack is null, air will be returned.
     * @param itemStack The item stack to modify
     * @param ready Sets if the item stack is ready for brewing exp collection. If set to true, clicking on the item/moving it will grant brewing exp
     * @return The item stack passed through
     */
    public static ItemStack setPotionItemReady(ItemStack itemStack, boolean ready)
    {
        if (itemStack != null)
        {
            if (itemStack.getItemMeta() != null && itemStack.getItemMeta() instanceof PotionMeta potionMeta)
            {
                potionMeta.getPersistentDataContainer().set(ItemKeys.POTION_CLAIMED_KEY, PersistentDataType.BOOLEAN, ready);
                itemStack.setItemMeta(potionMeta);
            }
            return itemStack;
        }
        return new ItemStack(Material.AIR);
    }

    /**
     * Gets if the item stack is ready for brewing exp collection. If the item stack's item meta is not an instance of a potion meta, this method returns false.
     * @param itemStack The item stack to check
     * @return True if ready for exp collection, false if not or if the item stack is not a potion
     */
    public static Boolean getPotionItemReady(ItemStack itemStack)
    {
        if (itemStack != null)
        {
           if (itemStack.getItemMeta() != null && itemStack.getItemMeta() instanceof PotionMeta potionMeta)
           {
               return potionMeta.getPersistentDataContainer().get(ItemKeys.POTION_CLAIMED_KEY, PersistentDataType.BOOLEAN);
           }
        }
        return false;
    }

    /**
     * Returns the instance as an item stack
     * @return The completed item stack
     */
    public ItemStack toItemStack() {
        return itemStack;
    }
}
