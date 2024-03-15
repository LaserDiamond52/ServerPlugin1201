package net.laserdiamond.serverplugin1201.stats.Components.PlayerStatKeys;

import net.laserdiamond.serverplugin1201.stats.Components.*;
import org.jetbrains.annotations.NotNull;

public enum PlayerStats {

    HEALTH,
    SPEED,
    STARVATION,
    LUCK,
    MANA,
    BASE_MELEE,
    BASE_MAGIC,
    BASE_RANGE,
    PERCENT_MELEE,
    PERCENT_MAGIC,
    PERCENT_RANGE,
    REACH,
    DEFENSE,
    FIRE_DEFENSE,
    EXPLOSION_DEFENSE,
    PROJECTILE_DEFENSE,
    MAGIC_DEFENSE,
    TOUGHNESS,
    MOB_LOOT,
    ORE_LOOT,
    WOOD_LOOT,
    DIG_LOOT,
    FISHING_LOOT;

    public static void add(@NotNull StatProfile statProfile, @NotNull PlayerStats playerStats, double amount)
    {
        Stats stats = statProfile.stats();
        DamageStats damageStats = statProfile.damageStats();
        DefenseStats defenseStats = statProfile.defenseStats();
        LootStats lootStats = statProfile.lootStats();

        switch (playerStats)
        {
            case HEALTH -> stats.setHealth(stats.getHealth() + amount);
            case SPEED -> stats.setSpeed(stats.getSpeed() + amount);
            case STARVATION -> stats.setStarvationRate((int) (stats.getStarvationRate() + amount));
            case LUCK -> stats.setLuck(stats.getLuck() + amount);
            case MANA -> stats.setMaxMana(stats.getMaxMana() + amount);
            case BASE_MELEE -> damageStats.setbMeleeDmg(damageStats.getbMeleeDmg() + amount);
            case BASE_MAGIC -> damageStats.setbMagicDmg(damageStats.getbMagicDmg() + amount);
            case BASE_RANGE -> damageStats.setbRangeDmg(damageStats.getbRangeDmg() + amount);
            case PERCENT_MELEE -> damageStats.setpMeleeDmg(damageStats.getpMeleeDmg() + amount);
            case PERCENT_MAGIC -> damageStats.setpMagicDmg(damageStats.getpMagicDmg() + amount);
            case PERCENT_RANGE -> damageStats.setpRangeDmg(damageStats.getpRangeDmg() + amount);
            case REACH -> damageStats.setReach(damageStats.getReach() + amount);
            case DEFENSE -> defenseStats.setDefense(defenseStats.getDefense() + amount);
            case FIRE_DEFENSE -> defenseStats.setFireDefense(defenseStats.getFireDefense() + amount);
            case EXPLOSION_DEFENSE -> defenseStats.setExplosionDefense(defenseStats.getExplosionDefense() + amount);
            case PROJECTILE_DEFENSE -> defenseStats.setProjectileDefense(defenseStats.getProjectileDefense() + amount);
            case MAGIC_DEFENSE -> defenseStats.setMagicDefense(defenseStats.getMagicDefense() + amount);
            case TOUGHNESS -> defenseStats.setToughness(defenseStats.getToughness() + amount);
            case MOB_LOOT -> lootStats.setBonusMobLoot(lootStats.getBonusMobLoot() + amount);
            case ORE_LOOT -> lootStats.setBonusOreLoot(lootStats.getBonusOreLoot() + amount);
            case WOOD_LOOT -> lootStats.setBonusWoodLoot(lootStats.getBonusWoodLoot() + amount);
            case DIG_LOOT -> lootStats.setBonusDigLoot(lootStats.getBonusDigLoot() + amount);
            case FISHING_LOOT -> lootStats.setFishingLoot(lootStats.getFishingLoot() + amount);
        }
    }

    public static void remove(StatProfile statProfile, PlayerStats playerStats, double amount)
    {
        Stats stats = statProfile.stats();
        DamageStats damageStats = statProfile.damageStats();
        DefenseStats defenseStats = statProfile.defenseStats();
        LootStats lootStats = statProfile.lootStats();

        switch (playerStats)
        {
            case HEALTH -> stats.setHealth(stats.getHealth() - amount);
            case SPEED -> stats.setSpeed(stats.getSpeed() - amount);
            case STARVATION -> stats.setStarvationRate((int) (stats.getStarvationRate() - amount));
            case LUCK -> stats.setLuck(stats.getLuck() - amount);
            case MANA -> stats.setMaxMana(stats.getMaxMana() - amount);
            case BASE_MELEE -> damageStats.setbMeleeDmg(damageStats.getbMeleeDmg() - amount);
            case BASE_MAGIC -> damageStats.setbMagicDmg(damageStats.getbMagicDmg() - amount);
            case BASE_RANGE -> damageStats.setbRangeDmg(damageStats.getbRangeDmg() - amount);
            case PERCENT_MELEE -> damageStats.setpMeleeDmg(damageStats.getpMeleeDmg() - amount);
            case PERCENT_MAGIC -> damageStats.setpMagicDmg(damageStats.getpMagicDmg() - amount);
            case PERCENT_RANGE -> damageStats.setpRangeDmg(damageStats.getpRangeDmg() - amount);
            case REACH -> damageStats.setReach(damageStats.getReach() - amount);
            case DEFENSE -> defenseStats.setDefense(defenseStats.getDefense() - amount);
            case FIRE_DEFENSE -> defenseStats.setFireDefense(defenseStats.getFireDefense() - amount);
            case EXPLOSION_DEFENSE -> defenseStats.setExplosionDefense(defenseStats.getExplosionDefense() - amount);
            case PROJECTILE_DEFENSE -> defenseStats.setProjectileDefense(defenseStats.getProjectileDefense() - amount);
            case MAGIC_DEFENSE -> defenseStats.setMagicDefense(defenseStats.getMagicDefense() - amount);
            case TOUGHNESS -> defenseStats.setToughness(defenseStats.getToughness() - amount);
            case MOB_LOOT -> lootStats.setBonusMobLoot(lootStats.getBonusMobLoot() - amount);
            case ORE_LOOT -> lootStats.setBonusOreLoot(lootStats.getBonusOreLoot() - amount);
            case WOOD_LOOT -> lootStats.setBonusWoodLoot(lootStats.getBonusWoodLoot() - amount);
            case DIG_LOOT -> lootStats.setBonusDigLoot(lootStats.getBonusDigLoot() - amount);
            case FISHING_LOOT -> lootStats.setFishingLoot(lootStats.getFishingLoot() - amount);
        }
    }

}
