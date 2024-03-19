package net.laserdiamond.ventureplugin.items.armor.Vanilla.Components;

import com.google.common.collect.Multimap;
import net.laserdiamond.ventureplugin.items.util.armor.VentureArmorSet;
import net.laserdiamond.ventureplugin.util.File.GetVarFile;
import net.laserdiamond.ventureplugin.util.VentureItemStatKeys;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.armor.Vanilla.Config.VanillaArmorConfig;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.items.util.ItemNameBuilder;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorCMD;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorPieceTypes;
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

public class NetheriteArmorManager extends VentureArmorSet {

    private final VanillaArmorConfig armorConfig;

    public NetheriteArmorManager(VenturePlugin plugin) {
        armorConfig = plugin.getVanillaArmorConfig();
    }


    @Override
    public @NotNull String armorSetName() {
        return "Netherite";
    }

    @Override
    public GetVarFile config() {
        return null;
    }

    @Override
    public List<String> createLore(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {


        double fireArmorMult = armorConfig.getDouble("NetheriteFireArmorMultiplier");
        HashMap<VentureItemStatKeys, Double> stats = createItemStats(armorPieceTypes, stars);
        double armor = stats.get(VentureItemStatKeys.ARMOR_KEY);
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
    public List<String> createPlayerLore(@NotNull Player player, @NotNull ArmorPieceTypes armorPieceTypes, int stars) {
        return null;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> createAttributes(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {

        ItemStack itemStack = new ItemStack(Material.STONE_SWORD);
        ItemMeta itemMeta = itemStack.getItemMeta();

        double armor = armorConfig.getDouble(armorSetName() + armorPieceTypes.getName() + "Armor");
        double toughness = armorConfig.getDouble(armorSetName() + armorPieceTypes.getName() + "Toughness");

        AttributeModifier armorModifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", armor, AttributeModifier.Operation.ADD_NUMBER, armorPieceTypes.getEquipmentSlot());
        AttributeModifier toughnessModifier = new AttributeModifier(UUID.randomUUID(), "generic.toughness", toughness, AttributeModifier.Operation.ADD_NUMBER, armorPieceTypes.getEquipmentSlot());

        itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, toughnessModifier);

        return itemMeta.getAttributeModifiers();
    }

    @Override
    public HashMap<VentureItemStatKeys, Double> createItemStats(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {

        double armor = armorConfig.getDouble(armorSetName() + armorPieceTypes.getName() + "Armor");
        double toughness = armorConfig.getDouble(armorSetName() + armorPieceTypes.getName() + "Toughness");
        double fortitude = armorConfig.getDouble(armorSetName() + armorPieceTypes.getName() + "Fortitude");

        HashMap<VentureItemStatKeys, Double> stats = new HashMap<>();
        stats.put(VentureItemStatKeys.ARMOR_KEY, armor);
        stats.put(VentureItemStatKeys.TOUGHNESS_KEY, toughness);
        stats.put(VentureItemStatKeys.FORTITUDE_KEY, fortitude);

        return stats;
    }

    @Override
    public ItemForger createArmorPiece(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {

        String armorPieceString = armorPieceTypes.getName();

        ItemForger itemForger = new ItemForger(Material.NETHERITE_HELMET);

        try {
            String armorName = armorPieceString.substring(0,1).toUpperCase() + armorPieceString.substring(1);

            if (armorPieceTypes.equals(ArmorPieceTypes.HELMET)) {
                itemForger = new ItemForger(Material.NETHERITE_HELMET);
                itemForger.setCustomModelData(ArmorCMD.NETHERITE_ARMOR.getHelmet());

            } else if (armorPieceTypes.equals(ArmorPieceTypes.CHESTPLATE)) {
                itemForger = new ItemForger(Material.NETHERITE_CHESTPLATE);
                itemForger.setCustomModelData(ArmorCMD.NETHERITE_ARMOR.getChestplate());

            } else if (armorPieceTypes.equals(ArmorPieceTypes.LEGGINGS)) {
                itemForger = new ItemForger(Material.NETHERITE_LEGGINGS);
                itemForger.setCustomModelData(ArmorCMD.NETHERITE_ARMOR.getLeggings());

            } else if (armorPieceTypes.equals(ArmorPieceTypes.BOOTS)) {
                itemForger = new ItemForger(Material.NETHERITE_BOOTS);
                itemForger.setCustomModelData(ArmorCMD.NETHERITE_ARMOR.getBoots());

            } else {
                throw new IllegalArgumentException(ChatColor.RED + "Not armor piece type: " + armorPieceString);
            }

            itemForger.setName(ItemNameBuilder.name(armorSetName() + " " + armorName, stars));
            itemForger.setStars(stars);
            itemForger.setLore(createLore(armorPieceTypes, stars));
            itemForger.setAttributeModifiers(createAttributes(armorPieceTypes, stars), false);
            itemForger.setRarity(VentureItemRarity.Rarity.COMMON);
            itemForger.setFireResistant(true);

            itemForger.setItemStats(createItemStats(armorPieceTypes, stars));
        } catch (NullPointerException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something about this item is null: " + itemForger.getName());
        }
        return itemForger;
    }



}
