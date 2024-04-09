package net.laserdiamond.ventureplugin.items.menuItems.tuning;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.items.menuItems.util.MenuItem;
import net.laserdiamond.ventureplugin.items.menuItems.util.VentureMenuItem;
import net.laserdiamond.ventureplugin.util.StatSymbols;
import net.laserdiamond.ventureplugin.util.Config.PlayerConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class TuningMenuItems {

    private static final VenturePlugin PLUGIN = VenturePlugin.getInstance();
    private static final PlayerConfig BASE_STATS_CONFIG = PLUGIN.getBaseStatsConfig();

    public static final List<String> manipulateLore = new ArrayList<>();
    static
    {
        manipulateLore.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "Left-click to add a point");
        manipulateLore.add(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Right-click to remove a point");
        manipulateLore.add(" ");
    }

    public static final VentureMenuItem POINTS = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.TUNING_POINTS;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);
            int tuningPoints = statPlayer.getTuningPointStats().getTuningPoints();

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(ChatColor.GRAY + "You have " + ChatColor.YELLOW + tuningPoints + ChatColor.GRAY + " tuning points to spend");
            lore.add(" ");
            return lore;
        }
    };

    public static final VentureMenuItem HEALTH = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.TUNING_HEALTH;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);

            int healthPoints = statPlayer.getTuningPointStats().getHealthPoints();
            double healthBonus = statPlayer.getTuningStats().getHealth();
            double healthValue = BASE_STATS_CONFIG.getDouble("healthValue");

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(ChatColor.GRAY + "Each point grants +" + ChatColor.RED + healthValue + StatSymbols.HEALTH.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Current level: " + ChatColor.YELLOW + healthPoints);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Current bonus: +" + ChatColor.RED + healthBonus + StatSymbols.HEALTH.getSymbol());
            lore.add(" ");
            lore.addAll(manipulateLore);
            return lore;
        }
    };

    public static final VentureMenuItem DEFENSE = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.TUNING_DEFENSE;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);

            int defensePoints = statPlayer.getTuningPointStats().getDefensePoints();
            double defenseBonus = statPlayer.getTuningStats().getDefense();
            double defenseValue = BASE_STATS_CONFIG.getDouble("defenseValue");

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(ChatColor.GRAY + "Each point grants +" + ChatColor.GREEN + defenseValue + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Current level: " + ChatColor.YELLOW + defensePoints);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Current bonus: +" + ChatColor.GREEN + defenseBonus + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.addAll(manipulateLore);
            return lore;
        }
    };

    public static final VentureMenuItem SPEED = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.TUNING_SPEED;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);

            int speedPoints = statPlayer.getTuningPointStats().getSpeedPoints();
            double speedBonus = statPlayer.getTuningStats().getSpeed();
            double speedValue = BASE_STATS_CONFIG.getDouble("speedValue");

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(ChatColor.GRAY + "Each point grants +" + ChatColor.WHITE + speedValue + StatSymbols.SPEED.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Current level: " + ChatColor.YELLOW + speedPoints);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Current bonus: +" + ChatColor.WHITE + speedBonus + StatSymbols.SPEED.getSymbol());
            lore.add(" ");
            lore.addAll(manipulateLore);
            return lore;
        }
    };

    public static final VentureMenuItem MANA = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.TUNING_MANA;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);

            int manaPoints = statPlayer.getTuningPointStats().getManaPoints();
            double manaBonus = statPlayer.getTuningStats().getMana();
            double manaValue = BASE_STATS_CONFIG.getDouble("manaValue");

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(ChatColor.GRAY + "Each point grants +" + ChatColor.BLUE + manaValue + StatSymbols.MANA.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Current level: " + ChatColor.YELLOW + manaPoints);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Current bonus: +" + ChatColor.BLUE + manaBonus + StatSymbols.MANA.getSymbol());
            lore.add(" ");
            lore.addAll(manipulateLore);
            return lore;
        }
    };

    public static final VentureMenuItem MELEE = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.TUNING_MELEE;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);

            int meleePoints = statPlayer.getTuningPointStats().getMeleePoints();
            double meleeBonus = statPlayer.getTuningStats().getMelee();
            double meleeValue = BASE_STATS_CONFIG.getDouble("meleeValue");

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(ChatColor.GRAY + "Each point grants +" + ChatColor.RED + meleeValue + StatSymbols.MELEE_DAMAGE.getSymbol() + ChatColor.GRAY + " base damage");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Current level: " + ChatColor.YELLOW + meleePoints);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Current bonus: +" + ChatColor.RED + meleeBonus + StatSymbols.MELEE_DAMAGE.getSymbol() + ChatColor.GRAY + " base damage");
            lore.add(" ");
            lore.addAll(manipulateLore);
            return lore;
        }
    };

    public static final VentureMenuItem MAGIC = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.TUNING_MAGIC;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);

            int magicPoints = statPlayer.getTuningPointStats().getMagicPoints();
            double magicBonus = statPlayer.getTuningStats().getMagic();
            double magicValue = BASE_STATS_CONFIG.getDouble("magicValue");

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(ChatColor.GRAY + "Each point grants +" + ChatColor.AQUA + magicValue + StatSymbols.MAGIC_DAMAGE.getSymbol() + ChatColor.GRAY + " base damage");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Current level: " + ChatColor.YELLOW + magicPoints);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Current bonus: +" + ChatColor.AQUA + magicBonus + StatSymbols.MAGIC_DAMAGE.getSymbol() + ChatColor.GRAY + " base damage");
            lore.add(" ");
            lore.addAll(manipulateLore);
            return lore;
        }
    };

    public static final VentureMenuItem RANGE = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.TUNING_RANGE;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);

            int rangePoints = statPlayer.getTuningPointStats().getRangePoints();
            double rangeBonus = statPlayer.getTuningStats().getRange();
            double rangeValue = BASE_STATS_CONFIG.getDouble("rangeValue");

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(ChatColor.GRAY + "Each point grants +" + ChatColor.DARK_PURPLE + rangeValue + StatSymbols.RANGE_DAMAGE.getSymbol() + ChatColor.GRAY + " base damage");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Current level: " + ChatColor.YELLOW + rangePoints);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Current bonus: +" + ChatColor.DARK_PURPLE + rangeBonus + StatSymbols.RANGE_DAMAGE.getSymbol() + ChatColor.GRAY + " base damage");
            lore.add(" ");
            lore.addAll(manipulateLore);
            return lore;
        }
    };

    public enum TuningItemSlots
    {
        POINTS (TuningMenuItems.POINTS, 40),
        HEALTH (TuningMenuItems.HEALTH, 19),
        DEFENSE (TuningMenuItems.DEFENSE, 20),
        SPEED (TuningMenuItems.SPEED, 21),
        MANA (TuningMenuItems.MANA, 22),
        MELEE (TuningMenuItems.MELEE, 23),
        MAGIC (TuningMenuItems.MAGIC, 24),
        RANGE (TuningMenuItems.RANGE, 25);

        private final VentureMenuItem ventureMenuItem;
        private final int inventorySlot;

        TuningItemSlots(VentureMenuItem ventureMenuItem, int inventorySlot)
        {
            this.ventureMenuItem = ventureMenuItem;
            this.inventorySlot = inventorySlot;
        }

        public VentureMenuItem getVentureMenuItem() {
            return ventureMenuItem;
        }

        public int getInventorySlot() {
            return inventorySlot;
        }


    }
}
