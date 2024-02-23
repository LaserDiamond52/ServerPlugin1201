package net.laserdiamond.serverplugin1201.items.armor.Vanilla.Components;

import com.google.common.collect.Multimap;
import net.laserdiamond.serverplugin1201.Management.ItemStatKeys;
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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DiamondArmorManager implements ArmorFabricate {

    private final ServerPlugin1201 plugin;
    private final VanillaArmorConfig armorConfig;

    public DiamondArmorManager(ServerPlugin1201 plugin) {
        this.plugin = plugin;
        armorConfig = plugin.getVanillaArmorConfig();
    }

    @Override
    public List<String> createLore(@NotNull ArmorTypes armorTypes, int stars) {

        List<String> lore = new ArrayList<>();
        lore.add(" ");

        List<String> statLore = ItemForger.createStatLore(createItemStats(armorTypes, stars));
        for (String l : statLore) {
            lore.add(l);
        }
        lore.add(" ");
        return lore;
    }

    @Override
    public List<String> createPlayerLore(@NotNull Player player, @NotNull ArmorTypes armorTypes, int stars) {
        return null;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> createAttributes(@NotNull ArmorTypes armorTypes, int stars) {
        return null;
    }

    @Override
    public HashMap<ItemStatKeys, Double> createItemStats(@NotNull ArmorTypes armorTypes, int stars) {

        double armor = armorConfig.getDouble("Diamond" + armorTypes.getName() + "Armor");
        double toughness = armorConfig.getDouble("Diamond" + armorTypes.getName() + "Toughness");

        HashMap<ItemStatKeys, Double> itemStats = new HashMap<>();
        itemStats.put(ItemStatKeys.ARMOR_KEY, armor);
        itemStats.put(ItemStatKeys.TOUGHNESS_KEY, toughness);
        return itemStats;
    }

    @Override
    public ItemForger createArmorPiece(@NotNull ArmorTypes armorTypes, int stars) {

        String armorPieceString = armorTypes.getName();

        ItemForger itemForger = null;

        try {
            if (armorTypes.equals(ArmorTypes.HELMET)) {
                itemForger = new ItemForger(Material.DIAMOND_HELMET);
                itemForger.setCustomModelData(ArmorCMD.DIAMOND_ARMOR.getHelmet());

            } else if (armorTypes.equals(ArmorTypes.CHESTPLATE)) {
                itemForger = new ItemForger(Material.DIAMOND_CHESTPLATE);
                itemForger.setCustomModelData(ArmorCMD.DIAMOND_ARMOR.getChestplate());

            } else if (armorTypes.equals(ArmorTypes.LEGGINGS)) {
                itemForger = new ItemForger(Material.DIAMOND_LEGGINGS);
                itemForger.setCustomModelData(ArmorCMD.DIAMOND_ARMOR.getLeggings());

            } else if (armorTypes.equals(ArmorTypes.BOOTS)) {
                itemForger = new ItemForger(Material.DIAMOND_BOOTS);
                itemForger.setCustomModelData(ArmorCMD.DIAMOND_ARMOR.getBoots());
            } else {
                throw new IllegalArgumentException(ChatColor.RED + "Not armor piece: " + armorPieceString);
            }

            itemForger.setName(ItemNameBuilder.name("Diamond " + armorPieceString, stars));
            itemForger.setStars(stars);
            itemForger.setLore(createLore(armorTypes, stars));
            itemForger.setRarity(PluginItemRarity.Rarity.COMMON);

            itemForger.setItemStats(createItemStats(armorTypes, stars));
        } catch (NullPointerException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something about this item is null: " + itemForger.getName());
        }



        return itemForger;
    }


}
