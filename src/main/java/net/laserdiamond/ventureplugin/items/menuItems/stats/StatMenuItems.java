package net.laserdiamond.ventureplugin.items.menuItems.stats;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.items.menuItems.stats.main.*;
import net.laserdiamond.ventureplugin.items.menuItems.util.MenuItem;
import net.laserdiamond.ventureplugin.items.menuItems.util.VentureMenuItem;
import net.laserdiamond.ventureplugin.stats.Components.DefenseStats;
import net.laserdiamond.ventureplugin.stats.Components.StatSymbols;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class StatMenuItems {

    private VentureMenuItem
            healthStatItem,
            defenseStatItem,
            toughnessStatItem,
            manaStatItem,
            damageStatItem,
            speedStatItem,
            fortitudeStatItem;


    public StatMenuItems(VenturePlugin plugin)
    {
        healthStatItem = new HealthStatItem(plugin);
        defenseStatItem = new DefenseStatItem(plugin);
        toughnessStatItem = new ToughnessStatItem(plugin);
        manaStatItem = new ManaStatItem(plugin);
        damageStatItem = new DamageStatItem(plugin);
        speedStatItem = new SpeedStatItem(plugin);
        fortitudeStatItem = new FortitudeStatItem(plugin);
    }

    public StatMenuItems()
    {

    }

    public VentureMenuItem getHealthStatItem() {
        return healthStatItem;
    }

    public VentureMenuItem getDefenseStatItem() {
        return defenseStatItem;
    }

    public VentureMenuItem getToughnessStatItem() {
        return toughnessStatItem;
    }

    public VentureMenuItem getManaStatItem() {
        return manaStatItem;
    }

    public VentureMenuItem getDamageStatItem() {
        return damageStatItem;
    }

    public VentureMenuItem getSpeedStatItem() {
        return speedStatItem;
    }

    public VentureMenuItem getFortitudeStatItem() {
        return fortitudeStatItem;
    }


    private static final VenturePlugin PLUGIN = VenturePlugin.getInstance();
    private static final double DEFENSE_FACTOR = PLUGIN.getBaseStatsConfig().getInt("a");

    public static double damageReduction(double defense)
    {
        return defense / (defense + DEFENSE_FACTOR);
    }

    @Deprecated
    public static VentureMenuItem HEALTH_STAT_ITEM = new VentureMenuItem(PLUGIN)
    {
        @Override
        public MenuItem menuItem() {
            return MenuItem.HEALTH_STAT;
        }

        @Override
        public List<String> createLore(Player player)
        {
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
            lore.add(ChatColor.GRAY + "Base Health: " + ChatColor.RED + baseHealth + StatSymbols.HEALTH.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Health from armor: " + ChatColor.RED + armorHealth + StatSymbols.HEALTH.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Health from enchants: " + ChatColor.RED + enchantHealth + StatSymbols.HEALTH.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Health from armor trims: " + ChatColor.RED + trimHealth + StatSymbols.HEALTH.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total Health: " + ChatColor.RED + totalHealth + StatSymbols.HEALTH.getSymbol());
            lore.add(" ");

            return lore;
        }
    };

    @Deprecated
    public static VentureMenuItem DEFENSE_STAT_ITEM = new VentureMenuItem(PLUGIN)
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


            //double damageReduction = 100 * (totalDefense/(totalDefense + defenseFactor));

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Defense reduces the amount of damage you take.");
            lore.add(ChatColor.GRAY + "The formula for damage reduction is:");
            lore.add(ChatColor.GOLD + "x / (x + " + DEFENSE_FACTOR + ")");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total Defense: " + ChatColor.GREEN + totalDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total Fire Defense: " + ChatColor.GOLD + totalFireDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total Explosion Defense: " + ChatColor.DARK_RED + totalExplosionDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total Projectile Defense: " + ChatColor.DARK_PURPLE + totalProjectileDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total Magic Defense: " + ChatColor.AQUA + totalMagicDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Click for more info");
            lore.add(" ");


            //
            // Defense reduces the amount of damage you take.
            // The formula for damage reduction is:
            // x / (x + 20)

            return lore;
        }
    };

    @Deprecated
    public static VentureMenuItem TOUGHNESS_STAT_ITEM = new VentureMenuItem(PLUGIN) {
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

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Toughness reduces damage you take from");
            lore.add(ChatColor.GRAY + "true damage attacks. Toughness does not");
            lore.add(ChatColor.GRAY + "affect damage from normal attacks");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base toughness: " + ChatColor.GREEN + baseToughness + StatSymbols.TOUGHNESS.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Toughness from armor: " + ChatColor.GREEN + armorToughness + StatSymbols.TOUGHNESS.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Toughness from enchants: " + ChatColor.GREEN + enchantToughness + StatSymbols.TOUGHNESS.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total toughness: " + ChatColor.GREEN + toughness + StatSymbols.TOUGHNESS.getSymbol());
            lore.add(" ");
            return lore;
        }
    };

    @Deprecated
    public static VentureMenuItem MANA_STAT_ITEM = new VentureMenuItem(PLUGIN) {
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
            lore.add(ChatColor.GRAY + "Mana regeneration rate: " + ChatColor.BLUE + manaRegen + StatSymbols.MANA.getSymbol() + ChatColor.GRAY + " per second");
            lore.add(" ");
            // TODO: Set up mana regeneration stat
            lore.add(ChatColor.GRAY + "Base mana: " + ChatColor.BLUE + baseMana + StatSymbols.MANA.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Mana from armor: " + ChatColor.BLUE + armorMana + StatSymbols.MANA.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Mana from enchants: " + ChatColor.BLUE + enchantMana + StatSymbols.MANA.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Mana from armor trim materials: " + ChatColor.BLUE + armorTrimMaterialMana + StatSymbols.MANA.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total mana: " + ChatColor.BLUE + totalMana + StatSymbols.MANA.getSymbol());
            lore.add(" ");

            // Mana is used to cast abilities and regenerates
            // at a base rate of 2% per second

            return lore;
        }
    };

    @Deprecated
    public static VentureMenuItem DAMAGE_STAT_ITEM = new VentureMenuItem(PLUGIN) {
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
    };

    // TODO: Move to own class
    public static VentureMenuItem DEFENSE_STAT_ITEM_MORE = new VentureMenuItem(PLUGIN)
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
                    totalDefense = statPlayer.getDefenseStats().getDefense(),
                    armorDefense = statPlayer.getArmorStats().getDefense(),
                    enchantDefense = statPlayer.getEnchantStats().getDefense(),
                    armorTrimMaterialDefense = statPlayer.getArmorTrimStats().armorTrimMaterialStats().getNetheriteBonusDefense();

            double finalDamageReduction = damageReduction(totalDefense);

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base Defense: " + ChatColor.GREEN + baseDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Defense from armor: " + ChatColor.GREEN + armorDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Defense from enchants: " + ChatColor.GREEN + enchantDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Defense from armor trims: " + ChatColor.GREEN + armorTrimMaterialDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total Defense: " + ChatColor.GREEN + totalDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Defense damage reduction: " + ChatColor.RED + finalDamageReduction + "%");
            lore.add(" ");

            return lore;
        }
    };

    // TODO: Move to own class
    public static VentureMenuItem FIRE_DEFENSE_STAT_ITEM_MORE = new VentureMenuItem(PLUGIN)
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

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(ChatColor.GRAY + "Fire defense from armor: " + ChatColor.GOLD + armorFireDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Fire defense from enchants: " + ChatColor.GOLD + enchantFireDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Fire defense from armor trims: " + ChatColor.GOLD + armorTrimMaterialFireDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total fire defense: " + ChatColor.GOLD + totalFireDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Fire defense damage reduction: " + ChatColor.RED + fireDefenseReduction + "%");
            lore.add(" ");

            return lore;
        }
    };

    // TODO: Move to own class
    public static VentureMenuItem EXPLOSION_DEFENSE_STAT_ITEM_MORE = new VentureMenuItem(PLUGIN)
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

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(ChatColor.GRAY + "Explosion defense from armor: " + ChatColor.DARK_RED + armorExplosionDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Explosion defense from enchants: " + ChatColor.DARK_RED + enchantExplosionDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total explosion defense: " + ChatColor.DARK_RED + totalExplosionDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Explosion defense damage reduction: " + ChatColor.RED + explosionDefenseReduction + "%");
            lore.add(" ");

            return lore;
        }
    };

    // TODO: Move to own class
    public static VentureMenuItem PROJECTILE_DEFENSE_STAT_ITEM_MORE = new VentureMenuItem(PLUGIN)
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

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(ChatColor.GRAY + "Projectile defense from armor: " + ChatColor.DARK_PURPLE + armorProjectileDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Projectile defense from enchants: " + ChatColor.DARK_PURPLE + enchantProjectileDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total projectile defense: " + ChatColor.DARK_PURPLE + totalProjectileDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Projectile defense damage reduction: " + ChatColor.RED + projectileDefenseReduction + "%");
            lore.add(" ");

            return lore;
        }
    };

    // TODO: Move to own class
    public static VentureMenuItem MAGIC_DEFENSE_STAT_ITEM_MORE = new VentureMenuItem(PLUGIN)
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

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(ChatColor.GRAY + "Magic defense from armor: " + ChatColor.AQUA + armorMagicDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Magic defense from enchants: " + ChatColor.AQUA + enchantMagicDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total magic defense: " + ChatColor.AQUA + totalMagicDefense + StatSymbols.DEFENSE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Magic defense damage reduction: " + ChatColor.RED + projectileDefenseReduction + "%");
            lore.add(" ");

            return lore;
        }
    };


    // TODO: Move to own class
    public static VentureMenuItem MELEE_DAMAGE_STAT_ITEM = new VentureMenuItem(PLUGIN)
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
                    enchantBaseMelee = statPlayer.getEnchantStats().getBaseMelee(),
                    totalPercentMelee = statPlayer.getDamageStats().getPercentMelee(),
                    armorPercentMelee = statPlayer.getArmorStats().getPercentMeleeDamage(),
                    enchantPercentMelee = statPlayer.getEnchantStats().getBaseMelee();

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base melee damage:");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base " + ChatColor.RED + "melee damage" + StatSymbols.MELEE_DAMAGE.getSymbol() + ChatColor.GRAY + " is added onto your base " + ChatColor.RED + "melee damage" + StatSymbols.MELEE_DAMAGE.getSymbol());
            lore.add(ChatColor.GRAY + "before modifiers/percent damages are applied");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total base melee damage: " + ChatColor.RED + totalBaseMelee + StatSymbols.MELEE_DAMAGE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base melee damage from enchants: " + ChatColor.RED + enchantBaseMelee + StatSymbols.MELEE_DAMAGE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total percent melee damage: " + ChatColor.RED + totalPercentMelee + StatSymbols.MELEE_DAMAGE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Percent melee damage from armor: " + ChatColor.RED + armorPercentMelee + StatSymbols.MELEE_DAMAGE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Enchant melee damage from armor: " + ChatColor.RED + enchantPercentMelee + StatSymbols.MELEE_DAMAGE.getSymbol());
            lore.add(" ");

            return lore;
        }
    };

    // TODO: Move to own class
    public static VentureMenuItem MAGIC_DAMAGE_STAT_ITEM = new VentureMenuItem(PLUGIN)
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
                    enchantBaseMagic = statPlayer.getEnchantStats().getBaseMagic(),
                    totalPercentMagic = statPlayer.getDamageStats().getPercentMagic(),
                    armorPercentMagic = statPlayer.getArmorStats().getPercentMagicDamage(),
                    enchantPercentMagic = statPlayer.getEnchantStats().getMagicDefense();

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base magic damage:");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base " + ChatColor.AQUA + "magic damage" + StatSymbols.MAGIC_DAMAGE.getSymbol() + ChatColor.GRAY + " is added onto your base " + ChatColor.AQUA + "magic damage" + StatSymbols.MAGIC_DAMAGE.getSymbol());
            lore.add(ChatColor.GRAY + "before modifiers/percent damages are applied");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total base magic damage: " + ChatColor.AQUA + totalBaseMagic + StatSymbols.MAGIC_DAMAGE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base magic damage from enchants: " + ChatColor.AQUA + enchantBaseMagic + StatSymbols.MAGIC_DAMAGE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total percent magic damage: " + ChatColor.AQUA + totalPercentMagic + StatSymbols.MAGIC_DAMAGE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Percent magic damage from armor: " + ChatColor.AQUA + armorPercentMagic + StatSymbols.MAGIC_DAMAGE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Enchant magic damage from armor: " + ChatColor.AQUA + enchantPercentMagic + StatSymbols.MAGIC_DAMAGE.getSymbol());
            lore.add(" ");

            return lore;
        }
    };

    // TODO: Move to own class
    public static VentureMenuItem RANGE_DAMAGE_STAT_ITEM = new VentureMenuItem(PLUGIN)
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
                    enchantBaseRange = statPlayer.getEnchantStats().getBaseRange(),
                    totalPercentRange = statPlayer.getDamageStats().getPercentRange(),
                    armorPercentRange = statPlayer.getArmorStats().getPercentRangeDamage(),
                    enchantPercentRange = statPlayer.getEnchantStats().getBaseRange();

            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base range damage:");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base " + ChatColor.DARK_PURPLE + "range damage" + StatSymbols.RANGE_DAMAGE.getSymbol() + ChatColor.GRAY + " is added onto your base " + ChatColor.DARK_PURPLE + "range damage" + StatSymbols.RANGE_DAMAGE.getSymbol());
            lore.add(ChatColor.GRAY + "before modifiers/percent damages are applied");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total base range damage: " + ChatColor.DARK_PURPLE + totalBaseRange + StatSymbols.RANGE_DAMAGE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base range damage from enchants: " + ChatColor.DARK_PURPLE + enchantBaseRange + StatSymbols.RANGE_DAMAGE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total percent range damage: " + ChatColor.DARK_PURPLE + totalPercentRange + StatSymbols.RANGE_DAMAGE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Percent range damage from armor: " + ChatColor.DARK_PURPLE + armorPercentRange + StatSymbols.RANGE_DAMAGE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Enchant range damage from armor: " + ChatColor.DARK_PURPLE + enchantPercentRange + StatSymbols.RANGE_DAMAGE.getSymbol());
            lore.add(" ");

            return lore;
        }
    };


    @Deprecated
    public static VentureMenuItem SPEED_STAT_ITEM = new VentureMenuItem(PLUGIN)
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
            lore.add(ChatColor.GRAY + "Speed attribute value: " + ChatColor.WHITE + baseSpeedAttribute + StatSymbols.SPEED.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Speed points are used as a more readable way to measure your movement");
            lore.add(ChatColor.GRAY + "speed. Each speed point represents 1% of the speed attribute value");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base speed points: " + ChatColor.WHITE + baseSpeedPoints + StatSymbols.SPEED.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Speed from armor: " + ChatColor.WHITE + armorSpeed + StatSymbols.SPEED.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Speed from enchants: " + ChatColor.WHITE + enchantSpeed + StatSymbols.SPEED.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Speed from armor trims: " + ChatColor.WHITE + armorTrimMaterialSpeed + StatSymbols.SPEED.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total Speed: " + ChatColor.WHITE + totalSpeed + StatSymbols.SPEED.getSymbol());
            lore.add(" ");

            return lore;
        }
    };

    @Deprecated
    public static VentureMenuItem FORTITUDE_STAT_ITEM = new VentureMenuItem(PLUGIN)
    {
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
            lore.add(ChatColor.GRAY + "Fortitude from armor: " + ChatColor.DARK_GREEN + armorFortitude + StatSymbols.FORTITUDE.getSymbol());
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Total fortitude: " + ChatColor.DARK_GREEN + totalFortitude + StatSymbols.FORTITUDE.getSymbol());
            lore.add(" ");

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
        FORTITUDE (FORTITUDE_STAT_ITEM, 25);

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

}
