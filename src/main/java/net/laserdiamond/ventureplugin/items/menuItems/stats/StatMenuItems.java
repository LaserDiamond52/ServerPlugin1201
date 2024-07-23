package net.laserdiamond.ventureplugin.items.menuItems.stats;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.items.menuItems.util.MenuItem;
import net.laserdiamond.ventureplugin.items.menuItems.util.VentureMenuItem;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.stats.Components.ArmorStats;
import net.laserdiamond.ventureplugin.stats.Components.DefenseStats;
import net.laserdiamond.ventureplugin.stats.Components.LootStats;
import net.laserdiamond.ventureplugin.util.StatSymbols;
import net.laserdiamond.ventureplugin.util.VentureMath;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public final class StatMenuItems {

    private static final VenturePlugin PLUGIN = VenturePlugin.getInstance();
    private static final double DEFENSE_FACTOR = PLUGIN.getBaseStatsConfig().getInt("a");
    private static final String ARMOR = ChatColor.GREEN + "armor" + ChatColor.GRAY;
    private static final String ARMOR_TRIMS = ChatColor.GREEN + "armor trims" + ChatColor.GRAY;
    private static final String ENCHANTS = ChatColor.LIGHT_PURPLE + "enchants" + ChatColor.GRAY;
    private static final String TUNING = ChatColor.AQUA + "tuning" + ChatColor.GRAY;
    private static final String MORE_INFO = ChatColor.YELLOW + "" + ChatColor.ITALIC + "Click for more info";

    public static double damageReduction(double defense)
    {
        return 100 * (defense / (defense + DEFENSE_FACTOR));
    }

    public static final VentureMenuItem HEALTH_STAT_ITEM = new VentureMenuItem(PLUGIN)
    {
        @Override
        public MenuItem menuItem() {
            return MenuItem.HEALTH_STAT;
        }

        @Override
        public List<String> createLore(Player player)
        {
            StatPlayer statPlayer = new StatPlayer(player);

            double baseHealth = plugin.getBaseStatsConfig().getDouble("baseHealth"),
                    tuningHealth = statPlayer.getTuningStats().getHealth(),
                    armorHealth = statPlayer.getArmorStats().getHealth(),
                    enchantHealth = statPlayer.getEnchantStats().getHealth(),
                    trimHealth = statPlayer.getArmorTrimStats().armorTrimMaterialStats().getIronHealthBoost(),
                    totalHealth = statPlayer.getStats().getHealth();

            String healthString = ChatColor.RED + "Health " + StatSymbols.HEALTH.getSymbol();
            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(healthString + ChatColor.GRAY + " determines how much damage");
            lore.add(ChatColor.GRAY + "you can take before you die");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base " + healthString + ChatColor.GRAY + ": " + ChatColor.YELLOW + baseHealth);
            lore.add(" ");
            lore.add(healthString + ChatColor.GRAY + " from " + TUNING + ": " + ChatColor.YELLOW + tuningHealth);
            lore.add(" ");
            lore.add(healthString + ChatColor.GRAY + " from " + ARMOR + ": " + ChatColor.YELLOW + armorHealth);
            lore.add(" ");
            lore.add(healthString + ChatColor.GRAY + " from " + ENCHANTS + ": " + ChatColor.YELLOW + enchantHealth);
            lore.add(" ");
            lore.add(healthString + ChatColor.GRAY + " from " + ARMOR_TRIMS + ": " + ChatColor.YELLOW + trimHealth);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total " + healthString + ChatColor.GRAY + ": " + ChatColor.YELLOW + totalHealth);
            lore.add(" ");

            return lore;
        }
    };

    public static final VentureMenuItem DEFENSE_STAT_ITEM = new VentureMenuItem(PLUGIN)
    {
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


            String defenseString = ChatColor.GREEN + "Defense " + StatSymbols.DEFENSE.getSymbol();
            String fireDefenseString = ChatColor.GOLD + "Fire Defense " + StatSymbols.DEFENSE.getSymbol();
            String explosionDefenseString = ChatColor.DARK_RED + "Explosion Defense " + StatSymbols.DEFENSE.getSymbol();
            String projectileDefenseString = ChatColor.DARK_PURPLE + "Projectile Defense " + StatSymbols.DEFENSE.getSymbol();
            String magicDefenseString = ChatColor.AQUA + "Magic Defense " + StatSymbols.DEFENSE.getSymbol();

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add(defenseString + ChatColor.GRAY + " reduces the amount of damage you take.");
            lore.add(ChatColor.GRAY + "The formula for damage reduction is:");
            lore.add(ChatColor.GOLD + "x / (x + " + DEFENSE_FACTOR + ")");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total " + defenseString + ChatColor.GRAY + ": " + ChatColor.YELLOW + totalDefense);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total " + fireDefenseString + ChatColor.GRAY + ": " + ChatColor.YELLOW + totalFireDefense);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total " + explosionDefenseString + ChatColor.GRAY + ": " + ChatColor.YELLOW + totalExplosionDefense);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total " + projectileDefenseString + ChatColor.GRAY + ": " + ChatColor.YELLOW + totalProjectileDefense);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total " + magicDefenseString + ChatColor.GRAY + ": " + ChatColor.YELLOW + totalMagicDefense);
            lore.add(" ");
            lore.add(MORE_INFO);
            lore.add(" ");


            //
            // Defense reduces the amount of damage you take.
            // The formula for damage reduction is:
            // x / (x + 20)

            return lore;
        }
    };

    public static final VentureMenuItem TOUGHNESS_STAT_ITEM = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.TOUGHNESS_STAT;
        }

        @Override
        public List<String> createLore(Player player) {
            StatPlayer statPlayer = new StatPlayer(player);
            double baseToughness = plugin.getBaseStatsConfig().getDouble("baseToughness");
            double toughness = statPlayer.getDefenseStats().getToughness();
            double armorToughness = statPlayer.getArmorStats().getToughness();
            double enchantToughness = statPlayer.getEnchantStats().getToughness();

            String toughnessString = ChatColor.GREEN + "Toughness " + StatSymbols.TOUGHNESS.getSymbol();
            String defenseString = ChatColor.GREEN + "Defense " + StatSymbols.DEFENSE.getSymbol();

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add(toughnessString + ChatColor.GRAY + " reduces damage you take from");
            lore.add(ChatColor.GRAY + "true damage attacks. " + toughnessString + ChatColor.GRAY + " does not");
            lore.add(ChatColor.GRAY + "affect damage from normal attacks");
            lore.add(" ");
            lore.add(toughnessString + ChatColor.GRAY + " is calculated at the same rate");
            lore.add(ChatColor.GRAY + "as " + defenseString + ChatColor.GRAY + ":");
            lore.add(ChatColor.GOLD + "x / (x + " + DEFENSE_FACTOR + ")");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base " + toughnessString + ChatColor.GRAY + ": " + ChatColor.YELLOW + baseToughness);
            lore.add(" ");
            lore.add(toughnessString + ChatColor.GRAY + " from " + ARMOR + ": " + ChatColor.YELLOW + armorToughness);
            lore.add(" ");
            lore.add(toughnessString + ChatColor.GRAY + " from " + ENCHANTS + ": " + ChatColor.YELLOW + enchantToughness);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total " + toughnessString + ChatColor.GRAY + ": " + ChatColor.YELLOW + toughness);
            lore.add(" ");
            return lore;
        }
    };

    public static final VentureMenuItem MANA_STAT_ITEM = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.MAX_MANA_STAT;
        }

        @Override
        public List<String> createLore(Player player) {
            StatPlayer statPlayer = new StatPlayer(player);

            double
                    baseMana = plugin.getBaseStatsConfig().getDouble("maxMana"),
                    tuningMana = statPlayer.getTuningStats().getMana(),
                    totalMana = statPlayer.getStats().getMaxMana(),
                    armorMana = statPlayer.getArmorStats().getMana(),
                    enchantMana = statPlayer.getEnchantStats().getMana(),
                    armorTrimMaterialMana = statPlayer.getArmorTrimStats().armorTrimMaterialStats().getDiamondBonusMana();

            double manaRegen = totalMana/50;

            String manaString = ChatColor.BLUE + "Mana " + StatSymbols.MANA.getSymbol();

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add(manaString + ChatColor.GRAY + " is used to cast abilities and regenerates");
            lore.add(ChatColor.GRAY + "at a base rate of " + ChatColor.YELLOW + "2" + ChatColor.GRAY + "% / second");
            lore.add(" ");
            lore.add(manaString + ChatColor.GRAY + " regeneration rate: " + ChatColor.BLUE + manaRegen + StatSymbols.MANA.getSymbol() + ChatColor.GRAY + " / second");
            lore.add(" ");
            // TODO: Set up mana regeneration stat
            lore.add(ChatColor.GRAY + "Base " + manaString + ChatColor.GRAY + ": " + ChatColor.YELLOW + baseMana);
            lore.add(" ");
            lore.add(manaString + ChatColor.GRAY + " from " + TUNING + ": " + ChatColor.YELLOW + tuningMana);
            lore.add(" ");
            lore.add(manaString + ChatColor.GRAY + " from " + ARMOR + ": " + ChatColor.YELLOW + armorMana);
            lore.add(" ");
            lore.add(manaString + ChatColor.GRAY + " from " + ENCHANTS + ": " + ChatColor.YELLOW + enchantMana);
            lore.add(" ");
            lore.add(manaString + ChatColor.GRAY + " from " + ARMOR_TRIMS + ": " + ChatColor.YELLOW + armorTrimMaterialMana);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total " + manaString + ChatColor.GRAY + ": " + ChatColor.YELLOW + totalMana);
            lore.add(" ");

            // Mana is used to cast abilities and regenerates
            // at a base rate of 2% per second

            return lore;
        }
    };

    public static final VentureMenuItem DAMAGE_STAT_ITEM = new VentureMenuItem(PLUGIN) {
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
            lore.add(ChatColor.GRAY + "-" + ChatColor.RED + " Melee" + StatSymbols.MELEE_DAMAGE.getSymbol());
            lore.add(ChatColor.GRAY + "-" + ChatColor.AQUA + " Magic" + StatSymbols.MAGIC_DAMAGE.getSymbol());
            lore.add(ChatColor.GRAY + "-" + ChatColor.DARK_PURPLE + " Range" + StatSymbols.RANGE_DAMAGE.getSymbol());
            lore.add(" ");
            lore.add(MORE_INFO);
            lore.add(" ");

            // Every attack you inflict has its damage modified
            // by one of the following damage types:
            // - Melee
            // - Magic
            // - Range
            return lore;
        }
    };

    public static final VentureMenuItem DEFENSE_STAT_ITEM_MORE = new VentureMenuItem(PLUGIN)
    {
        @Override
        public MenuItem menuItem() {
            return MenuItem.DEFENSE_STAT_MORE;
        }

        @Override
        public List<String> createLore(Player player) {
            StatPlayer statPlayer = new StatPlayer(player);

            double
                    baseDefense = PLUGIN.getBaseStatsConfig().getDouble("baseDefense"),
                    tuningDefense = statPlayer.getTuningStats().getDefense(),
                    totalDefense = statPlayer.getDefenseStats().getDefense(),
                    armorDefense = statPlayer.getArmorStats().getDefense(),
                    enchantDefense = statPlayer.getEnchantStats().getDefense(),
                    armorTrimMaterialDefense = statPlayer.getArmorTrimStats().armorTrimMaterialStats().getNetheriteBonusDefense();

            double finalDamageReduction = damageReduction(totalDefense);

            String defenseString = ChatColor.GREEN + "Defense " + StatSymbols.DEFENSE.getSymbol();

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base " + defenseString + ChatColor.GRAY + ": " + ChatColor.YELLOW + baseDefense);
            lore.add(" ");
            lore.add(defenseString + ChatColor.GRAY + " from " + TUNING + ": " + ChatColor.YELLOW + tuningDefense);
            lore.add(" ");
            lore.add(defenseString + ChatColor.GRAY + " from " + ARMOR + ": " + ChatColor.YELLOW + armorDefense);
            lore.add(" ");
            lore.add(defenseString + ChatColor.GRAY + " from " + ENCHANTS + ": " + ChatColor.YELLOW + enchantDefense);
            lore.add(" ");
            lore.add(defenseString + ChatColor.GRAY + " from " + ARMOR_TRIMS + ": " + ChatColor.YELLOW + armorTrimMaterialDefense);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total " + defenseString + ChatColor.GRAY + ": " + ChatColor.YELLOW + totalDefense);
            lore.add(" ");
            lore.add(defenseString + ChatColor.GRAY + " damage reduction: " + ChatColor.RED + finalDamageReduction + "%");
            lore.add(" ");

            return lore;
        }
    };

    public static final VentureMenuItem FIRE_DEFENSE_STAT_ITEM_MORE = new VentureMenuItem(PLUGIN)
    {
        @Override
        public MenuItem menuItem() {
            return MenuItem.FIRE_DEFENSE_STAT_MORE;
        }

        @Override
        public List<String> createLore(Player player) {
            StatPlayer statPlayer = new StatPlayer(player);

            double
                    totalFireDefense = statPlayer.getDefenseStats().getFireDefense(),
                    armorFireDefense = statPlayer.getArmorStats().getFireDefense(),
                    enchantFireDefense = statPlayer.getEnchantStats().getFireDefense(),
                    armorTrimMaterialFireDefense = statPlayer.getArmorTrimStats().armorTrimMaterialStats().getNetheriteBonusFireDefense();

            double fireDefenseReduction = damageReduction(totalFireDefense);

            String fireDefenseString = ChatColor.GOLD + "Fire Defense " + StatSymbols.DEFENSE.getSymbol();

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(fireDefenseString + ChatColor.GRAY + " from " + ARMOR + ": " + ChatColor.YELLOW + armorFireDefense);
            lore.add(" ");
            lore.add(fireDefenseString + ChatColor.GRAY + " from " + ENCHANTS + ": " + ChatColor.YELLOW + enchantFireDefense);
            lore.add(" ");
            lore.add(fireDefenseString + ChatColor.GRAY + " from " + ARMOR_TRIMS + ": " + ChatColor.YELLOW + armorTrimMaterialFireDefense);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total " + fireDefenseString + ChatColor.GRAY + ": " + ChatColor.YELLOW + totalFireDefense);
            lore.add(" ");
            lore.add(fireDefenseString + ChatColor.GRAY + " damage reduction: " + ChatColor.RED + fireDefenseReduction + "%");
            lore.add(" ");

            return lore;
        }
    };

    public static final VentureMenuItem EXPLOSION_DEFENSE_STAT_ITEM_MORE = new VentureMenuItem(PLUGIN)
    {
        @Override
        public MenuItem menuItem() {
            return MenuItem.EXPLOSION_DEFENSE_STAT_MORE;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);

            double
                    totalExplosionDefense = statPlayer.getDefenseStats().getExplosionDefense(),
                    armorExplosionDefense = statPlayer.getArmorStats().getExplosionDefense(),
                    enchantExplosionDefense = statPlayer.getEnchantStats().getExplosionDefense();

            double explosionDefenseReduction = damageReduction(totalExplosionDefense);

            String explosionDefenseString = ChatColor.DARK_RED + "Explosion Defense " + StatSymbols.DEFENSE.getSymbol();

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(explosionDefenseString + ChatColor.GRAY + " from " + ARMOR + ": " + ChatColor.YELLOW + armorExplosionDefense);
            lore.add(" ");
            lore.add(explosionDefenseString + ChatColor.GRAY + " from " + ENCHANTS + ": " + ChatColor.YELLOW + enchantExplosionDefense);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total " + explosionDefenseString + ChatColor.GRAY + ": " + ChatColor.YELLOW + totalExplosionDefense);
            lore.add(" ");
            lore.add(explosionDefenseString + ChatColor.GRAY + " damage reduction: " + ChatColor.RED + explosionDefenseReduction + "%");
            lore.add(" ");

            return lore;
        }
    };

    public static final VentureMenuItem PROJECTILE_DEFENSE_STAT_ITEM_MORE = new VentureMenuItem(PLUGIN)
    {
        @Override
        public MenuItem menuItem() {
            return MenuItem.PROJECTILE_DEFENSE_STAT_MORE;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);

            double
                    totalProjectileDefense = statPlayer.getDefenseStats().getProjectileDefense(),
                    armorProjectileDefense = statPlayer.getArmorStats().getProjectileDefense(),
                    enchantProjectileDefense = statPlayer.getEnchantStats().getProjectileDefense();

            double projectileDefenseReduction = damageReduction(totalProjectileDefense);

            String projectileDefenseString = ChatColor.DARK_PURPLE + "Projectile Defense " + StatSymbols.DEFENSE.getSymbol();

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(projectileDefenseString + ChatColor.GRAY + " from " + ARMOR + ": " + ChatColor.YELLOW + armorProjectileDefense);
            lore.add(" ");
            lore.add(projectileDefenseString + ChatColor.GRAY + " from " + ENCHANTS + ": " + ChatColor.YELLOW + enchantProjectileDefense);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total " + projectileDefenseString + ChatColor.GRAY + ": " + ChatColor.YELLOW + totalProjectileDefense);
            lore.add(" ");
            lore.add(projectileDefenseString + ChatColor.GRAY + " damage reduction: " + ChatColor.RED + projectileDefenseReduction + "%");
            lore.add(" ");

            return lore;
        }
    };

    public static final VentureMenuItem MAGIC_DEFENSE_STAT_ITEM_MORE = new VentureMenuItem(PLUGIN)
    {
        @Override
        public MenuItem menuItem() {
            return MenuItem.MAGIC_DEFENSE_STAT_MORE;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);

            double
                    totalMagicDefense = statPlayer.getDefenseStats().getMagicDefense(),
                    armorMagicDefense = statPlayer.getArmorStats().getMagicDefense(),
                    enchantMagicDefense = statPlayer.getEnchantStats().getMagicDefense();

            double projectileDefenseReduction = damageReduction(totalMagicDefense);

            String magicDefenseString = ChatColor.AQUA + "Magic Defense " + StatSymbols.DEFENSE.getSymbol();

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(magicDefenseString + ChatColor.GRAY + " from " + ARMOR + ": " + ChatColor.YELLOW + armorMagicDefense);
            lore.add(" ");
            lore.add(magicDefenseString + ChatColor.GRAY + " from " + ENCHANTS + ": " + ChatColor.YELLOW + enchantMagicDefense);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total " + magicDefenseString + ChatColor.GRAY + ": " + ChatColor.YELLOW + totalMagicDefense);
            lore.add(" ");
            lore.add(magicDefenseString + ChatColor.GRAY + " damage reduction: " + ChatColor.RED + projectileDefenseReduction + "%");
            lore.add(" ");

            return lore;
        }
    };

    public static final VentureMenuItem MELEE_DAMAGE_STAT_ITEM = new VentureMenuItem(PLUGIN)
    {
        @Override
        public MenuItem menuItem() {
            return MenuItem.MELEE_DAMAGE_STAT;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);

            double
                    totalBaseMelee = statPlayer.getDamageStats().getBaseMelee() + 1,
                    tuningBaseMelee = statPlayer.getTuningStats().getMelee(),
                    enchantBaseMelee = statPlayer.getEnchantStats().getBaseMelee(),
                    totalPercentMelee = statPlayer.getDamageStats().getPercentMelee(),
                    armorPercentMelee = statPlayer.getArmorStats().getPercentMeleeDamage();

            String meleeDamageString = ChatColor.RED + "Melee Damage " + StatSymbols.MELEE_DAMAGE.getSymbol();
            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base " + meleeDamageString + ChatColor.GRAY + ":");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base " + meleeDamageString + ChatColor.GRAY + " is added onto your base " + meleeDamageString);
            lore.add(ChatColor.GRAY + "before modifiers/percent damages are applied");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base " + meleeDamageString + ChatColor.GRAY + " from " + ENCHANTS + ": " + ChatColor.YELLOW + enchantBaseMelee);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base " + meleeDamageString + ChatColor.GRAY + " from " + TUNING + ": "  + ChatColor.YELLOW + tuningBaseMelee);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total base " + meleeDamageString + ChatColor.GRAY + ": " + ChatColor.YELLOW + totalBaseMelee);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Percent " + meleeDamageString + ChatColor.GRAY + " increases your " + meleeDamageString + ChatColor.GRAY + " by " + ChatColor.YELLOW + "x" + ChatColor.GRAY + "%");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Percent " + meleeDamageString + ChatColor.GRAY + " from " + ARMOR + ": " + ChatColor.YELLOW + armorPercentMelee);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total percent " + meleeDamageString + ChatColor.GRAY + ": " + ChatColor.YELLOW + totalPercentMelee);
            lore.add(" ");

            return lore;
        }
    };

    public static final VentureMenuItem MAGIC_DAMAGE_STAT_ITEM = new VentureMenuItem(PLUGIN)
    {
        @Override
        public MenuItem menuItem() {
            return MenuItem.MAGIC_DAMAGE_STAT;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);

            double
                    totalBaseMagic = statPlayer.getDamageStats().getBaseMagic() + 1,
                    tuningBaseMagic = statPlayer.getTuningStats().getMagic(),
                    enchantBaseMagic = statPlayer.getEnchantStats().getBaseMagic(),
                    totalPercentMagic = statPlayer.getDamageStats().getPercentMagic(),
                    armorPercentMagic = statPlayer.getArmorStats().getPercentMagicDamage();

            String magicDamageString = ChatColor.AQUA + "Magic Damage " + StatSymbols.MAGIC_DAMAGE.getSymbol();
            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base " + magicDamageString + ChatColor.GRAY + ":");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base " + magicDamageString + ChatColor.GRAY + " is added onto your base " + magicDamageString);
            lore.add(ChatColor.GRAY + "before modifiers/percent damages are applied");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base " + magicDamageString + ChatColor.GRAY + " from " + TUNING + ": " + ChatColor.YELLOW + tuningBaseMagic);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base " + magicDamageString + ChatColor.GRAY + " from " + ENCHANTS + ": " + ChatColor.YELLOW + enchantBaseMagic);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total base " + magicDamageString + ChatColor.GRAY + ": " + ChatColor.YELLOW + totalBaseMagic);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Percent " + magicDamageString + ChatColor.GRAY + " increases your " + magicDamageString + ChatColor.GRAY + " by " + ChatColor.YELLOW + "x" + ChatColor.GRAY + "%");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Percent " + magicDamageString + ChatColor.GRAY + " from " + ARMOR + ": " + ChatColor.YELLOW + armorPercentMagic);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total percent " + magicDamageString + ChatColor.GRAY + ": " + ChatColor.YELLOW + totalPercentMagic);
            lore.add(" ");

            return lore;
        }
    };

    public static final VentureMenuItem RANGE_DAMAGE_STAT_ITEM = new VentureMenuItem(PLUGIN)
    {
        @Override
        public MenuItem menuItem() {
            return MenuItem.RANGE_DAMAGE_STAT;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);

            double
                    totalBaseRange = statPlayer.getDamageStats().getBaseRange() + 1,
                    tuningBaseRange = statPlayer.getTuningStats().getRange(),
                    enchantBaseRange = statPlayer.getEnchantStats().getBaseRange(),
                    totalPercentRange = statPlayer.getDamageStats().getPercentRange(),
                    armorPercentRange = statPlayer.getArmorStats().getPercentRangeDamage();

            String rangeDamageString = ChatColor.DARK_PURPLE + "Range Damage " + StatSymbols.RANGE_DAMAGE.getSymbol();

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base " + rangeDamageString + ChatColor.GRAY + ":");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base " + rangeDamageString + ChatColor.GRAY + " is added onto your base " + rangeDamageString);
            lore.add(ChatColor.GRAY + "before modifiers/percent damages are applied");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base " + rangeDamageString + ChatColor.GRAY + " from " + ENCHANTS + ": " + ChatColor.YELLOW + enchantBaseRange);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base " + rangeDamageString + ChatColor.GRAY +" from " + TUNING + ": " + ChatColor.YELLOW + tuningBaseRange);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total base " + rangeDamageString + ChatColor.GRAY + ": " + ChatColor.YELLOW + totalBaseRange);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Percent " + rangeDamageString + ChatColor.GRAY + " increases your " + rangeDamageString + ChatColor.GRAY + " by " + ChatColor.YELLOW + "x" + ChatColor.GRAY + "%");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Percent " + rangeDamageString + ChatColor.GRAY + " from " + ARMOR + ": " + ChatColor.YELLOW + armorPercentRange);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total percent " + rangeDamageString + ChatColor.GRAY + ": " + ChatColor.YELLOW + totalPercentRange);
            lore.add(" ");

            return lore;
        }
    };

    public static final VentureMenuItem SPEED_STAT_ITEM = new VentureMenuItem(PLUGIN)
    {
        @Override
        public MenuItem menuItem() {
            return MenuItem.SPEED_STAT;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);

            double
                    baseSpeedAttribute = PLUGIN.getBaseStatsConfig().getDouble("baseSpeed"),
                    baseSpeedPoints = PLUGIN.getBaseStatsConfig().getDouble("baseSpeedPoints"),
                    totalSpeed = statPlayer.getStats().getSpeed(),
                    tuningSpeed = statPlayer.getTuningStats().getSpeed(),
                    armorSpeed = statPlayer.getArmorStats().getSpeed(),
                    enchantSpeed = statPlayer.getEnchantStats().getSpeed(),
                    armorTrimMaterialSpeed = statPlayer.getArmorTrimStats().armorTrimMaterialStats().getCopperSpeed();

            String speedString = ChatColor.WHITE + "Speed " + StatSymbols.SPEED.getSymbol();
            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(speedString + ChatColor.GRAY + " determines how fast you can walk/run");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "The " + speedString + ChatColor.GRAY + " attribute value is a " + ChatColor.LIGHT_PURPLE + "universal constant");
            lore.add(ChatColor.GRAY + "amongst all players, and thus cannot be changed.");
            lore.add(ChatColor.GRAY + "This determines how much each " + speedString + ChatColor.GRAY + " point is worth");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base " + speedString + ChatColor.GRAY + " attribute value: " + ChatColor.YELLOW + baseSpeedAttribute);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Current " + speedString + ChatColor.GRAY + " attribute value: " + ChatColor.YELLOW + player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue());
            lore.add(" ");
            lore.add(speedString + ChatColor.GRAY + " points are used as a more readable way ");
            lore.add(ChatColor.GRAY + "to measure your movement speed. Each " + speedString + ChatColor.GRAY + " point");
            lore.add(ChatColor.GRAY + "represents " + ChatColor.YELLOW + "1" + ChatColor.GRAY + "% of the " + speedString + ChatColor.GRAY + " attribute value");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base " + speedString + ChatColor.GRAY + " points: " + ChatColor.YELLOW + baseSpeedPoints);
            lore.add(" ");
            lore.add(speedString + ChatColor.GRAY + " from " + TUNING + ": " + ChatColor.YELLOW + tuningSpeed);
            lore.add(" ");
            lore.add(speedString + ChatColor.GRAY + " from " + ARMOR + ": " + ChatColor.YELLOW + armorSpeed);
            lore.add(" ");
            lore.add(speedString + ChatColor.GRAY + " from " + ENCHANTS + ": " + ChatColor.YELLOW + enchantSpeed);
            lore.add(" ");
            lore.add(speedString + ChatColor.GRAY + " from " + ARMOR_TRIMS + ": " + ChatColor.YELLOW + armorTrimMaterialSpeed);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total " + speedString + ChatColor.GRAY + ": " + ChatColor.YELLOW + totalSpeed);
            lore.add(" ");

            return lore;
        }
    };

    public static final VentureMenuItem FORTITUDE_STAT_ITEM = new VentureMenuItem(PLUGIN)
    {
        @Override
        public MenuItem menuItem() {
            return MenuItem.FORTITUDE;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);

            double totalFortitude = statPlayer.getDefenseStats().getFortitude(),
                    armorFortitude = statPlayer.getArmorStats().getFortitude();

            String fortitudeString = ChatColor.DARK_GREEN + "Fortitude " + StatSymbols.FORTITUDE.getSymbol();

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(fortitudeString + ChatColor.GRAY + " is knockback resistance, and reduces");
            lore.add(ChatColor.GRAY + "knockback you receive from attacks");
            lore.add(" ");
            lore.add(fortitudeString + ChatColor.GRAY + " from armor: " + ChatColor.YELLOW + armorFortitude);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total " + fortitudeString + ChatColor.GRAY + ": " + ChatColor.YELLOW + totalFortitude);
            lore.add(" ");

            return lore;
        }
    };

    // TODO: Lore
    public static final VentureMenuItem FORTUNE_LUCK_STAT_ITEM = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.FORTUNE_STAT;
        }

        @Override
        public List<String> createLore(Player player) {

            String fortuneString = ChatColor.DARK_GREEN + "Fortune " + StatSymbols.FORTUNE.getSymbol();
            String luckString = ChatColor.GREEN + "Luck " + StatSymbols.LUCK.getSymbol();

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add(fortuneString + ChatColor.GRAY + " grants a chance for gaining");
            lore.add(ChatColor.GRAY + "more drops and finding rare drops");
            lore.add(" ");
            lore.add(luckString + ChatColor.GRAY + " increases the chance of a rare");
            lore.add(ChatColor.GRAY + "event happening");
            lore.add(" ");
            lore.add(MORE_INFO);

            // Fortune X grants a chance for gaining
            // more drops and finding rare drops
            //
            // Luck X increases the chance of a rare
            // event happening


            return lore;
        }
    };

    // TODO: Lore
    public static final VentureMenuItem MOB_FORTUNE_STAT_ITEM = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.MOB_FORTUNE_MORE;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);
            LootStats lootStats = statPlayer.getLootStats();
            ArmorStats armorStats = statPlayer.getArmorStats();

            String mobFortuneString = ChatColor.DARK_RED + "Looting " + StatSymbols.MOB_FORTUNE.getSymbol();
            String commonRarity = VentureItemRarity.Rarity.COMMON.getRarityColor() + VentureItemRarity.Rarity.COMMON.getDisplayName();

            double mobFortune = lootStats.getMobLooting();

            double chance = VentureMath.getLastTwoDigitsChanceFromChance(mobFortune);
            double guaranteedChance = VentureMath.getGuaranteedFromChance(mobFortune);


            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(mobFortuneString + ChatColor.GRAY + " increases the amount of loot");
            lore.add(ChatColor.GRAY + "dropped from mobs you kill");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "The last two digits of your " + mobFortuneString);
            lore.add(ChatColor.GRAY + "is the chance for you to receive +" + ChatColor.DARK_RED + 1);
            lore.add(ChatColor.GRAY + "extra " + commonRarity + ChatColor.GRAY + " loot drop");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Chance for +" + ChatColor.DARK_RED + 1 + ChatColor.GRAY + " extra " + commonRarity + ChatColor.GRAY + " loot drop: " + ChatColor.YELLOW + chance + "%");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Every " + ChatColor.YELLOW + 100 + " " + mobFortuneString + ChatColor.GRAY + " guarantees +" + ChatColor.DARK_RED + 1 + ChatColor.GRAY + " of a");
            lore.add(commonRarity + ChatColor.GRAY + " loot drop");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Extra " + commonRarity + ChatColor.GRAY + " loot drop amount: " + ChatColor.YELLOW + guaranteedChance);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total " + mobFortuneString + ChatColor.GRAY + ": " + ChatColor.YELLOW + mobFortune);


            // Looting X increases the amount of loot
            // dropped from mobs you kill, and increases
            // your chances for rare drops
            //
            // The last two digits of your Looting X
            // is the chance for you to receive +1
            // extra Common loot drop
            //
            // Chance for +1 extra Common loot drop:
            //
            // Every 100 Looting X guarantees +1 of a
            // Common loot drop
            //
            // Extra Common loot drop amount:
            // Every 100
            //
            return lore;
        }
    };

    // TODO: Lore
    public static final VentureMenuItem MINING_FORTUNE_STAT_ITEM = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.MINING_FORTUNE_MORE;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);

            String miningFortune = ChatColor.DARK_BLUE + "Mining Fortune " + StatSymbols.MINING_FORTUNE.getSymbol();

            List<String> lore = new ArrayList<>();



            return lore;
        }
    };

    // TODO: Lore
    public static final VentureMenuItem FORAGING_FORTUNE_STAT_ITEM = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.FORAGING_FORTUNE_MORE;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);

            String foragingFortune = ChatColor.DARK_GREEN + "Foraging Fortune " + StatSymbols.FORAGING_FORTUNE.getSymbol();

            List<String> lore = new ArrayList<>();



            return lore;
        }
    };

    // TODO: Lore
    public static final VentureMenuItem FARMING_FORTUNE_STAT_ITEM = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.FARMING_FORTUNE_MORE;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);

            String farmingFortune = ChatColor.GREEN + "Farming Fortune " + StatSymbols.FARMING.getSymbol();

            List<String> lore = new ArrayList<>();



            return lore;
        }
    };

    public static final VentureMenuItem LUCK_STAT_ITEM = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.LUCK_MORE;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);

            String luckString = ChatColor.GREEN + "Luck " + StatSymbols.LUCK.getSymbol();

            List<String> lore = new ArrayList<>();



            return lore;
        }
    };

    // TODO: Lore
    public static final VentureMenuItem FISHING_LUCK_STAT_ITEM = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.FISHING_LUCK_MORE;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);

            String fishingLuckString = ChatColor.DARK_AQUA + "Fishing Luck " + StatSymbols.FISHING_LUCK.getSymbol();

            List<String> lore = new ArrayList<>();



            return lore;
        }
    };

    public static final VentureMenuItem POTION_STAT_ITEM = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.POTION_STAT;
        }

        @Override
        public List<String> createLore(Player player) {
            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add(ChatColor.GREEN + "Positive" + ChatColor.DARK_AQUA + " Potion Effects" + ChatColor.GRAY + " you gain from brewed");
            lore.add(ChatColor.GRAY + "potions can be enhanced by the following stats:");
            lore.add(" ");
            lore.add(ChatColor.DARK_RED + "Longevity " + StatSymbols.LONGEVITY.getSymbol());
            lore.add(ChatColor.GRAY + " Increases the duration of consumed potions");
            lore.add(" ");
            lore.add(ChatColor.LIGHT_PURPLE + "Caffeination " + StatSymbols.CAFFEINATION.getSymbol());
            lore.add(ChatColor.GRAY + " Chance to increase the amplifier of the");
            lore.add(ChatColor.GRAY + " consumed " + ChatColor.DARK_AQUA + "Potion Effect" + ChatColor.GRAY + " by +" + ChatColor.LIGHT_PURPLE + 1 + ChatColor.GRAY + " level");
            lore.add(" ");
            lore.add(MORE_INFO);
            return lore;
        }
    };

    public static final VentureMenuItem LONGEVITY_STAT_ITEM = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.LONGEVITY_POTION_MORE;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);
            double longevity = statPlayer.getPotionStats().getLongevity();

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add(ChatColor.DARK_RED + "Longevity " + StatSymbols.LONGEVITY.getSymbol() + ChatColor.GRAY + " increases the duration of");
            lore.add(ChatColor.GREEN + "Positive " + ChatColor.DARK_AQUA + "Potion Effects" + ChatColor.GRAY + " consumed from");
            lore.add(ChatColor.GRAY + "potions by " + ChatColor.YELLOW + "x" + ChatColor.GRAY + "%");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Current " + ChatColor.DARK_RED + "Longevity " + StatSymbols.LONGEVITY.getSymbol() + ChatColor.GRAY + ": " + ChatColor.YELLOW + longevity);
            lore.add(" ");

            //
            // Longevity X increases the duration of
            // positive potion effects consumed from
            // potions by x%
            //
            // Current Longevity: XXX
            //
            return lore;
        }
    };

    public static final VentureMenuItem CAFFEINATION_STAT_ITEM = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.CAFFEINATION_POTION_MORE;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);
            double caffeination = statPlayer.getPotionStats().getCaffeination();

            double chance = VentureMath.getLastTwoDigitsChanceFromChance(caffeination);
            int guaranteed = VentureMath.getGuaranteedFromChance(caffeination);

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(ChatColor.LIGHT_PURPLE + "Caffeination " + StatSymbols.CAFFEINATION.getSymbol() + ChatColor.GRAY + " grants a chance to increase the");
            lore.add(ChatColor.GRAY + "amplifier of " + ChatColor.GREEN + "Positive " + ChatColor.DARK_AQUA + "Potion Effects" + ChatColor.GRAY + " consumed");
            lore.add(ChatColor.GRAY + "from potions");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Current " + ChatColor.LIGHT_PURPLE + "Caffeination " + StatSymbols.CAFFEINATION.getSymbol() + ChatColor.GRAY + ": " + ChatColor.YELLOW + caffeination);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "The last two digits of your " + ChatColor.LIGHT_PURPLE + "Caffeination " + StatSymbols.CAFFEINATION.getSymbol() + ChatColor.GRAY + " is");
            lore.add(ChatColor.GRAY + "the chance for you to gain +" + ChatColor.LIGHT_PURPLE + 1 + " Amplifier Level");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Chance for +" + ChatColor.LIGHT_PURPLE + 1 + " Amplifier Level" + ChatColor.GRAY + ": " + ChatColor.YELLOW + chance + "%");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Every " + ChatColor.YELLOW + 100 + ChatColor.LIGHT_PURPLE + " Caffeination " + StatSymbols.CAFFEINATION.getSymbol() + ChatColor.GRAY + " guarantees +" + ChatColor.LIGHT_PURPLE + 1 + " Amplifier");
            lore.add(ChatColor.LIGHT_PURPLE + "Level" + ChatColor.GRAY + " to the consumed " + ChatColor.DARK_AQUA + "Potion Effect");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Guaranteed " + ChatColor.LIGHT_PURPLE + " Amplifier Levels" + ChatColor.GRAY + ": " + ChatColor.YELLOW + guaranteed);
            lore.add(" ");

            //
            // Caffeination X grants a chance to increase the
            // Amplifier of positive potion effects consumed
            // from potions
            //
            // The last two digits of your Caffeination X is
            // the chance for you to gain +1 Amplifier Level
            //
            // Every 100 Caffeination X guarantees +1 Amplifier
            // Level to the consumed potion effect
            //
            return lore;
        }
    };

    public enum StatItemSlots
    {
        HEALTH (HEALTH_STAT_ITEM, 19),
        DEFENSE (DEFENSE_STAT_ITEM, 20),
        TOUGHNESS (TOUGHNESS_STAT_ITEM, 21),
        MANA (MANA_STAT_ITEM, 22),
        DAMAGE (DAMAGE_STAT_ITEM, 23),
        SPEED (SPEED_STAT_ITEM, 24),
        FORTITUDE (FORTITUDE_STAT_ITEM, 25),
        FORTUNE (FORTUNE_LUCK_STAT_ITEM, 30),
        POTION (POTION_STAT_ITEM, 32);

        private final VentureMenuItem ventureMenuItem;
        private final int inventorySlot;

        StatItemSlots(VentureMenuItem ventureMenuItem, int inventorySlot)
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

    public enum DefenseStatSlots
    {
        DEFENSE (DEFENSE_STAT_ITEM_MORE, 20),
        FIRE_DEFENSE (FIRE_DEFENSE_STAT_ITEM_MORE, 21),
        EXPLOSION_DEFENSE (EXPLOSION_DEFENSE_STAT_ITEM_MORE, 22),
        PROJECTILE_DEFENSE (PROJECTILE_DEFENSE_STAT_ITEM_MORE, 23),
        MAGIC_DEFENSE (MAGIC_DEFENSE_STAT_ITEM_MORE, 24);

        private final VentureMenuItem ventureMenuItem;
        private final int inventorySlot;

        DefenseStatSlots(VentureMenuItem ventureMenuItem, int inventorySlot)
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

    public enum DamageStatSlots
    {
        MELEE_DAMAGE (MELEE_DAMAGE_STAT_ITEM, 21),
        MAGIC_DAMAGE (MAGIC_DAMAGE_STAT_ITEM, 22),
        RANGE_DAMAGE (RANGE_DAMAGE_STAT_ITEM, 23);

        private final VentureMenuItem ventureMenuItem;
        private final int inventorySlot;

        DamageStatSlots(VentureMenuItem ventureMenuItem, int inventorySlot)
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


    public enum FortuneStatSlots
    {
        MOB (MOB_FORTUNE_STAT_ITEM, 20),
        MINING (MINING_FORTUNE_STAT_ITEM, 21),
        FORAGING (FORAGING_FORTUNE_STAT_ITEM, 22),
        FARMING (FARMING_FORTUNE_STAT_ITEM, 23),
        FISHING (FISHING_LUCK_STAT_ITEM, 24),
        LUCK (LUCK_STAT_ITEM, 31);

        private final VentureMenuItem ventureMenuItem;
        private final int inventorySlot;

        FortuneStatSlots(VentureMenuItem ventureMenuItem, int inventorySlot)
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

    public enum PotionStatSlots
    {
        LONGEVITY (LONGEVITY_STAT_ITEM, 21),
        CAFFEINATION (CAFFEINATION_STAT_ITEM, 23);

        private final VentureMenuItem ventureMenuItem;
        private final int inventorySlot;


        PotionStatSlots(VentureMenuItem ventureMenuItem, int inventorySlot) {
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
