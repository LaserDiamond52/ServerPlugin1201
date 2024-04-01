package net.laserdiamond.ventureplugin.items.menuItems.stats.main;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.menuItems.util.MenuItem;
import net.laserdiamond.ventureplugin.items.menuItems.util.VentureMenuItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DamageStatItem extends VentureMenuItem {

    public DamageStatItem(VenturePlugin plugin) {
        super(plugin);
    }

    @Override
    public MenuItem menuItem() {
        return MenuItem.DAMAGE_STAT;
    }

    @Override
    public List<String> createLore(Player player) {

        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add(ChatColor.GRAY + "Every attack you inflict has its damage modified");
        lore.add(ChatColor.GRAY + "by one of the following damage types:");
        lore.add(ChatColor.GRAY + "-" + ChatColor.RED + " Melee");
        lore.add(ChatColor.GRAY + "-" + ChatColor.AQUA + " Magic");
        lore.add(ChatColor.GRAY + "-" + ChatColor.DARK_PURPLE + " Range");
        lore.add(" ");
        lore.add(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Click for more info");
        lore.add(" ");

        // Every attack you inflict has its damage modified
        // by one of the following damage types:
        // - Melee
        // - Magic
        // - Range
        return lore;
    }
}
