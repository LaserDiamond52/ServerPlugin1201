package net.laserdiamond.serverplugin1201.stats.Components.PlayerStatKeys;

import net.laserdiamond.serverplugin1201.enchants.Components.EnchantStats;
import net.laserdiamond.serverplugin1201.stats.Components.*;
import org.jetbrains.annotations.NotNull;

public enum PlayerEnchantStats {

    DEFENSE,
    FIRE_DEFENSE,
    EXPLOSION_DEFENSE,
    PROJECTILE_DEFENSE,
    MAGIC_DEFENSE,
    TOUGHNESS,
    BASE_MELEE,
    BASE_MAGIC,
    BASE_RANGE,
    MANA,
    HEALTH,
    REACH,
    SPEED,
    MOB_LOOT,
    ORE_LOOT,
    WOOD_LOOT,
    DIG_LOOT,
    FISHING_LOOT;

    public static void add(@NotNull StatProfile statProfile, @NotNull PlayerEnchantStats playerEnchantStats, double amount, boolean applyToBase)
    {
        EnchantStats enchantStats = statProfile.enchantStats();

        switch (playerEnchantStats)
        {
            case DEFENSE -> {
                enchantStats.setDefense(enchantStats.getDefense() + amount);
                if (applyToBase)
                {
                    PlayerStats.add(statProfile, PlayerStats.DEFENSE, amount);
                }
            }
            case FIRE_DEFENSE -> {
                enchantStats.setFireDefense(enchantStats.getFireDefense() + amount);
                if (applyToBase)
                {
                    PlayerStats.add(statProfile, PlayerStats.FIRE_DEFENSE, amount);
                }
            }
            case EXPLOSION_DEFENSE -> {
                enchantStats.setExplosionDefense(enchantStats.getExplosionDefense() + amount);
                if (applyToBase)
                {
                    PlayerStats.add(statProfile, PlayerStats.EXPLOSION_DEFENSE, amount);
                }
            }
            case PROJECTILE_DEFENSE -> {
                enchantStats.setProjectileDefense(enchantStats.getProjectileDefense() + amount);
                if (applyToBase)
                {
                    PlayerStats.add(statProfile, PlayerStats.PROJECTILE_DEFENSE, amount);
                }
            }
            case MAGIC_DEFENSE -> {
                enchantStats.setMagicDefense(enchantStats.getMagicDefense() + amount);
                if (applyToBase)
                {
                    PlayerStats.add(statProfile, PlayerStats.MAGIC_DEFENSE, amount);
                }
            }
            case TOUGHNESS -> {
                enchantStats.setToughness(enchantStats.getToughness() + amount);
                if (applyToBase)
                {
                    PlayerStats.add(statProfile, PlayerStats.TOUGHNESS, amount);
                }
            }
            case BASE_MELEE -> {
                enchantStats.setBaseMelee(enchantStats.getBaseMelee() + amount);
                if (applyToBase)
                {
                    PlayerStats.add(statProfile, PlayerStats.BASE_MELEE, amount);
                }
            }
            case BASE_MAGIC -> {
                enchantStats.setBaseMagic(enchantStats.getBaseMagic() + amount);
                if (applyToBase)
                {
                    PlayerStats.add(statProfile, PlayerStats.BASE_MAGIC, amount);
                }
            }
            case BASE_RANGE -> {
                enchantStats.setBaseRange(enchantStats.getBaseRange() + amount);
                if (applyToBase)
                {
                    PlayerStats.add(statProfile, PlayerStats.BASE_RANGE, amount);
                }
            }
            case MANA -> {
                enchantStats.setMana(enchantStats.getMana() + amount);
                if (applyToBase)
                {
                    PlayerStats.add(statProfile, PlayerStats.MANA, amount);
                }
            }
            case HEALTH -> {
                enchantStats.setHealth(enchantStats.getHealth() + amount);
                if (applyToBase)
                {
                    PlayerStats.add(statProfile, PlayerStats.HEALTH, amount);
                }
            }
            case REACH -> {
                enchantStats.setReach(enchantStats.getReach() + amount);
                if (applyToBase)
                {
                    PlayerStats.add(statProfile, PlayerStats.REACH, amount);
                }
            }
            case SPEED -> {
                enchantStats.setSpeed(enchantStats.getSpeed() + amount);
                if (applyToBase)
                {
                    PlayerStats.add(statProfile, PlayerStats.SPEED, amount);
                }
            }
            case MOB_LOOT -> {
                enchantStats.setMobLoot(enchantStats.getMobLoot() + amount);
                if (applyToBase)
                {
                    PlayerStats.add(statProfile, PlayerStats.MOB_LOOT, amount);
                }
            }
            case ORE_LOOT -> {
                enchantStats.setOreLoot(enchantStats.getOreLoot() + amount);
                if (applyToBase)
                {
                    PlayerStats.add(statProfile, PlayerStats.ORE_LOOT, amount);
                }
            }
            case WOOD_LOOT -> {
                enchantStats.setWoodLoot(enchantStats.getWoodLoot() + amount);
                if (applyToBase)
                {
                    PlayerStats.add(statProfile, PlayerStats.WOOD_LOOT, amount);
                }
            }
            case DIG_LOOT -> {
                enchantStats.setDigLoot(enchantStats.getDigLoot() + amount);
                if (applyToBase)
                {
                    PlayerStats.add(statProfile, PlayerStats.DIG_LOOT, amount);
                }
            }
            case FISHING_LOOT -> {
                enchantStats.setFishingLoot(enchantStats.getFishingLoot() + amount);
                if (applyToBase)
                {
                    PlayerStats.add(statProfile, PlayerStats.FISHING_LOOT, amount);
                }
            }
        }
    }

    public static void remove(@NotNull StatProfile statProfile, @NotNull PlayerEnchantStats playerEnchantStats, double amount, boolean applyToBase)
    {
        EnchantStats enchantStats = statProfile.enchantStats();

        switch (playerEnchantStats)
        {
            case DEFENSE -> {
                enchantStats.setDefense(enchantStats.getDefense() - amount);
                if (applyToBase)
                {
                    PlayerStats.remove(statProfile, PlayerStats.DEFENSE, amount);
                }
            }
            case FIRE_DEFENSE -> {
                enchantStats.setFireDefense(enchantStats.getFireDefense() - amount);
                if (applyToBase)
                {
                    PlayerStats.remove(statProfile, PlayerStats.FIRE_DEFENSE, amount);
                }
            }
            case EXPLOSION_DEFENSE -> {
                enchantStats.setExplosionDefense(enchantStats.getExplosionDefense() - amount);
                if (applyToBase)
                {
                    PlayerStats.remove(statProfile, PlayerStats.EXPLOSION_DEFENSE, amount);
                }
            }
            case PROJECTILE_DEFENSE -> {
                enchantStats.setProjectileDefense(enchantStats.getProjectileDefense() - amount);
                if (applyToBase)
                {
                    PlayerStats.remove(statProfile, PlayerStats.PROJECTILE_DEFENSE, amount);
                }
            }
            case MAGIC_DEFENSE -> {
                enchantStats.setMagicDefense(enchantStats.getMagicDefense() - amount);
                if (applyToBase)
                {
                    PlayerStats.remove(statProfile, PlayerStats.MAGIC_DEFENSE, amount);
                }
            }
            case TOUGHNESS -> {
                enchantStats.setToughness(enchantStats.getToughness() - amount);
                if (applyToBase)
                {
                    PlayerStats.remove(statProfile, PlayerStats.TOUGHNESS, amount);
                }
            }
            case BASE_MELEE -> {
                enchantStats.setBaseMelee(enchantStats.getBaseMelee() - amount);
                if (applyToBase)
                {
                    PlayerStats.remove(statProfile, PlayerStats.BASE_MELEE, amount);
                }
            }
            case BASE_MAGIC -> {
                enchantStats.setBaseMagic(enchantStats.getBaseMagic() - amount);
                if (applyToBase)
                {
                    PlayerStats.remove(statProfile, PlayerStats.BASE_MAGIC, amount);
                }
            }
            case BASE_RANGE -> {
                enchantStats.setBaseRange(enchantStats.getBaseRange() - amount);
                if (applyToBase)
                {
                    PlayerStats.remove(statProfile, PlayerStats.BASE_RANGE, amount);
                }
            }
            case MANA -> {
                enchantStats.setMana(enchantStats.getMana() - amount);
                if (applyToBase)
                {
                    PlayerStats.remove(statProfile, PlayerStats.MANA, amount);
                }
            }
            case HEALTH -> {
                enchantStats.setHealth(enchantStats.getHealth() - amount);
                if (applyToBase)
                {
                    PlayerStats.remove(statProfile, PlayerStats.HEALTH, amount);
                }
            }
            case REACH -> {
                enchantStats.setReach(enchantStats.getReach() - amount);
                if (applyToBase)
                {
                    PlayerStats.remove(statProfile, PlayerStats.REACH, amount);
                }
            }
            case SPEED -> {
                enchantStats.setSpeed(enchantStats.getSpeed() - amount);
                if (applyToBase)
                {
                    PlayerStats.remove(statProfile, PlayerStats.SPEED, amount);
                }
            }
            case MOB_LOOT -> {
                enchantStats.setMobLoot(enchantStats.getMobLoot() - amount);
                if (applyToBase)
                {
                    PlayerStats.remove(statProfile, PlayerStats.MOB_LOOT, amount);
                }
            }
            case ORE_LOOT -> {
                enchantStats.setOreLoot(enchantStats.getOreLoot() - amount);
                if (applyToBase)
                {
                    PlayerStats.remove(statProfile, PlayerStats.ORE_LOOT, amount);
                }
            }
            case WOOD_LOOT -> {
                enchantStats.setWoodLoot(enchantStats.getWoodLoot() - amount);
                if (applyToBase)
                {
                    PlayerStats.remove(statProfile, PlayerStats.WOOD_LOOT, amount);
                }
            }
            case DIG_LOOT -> {
                enchantStats.setDigLoot(enchantStats.getDigLoot() - amount);
                if (applyToBase)
                {
                    PlayerStats.remove(statProfile, PlayerStats.DIG_LOOT, amount);
                }
            }
            case FISHING_LOOT -> {
                enchantStats.setFishingLoot(enchantStats.getFishingLoot() - amount);
                if (applyToBase)
                {
                    PlayerStats.remove(statProfile, PlayerStats.FISHING_LOOT, amount);
                }
            }
        }
    }
}
