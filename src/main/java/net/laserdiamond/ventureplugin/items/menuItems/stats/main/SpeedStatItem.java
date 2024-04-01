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

public class SpeedStatItem extends VentureMenuItem {

    public SpeedStatItem(VenturePlugin plugin) {
        super(plugin);
    }

    @Override
    public MenuItem menuItem() {
        return MenuItem.SPEED_STAT;
    }

    @Override
    public List<String> createLore(Player player) {

        StatPlayer statPlayer = new StatPlayer(player);

        double
                baseSpeedAttribute = plugin.getBaseStatsConfig().getDouble("baseSpeed"),
                baseSpeedPoints = plugin.getBaseStatsConfig().getDouble("baseSpeedPoints"),
                totalSpeed = statPlayer.getStats().getSpeed(),
                armorSpeed = statPlayer.getArmorStats().getSpeed(),
                enchantSpeed = statPlayer.getEnchantStats().getSpeed(),
                armorTrimMaterialSpeed = statPlayer.getArmorTrimStats().armorTrimMaterialStats().getCopperSpeed();

        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add(ChatColor.GRAY + "Speed determines how fast you can walk/run");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "The speed attribute value is a universal constant amongst all");
        lore.add(ChatColor.GRAY + "players, and thus cannot be changed. This determines how much");
        lore.add(ChatColor.GRAY + "each speed point is worth");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Speed attribute value: " + ChatColor.WHITE + baseSpeedAttribute + StatSymbols.SPEED);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Speed points are used as a more readable way to measure your movement");
        lore.add(ChatColor.GRAY + "speed. Each speed point represents 1% of the speed attribute value");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Base speed points: " + ChatColor.WHITE + baseSpeedPoints + StatSymbols.SPEED);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Speed from armor: " + ChatColor.WHITE + armorSpeed + StatSymbols.SPEED);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Speed from enchants: " + ChatColor.WHITE + enchantSpeed + StatSymbols.SPEED);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Speed from armor trims: " + ChatColor.WHITE + armorTrimMaterialSpeed + StatSymbols.SPEED);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Total Speed: " + ChatColor.WHITE + totalSpeed + StatSymbols.SPEED);
        lore.add(" ");

        return lore;
    }
}
