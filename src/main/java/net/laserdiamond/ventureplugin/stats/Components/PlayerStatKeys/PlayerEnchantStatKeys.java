package net.laserdiamond.ventureplugin.stats.Components.PlayerStatKeys;

import net.laserdiamond.ventureplugin.enchants.Components.EnchantStats;
import net.laserdiamond.ventureplugin.stats.Components.*;
import org.jetbrains.annotations.NotNull;

public enum PlayerEnchantStatKeys {

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

    public static void add(@NotNull StatProfile statProfile, @NotNull PlayerEnchantStatKeys playerEnchantStatKeys, double amount)
    {
        EnchantStats enchantStats = statProfile.enchantStats();

        switch (playerEnchantStatKeys)
        {
            case DEFENSE -> {
                enchantStats.setDefense(enchantStats.getDefense() + amount);
                PlayerStatKeys.add(statProfile, PlayerStatKeys.DEFENSE, amount);
            }
            case FIRE_DEFENSE -> {
                enchantStats.setFireDefense(enchantStats.getFireDefense() + amount);
                PlayerStatKeys.add(statProfile, PlayerStatKeys.FIRE_DEFENSE, amount);
            }
            case EXPLOSION_DEFENSE -> {
                enchantStats.setExplosionDefense(enchantStats.getExplosionDefense() + amount);
                PlayerStatKeys.add(statProfile, PlayerStatKeys.EXPLOSION_DEFENSE, amount);
            }
            case PROJECTILE_DEFENSE -> {
                enchantStats.setProjectileDefense(enchantStats.getProjectileDefense() + amount);
                PlayerStatKeys.add(statProfile, PlayerStatKeys.PROJECTILE_DEFENSE, amount);
            }
            case MAGIC_DEFENSE -> {
                enchantStats.setMagicDefense(enchantStats.getMagicDefense() + amount);
                PlayerStatKeys.add(statProfile, PlayerStatKeys.MAGIC_DEFENSE, amount);
            }
            case TOUGHNESS -> {
                enchantStats.setToughness(enchantStats.getToughness() + amount);
                PlayerStatKeys.add(statProfile, PlayerStatKeys.TOUGHNESS, amount);
            }
            case BASE_MELEE -> {
                enchantStats.setBaseMelee(enchantStats.getBaseMelee() + amount);
                PlayerStatKeys.add(statProfile, PlayerStatKeys.BASE_MELEE, amount);
            }
            case BASE_MAGIC -> {
                enchantStats.setBaseMagic(enchantStats.getBaseMagic() + amount);
                PlayerStatKeys.add(statProfile, PlayerStatKeys.BASE_MAGIC, amount);
            }
            case BASE_RANGE -> {
                enchantStats.setBaseRange(enchantStats.getBaseRange() + amount);
                PlayerStatKeys.add(statProfile, PlayerStatKeys.BASE_RANGE, amount);
            }
            case MANA -> {
                enchantStats.setMana(enchantStats.getMana() + amount);
                PlayerStatKeys.add(statProfile, PlayerStatKeys.MANA, amount);
            }
            case HEALTH -> {
                enchantStats.setHealth(enchantStats.getHealth() + amount);
                PlayerStatKeys.add(statProfile, PlayerStatKeys.HEALTH, amount);
            }
            case REACH -> {
                enchantStats.setReach(enchantStats.getReach() + amount);
                PlayerStatKeys.add(statProfile, PlayerStatKeys.REACH, amount);
            }
            case SPEED -> {
                enchantStats.setSpeed(enchantStats.getSpeed() + amount);
                PlayerStatKeys.add(statProfile, PlayerStatKeys.SPEED, amount);
            }
            case MOB_LOOT -> {
                enchantStats.setMobLoot(enchantStats.getMobLoot() + amount);
                PlayerStatKeys.add(statProfile, PlayerStatKeys.MOB_LOOT, amount);
            }
            case ORE_LOOT -> {
                enchantStats.setOreLoot(enchantStats.getOreLoot() + amount);
                PlayerStatKeys.add(statProfile, PlayerStatKeys.ORE_LOOT, amount);
            }
            case WOOD_LOOT -> {
                enchantStats.setWoodLoot(enchantStats.getWoodLoot() + amount);
                PlayerStatKeys.add(statProfile, PlayerStatKeys.WOOD_LOOT, amount);
            }
            case DIG_LOOT -> {
                enchantStats.setDigLoot(enchantStats.getDigLoot() + amount);
                PlayerStatKeys.add(statProfile, PlayerStatKeys.FARMING_LOOT, amount);
            }
            case FISHING_LOOT -> {
                enchantStats.setFishingLoot(enchantStats.getFishingLoot() + amount);
                PlayerStatKeys.add(statProfile, PlayerStatKeys.FISHING_LOOT, amount);
            }
        }
    }

    public static void remove(@NotNull StatProfile statProfile, @NotNull PlayerEnchantStatKeys playerEnchantStatKeys, double amount)
    {
        EnchantStats enchantStats = statProfile.enchantStats();

        switch (playerEnchantStatKeys)
        {
            case DEFENSE -> {
                enchantStats.setDefense(enchantStats.getDefense() - amount);
                PlayerStatKeys.remove(statProfile, PlayerStatKeys.DEFENSE, amount);
            }
            case FIRE_DEFENSE -> {
                enchantStats.setFireDefense(enchantStats.getFireDefense() - amount);
                PlayerStatKeys.remove(statProfile, PlayerStatKeys.FIRE_DEFENSE, amount);
            }
            case EXPLOSION_DEFENSE -> {
                enchantStats.setExplosionDefense(enchantStats.getExplosionDefense() - amount);
                PlayerStatKeys.remove(statProfile, PlayerStatKeys.EXPLOSION_DEFENSE, amount);
            }
            case PROJECTILE_DEFENSE -> {
                enchantStats.setProjectileDefense(enchantStats.getProjectileDefense() - amount);
                PlayerStatKeys.remove(statProfile, PlayerStatKeys.PROJECTILE_DEFENSE, amount);
            }
            case MAGIC_DEFENSE -> {
                enchantStats.setMagicDefense(enchantStats.getMagicDefense() - amount);
                PlayerStatKeys.remove(statProfile, PlayerStatKeys.MAGIC_DEFENSE, amount);
            }
            case TOUGHNESS -> {
                enchantStats.setToughness(enchantStats.getToughness() - amount);
                PlayerStatKeys.remove(statProfile, PlayerStatKeys.TOUGHNESS, amount);
            }
            case BASE_MELEE -> {
                enchantStats.setBaseMelee(enchantStats.getBaseMelee() - amount);
                PlayerStatKeys.remove(statProfile, PlayerStatKeys.BASE_MELEE, amount);
            }
            case BASE_MAGIC -> {
                enchantStats.setBaseMagic(enchantStats.getBaseMagic() - amount);
                PlayerStatKeys.remove(statProfile, PlayerStatKeys.BASE_MAGIC, amount);
            }
            case BASE_RANGE -> {
                enchantStats.setBaseRange(enchantStats.getBaseRange() - amount);
                PlayerStatKeys.remove(statProfile, PlayerStatKeys.BASE_RANGE, amount);
            }
            case MANA -> {
                enchantStats.setMana(enchantStats.getMana() - amount);
                PlayerStatKeys.remove(statProfile, PlayerStatKeys.MANA, amount);
            }
            case HEALTH -> {
                enchantStats.setHealth(enchantStats.getHealth() - amount);
                PlayerStatKeys.remove(statProfile, PlayerStatKeys.HEALTH, amount);
            }
            case REACH -> {
                enchantStats.setReach(enchantStats.getReach() - amount);
                PlayerStatKeys.remove(statProfile, PlayerStatKeys.REACH, amount);
            }
            case SPEED -> {
                enchantStats.setSpeed(enchantStats.getSpeed() - amount);
                PlayerStatKeys.remove(statProfile, PlayerStatKeys.SPEED, amount);
            }
            case MOB_LOOT -> {
                enchantStats.setMobLoot(enchantStats.getMobLoot() - amount);
                PlayerStatKeys.remove(statProfile, PlayerStatKeys.MOB_LOOT, amount);
            }
            case ORE_LOOT -> {
                enchantStats.setOreLoot(enchantStats.getOreLoot() - amount);
                PlayerStatKeys.remove(statProfile, PlayerStatKeys.ORE_LOOT, amount);
            }
            case WOOD_LOOT -> {
                enchantStats.setWoodLoot(enchantStats.getWoodLoot() - amount);
                PlayerStatKeys.remove(statProfile, PlayerStatKeys.WOOD_LOOT, amount);
            }
            case DIG_LOOT -> {
                enchantStats.setDigLoot(enchantStats.getDigLoot() - amount);
                PlayerStatKeys.remove(statProfile, PlayerStatKeys.FARMING_LOOT, amount);
            }
            case FISHING_LOOT -> {
                enchantStats.setFishingLoot(enchantStats.getFishingLoot() - amount);
                PlayerStatKeys.remove(statProfile, PlayerStatKeys.FISHING_LOOT, amount);
            }
        }
    }
}
