package net.laserdiamond.serverplugin1201.items.armor.Vanilla.Components;

import com.google.common.collect.Multimap;
import net.laserdiamond.serverplugin1201.management.ItemStatKeys;
import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.items.armor.Vanilla.Config.VanillaArmorConfig;
import net.laserdiamond.serverplugin1201.items.management.ItemForger;
import net.laserdiamond.serverplugin1201.items.management.ItemNameBuilder;
import net.laserdiamond.serverplugin1201.items.management.PluginItemRarity;
import net.laserdiamond.serverplugin1201.items.management.armor.ArmorCMD;
import net.laserdiamond.serverplugin1201.items.management.armor.ArmorFabricate;
import net.laserdiamond.serverplugin1201.items.management.armor.ArmorTypes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NetheriteArmorManager implements ArmorFabricate {

    private final ServerPlugin1201 plugin;
    private final VanillaArmorConfig armorConfig;

    public NetheriteArmorManager(ServerPlugin1201 plugin) {
        this.plugin = plugin;
        armorConfig = plugin.getVanillaArmorConfig();
    }


    @Override
    public List<String> createLore(@NotNull ArmorTypes armorTypes, int stars) {


        double fireArmorMult = armorConfig.getDouble("NetheriteFireArmorMultiplier");
        HashMap<ItemStatKeys, Double> stats = createItemStats(armorTypes, stars);
        double armor = stats.get(ItemStatKeys.ARMOR_KEY);
        double fireArmor = armor * fireArmorMult;
        List<String> lore = new ArrayList<>();

        lore.add(" ");
        List<String> statLore = ItemForger.createStatLore(stats);
        for (String l : statLore) {
            lore.add(l);
        }
        lore.add(" ");
        lore.add(ChatColor.GOLD + "Piece Bonus: Fire Protection");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Grants +" + ChatColor.GOLD + fireArmor + ChatColor.GRAY + " fire defense");
        lore.add(" ");

        return lore;
    }

    @Override
    public List<String> createPlayerLore(@NotNull Player player, @NotNull ArmorTypes armorTypes, int stars) {
        return null;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> createAttributes(@NotNull ArmorTypes armorTypes, int stars) {

        ItemStack itemStack = new ItemStack(Material.STONE_SWORD);
        ItemMeta itemMeta = itemStack.getItemMeta();

        double armor = armorConfig.getDouble("Netherite" + armorTypes.getName() + "Armor");
        double toughness = armorConfig.getDouble("Netherite" + armorTypes.getName() + "Toughness");

        AttributeModifier armorModifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", armor, AttributeModifier.Operation.ADD_NUMBER, armorTypes.getEquipmentSlot());
        AttributeModifier toughnessModifier = new AttributeModifier(UUID.randomUUID(), "generic.toughness", toughness, AttributeModifier.Operation.ADD_NUMBER, armorTypes.getEquipmentSlot());

        itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, toughnessModifier);

        return itemMeta.getAttributeModifiers();
    }

    @Override
    public HashMap<ItemStatKeys, Double> createItemStats(@NotNull ArmorTypes armorTypes, int stars) {

        double armor = armorConfig.getDouble("Netherite" + armorTypes.getName() + "Armor");
        double toughness = armorConfig.getDouble("Netherite" + armorTypes.getName() + "Toughness");
        double fortitude = armorConfig.getDouble("Netherite" + armorTypes.getName() + "Fortitude");

        HashMap<ItemStatKeys, Double> stats = new HashMap<>();
        stats.put(ItemStatKeys.ARMOR_KEY, armor);
        stats.put(ItemStatKeys.TOUGHNESS_KEY, toughness);
        stats.put(ItemStatKeys.FORTITUDE_KEY, fortitude);

        return stats;
    }

    @Override
    public ItemForger createArmorPiece(@NotNull ArmorTypes armorTypes, int stars) {

        String armorPieceString = armorTypes.getName();

        ItemForger itemForger = new ItemForger(Material.NETHERITE_HELMET);

        try {
            String armorName = armorPieceString.substring(0,1).toUpperCase() + armorPieceString.substring(1);

            if (armorTypes.equals(ArmorTypes.HELMET)) {
                itemForger = new ItemForger(Material.NETHERITE_HELMET);
                itemForger.setCustomModelData(ArmorCMD.NETHERITE_ARMOR.getHelmet());

            } else if (armorTypes.equals(ArmorTypes.CHESTPLATE)) {
                itemForger = new ItemForger(Material.NETHERITE_CHESTPLATE);
                itemForger.setCustomModelData(ArmorCMD.NETHERITE_ARMOR.getChestplate());

            } else if (armorTypes.equals(ArmorTypes.LEGGINGS)) {
                itemForger = new ItemForger(Material.NETHERITE_LEGGINGS);
                itemForger.setCustomModelData(ArmorCMD.NETHERITE_ARMOR.getLeggings());

            } else if (armorTypes.equals(ArmorTypes.BOOTS)) {
                itemForger = new ItemForger(Material.NETHERITE_BOOTS);
                itemForger.setCustomModelData(ArmorCMD.NETHERITE_ARMOR.getBoots());

            } else {
                throw new IllegalArgumentException(ChatColor.RED + "Not armor piece type: " + armorPieceString);
            }

            itemForger.setName(ItemNameBuilder.name("Netherite " + armorName, stars));
            itemForger.setStars(stars);
            itemForger.setLore(createLore(armorTypes, stars));
            itemForger.setAttributeModifiers(createAttributes(armorTypes, stars), false);
            itemForger.setRarity(PluginItemRarity.Rarity.COMMON);
            itemForger.setFireResistant();

            itemForger.setItemStats(createItemStats(armorTypes, stars));
        } catch (NullPointerException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something about this item is null: " + itemForger.getName());
        }
        return itemForger;
    }
}
