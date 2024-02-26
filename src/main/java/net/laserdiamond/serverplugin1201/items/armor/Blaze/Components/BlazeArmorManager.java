package net.laserdiamond.serverplugin1201.items.armor.Blaze.Components;

import com.google.common.collect.Multimap;
import net.laserdiamond.serverplugin1201.items.management.ItemForger;
import net.laserdiamond.serverplugin1201.items.management.armor.ArmorFabricate;
import net.laserdiamond.serverplugin1201.items.management.armor.ArmorTypes;
import net.laserdiamond.serverplugin1201.management.ItemStatKeys;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class BlazeArmorManager implements ArmorFabricate {

    @Override
    public List<String> createLore(@NotNull ArmorTypes armorTypes, int stars) {
        return null;
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
        return null;
    }

    @Override
    public ItemForger createArmorPiece(@NotNull ArmorTypes armorTypes, int stars) {
        return null;
    }
}
