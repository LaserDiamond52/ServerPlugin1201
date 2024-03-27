package net.laserdiamond.ventureplugin.stats.Components;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.enchants.Components.EnchantStats;
import net.laserdiamond.ventureplugin.items.armor.trims.Components.ArmorTrimMaterialStats;
import net.laserdiamond.ventureplugin.items.armor.trims.Components.ArmorTrimPatternStats;
import net.laserdiamond.ventureplugin.items.armor.trims.Manager.ArmorTrimStats;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.stats.Manager.StatProfileManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class StatsItemManager {

    private static final VenturePlugin plugin = VenturePlugin.getInstance();
    private static final StatProfileManager statProfileManager = plugin.getStatProfileManager();
    public static List<String> createLore (Player player, StatsItem stat) {

        Stats stats = statProfileManager.getStatProfile(player.getUniqueId()).stats();
        DamageStats damageStats = statProfileManager.getStatProfile(player.getUniqueId()).damageStats();
        DefenseStats defenseStats = statProfileManager.getStatProfile(player.getUniqueId()).defenseStats();
        EnchantStats enchantStats = statProfileManager.getStatProfile(player.getUniqueId()).enchantStats();
        ArmorTrimStats armorTrimStats = statProfileManager.getStatProfile(player.getUniqueId()).armorTrimStats();
        ArmorTrimMaterialStats materialStats = armorTrimStats.armorTrimMaterialStats();
        ArmorTrimPatternStats patternStats = armorTrimStats.armorTrimPatternStats();

        DecimalFormat doubleDecimal = new DecimalFormat("0.00");

        // TODO: Base Stats
        double maxHealth = player.getMaxHealth();
        double speed = stats.getSpeed();
        double maxMana = stats.getMaxMana();

        double baseMelee = damageStats.getBaseMelee();
        double baseMagic = damageStats.getBaseMagic();
        double baseRange = damageStats.getBaseRange();
        double meleeDamage = damageStats.getPercentMelee();
        double magicDamage = damageStats.getPercentMagic();
        double rangeDamage = damageStats.getPercentRange();

        double armor = defenseStats.getDefense();
        double fire_armor = defenseStats.getFireDefense();
        double explosion_armor = defenseStats.getExplosionDefense();
        double projectile_armor = defenseStats.getProjectileDefense();
        double magic_armor = defenseStats.getMagicDefense();
        double toughness = defenseStats.getToughness();


        // TODO: Enchantment Stats
        double enchant_armor = enchantStats.getDefense();
        double enchant_fire_armor = enchantStats.getDefense();
        double enchant_explosion_armor = enchantStats.getDefense();
        double enchant_projectile_armor = enchantStats.getDefense();
        double enchant_magic_armor = enchantStats.getDefense();
        double enchant_toughness = enchantStats.getDefense();
        double enchant_base_melee = enchantStats.getDefense();
        double enchant_base_magic = enchantStats.getDefense();
        double enchant_base_range = enchantStats.getDefense();
        double enchant_base_mana = enchantStats.getDefense();
        double enchant_health = enchantStats.getHealth();

        // TODO: Tuning Menu

        // TODO: Trims

        double copperSpeed = materialStats.getCopperSpeed();
        double goldSaturation = materialStats.getGoldSaturationChance();
        double ironHealthBoost = materialStats.getIronHealthBoost();
        double lapisExpBoost = materialStats.getLapisBonusExp();
        double quartzMiningExp = materialStats.getQuartzBonusMiningExp();
        double redstonePotionBonus = materialStats.getRedstoneBonusPotion();
        double emeraldLuck = materialStats.getEmeraldBonusLuck();
        double amethystDamage = materialStats.getAmethystBonusDamage();
        double diamondMana = materialStats.getDiamondBonusMana();
        double netheriteDefense = materialStats.getNetheriteBonusDefense();
        double netheriteFireDefense = materialStats.getNetheriteBonusFireDefense();

        // TODO: Patterns
        double sentryEmerald = patternStats.getSentryEmeralds();
        double vexManaRegen = patternStats.getVexManaRegen();
        double wildForagingExp = patternStats.getWildForagingExp();
        double coastFishingExp = patternStats.getCoastFishingExp();
        double duneMiningExp = patternStats.getDuneMiningExp();
        double raiserSusSand = patternStats.getRaiserSusSand();
        double shaperSusGravel = patternStats.getShaperSusGravel();
        double hostBonusExp = patternStats.getHostBonusExp();
        double wardCombatExp = patternStats.getWardCombatExp();
        double silenceAttackChance = patternStats.getSilenceAttackChance();
        double tideAquaticDamage = patternStats.getTideAquaticDamage();
        double snoutCounterChance = patternStats.getSnoutCounterChance();
        double ribWitherDuration = patternStats.getRibWitherDuration();
        double eyeMagicDamage = patternStats.getEyeMagicDamagePerExpLevel();

        double spireSaveElytraFuel = patternStats.getSpireSaveElytraFuel();


        // TODO: Base Attributes
        double playerBaseHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
        //double baseMeleeStat = baseMelee - enchant_base_melee;
        //double baseMagicStat = baseMagic - enchant_base_magic;
        //double baseRangeStat = baseRange - enchant_base_range;

        double armorHealth = maxHealth - playerBaseHealth;
        double armorDef = armor - enchant_armor;
        double armorToughness = toughness - enchant_toughness;
        double armorMana = maxMana - enchant_base_mana;

        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack leggings = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();

        if (helmet != null) {
            ItemMeta itemMeta = helmet.getItemMeta();
            if (itemMeta != null) {

            }
        }
        if (chestplate != null) {
            ItemMeta itemMeta = chestplate.getItemMeta();

        }
        if (leggings != null) {
            ItemMeta itemMeta = leggings.getItemMeta();

        }
        if (boots != null) {
            ItemMeta itemMeta = boots.getItemMeta();

        }


        List<String> lore = new ArrayList<>();
        lore.add(" ");

        if (stat.equals(StatsItem.HEALTH)) {
            lore.add(ChatColor.GRAY + "Total health: " + stat.statColor + maxHealth + "❤");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base health: " + stat.statColor + playerBaseHealth + "❤");

            /*
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Health from armor: " + stat.statColor + armorHealth + "❤");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Health from enchants: " + stat.statColor + enchant_health + "❤");

             */

        } else if (stat.equals(StatsItem.DEFENSE)) {
            double damageReduction = 100 * (armor/(armor + 20));
            lore.add(ChatColor.GRAY + "Total defense: " + stat.statColor + armor + "⛉");
            lore.add(" ");
            //lore.add(ChatColor.GRAY + "Defense from armor: " + stat.statColor + armorDef + "⛉");
            //lore.add(" ");
            lore.add(ChatColor.GRAY + "Fire defense: " + ChatColor.GOLD + fire_armor);
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Blast Defense: " + ChatColor.DARK_GRAY + explosion_armor + "⛉");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Projectile Defense: " + ChatColor.DARK_PURPLE + projectile_armor + "⛉");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Magic Defense: " + ChatColor.AQUA + magic_armor + "⛉");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Damage Reduction: " + ChatColor.GOLD + doubleDecimal.format(damageReduction) + "%");

        } else if (stat.equals(StatsItem.TOUGHNESS)) {

            lore.add(ChatColor.GRAY + "Total toughness: " + stat.statColor + toughness + "✧");
            //lore.add(" ");
            //lore.add(ChatColor.GRAY + "Toughness from armor: " + stat.statColor + armorToughness + "✧");

            /*
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Toughness from enchants: " + stat.statColor + enchant_toughness);

             */

        } else if (stat.equals(StatsItem.MAX_MANA)) {

            lore.add(ChatColor.GRAY + "Total mana: " + stat.statColor + maxMana + "\uD83D\uDD89");

            /*
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Mana from armor: " + stat.statColor + armorMana + "\uD83D\uDD89");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Mana from enchants: " + stat.statColor + enchant_base_mana + "\uD83D\uDD89");

             */

        } else if (stat.equals(StatsItem.MELEE_DAMAGE)) {

            lore.add(ChatColor.GRAY + "Melee damage increase %: " + stat.statColor + meleeDamage + "\uD83D\uDDE1");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base melee damage: " + stat.statColor + baseMelee + "\uD83D\uDDE1");

            /*
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base melee damage from enchants: " + stat.statColor + enchant_base_melee + "\uD83D\uDDE1");

             */

        } else if (stat.equals(StatsItem.MAGIC_DAMAGE)) {

            lore.add(ChatColor.GRAY + "Magic damage increase %: " + stat.statColor + magicDamage + "⚝");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base magic damage: " + stat.statColor + baseMagic + "⚝");

            /*
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base magic damage from enchants: " + stat.statColor + enchant_base_magic + "⚝");

             */

        } else if (stat.equals(StatsItem.RANGE_DAMAGE)) {

            lore.add(ChatColor.GRAY + "Range damage increase %: " + stat.statColor + rangeDamage + "➶");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base range damage: " + stat.statColor + baseRange + "➶");

            /*
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Base range damage from enchants: " + stat.statColor + enchant_base_range + "➶");

             */

        } else if (stat.equals(StatsItem.SPEED)) {

            // TODO: make speed stat
            lore.add(ChatColor.GRAY + "Current movement speed: " + stat.statColor + doubleDecimal.format(speed) + ChatColor.GRAY + "%");
            lore.add(" ");

            double speedPercent = speed - 100;

            if (speed >= 100)
            {
                lore.add(ChatColor.GRAY + "You move " + stat.statColor + doubleDecimal.format(speedPercent) + ChatColor.GRAY + "% faster!");
            } else
            {
                lore.add(ChatColor.GRAY + "You move " + stat.statColor + doubleDecimal.format(speedPercent) + ChatColor.GRAY + "% slower!");
            }

        }
        lore.add(" ");

        return lore;
    }

    public static ItemForger createStatItem(Player player, StatsItem stat) {

        ItemForger itemForger = new ItemForger(stat.itemMaterial);
        itemForger.setName(stat.statColor + stat.statName);
        itemForger.hideAllItemFlags();
        itemForger.setLore(createLore(player, stat));

        return itemForger;
    }

    public enum StatsItem {

        HEALTH (116, "Health", Material.RED_DYE, ChatColor.RED,19),
        DEFENSE (117, "Defense", Material.IRON_CHESTPLATE, ChatColor.GREEN,20),
        TOUGHNESS (118, "Toughness", Material.DIAMOND_CHESTPLATE, ChatColor.GREEN,21),
        MAX_MANA (119, "Mana", Material.HEART_OF_THE_SEA, ChatColor.BLUE,22),
        MELEE_DAMAGE (120, "Melee Damage", Material.IRON_SWORD, ChatColor.RED,23),
        MAGIC_DAMAGE (121, "Magic Damage", Material.DIAMOND, ChatColor.AQUA,24),
        RANGE_DAMAGE (122, "Range Damage", Material.BOW, ChatColor.DARK_PURPLE,25),
        SPEED (123, "Movement Speed", Material.IRON_BOOTS, ChatColor.WHITE,31);

        private final int CMD;
        private final String statName;
        private final Material itemMaterial;
        private final ChatColor statColor;
        private final int inventorySlot;

        StatsItem(int CMD, String statName, Material itemMaterial, ChatColor statColor, int inventorySlot) {
            this.CMD = CMD;
            this.statName = statName;
            this.itemMaterial = itemMaterial;
            this.statColor = statColor;
            this.inventorySlot = inventorySlot;
        }

        public int getCMD() {
            return CMD;
        }

        public String getStatName() {
            return statName;
        }

        public static String ofName(String inputStatName) {
            for (StatsItem statsItem : values()) {
                if (statsItem.statName.equals(inputStatName)) {
                    return statsItem.statName;
                }
            }
            throw new IllegalArgumentException(ChatColor.RED + "Not a stat name: " + inputStatName);
        }

        public static int ofCMD(String inputStatName) {
            for (StatsItem statsItem : values()) {
                if (statsItem.statName.equals(inputStatName)) {
                    return statsItem.CMD;
                }
            }
            throw new IllegalArgumentException(ChatColor.RED + "No custom model data for stat item: " + inputStatName);
        }

        public Material getItemMaterial() {
            return itemMaterial;
        }

        public ChatColor getStatColor() {
            return statColor;
        }

        public int getInventorySlot() {
            return inventorySlot;
        }
    }
}
