package net.laserdiamond.ventureplugin.items.menuItems.stats.damage;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.menuItems.util.MenuItem;
import net.laserdiamond.ventureplugin.items.menuItems.util.VentureMenuItem;
import org.bukkit.entity.Player;

import java.util.List;

public class RangeDamageStatItem extends VentureMenuItem {
    public RangeDamageStatItem(VenturePlugin plugin) {
        super(plugin);
    }

    @Override
    public MenuItem menuItem() {
        return null;
    }

    @Override
    public List<String> createLore(Player player) {
        return null;
    }
}
