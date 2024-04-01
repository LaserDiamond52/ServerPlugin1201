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

public class ManaStatItem extends VentureMenuItem {

    public ManaStatItem(VenturePlugin plugin) {
        super(plugin);
    }

    @Override
    public MenuItem menuItem() {
        return MenuItem.MAX_MANA_STAT;
    }

    @Override
    public List<String> createLore(Player player) {
        StatPlayer statPlayer = new StatPlayer(player);

        double
                baseMana = plugin.getBaseStatsConfig().getDouble("maxMana"),
                totalMana = statPlayer.getStats().getMaxMana(),
                armorMana = statPlayer.getArmorStats().getMana(),
                enchantMana = statPlayer.getEnchantStats().getMana(),
                armorTrimMaterialMana = statPlayer.getArmorTrimStats().armorTrimMaterialStats().getDiamondBonusMana();

        double manaRegen = totalMana/50;

        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add(ChatColor.GRAY + "Mana is used to cast abilities and regenerates");
        lore.add(ChatColor.GRAY + "at a base rate of 2% per second");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Mana regeneration rate: " + ChatColor.BLUE + manaRegen + StatSymbols.MANA + ChatColor.GRAY + " per second");
        lore.add(" ");
        // TODO: Set up mana regeneration stat
        lore.add(ChatColor.GRAY + "Base mana: " + ChatColor.BLUE + baseMana + StatSymbols.MANA);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Mana from armor: " + ChatColor.BLUE + armorMana + StatSymbols.MANA);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Mana from enchants: " + ChatColor.BLUE + enchantMana + StatSymbols.MANA);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Mana from armor trim materials: " + ChatColor.BLUE + armorTrimMaterialMana + StatSymbols.MANA);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Total mana: " + ChatColor.BLUE + totalMana + StatSymbols.MANA);
        lore.add(" ");

        // Mana is used to cast abilities and regenerates
        // at a base rate of 2% per second

        return lore;
    }
}
