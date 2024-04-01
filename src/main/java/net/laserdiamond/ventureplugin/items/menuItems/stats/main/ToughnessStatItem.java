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


public class ToughnessStatItem extends VentureMenuItem {
    public ToughnessStatItem(VenturePlugin plugin) {
        super(plugin);
    }

    @Override
    public MenuItem menuItem() {
        return MenuItem.TOUGHNESS_STAT;
    }

    @Override
    public List<String> createLore(Player player)
    {
        StatPlayer statPlayer = new StatPlayer(player);
        double baseToughness = plugin.getBaseStatsConfig().getDouble("baseToughness");
        double toughness = statPlayer.getDefenseStats().getToughness();
        double armorToughness = statPlayer.getArmorStats().getToughness();
        double enchantToughness = statPlayer.getEnchantStats().getToughness();

        List<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Toughness reduces damage you take from");
        lore.add(ChatColor.GRAY + "true damage attacks. Toughness does not");
        lore.add(ChatColor.GRAY + "affect damage from normal attacks");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Base toughness: " + ChatColor.GREEN + baseToughness + StatSymbols.TOUGHNESS);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Toughness from armor: " + ChatColor.GREEN + armorToughness + StatSymbols.TOUGHNESS);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Toughness from enchants: " + ChatColor.GREEN + enchantToughness + StatSymbols.TOUGHNESS);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Total toughness: " + ChatColor.GREEN + toughness + StatSymbols.TOUGHNESS);
        lore.add(" ");
        return lore;
    }
}
