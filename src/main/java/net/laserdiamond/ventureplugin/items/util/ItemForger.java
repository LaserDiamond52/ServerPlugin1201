package net.laserdiamond.ventureplugin.items.util;

import com.google.common.collect.Multimap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.laserdiamond.ventureplugin.util.UniqueVentureItemDataKey;
import net.laserdiamond.ventureplugin.util.VentureItemStatKeys;
import net.laserdiamond.ventureplugin.items.attributes.AttributeLoreNameMap;
import org.apache.commons.codec.binary.Base64;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.util.ItemPropertiesKeys;
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

public class ItemForger {

    private final VenturePlugin plugin = VenturePlugin.getInstance();

    private final ItemStack itemStack;
    private static final NamespacedKey rarityKey = ItemPropertiesKeys.RARITY_KEY.getKey();
    private static final NamespacedKey starsKey = ItemPropertiesKeys.STARS_KEY.getKey();
    private final NamespacedKey fireResistanceKey = ItemPropertiesKeys.FIRE_RESISTANCE_KEY.getKey();
    private final NamespacedKey healthKey = VentureItemStatKeys.ARMOR_HEALTH_KEY.getKey();
    private final NamespacedKey armorKey = VentureItemStatKeys.ARMOR_DEFENSE_KEY.getKey();
    private final NamespacedKey toughnessKey = VentureItemStatKeys.ARMOR_TOUGHNESS_KEY.getKey();
    private final NamespacedKey fortitudeKey = VentureItemStatKeys.ARMOR_FORTITUDE_KEY.getKey();
    private final NamespacedKey maxManaKey = VentureItemStatKeys.ARMOR_MAX_MANA_KEY.getKey();
    private final NamespacedKey meleeDamageKey = VentureItemStatKeys.ARMOR_MELEE_DAMAGE_KEY.getKey();
    private final NamespacedKey magicDamageKey = VentureItemStatKeys.ARMOR_MAGIC_DAMAGE_KEY.getKey();
    private final NamespacedKey rangeDamageKey = VentureItemStatKeys.ARMOR_RANGE_DAMAGE_KEY.getKey();
    private static final NamespacedKey ITEM_MAP_KEY = ItemPropertiesKeys.ITEM_MAP_KEY.getKey();

    private final int maxStars = plugin.getConfig().getInt("maxStars");

    public ItemForger(Material material) {
        itemStack = new ItemStack(material);
    }

    public ItemForger(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemForger(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
    }

    public ItemForger(Material material, int amount, String name) {
        itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
    }

    public ItemForger(Material material, int amount, byte durability) {
        itemStack = new ItemStack(material, amount, durability);
    }

    public ItemForger setMaterial(Material material)
    {
        itemStack.setType(material);
        return this;
    }

    public Material getMaterial()
    {
        return itemStack.getType();
    }

    public ItemForger clone() {
        return new ItemForger(itemStack);
    }

    public ItemMeta getItemMeta()
    {
        return itemStack.getItemMeta();
    }

    public boolean isArmorItem()
    {
        if (itemStack.getItemMeta() instanceof ArmorMeta)
        {
            return true;
        }
        return false;
    }

    public ArmorMeta getArmorMeta()
    {
        if (itemStack.getItemMeta() instanceof ArmorMeta armorMeta)
        {
            return armorMeta;
        }
        return null;
    }

    public boolean isLeatherArmorItem()
    {
        if (itemStack.getItemMeta() instanceof LeatherArmorMeta)
        {
            return true;
        }
        return false;
    }

    public LeatherArmorMeta getLeatherArmorMeta()
    {
        if (itemStack.getItemMeta() instanceof LeatherArmorMeta leatherArmorMeta)
        {
            return leatherArmorMeta;
        }
        return null;
    }

    public boolean isHead()
    {
        if (itemStack.getItemMeta() instanceof SkullMeta)
        {
            return true;
        }
        return false;
    }

    public SkullMeta getSkullMeta()
    {
        if (itemStack.getItemMeta() instanceof SkullMeta skullMeta)
        {
            return skullMeta;
        }
        return null;
    }

    public boolean isEnchantedBook()
    {
        if (itemStack.getItemMeta() instanceof EnchantmentStorageMeta)
        {
            return true;
        }
        return false;
    }

    public EnchantmentStorageMeta getEnchantmentStorageMeta()
    {
        if (itemStack.getItemMeta() instanceof EnchantmentStorageMeta enchantmentStorageMeta)
        {
            return enchantmentStorageMeta;
        }
        return null;
    }

    public String getName() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getDisplayName();
    }

    public ItemForger setName(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemForger addUnsafeEnchantment(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemForger removeEnchantment(Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    public ItemForger setUnstackable() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("uuid"), PersistentDataType.STRING, UUID.randomUUID().toString());
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemForger setPlayerHeadSkin(@NotNull String url, int mostSigBits, int leastSigBits) {

        if (itemStack.getItemMeta() instanceof SkullMeta skullMeta)
        {
            //SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

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
        /*
        try {

        } catch (ClassCastException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not cast item to 'SkullMeta'!");
            exception.printStackTrace();
        }

         */
        return this;
    }

    public ItemForger setCustomModelData(int CustomModelData) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(CustomModelData);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public Integer getCustomModelData() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasCustomModelData()) {
            return itemMeta.getCustomModelData();
        }
        return null;
    }

    public boolean hasCustomModelData() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasCustomModelData()) {
            return true;
        }
        return false;
    }

    public ItemForger addEnchant(Enchantment enchantment, int level) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(enchantment, level, true);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemForger addEnchantments(Map<Enchantment, Integer> enchantments) {
        itemStack.addEnchantments(enchantments);
        return this;
    }

    public ItemForger removeEnchantments() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Map<Enchantment, Integer> enchants = itemMeta.getEnchants();
        for (Enchantment enchantment : enchants.keySet()) {
            itemMeta.removeEnchant(enchantment);
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public Map<Enchantment, Integer> getEnchantments() {

        if (isEnchantedBook())
        {
            return getEnchantmentStorageMeta().getStoredEnchants();
        } else {
            return itemStack.getItemMeta().getEnchants();
        }
        //return itemStack.getItemMeta().getEnchants();
    }

    public boolean hasEnchants() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasEnchants()) {
            return true;
        }
        return false;
    }

    public ItemForger addAttributeModifer(String name, double amount, Attribute attribute, AttributeModifier.Operation operation, EquipmentSlot equipmentSlot)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), name, amount, operation, equipmentSlot);
        addAttributeModifier(attribute, attributeModifier);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemForger addAttributeModifier(Attribute attribute, AttributeModifier attributeModifier) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addAttributeModifier(attribute, attributeModifier);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public Multimap<Attribute, AttributeModifier> getAttributes() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getAttributeModifiers();
    }

    public ItemForger setAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers, boolean hideAttributes) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setAttributeModifiers(attributeModifiers);
        if (hideAttributes) itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemForger removeAttributeModifiers(Attribute attribute)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.removeAttributeModifier(attribute);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemForger setUnbreakable(boolean unbreakable) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(unbreakable);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemForger setInfiniteDurability() {
        itemStack.setDurability(Short.MAX_VALUE);
        return this;
    }

    public List<String> getLore() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getLore();
    }

    public ItemForger setLore(String... lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemForger setLore(List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemForger removeLoreLine(String line) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>(itemMeta.getLore());
        if (!lore.contains(line)) return this;
        lore.remove(line);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemForger removeLoreLine(int index) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>(itemMeta.getLore());
        if (index < 0 || index > lore.size()) return this;
        lore.remove(index);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemForger addLoreLine(String line) {
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

    public ItemForger addLoreLine(String line, int index) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>(itemMeta.getLore());
        lore.set(index, line);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemForger LeatherArmorColor(Color color) {
        if (itemStack.getItemMeta() instanceof LeatherArmorMeta leatherArmorMeta)
        {
            leatherArmorMeta.setColor(color);
            itemStack.setItemMeta(leatherArmorMeta);
        }
        /*
        try {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();


        } catch (ClassCastException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not cast item to 'LeatherArmorMeta'!");
            exception.printStackTrace();
        }

         */
        return this;
    }

    public ItemForger LeatherArmorColor(int R, int G, int B) {
        if (itemStack.getItemMeta() instanceof LeatherArmorMeta leatherArmorMeta)
        {
            leatherArmorMeta.setColor(Color.fromRGB(R, G, B));
            itemStack.setItemMeta(leatherArmorMeta);
        }
        /*
        try {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();


        } catch (ClassCastException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not cast item to 'LeatherArmorMeta'!");
            exception.printStackTrace();
        }
         */
        return this;
    }

    public Color getLeatherArmorColor() {
        if (itemStack.getItemMeta() instanceof LeatherArmorMeta leatherArmorMeta)
        {
            return leatherArmorMeta.getColor();
        }
        /*
        try {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();

        } catch (ClassCastException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not cast item to 'LeatherArmorMeta'!");
            exception.printStackTrace();
        }
         */
        return null;
    }

    public ArmorTrim getArmorTrim() {
        if (itemStack.getItemMeta() instanceof ArmorMeta armorMeta)
        {
            return armorMeta.getTrim();
        }
        /*
        try {
            ArmorMeta armorMeta = (ArmorMeta) itemStack.getItemMeta();

        } catch (ClassCastException ignored) {
            //Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not case item to 'ArmorMeta'!");
            //exception.printStackTrace();
        }

         */
        return null;
    }

    public TrimMaterial getArmorTrimMaterial() {
        if (itemStack.getItemMeta() instanceof ArmorMeta armorMeta)
        {
            if (armorMeta.getTrim() != null) {
                return armorMeta.getTrim().getMaterial();
            }
        }
        /*
        try {
            ArmorMeta armorMeta = (ArmorMeta) itemStack.getItemMeta();

        } catch (ClassCastException ignored) {
            //Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not case item to 'ArmorMeta'!");
            //exception.printStackTrace();
        }
         */
        return null;
    }

    public TrimPattern getArmorTrimPattern() {
        if (itemStack.getItemMeta() instanceof ArmorMeta armorMeta)
        {
            if (armorMeta.getTrim() != null) {
                return armorMeta.getTrim().getPattern();
            }
        }
        /*
        try {
            ArmorMeta armorMeta = (ArmorMeta) itemStack.getItemMeta();

        } catch (ClassCastException ignored) {
            //Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not cast item to 'ArmorMeta'!");
            //exception.printStackTrace();
        }

         */
        return null;
    }

    public ItemForger setArmorTrim(ArmorTrim armorTrim) {
        if (itemStack.getItemMeta() instanceof ArmorMeta armorMeta)
        {
            armorMeta.setTrim(armorTrim);
            itemStack.setItemMeta(armorMeta);
        }
        /*
        try {
            ArmorMeta armorMeta = (ArmorMeta) itemStack.getItemMeta();

        } catch (ClassCastException ignored) {
            //Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not cast item to 'ArmorMeta'!");
            //exception.printStackTrace();
        }

         */
        return this;
    }

    public ItemForger setArmorTrim(TrimMaterial trimMaterial, TrimPattern trimPattern)
    {
        if (itemStack.getItemMeta() instanceof ArmorMeta armorMeta)
        {
            ArmorTrim trim = new ArmorTrim(trimMaterial, trimPattern);
            armorMeta.setTrim(trim);
            itemStack.setItemMeta(armorMeta);
        }
        return this;
    }

    public boolean hasArmorTrim() {
        if (itemStack.getItemMeta() instanceof ArmorMeta armorMeta)
        {
            if (armorMeta.hasTrim()) {
                return true;
            }
        }
        /*
        try {
            ArmorMeta armorMeta = (ArmorMeta) itemStack.getItemMeta();

        } catch (ClassCastException ignored) {
            //Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not cast item to 'ArmorMeta'!");
            //exception.printStackTrace();
        }

         */
        return false;
    }

    public ItemForger removeArmorTrim() {
        if (itemStack.getItemMeta() instanceof ArmorMeta armorMeta)
        {
            armorMeta.setTrim(null);
            itemStack.setItemMeta(armorMeta);
        }
        /*
        try {
            ArmorMeta armorMeta = (ArmorMeta) itemStack.getItemMeta();

        } catch (ClassCastException ignored) {
            //Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not cast item to 'ArmorMeta'!");
            //exception.printStackTrace();
        }

         */
        return this;
    }


    @Deprecated
    public String getRarityOld() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        String rarityString = itemMeta.getPersistentDataContainer().get(rarityKey, PersistentDataType.STRING);

        for (VentureItemRarity.Rarity rarity : VentureItemRarity.Rarity.values()) {
            if (rarityString != null) {
                if (rarityString.equals(rarity.getRarity())) {
                    return rarityString;
                }
            }
        }

        return null;
    }

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

                String rarityFromStringPDC = itemMeta.getPersistentDataContainer().get(rarityKey, VentureItemRarity.STRING);
                if (rarityFromStringPDC != null) {
                    return rarityInst.fromPrimitive(rarityFromStringPDC, persistentDataAdapterContext);
                } else {
                    return null;
                }

            }
        }
        return null;
    }

    @Deprecated
    public ItemForger setRarityOld(VentureItemRarity.Rarity itemRarity) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = itemMeta.getDisplayName();
        itemMeta.setDisplayName(itemRarity.getRarityColor() + displayName);
        itemMeta.getPersistentDataContainer().set(rarityKey, PersistentDataType.STRING, itemRarity.getRarity());

        List<String> lore = itemMeta.getLore();
        List<String> rarityLore = new ArrayList<>();
        if (lore != null) {
            for (String l : lore) {
                    rarityLore.add(l);
            }
        }
        rarityLore.add(itemRarity.getDisplayName() + " Item");

        itemMeta.setLore(rarityLore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }


    public ItemForger setRarity(VentureItemRarity.Rarity itemRarity) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = itemMeta.getDisplayName();
        itemMeta.setDisplayName(itemRarity.getRarityColor() + displayName);
        itemMeta.getPersistentDataContainer().set(rarityKey, VentureItemRarity.STRING, itemRarity.getRarity());
        itemStack.setItemMeta(itemMeta);
        return this;
    }

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

                String rarityFromStringPDC = itemMeta.getPersistentDataContainer().get(rarityKey, VentureItemRarity.STRING);
                if (rarityFromStringPDC != null) {
                    return rarityInst.fromPrimitive(rarityFromStringPDC, persistentDataAdapterContext);
                } else {
                    return null;
                }

            }
        }
        return null;
    }

    public static void setItemRarity(ItemMeta itemMeta, VentureItemRarity.Rarity itemRarity, String newDisplayName) {

        itemMeta.setDisplayName(newDisplayName);

        if (itemMeta.getPersistentDataContainer().get(rarityKey, VentureItemRarity.STRING) != null) {
            itemMeta.getPersistentDataContainer().remove(rarityKey);
        }
        itemMeta.getPersistentDataContainer().set(rarityKey, VentureItemRarity.STRING, itemRarity.getRarity());
    }

    @Deprecated
    public Integer getStars() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer().get(starsKey, PersistentDataType.INTEGER) != null) {
            return itemMeta.getPersistentDataContainer().get(starsKey, PersistentDataType.INTEGER);
        }
        return 0;
    }

    /**
     * Gets the stars on the item. This is checked through the item key, where the stars are stored at the end of the string key
     * <p>
     * Item key names SHOULD NOT include numbers in their name. This can mess up this system and make it unreliable
     * @return The stars if applicable, otherwise 0
     */
    public Integer getStarsNew()
    {
        String itemKey = this.getItemKey();
        int stars = 0;
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
        return stars;
    }

    /**
     * Sets the amount of stars on the item
     * @param stars The amount of stars the item should have. Stars cannot be less than 0 or greater than the max stars set in the config.yml
     * @return ItemForger instance of item (builder class)
     */
    public ItemForger setStarsNew(int stars)
    {
        String itemKey = this.getItemKey();
        stars = Math.min(stars, plugin.getConfig().getInt("maxStars"));
        String newStar = Integer.toString(Math.max(0, stars));
        if (itemKey != null)
        {
            for (int i = 0; i < itemKey.length(); i++)
            {
                // TODO: Change way this works to get all the digits of the star count (only gets first) so that double digits+ stars can be possible to manage and maintain
                char letter = itemKey.charAt(i);
                if (Character.isDigit(letter))
                {
                    this.setItemKey(itemKey.replace(String.valueOf(letter), newStar));
                    break;
                }
            }
        }
        return this;
    }

    @Deprecated
    public static Integer getItemStars(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer().get(starsKey, PersistentDataType.INTEGER) != null) {
            return itemMeta.getPersistentDataContainer().get(starsKey, PersistentDataType.INTEGER);
        }
        return 0;
    }

    @Deprecated
    public ItemForger setStars(int starCount) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        starCount = Math.min(Math.max(0, starCount), maxStars);
        itemMeta.getPersistentDataContainer().set(starsKey, PersistentDataType.INTEGER, starCount);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    @Deprecated
    public Double getHealth() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(healthKey, PersistentDataType.DOUBLE);
    }

    @Deprecated
    public ItemForger setHealth(double healthPoints) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(healthKey, PersistentDataType.DOUBLE, healthPoints);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    @Deprecated
    public Double getArmor() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(armorKey, PersistentDataType.DOUBLE);
    }

    @Deprecated
    public ItemForger setArmor(double armorPoints) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(armorKey, PersistentDataType.DOUBLE, armorPoints);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    @Deprecated
    public Double getToughness() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(toughnessKey, PersistentDataType.DOUBLE);
    }

    @Deprecated
    public ItemForger setToughness(double toughnessPoints) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(toughnessKey, PersistentDataType.DOUBLE, toughnessPoints);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    @Deprecated
    public Double getFortitude() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(fortitudeKey, PersistentDataType.DOUBLE);
    }

    @Deprecated
    public ItemForger setFortitude(double fortitudePoints) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(fortitudeKey, PersistentDataType.DOUBLE, fortitudePoints);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    @Deprecated
    public Double getMaxMana() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(maxManaKey, PersistentDataType.DOUBLE);
    }

    @Deprecated
    public ItemForger setMaxMana(double manaPoints) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(maxManaKey, PersistentDataType.DOUBLE, manaPoints);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    @Deprecated
    public Double getMeleeDamage() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(meleeDamageKey, PersistentDataType.DOUBLE);
    }

    @Deprecated
    public ItemForger setMeleeDamage(double meleeDamagePoints) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(meleeDamageKey, PersistentDataType.DOUBLE, meleeDamagePoints);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    @Deprecated
    public Double getMagicDamage() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(magicDamageKey, PersistentDataType.DOUBLE);
    }

    @Deprecated
    public ItemForger setMagicDamage(double magicDamagePoints) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(magicDamageKey, PersistentDataType.DOUBLE, magicDamagePoints);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    @Deprecated
    public Double getRangeDamage() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(rangeDamageKey, PersistentDataType.DOUBLE);
    }

    @Deprecated
    public ItemForger setRangeDamage(double rangeDamagePoints) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(rangeDamageKey, PersistentDataType.DOUBLE, rangeDamagePoints);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public Double getItemStat(VentureItemStatKeys itemStatKey) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer().get(itemStatKey.getKey(), PersistentDataType.DOUBLE) != null) {
            return itemMeta.getPersistentDataContainer().get(itemStatKey.getKey(), PersistentDataType.DOUBLE);
        }
        return 0.0;
    }

    public ItemForger setItemStat(VentureItemStatKeys itemStatKey, double statPoints) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(itemStatKey.getKey(), PersistentDataType.DOUBLE, statPoints);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public static void setItemStat(ItemStack itemStack, VentureItemStatKeys key, double points) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(key.getKey(), PersistentDataType.DOUBLE, points);
        itemStack.setItemMeta(itemMeta);
    }

    public ItemForger setItemStats(HashMap<VentureItemStatKeys, Double> itemStatsKeyMap) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        for (VentureItemStatKeys ventureItemStatKeys : itemStatsKeyMap.keySet()) {
            NamespacedKey itemStatKey = ventureItemStatKeys.getKey();
            Double keyValue = itemStatsKeyMap.get(ventureItemStatKeys);

            itemMeta.getPersistentDataContainer().set(itemStatKey, PersistentDataType.DOUBLE, keyValue);
        }


        itemStack.setItemMeta(itemMeta);
        return this;
    }

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

    public HashMap<VentureItemStatKeys, Double> getItemStats() {

        HashMap<VentureItemStatKeys, Double> itemStatsMap = new HashMap<>();
        for (VentureItemStatKeys ventureItemStatKeys : VentureItemStatKeys.values()) {
            Double itemStatKeyValue = getItemStat(ventureItemStatKeys);
            itemStatsMap.put(ventureItemStatKeys, itemStatKeyValue);
        }

        return itemStatsMap;
    }

    public ItemForger setUniqueItemDataKeyInt(UniqueVentureItemDataKey dataKey, int amount)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(dataKey.getKey(), PersistentDataType.INTEGER, amount);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public static void setUniqueItemDataKeyInt(ItemStack itemStack, UniqueVentureItemDataKey dataKey, int amount)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(dataKey.getKey(), PersistentDataType.INTEGER, amount);
        itemStack.setItemMeta(itemMeta);
    }

    public ItemForger setUniqueItemDataKeyDouble(UniqueVentureItemDataKey dataKey, double amount)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(dataKey.getKey(), PersistentDataType.DOUBLE, amount);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public static void setUniqueItemDataKeyDouble(ItemStack itemStack, UniqueVentureItemDataKey dataKey, double amount)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(dataKey.getKey(), PersistentDataType.DOUBLE, amount);
        itemStack.setItemMeta(itemMeta);
    }

    public Integer getUniqueItemDataKeyInt(UniqueVentureItemDataKey dataKey)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer().get(dataKey.getKey(), PersistentDataType.INTEGER) != null)
        {
            return itemMeta.getPersistentDataContainer().get(dataKey.getKey(), PersistentDataType.INTEGER);
        }
        return 0;
    }

    public static Integer getUniqueItemDataKeyInt(ItemStack itemStack, UniqueVentureItemDataKey dataKey)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer().get(dataKey.getKey(), PersistentDataType.INTEGER) != null)
        {
            return itemMeta.getPersistentDataContainer().get(dataKey.getKey(), PersistentDataType.INTEGER);
        }
        return 0;
    }

    public Double getUniqueItemDataKeyDouble(UniqueVentureItemDataKey dataKey)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer().get(dataKey.getKey(), PersistentDataType.DOUBLE) != null)
        {
            return itemMeta.getPersistentDataContainer().get(dataKey.getKey(), PersistentDataType.DOUBLE);
        }
        return 0.0;
    }

    public static Double getUniqueItemDataKeyDouble(ItemStack itemStack, UniqueVentureItemDataKey dataKey)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer().get(dataKey.getKey(), PersistentDataType.DOUBLE) != null)
        {
            return itemMeta.getPersistentDataContainer().get(dataKey.getKey(), PersistentDataType.DOUBLE);
        }
        return 0.0;
    }

    public static List<String> createStatLore(HashMap<VentureItemStatKeys, Double> itemStatsKeyMap) {

        List<String> statLore = new ArrayList<>();

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
            if (statValue != 0)
            {
                statLore.add(displayStringBuilder.toString());
            }
        }
        return statLore;
    }

    public static List<String> createAttributeLore(Multimap<Attribute, AttributeModifier> attributes) {

        List<String> attributeLore = new ArrayList<>();

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

    public static List<String> createStatLore(HashMap<VentureItemStatKeys, Double> itemStatsKeyMap, Multimap<Attribute, AttributeModifier> attributes) {

        List<String> statLore = new ArrayList<>();

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
            List<String> attributeLore = createAttributeLore(attributes);
            for (String l : attributeLore) {
                statLore.add(l);
            }
        }

        return statLore;
    }

    public ItemForger setFireResistant(boolean fireResistant) {
        if (fireResistant)
        {
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.getPersistentDataContainer().set(fireResistanceKey, PersistentDataType.BOOLEAN, true);
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    public boolean isFireResistant() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer().get(fireResistanceKey, PersistentDataType.BOOLEAN).equals(true)) {
            return true;
        }
        return false;
    }

    public ItemForger hideAllItemFlags() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        for (ItemFlag itemFlag : ItemFlag.values()) {
            itemMeta.addItemFlags(itemFlag);
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemForger setItemKey(String value)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(ITEM_MAP_KEY, PersistentDataType.STRING, value);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public String getItemKey()
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(ITEM_MAP_KEY, PersistentDataType.STRING);
    }

    public ItemStack toItemStack() {
        return itemStack;
    }
}
