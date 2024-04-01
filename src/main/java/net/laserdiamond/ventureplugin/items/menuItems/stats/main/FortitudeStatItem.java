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

public class FortitudeStatItem extends VentureMenuItem {
    public FortitudeStatItem(VenturePlugin plugin) {
        super(plugin);
    }

    @Override
    public MenuItem menuItem() {
        return MenuItem.FORTITUDE;
    }

    @Override
    public List<String> createLore(Player player) {

        StatPlayer statPlayer = new StatPlayer(player);

        double
                totalFortitude = statPlayer.getDefenseStats().getFortitude(player),
                armorFortitude = statPlayer.getArmorStats().getFortitude();

        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add(ChatColor.GRAY + "Fortitude is knockback resistance, and reduces");
        lore.add(ChatColor.GRAY + "knockback you receive from attacks");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Fortitude from armor: " + ChatColor.DARK_GREEN + armorFortitude + StatSymbols.FORTITUDE);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Total fortitude: " + ChatColor.DARK_GREEN + totalFortitude + StatSymbols.FORTITUDE);
        lore.add(" ");

        return lore;
    }
}
