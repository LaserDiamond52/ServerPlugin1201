package net.laserdiamond.ventureplugin.items.menuItems.stats.main;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.items.menuItems.util.MenuItem;
import net.laserdiamond.ventureplugin.items.menuItems.util.VentureMenuItem;
import net.laserdiamond.ventureplugin.stats.Components.StatSymbols;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HealthStatItem extends VentureMenuItem {
    public HealthStatItem(VenturePlugin plugin) {
        super(plugin);
    }

    @Override
    public MenuItem menuItem() {
        return MenuItem.HEALTH_STAT;
    }

    @Override
    public List<String> createLore(Player player) {
        StatPlayer statPlayer = new StatPlayer(player);

        double baseHealth = plugin.getBaseStatsConfig().getDouble("baseHealth");
        double armorHealth = statPlayer.getArmorStats().getHealth();
        double enchantHealth = statPlayer.getEnchantStats().getHealth();
        double trimHealth = statPlayer.getArmorTrimStats().armorTrimMaterialStats().getIronHealthBoost();
        double totalHealth = statPlayer.getStats().getHealth();

        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add(ChatColor.GRAY + "Health determines how much damage you can take before you die");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Base Health:" + ChatColor.RED + baseHealth + StatSymbols.HEALTH);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Health from armor: " + ChatColor.RED + armorHealth + StatSymbols.HEALTH);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Health from enchants: " + ChatColor.RED + enchantHealth + StatSymbols.HEALTH);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Health from armor trims: " + ChatColor.RED + trimHealth + StatSymbols.HEALTH);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Total Health: " + ChatColor.RED + totalHealth + StatSymbols.HEALTH);
        lore.add(" ");

        return lore;
    }
}
