package net.laserdiamond.serverplugin1201.items.management;

import com.google.common.collect.Multimap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.laserdiamond.serverplugin1201.Management.ItemStatKeys;
import net.laserdiamond.serverplugin1201.items.attributes.AttributeLoreNameMap;
import org.apache.commons.codec.binary.Base64;
import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.Management.ItemPropertiesKeys;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.*;

public class ItemForger {

    private final ServerPlugin1201 plugin = ServerPlugin1201.getInstance();

    private final ItemStack itemStack;
    private static final NamespacedKey rarityKey = ItemPropertiesKeys.RARITY_KEY.getKey();
    private static final NamespacedKey starsKey = ItemPropertiesKeys.STARS_KEY.getKey();
    private final NamespacedKey fireResistanceKey = ItemPropertiesKeys.FIRE_RESISTANCE_KEY.getKey();
    private final NamespacedKey healthKey = ItemStatKeys.HEALTH_KEY.getKey();
    private final NamespacedKey armorKey = ItemStatKeys.ARMOR_KEY.getKey();
    private final NamespacedKey toughnessKey = ItemStatKeys.TOUGHNESS_KEY.getKey();
    private final NamespacedKey fortitudeKey = ItemStatKeys.FORTITUDE_KEY.getKey();
    private final NamespacedKey maxManaKey = ItemStatKeys.MAX_MANA_KEY.getKey();
    private final NamespacedKey meleeDamageKey = ItemStatKeys.MELEE_DAMAGE_KEY.getKey();
    private final NamespacedKey magicDamageKey = ItemStatKeys.MAGIC_DAMAGE_KEY.getKey();
    private final NamespacedKey rangeDamageKey = ItemStatKeys.RANGE_DAMAGE_KEY.getKey();
    private static final NamespacedKey itemTypeKey = ItemPropertiesKeys.ITEM_TYPE.getKey();

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

    public ItemForger clone() {
        return new ItemForger(itemStack);
    }

    public ItemMeta getItemMeta() {
        return itemStack.getItemMeta();
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

    public void setPlayerHeadSkin(@NotNull String url, int mostSigBits, int leastSigBits) {

        try {
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

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
        } catch (ClassCastException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not cast item to 'SkullMeta'!");
            exception.printStackTrace();
        }

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

    public Map<Enchantment, Integer> getEnchantments() {
        return itemStack.getItemMeta().getEnchants();
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

    public ItemForger setUnbreakable() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
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
        try {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
            leatherArmorMeta.setColor(color);
            itemStack.setItemMeta(leatherArmorMeta);

        } catch (ClassCastException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not cast item to 'LeatherArmorMeta'!");
            exception.printStackTrace();
        }
        return this;
    }

    public ItemForger LeatherArmorColor(int R, int G, int B) {
        try {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
            leatherArmorMeta.setColor(Color.fromRGB(R, G, B));
            itemStack.setItemMeta(leatherArmorMeta);

        } catch (ClassCastException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not cast item to 'LeatherArmorMeta'!");
            exception.printStackTrace();
        }
        return this;
    }

    public Color getLeatherArmorColor() {
        try {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
            return leatherArmorMeta.getColor();
        } catch (ClassCastException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not cast item to 'LeatherArmorMeta'!");
            exception.printStackTrace();
        }
        return null;
    }

    public ArmorTrim getArmorTrim() {
        try {
            ArmorMeta armorMeta = (ArmorMeta) itemStack.getItemMeta();
            return armorMeta.getTrim();
        } catch (ClassCastException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not case item to 'ArmorMeta'!");
            exception.printStackTrace();
        }
        return null;
    }

    public TrimMaterial getArmorTrimMaterial() {
        try {
            ArmorMeta armorMeta = (ArmorMeta) itemStack.getItemMeta();
            if (armorMeta.getTrim() != null) {
                return armorMeta.getTrim().getMaterial();
            }
        } catch (ClassCastException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not case item to 'ArmorMeta'!");
            exception.printStackTrace();
        }
        return null;
    }

    public TrimPattern getArmorTrimPattern() {
        try {
            ArmorMeta armorMeta = (ArmorMeta) itemStack.getItemMeta();
            if (armorMeta.getTrim() != null) {
                return armorMeta.getTrim().getPattern();
            }
        } catch (ClassCastException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not case item to 'ArmorMeta'!");
            exception.printStackTrace();
        }
        return null;
    }

    public ItemForger setArmorTrim(ArmorTrim armorTrim) {
        try {
            ArmorMeta armorMeta = (ArmorMeta) itemStack.getItemMeta();
            armorMeta.setTrim(armorTrim);
            itemStack.setItemMeta(armorMeta);
        } catch (ClassCastException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not case item to 'ArmorMeta'!");
            exception.printStackTrace();
        }
        return this;
    }


    @Deprecated
    public String getRarityOld() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        String rarityString = itemMeta.getPersistentDataContainer().get(rarityKey, PersistentDataType.STRING);

        for (PluginItemRarity.Rarity rarity : PluginItemRarity.Rarity.values()) {
            if (rarityString != null) {
                if (rarityString.equals(rarity.getRarity())) {
                    return rarityString;
                }
            }
        }

        return null;
    }

    public PluginItemRarity.Rarity getRarity() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        PluginItemRarity rarityInst = new PluginItemRarity();


        for (PluginItemRarity.Rarity rarity : PluginItemRarity.Rarity.values()) {
            if (rarity != null) {

                PersistentDataAdapterContext persistentDataAdapterContext = new PersistentDataAdapterContext() {
                    @Override
                    public @NotNull PersistentDataContainer newPersistentDataContainer() {
                        return itemMeta.getPersistentDataContainer();
                    }
                };

                String rarityFromStringPDC = itemMeta.getPersistentDataContainer().get(rarityKey, PluginItemRarity.STRING);
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
    public ItemForger setRarityOld(PluginItemRarity.Rarity itemRarity) {
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


    public ItemForger setRarity(PluginItemRarity.Rarity itemRarity) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = itemMeta.getDisplayName();
        itemMeta.setDisplayName(itemRarity.getRarityColor() + displayName);
        itemMeta.getPersistentDataContainer().set(rarityKey, PluginItemRarity.STRING, itemRarity.getRarity());
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public static PluginItemRarity.Rarity getItemRarity(ItemMeta itemMeta) {
        PluginItemRarity rarityInst = new PluginItemRarity();


        for (PluginItemRarity.Rarity rarity : PluginItemRarity.Rarity.values()) {
            if (rarity != null) {

                PersistentDataAdapterContext persistentDataAdapterContext = new PersistentDataAdapterContext() {
                    @Override
                    public @NotNull PersistentDataContainer newPersistentDataContainer() {
                        return itemMeta.getPersistentDataContainer();
                    }
                };

                String rarityFromStringPDC = itemMeta.getPersistentDataContainer().get(rarityKey, PluginItemRarity.STRING);
                if (rarityFromStringPDC != null) {
                    return rarityInst.fromPrimitive(rarityFromStringPDC, persistentDataAdapterContext);
                } else {
                    return null;
                }

            }
        }
        return null;
    }

    public static void setItemRarity(ItemMeta itemMeta, PluginItemRarity.Rarity itemRarity, String newDisplayName) {

        itemMeta.setDisplayName(newDisplayName);

        if (itemMeta.getPersistentDataContainer().get(rarityKey, PluginItemRarity.STRING) != null) {
            itemMeta.getPersistentDataContainer().remove(rarityKey);
        }
        itemMeta.getPersistentDataContainer().set(rarityKey, PluginItemRarity.STRING, itemRarity.getRarity());
    }

    public Integer getStars() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer().get(starsKey, PersistentDataType.INTEGER) != null) {
            return itemMeta.getPersistentDataContainer().get(starsKey, PersistentDataType.INTEGER);
        }
        return 0;
    }

    public static Integer getItemStars(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer().get(starsKey, PersistentDataType.INTEGER) != null) {
            return itemMeta.getPersistentDataContainer().get(starsKey, PersistentDataType.INTEGER);
        }
        return 0;
    }

    public ItemForger setStars(int starCount) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        starCount = Math.min(Math.max(0, starCount), maxStars);
        itemMeta.getPersistentDataContainer().set(starsKey, PersistentDataType.INTEGER, starCount);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public Double getHealth() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(healthKey, PersistentDataType.DOUBLE);
    }

    public ItemForger setHealth(double healthPoints) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(healthKey, PersistentDataType.DOUBLE, healthPoints);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public Double getArmor() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(armorKey, PersistentDataType.DOUBLE);
    }

    public ItemForger setArmor(double armorPoints) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(armorKey, PersistentDataType.DOUBLE, armorPoints);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public Double getToughness() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(toughnessKey, PersistentDataType.DOUBLE);
    }

    public ItemForger setToughness(double toughnessPoints) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(toughnessKey, PersistentDataType.DOUBLE, toughnessPoints);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public Double getFortitude() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(fortitudeKey, PersistentDataType.DOUBLE);
    }

    public ItemForger setFortitude(double fortitudePoints) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(fortitudeKey, PersistentDataType.DOUBLE, fortitudePoints);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public Double getMaxMana() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(maxManaKey, PersistentDataType.DOUBLE);
    }

    public ItemForger setMaxMana(double manaPoints) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(maxManaKey, PersistentDataType.DOUBLE, manaPoints);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public Double getMeleeDamage() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(meleeDamageKey, PersistentDataType.DOUBLE);
    }

    public ItemForger setMeleeDamage(double meleeDamagePoints) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(meleeDamageKey, PersistentDataType.DOUBLE, meleeDamagePoints);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public Double getMagicDamage() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(magicDamageKey, PersistentDataType.DOUBLE);
    }

    public ItemForger setMagicDamage(double magicDamagePoints) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(magicDamageKey, PersistentDataType.DOUBLE, magicDamagePoints);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public Double getRangeDamage() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(rangeDamageKey, PersistentDataType.DOUBLE);
    }

    public ItemForger setRangeDamage(double rangeDamagePoints) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(rangeDamageKey, PersistentDataType.DOUBLE, rangeDamagePoints);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public Double getItemStat(ItemStatKeys itemStatKey) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer().get(itemStatKey.getKey(), PersistentDataType.DOUBLE) != null) {
            return itemMeta.getPersistentDataContainer().get(itemStatKey.getKey(), PersistentDataType.DOUBLE);
        }
        return 0.0;
    }

    public ItemForger setItemStat(ItemStatKeys itemStatKey, double statPoints) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(itemStatKey.getKey(), PersistentDataType.DOUBLE, statPoints);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public static void setItemStat(ItemStack itemStack, ItemStatKeys key, double points) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(key.getKey(), PersistentDataType.DOUBLE, points);
        itemStack.setItemMeta(itemMeta);
    }

    public ItemForger setItemStats(HashMap<ItemStatKeys, Double> itemStatsKeyMap) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        for (ItemStatKeys itemStatKeys : itemStatsKeyMap.keySet()) {
            NamespacedKey itemStatKey = itemStatKeys.getKey();
            Double keyValue = itemStatsKeyMap.get(itemStatKeys);

            itemMeta.getPersistentDataContainer().set(itemStatKey, PersistentDataType.DOUBLE, keyValue);
        }


        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public static void setItemStats(ItemStack itemStack, HashMap<ItemStatKeys, Double> itemStatMap) {

        ItemMeta itemMeta = itemStack.getItemMeta();
        for (ItemStatKeys itemStatKeys : itemStatMap.keySet()) {
            NamespacedKey itemStatKey = itemStatKeys.getKey();
            Double keyValue = itemStatMap.get(itemStatKeys);
            if (itemMeta.getPersistentDataContainer().get(itemStatKey, PersistentDataType.DOUBLE) != null) {
                itemMeta.getPersistentDataContainer().remove(itemStatKey);
            }
            itemMeta.getPersistentDataContainer().set(itemStatKey, PersistentDataType.DOUBLE, keyValue);
        }



        itemStack.setItemMeta(itemMeta);
    }

    public HashMap<ItemStatKeys, Double> getItemStats() {

        HashMap<ItemStatKeys, Double> itemStatsMap = new HashMap<>();
        for (ItemStatKeys itemStatKeys : ItemStatKeys.values()) {
            Double itemStatKeyValue = getItemStat(itemStatKeys);
            itemStatsMap.put(itemStatKeys, itemStatKeyValue);
        }

        return itemStatsMap;
    }

    public static List<String> createStatLore(HashMap<ItemStatKeys, Double> itemStatsKeyMap) {

        List<String> statLore = new ArrayList<>();

        DecimalFormat doubleDecimalFormat = new DecimalFormat("0.00");

        // Create Lore for item
        for (ItemStatKeys itemStatKeys : ItemStatKeys.values()) {
            String displayName = itemStatKeys.getDisplayName();
            double statValue = 0;
            if (itemStatsKeyMap.get(itemStatKeys) != null) {
                statValue = itemStatsKeyMap.get(itemStatKeys);
            }

            String displayString = displayName + itemStatKeys.getDisplayColor() + doubleDecimalFormat.format(statValue);
            StringBuilder displayStringBuilder = new StringBuilder(displayString);

            if (ItemStatKeys.isPercentageStat(itemStatKeys)) {
                displayStringBuilder.append("%");

            }
            if (statValue > 0) {
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

    public static List<String> createStatLore(HashMap<ItemStatKeys, Double> itemStatsKeyMap, Multimap<Attribute, AttributeModifier> attributes) {

        List<String> statLore = new ArrayList<>();

        DecimalFormat doubleDecimalFormat = new DecimalFormat("0.00");

        // Create Lore for item
        for (ItemStatKeys itemStatKeys : ItemStatKeys.values()) {
            String displayName = itemStatKeys.getDisplayName();
            double statValue = 0;
            if (itemStatsKeyMap.get(itemStatKeys) != null) {
                statValue = itemStatsKeyMap.get(itemStatKeys);
            }

            String displayString = displayName + itemStatKeys.getDisplayColor() + doubleDecimalFormat.format(statValue);
            StringBuilder displayStringBuilder = new StringBuilder(displayString);

            if (ItemStatKeys.isPercentageStat(itemStatKeys)) {
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

    public String getItemTypeKey() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(itemTypeKey, PersistentDataType.STRING);
    }

    public static String getItemTypeKey(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(itemTypeKey, PersistentDataType.STRING);
    }

    public ItemForger setItemTypeKey(String input) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(itemTypeKey, PersistentDataType.STRING, input);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemForger setFireResistant() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(fireResistanceKey, PersistentDataType.BOOLEAN, true);
        itemStack.setItemMeta(itemMeta);
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

    public ItemStack toItemStack() {
        return itemStack;
    }
}
