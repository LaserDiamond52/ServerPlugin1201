package net.laserdiamond.serverplugin1201.items.management.armor;

import com.google.common.collect.Multimap;
import net.laserdiamond.serverplugin1201.items.management.ItemForger;
import net.laserdiamond.serverplugin1201.management.ItemStatKeys;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public interface ArmorFabricate {

    List<String> createLore(@NotNull ArmorTypes armorTypes, int stars);
    List<String> createPlayerLore(@NotNull Player player, @NotNull ArmorTypes armorTypes, int stars);
    Multimap<Attribute, AttributeModifier> createAttributes(@NotNull ArmorTypes armorTypes, int stars);
    HashMap<ItemStatKeys, Double> createItemStats(@NotNull ArmorTypes armorTypes, int stars);
    ItemForger createArmorPiece(@NotNull ArmorTypes armorTypes, int stars);

}
