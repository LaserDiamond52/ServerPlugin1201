package net.laserdiamond.serverplugin1201.items.management.misc;

import net.laserdiamond.serverplugin1201.items.management.ItemForger;
import org.bukkit.entity.Player;

import java.util.List;

public interface MiscFabricate {

    List<String> createLore();
    List<String> createPlayerLore(Player player);
    ItemForger createMiscItem();
}
