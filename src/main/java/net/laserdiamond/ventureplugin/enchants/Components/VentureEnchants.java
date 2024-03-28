package net.laserdiamond.ventureplugin.enchants.Components;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;

import java.lang.reflect.Field;
import java.util.*;

public class VentureEnchants {

    public static ArrayList<Enchantment> ventureEnchants = new ArrayList<>();
    public static HashMap<Enchantment, String> EnchantmentNames = new HashMap<>();
    public static final Enchantment GLINT = new EnchantmentWrapper("glint", "", 1,1, EnchantmentTarget.ALL, false);
    public static final Enchantment GLOW = new EnchantmentWrapper("glow", "Glow", 1,2, EnchantmentTarget.WEAPON, false, aspectConflicting());
    public static final Enchantment VENOMOUS_ASPECT = new EnchantmentWrapper("venomousaspect", "Venomous Aspect", 1, 2, EnchantmentTarget.WEAPON, true, aspectConflicting());
    public static final Enchantment DECAY = new EnchantmentWrapper("decay", "Decay", 1, 2, EnchantmentTarget.WEAPON, false, aspectConflicting());
    public static final Enchantment THUNDER_STRIKE = new EnchantmentWrapper("thunderstrike", "Thunder Strike", 1, 3, EnchantmentTarget.WEAPON, true, thunderStrikeConflicting());
    public static final Enchantment MAGIC_PROTECTION = new EnchantmentWrapper("magicprotection", "Magic Protection", 1, 4, EnchantmentTarget.ARMOR, true, protectionConflicting());
    public static final Enchantment TOUGHNESS = new EnchantmentWrapper("toughness", "Toughness", 1, 1, EnchantmentTarget.ARMOR, false);
    public static final Enchantment MANA_POOL = new EnchantmentWrapper("manapool", "Mana Pool", 1, 5, EnchantmentTarget.ARMOR, false);
    public static final Enchantment MELEE_DAMAGE = new EnchantmentWrapper("meleedamage", "Strength", 1, 3, EnchantmentTarget.ARMOR_TORSO, false, damageConflicting());
    public static final Enchantment MAGIC_DAMAGE = new EnchantmentWrapper("magicdamage", "Arcane", 1, 3, EnchantmentTarget.ARMOR_TORSO, false, damageConflicting());
    public static final Enchantment RANGE_DAMAGE = new EnchantmentWrapper("rangedamage", "Archer", 1, 3, EnchantmentTarget.ARMOR_TORSO, false, damageConflicting());
    public static final Enchantment HEALTH = new EnchantmentWrapper("health", "Growth", 1, 5, EnchantmentTarget.ARMOR, false);
    public static final Enchantment NIGHT_VISION = new EnchantmentWrapper("nightvision", "Night Vision", 1, 1, EnchantmentTarget.ARMOR_HEAD, false);
    public static final Enchantment SPEED = new EnchantmentWrapper("speed", "Quick Feet", 1, 5, EnchantmentTarget.ARMOR_FEET, false);
    public static final Enchantment REACH = new EnchantmentWrapper("reach", "Reach", 1, 3, EnchantmentTarget.WEAPON, false);
    public static final Enchantment ARMOR_MINING_FORTUNE = new EnchantmentWrapper("armorminingfortune", "Miner", 1, 3, EnchantmentTarget.ARMOR, false);
    public static final Enchantment ARMOR_FORAGING_FORTUNE = new EnchantmentWrapper("armorforagingfortune", "Forager", 1, 3, EnchantmentTarget.ARMOR, false);
    public static final Enchantment ARMOR_FISHING_FORTUNE = new EnchantmentWrapper("armorfishingfortune", "Fisherman", 1, 3, EnchantmentTarget.ARMOR, false);


    public static void register() {

        List<Enchantment> enchantsClasses = Arrays.stream(Enchantment.values()).toList();

        boolean registeredGlint = enchantsClasses.contains(VentureEnchants.GLINT);
        boolean registeredGlow = enchantsClasses.contains(VentureEnchants.GLOW);
        boolean registeredVenomousAspect = enchantsClasses.contains(VentureEnchants.VENOMOUS_ASPECT);
        boolean registeredDecay = enchantsClasses.contains(VentureEnchants.DECAY);
        boolean registeredThunderStrike = enchantsClasses.contains(VentureEnchants.THUNDER_STRIKE);
        boolean registeredMagicProtection = enchantsClasses.contains(VentureEnchants.MAGIC_PROTECTION);
        boolean registeredToughness = enchantsClasses.contains(VentureEnchants.TOUGHNESS);
        boolean registeredManaPool = enchantsClasses.contains(MANA_POOL);
        boolean registeredMeleeDamage = enchantsClasses.contains(MELEE_DAMAGE);
        boolean registeredMagicDamage = enchantsClasses.contains(MAGIC_DAMAGE);
        boolean registeredRangeDamage = enchantsClasses.contains(RANGE_DAMAGE);
        boolean registeredHealth = enchantsClasses.contains(HEALTH);
        boolean registeredNightVision = enchantsClasses.contains(NIGHT_VISION);
        boolean registeredSpeed = enchantsClasses.contains(SPEED);
        boolean registeredReach = enchantsClasses.contains(REACH);
        boolean registeredMiner = enchantsClasses.contains(ARMOR_MINING_FORTUNE);
        boolean registeredForager = enchantsClasses.contains(ARMOR_FORAGING_FORTUNE);
        boolean registeredFisherman = enchantsClasses.contains(ARMOR_FISHING_FORTUNE);

        if (!registeredGlint) {
            registerEnchantment(GLINT);
        }
        if (!registeredGlow) {
            registerEnchantment(GLOW);
        }
        if (!registeredVenomousAspect) {
            registerEnchantment(VENOMOUS_ASPECT);
        }
        if (!registeredDecay) {
            registerEnchantment(DECAY);
        }
        if (!registeredThunderStrike) {
            registerEnchantment(THUNDER_STRIKE);
        }
        if (!registeredMagicProtection) {
            registerEnchantment(MAGIC_PROTECTION);
        }
        if (!registeredToughness) {
            registerEnchantment(TOUGHNESS);
        }
        if (!registeredManaPool) {
            registerEnchantment(MANA_POOL);
        }
        if (!registeredMeleeDamage) {
            registerEnchantment(MELEE_DAMAGE);
        }
        if (!registeredMagicDamage) {
            registerEnchantment(MAGIC_DAMAGE);
        }
        if (!registeredRangeDamage) {
            registerEnchantment(RANGE_DAMAGE);
        }
        if (!registeredHealth)
        {
            registerEnchantment(HEALTH);
        }
        if (!registeredSpeed)
        {
            registerEnchantment(SPEED);
        }
        if (!registeredNightVision)
        {
            registerEnchantment(NIGHT_VISION);
        }
        if (!registeredReach)
        {
            registerEnchantment(REACH);
        }
        if (!registeredMiner)
        {
            registerEnchantment(ARMOR_MINING_FORTUNE);
        }
        if(!registeredForager)
        {
            registerEnchantment(ARMOR_FORAGING_FORTUNE);
        }
        if (!registeredFisherman)
        {
            registerEnchantment(ARMOR_FISHING_FORTUNE);
        }

        EnchantmentNames.put(Enchantment.PROTECTION_ENVIRONMENTAL, "Protection");
        EnchantmentNames.put(Enchantment.PROTECTION_FIRE, "Fire Protection");
        EnchantmentNames.put(Enchantment.PROTECTION_FALL, "Feather Falling");
        EnchantmentNames.put(Enchantment.PROTECTION_EXPLOSIONS, "Blast Protection");
        EnchantmentNames.put(Enchantment.PROTECTION_PROJECTILE, "Projectile Protection");
        EnchantmentNames.put(Enchantment.OXYGEN, "Respiration");
        EnchantmentNames.put(Enchantment.WATER_WORKER, "Aqua Affinity");
        EnchantmentNames.put(Enchantment.THORNS, "Thorns");
        EnchantmentNames.put(Enchantment.DEPTH_STRIDER, "Depth Strider");
        EnchantmentNames.put(Enchantment.FROST_WALKER, "Frost Walker");
        EnchantmentNames.put(Enchantment.BINDING_CURSE, "Curse of Binding");
        EnchantmentNames.put(Enchantment.DAMAGE_ALL, "Sharpness");
        EnchantmentNames.put(Enchantment.DAMAGE_UNDEAD, "Smite");
        EnchantmentNames.put(Enchantment.DAMAGE_ARTHROPODS, "Bane of Arthropods");
        EnchantmentNames.put(Enchantment.KNOCKBACK, "Knockback");
        EnchantmentNames.put(Enchantment.FIRE_ASPECT, "Fire Aspect");
        EnchantmentNames.put(Enchantment.LOOT_BONUS_MOBS, "Looting");
        EnchantmentNames.put(Enchantment.SWEEPING_EDGE, "Sweeping Edge");
        EnchantmentNames.put(Enchantment.DIG_SPEED, "Efficiency");
        EnchantmentNames.put(Enchantment.SILK_TOUCH, "Silk Touch");
        EnchantmentNames.put(Enchantment.DURABILITY, "Unbreaking");
        EnchantmentNames.put(Enchantment.LOOT_BONUS_BLOCKS, "Fortune");
        EnchantmentNames.put(Enchantment.ARROW_DAMAGE, "Power");
        EnchantmentNames.put(Enchantment.ARROW_KNOCKBACK, "Punch");
        EnchantmentNames.put(Enchantment.ARROW_FIRE, "Flame");
        EnchantmentNames.put(Enchantment.ARROW_INFINITE, "Infinity");
        EnchantmentNames.put(Enchantment.LUCK, "Luck");
        EnchantmentNames.put(Enchantment.LURE, "Lure");
        EnchantmentNames.put(Enchantment.LOYALTY, "Loyalty");
        EnchantmentNames.put(Enchantment.IMPALING, "Impaling");
        EnchantmentNames.put(Enchantment.RIPTIDE, "Riptide");
        EnchantmentNames.put(Enchantment.CHANNELING, "Channeling");
        EnchantmentNames.put(Enchantment.MULTISHOT, "Multishot");
        EnchantmentNames.put(Enchantment.QUICK_CHARGE, "Quick Charge");
        EnchantmentNames.put(Enchantment.PIERCING, "Piercing");
        EnchantmentNames.put(Enchantment.MENDING, "Mending");
        EnchantmentNames.put(Enchantment.VANISHING_CURSE, "Curse of Vanishing");
        EnchantmentNames.put(Enchantment.SOUL_SPEED, "Soul Speed");
        EnchantmentNames.put(Enchantment.SWIFT_SNEAK, "Swift Sneak");

        for (Enchantment enchantment : ventureEnchants)
        {
            EnchantmentNames.put(enchantment, enchantment.getName());
        }
    }

    public static void registerEnchantment(Enchantment enchantment) {
        boolean registered = true;
        try {
            Field field = Enchantment.class.getDeclaredField("acceptingNew");
            field.setAccessible(true);
            field.set(null, true);
            Enchantment.registerEnchantment(enchantment);
            ventureEnchants.add(enchantment);
        } catch (Exception e) {
            registered = false;
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ERROR WHILE REGISTERING " + enchantment.getName() + "!");
            e.printStackTrace();
        }
        if (registered) {
            // send message to console
            String enchantmentName = enchantment.getName();
            Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + enchantmentName + " Enchantment registered!");
        }
    }

    public static void unregisterEnchantments() {

        try {
            Field keyField = Enchantment.class.getDeclaredField("byKey");
            keyField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);

            for (Enchantment enchantment : ventureEnchants) {
                if (byKey.containsKey(enchantment.getKey())) {
                    byKey.remove(enchantment.getKey());
                }
            }

            Field nameField = Enchantment.class.getDeclaredField("byName");
            nameField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);

            for (Enchantment enchantment : ventureEnchants) {
                if (byName.containsKey(enchantment.getName())) {
                    byName.remove(enchantment.getName());
                }
            }

            Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "Unregistering Enchantments");

        } catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ERROR WHILE UNREGISTERING ENCHANTMENTS");
            exception.printStackTrace();
        }
    }

    public static HashMap<Integer, Enchantment> playerHeadEnchantmentTable = new HashMap<>();
    static {
        playerHeadEnchantmentTable.put(1, Enchantment.PROTECTION_ENVIRONMENTAL);
        playerHeadEnchantmentTable.put(2, Enchantment.PROTECTION_FIRE);
        playerHeadEnchantmentTable.put(3, Enchantment.PROTECTION_EXPLOSIONS);
        playerHeadEnchantmentTable.put(4, Enchantment.PROTECTION_PROJECTILE);
        playerHeadEnchantmentTable.put(5, Enchantment.THORNS);
        playerHeadEnchantmentTable.put(6, Enchantment.OXYGEN);
        playerHeadEnchantmentTable.put(7, Enchantment.WATER_WORKER);

        // Treasure Enchants & Curses
        playerHeadEnchantmentTable.put(8, Enchantment.MENDING);
        playerHeadEnchantmentTable.put(9, Enchantment.VANISHING_CURSE);

        // These enchants aren't included because player head is unbreakable
        //playerHeadEnchants.add(Enchantment.DURABILITY);
        //playerHeadEnchants.add(Enchantment.BINDING_CURSE);

    }

    public static List<Enchantment> playerHeadEnchants = new ArrayList<>();
    static {
        playerHeadEnchants.add(Enchantment.PROTECTION_ENVIRONMENTAL);
        playerHeadEnchants.add(Enchantment.PROTECTION_FIRE);
        playerHeadEnchants.add(Enchantment.PROTECTION_EXPLOSIONS);
        playerHeadEnchants.add(Enchantment.PROTECTION_PROJECTILE);
        playerHeadEnchants.add(Enchantment.THORNS);
        playerHeadEnchants.add(Enchantment.WATER_WORKER);
        playerHeadEnchants.add(Enchantment.OXYGEN);
        playerHeadEnchants.add(Enchantment.WATER_WORKER);

        playerHeadEnchants.add(Enchantment.MENDING);
        playerHeadEnchants.add(Enchantment.VANISHING_CURSE);
    }

    public static List<Enchantment> aspectConflicting() {


        List<Enchantment> conflicting = new ArrayList<>();
        conflicting.add(GLOW);
        conflicting.add(VENOMOUS_ASPECT);
        conflicting.add(DECAY);
        conflicting.add(Enchantment.FIRE_ASPECT);

        return conflicting;
    }

    public static List<Enchantment> thunderStrikeConflicting() {

        List<Enchantment> conflicting = new ArrayList<>();
        conflicting.add(THUNDER_STRIKE);
        return conflicting;
    }

    public static List<Enchantment> protectionConflicting() {

        List<Enchantment> conflicting = new ArrayList<>();
        conflicting.add(MAGIC_PROTECTION);
        conflicting.add(Enchantment.PROTECTION_ENVIRONMENTAL);
        conflicting.add(Enchantment.PROTECTION_FIRE);
        conflicting.add(Enchantment.PROTECTION_EXPLOSIONS);
        conflicting.add(Enchantment.PROTECTION_PROJECTILE);
        return conflicting;
    }

    public static List<Enchantment> damageConflicting() {

        List<Enchantment> conflicting = new ArrayList<>();
        conflicting.add(MELEE_DAMAGE);
        conflicting.add(MAGIC_DAMAGE);
        conflicting.add(RANGE_DAMAGE);
        return conflicting;
    }

    public static List<String> enchantLore(Map<Enchantment, Integer> enchants) {

        List<String> enchantLore = new ArrayList<>();
        String enchantString;
        for (Enchantment enchantment : enchants.keySet()) {
            String enchantmentName = VentureEnchants.EnchantmentNames.get(enchantment);
            int enchantLvl = enchants.get(enchantment);

            if (enchantLvl == enchantment.getMaxLevel()) {
                // Enchant is Max Level

                // Check if is curse enchantment
                if (enchantment.isCursed()) {
                    enchantString = ChatColor.RED + enchantmentName + " " + enchantLvl;
                } else {
                    enchantString = ChatColor.GOLD + enchantmentName + " " + enchantLvl;
                }

            } else if (enchantLvl > enchantment.getMaxLevel()) {
                // Enchant is over Max Level

                // Check if is curse enchantment
                if (enchantment.isCursed()) {
                    enchantString = ChatColor.RED + enchantmentName + " " + enchantLvl;
                } else {
                    enchantString = ChatColor.LIGHT_PURPLE + enchantmentName + " " + enchantLvl;
                }

            } else if (enchantLvl > enchantment.getStartLevel() && enchantLvl < enchantment.getMaxLevel()) {
                // Enchant is greater than start level, but lower than the max level

                // Check if is curse enchantment
                if (enchantment.isCursed()) {
                    enchantString = ChatColor.RED + enchantmentName + " " + enchantLvl;
                } else {
                    enchantString = ChatColor.DARK_PURPLE + enchantmentName + " " + enchantLvl;
                }

            } else {
                // Enchantment is the start level

                // Check if is curse enchantment
                if (enchantment.isCursed()) {
                    enchantString = ChatColor.RED + enchantmentName + " " + enchantLvl;
                } else {
                    enchantString = ChatColor.GRAY + enchantmentName + " " + enchantLvl;
                }
            }
            enchantLore.add(enchantString);
        }

        return enchantLore;
    }

    public enum EnchantEnum {

        PROTECTION (Enchantment.PROTECTION_ENVIRONMENTAL, "protection"),
        FIRE_PROTECTION (Enchantment.PROTECTION_FIRE, "fire_protection"),
        FEATHER_FALLING (Enchantment.PROTECTION_FALL, "feather_falling"),
        BLAST_PROTECTION (Enchantment.PROTECTION_EXPLOSIONS, "blast_protection"),
        RESPIRATION (Enchantment.OXYGEN, "respiration"),
        AQUA_AFFINITY (Enchantment.WATER_WORKER, "aqua_affinity"),
        THORNS (Enchantment.THORNS, "thorns"),
        DEPTH_STRIDER (Enchantment.DEPTH_STRIDER, "depth_strider"),
        FROST_WALKER (Enchantment.FROST_WALKER, "frost_walker"),
        CURSE_OF_BINDING (Enchantment.BINDING_CURSE, "curse_of_binding"),
        SHARPNESS (Enchantment.DAMAGE_ALL, "sharpness"),
        SMITE (Enchantment.DAMAGE_UNDEAD, "smite"),
        BANE_OF_ARTHROPODS (Enchantment.DAMAGE_ARTHROPODS, "bane_of_arthropods"),
        KNOCKBACK (Enchantment.KNOCKBACK, "knockback"),
        FIRE_ASPECT (Enchantment.FIRE_ASPECT, "fire_aspect"),
        LOOTING (Enchantment.LOOT_BONUS_MOBS, "looting"),
        SWEEPING_EDGE (Enchantment.SWEEPING_EDGE, "sweeping_edge"),
        EFFICIENCY (Enchantment.DIG_SPEED, "efficiency"),
        SILK_TOUCH (Enchantment.SILK_TOUCH, "silk_touch"),
        DURABILITY (Enchantment.DURABILITY, "unbreaking"),
        FORTUNE (Enchantment.LOOT_BONUS_BLOCKS, "fortune"),
        POWER (Enchantment.ARROW_DAMAGE, "power"),
        PUNCH (Enchantment.ARROW_KNOCKBACK, "punch"),
        FLAME (Enchantment.ARROW_FIRE, "flame"),
        INFINITY (Enchantment.ARROW_INFINITE, "infinity"),
        LUCK (Enchantment.LUCK, "luck"),
        LURE (Enchantment.LURE, "lure"),
        LOYALTY (Enchantment.LOYALTY, "loyalty"),
        IMPALING (Enchantment.IMPALING, "impaling"),
        RIPTIDE (Enchantment.RIPTIDE, "riptide"),
        CHANNELING (Enchantment.CHANNELING, "channeling"),
        MULTISHOT (Enchantment.MULTISHOT, "multishot"),
        QUICK_CHARGE (Enchantment.QUICK_CHARGE, "quick_charge"),
        PIERCING (Enchantment.PIERCING, "piercing"),
        MENDING (Enchantment.MENDING, "mending"),
        CURSE_OF_VANISHING (Enchantment.VANISHING_CURSE, "curse_of_vanishing"),
        SOUL_SPEED (Enchantment.SOUL_SPEED, "soul_speed"),
        SWIFT_SNEAK (Enchantment.SWIFT_SNEAK, "swift_sneak"),
        GLOW (VentureEnchants.GLOW, "glow"),
        VENOMOUS_ASPECT (VentureEnchants.VENOMOUS_ASPECT, "venomous_aspect"),
        DECAY (VentureEnchants.DECAY, "decay"),
        THUNDER_STRIKE (VentureEnchants.THUNDER_STRIKE, "thunder_strike"),
        MAGIC_PROTECTION (VentureEnchants.MAGIC_PROTECTION, "magic_protection"),
        TOUGHNESS (VentureEnchants.TOUGHNESS, "toughness"),
        MANA_POOL (VentureEnchants.MANA_POOL, "mana_pool"),
        STRENGTH (VentureEnchants.MELEE_DAMAGE, "strength"),
        ARCANE (VentureEnchants.MAGIC_DAMAGE, "arcane"),
        ARCHER (VentureEnchants.RANGE_DAMAGE, "archer"),
        HEALTH (VentureEnchants.HEALTH, "health"),
        NIGHT_VISION (VentureEnchants.NIGHT_VISION, "night_vision"),
        SPEED (VentureEnchants.SPEED, "speed"),
        REACH (VentureEnchants.REACH, "reach"),
        ARMOR_MINING_FORTUNE (VentureEnchants.ARMOR_MINING_FORTUNE, "miner"),
        ARMOR_FORAGING_FORTUNE (VentureEnchants.ARMOR_FORAGING_FORTUNE, "forager"),
        ARMOR_FISHING_FORTUNE (VentureEnchants.ARMOR_FISHING_FORTUNE, "fishing");


        private final Enchantment enchantment;
        private final String commandName;

        EnchantEnum(Enchantment enchantment, String commandName) {
            this.enchantment = enchantment;
            this.commandName = commandName;
        }

        public Enchantment getEnchantment() {
            return enchantment;
        }

        public String getCommandName() {
            return commandName;
        }

        public static Enchantment of(String inputName) {
            for (EnchantEnum enchantEnum : values()) {
                if (enchantEnum.commandName.equals(inputName)) {
                    return enchantEnum.enchantment;
                }
            }
            throw new IllegalArgumentException(ChatColor.RED + "Not an enchantment: " + inputName);
        }
    }
}
