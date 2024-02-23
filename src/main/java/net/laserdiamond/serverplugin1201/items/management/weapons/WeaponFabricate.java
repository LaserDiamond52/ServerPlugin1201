package net.laserdiamond.serverplugin1201.items.management.weapons;

import com.google.common.collect.Multimap;
import net.laserdiamond.serverplugin1201.items.management.ItemForger;
import org.bukkit.attribute.AttributeModifier;

import javax.print.attribute.Attribute;
import java.util.List;

public interface WeaponFabricate {

    List<String> createLore(int stars);
    List<String> createPlayerLore(int stars);
    Multimap<Attribute, AttributeModifier> createAttributes(int stars);
    ItemForger createWeapon(int stars);
}
