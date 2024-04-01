package net.laserdiamond.ventureplugin.items.menuItems.stats.main;


import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.items.menuItems.util.MenuItem;
import net.laserdiamond.ventureplugin.items.menuItems.util.VentureMenuItem;
import net.laserdiamond.ventureplugin.stats.Components.DefenseStats;
import net.laserdiamond.ventureplugin.stats.Components.StatSymbols;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DefenseStatItem extends VentureMenuItem {

    public DefenseStatItem(VenturePlugin plugin) {
        super(plugin);
    }

    @Override
    public MenuItem menuItem() {
        return MenuItem.DEFENSE_STAT;
    }

    @Override
    public List<String> createLore(Player player)
    {
        StatPlayer statPlayer = new StatPlayer(player);
        DefenseStats defenseStats = statPlayer.getDefenseStats();
        double
                totalDefense = defenseStats.getDefense(),
                totalFireDefense = defenseStats.getFireDefense(),
                totalExplosionDefense = defenseStats.getExplosionDefense(),
                totalProjectileDefense = defenseStats.getProjectileDefense(),
                totalMagicDefense = defenseStats.getMagicDefense();

        double defenseFactor = plugin.getBaseStatsConfig().getInt("a");

        List<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Defense reduces the amount of damage you take.");
        lore.add(ChatColor.GRAY + "The formula for damage reduction is:");
        lore.add(ChatColor.GOLD + "x / (x + " + defenseFactor + ")");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Total Defense: " + ChatColor.GREEN + totalDefense + StatSymbols.DEFENSE);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Total Fire Defense: " + ChatColor.GOLD + totalFireDefense + StatSymbols.DEFENSE);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Total Explosion Defense: " + ChatColor.DARK_RED + totalExplosionDefense + StatSymbols.DEFENSE);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Total Projectile Defense: " + ChatColor.DARK_PURPLE + totalProjectileDefense + StatSymbols.DEFENSE);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Total Magic Defense: " + ChatColor.AQUA + totalMagicDefense + StatSymbols.DEFENSE);
        lore.add(" ");
        lore.add(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Click for more info");
        lore.add(" ");


        //
        // Defense reduces the amount of damage you take.
        // The formula for damage reduction is:
        // x / (x + 20)

        return lore;
    }
}
