package net.laserdiamond.ventureplugin.stats.Components.PlayerStatKeys;

import net.laserdiamond.ventureplugin.stats.Components.*;
import org.jetbrains.annotations.NotNull;

public enum PlayerStatKeys {

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
    FARMING_LOOT,
    FISHING_LOOT;

    public static void add(@NotNull StatProfile statProfile, @NotNull PlayerStatKeys playerStatKeys, double amount)
    {
        Stats stats = statProfile.stats();
        DamageStats damageStats = statProfile.damageStats();
        DefenseStats defenseStats = statProfile.defenseStats();
        LootStats lootStats = statProfile.lootStats();

        switch (playerStatKeys)
        {
            case HEALTH -> stats.setHealth(stats.getHealth() + amount);
            case SPEED -> stats.setSpeed(stats.getSpeed() + amount);
            case STARVATION -> stats.setStarvationRate((int) (stats.getStarvationRate() + amount));
            case LUCK -> stats.setLuck(stats.getLuck() + amount);
            case MANA -> stats.setMaxMana(stats.getMaxMana() + amount);
            case BASE_MELEE -> damageStats.setBaseMeleeDmg(damageStats.getBaseMelee() + amount);
            case BASE_MAGIC -> damageStats.setBaseMagicDmg(damageStats.getBaseMagic() + amount);
            case BASE_RANGE -> damageStats.setBaseRangeDmg(damageStats.getBaseRange() + amount);
            case PERCENT_MELEE -> damageStats.setPercentMeleeDmg(damageStats.getPercentMelee() + amount);
            case PERCENT_MAGIC -> damageStats.setPercentMagicDmg(damageStats.getPercentMagic() + amount);
            case PERCENT_RANGE -> damageStats.setPercentRangeDmg(damageStats.getPercentRange() + amount);
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
            case FARMING_LOOT -> lootStats.setBonusFarmingLoot(lootStats.getBonusFarmingLoot() + amount);
            case FISHING_LOOT -> lootStats.setFishingLuck(lootStats.getFishingLuck() + amount);
        }
    }

    public static void remove(StatProfile statProfile, PlayerStatKeys playerStatKeys, double amount)
    {
        Stats stats = statProfile.stats();
        DamageStats damageStats = statProfile.damageStats();
        DefenseStats defenseStats = statProfile.defenseStats();
        LootStats lootStats = statProfile.lootStats();

        switch (playerStatKeys)
        {
            case HEALTH -> stats.setHealth(stats.getHealth() - amount);
            case SPEED -> stats.setSpeed(stats.getSpeed() - amount);
            case STARVATION -> stats.setStarvationRate((int) (stats.getStarvationRate() - amount));
            case LUCK -> stats.setLuck(stats.getLuck() - amount);
            case MANA -> stats.setMaxMana(stats.getMaxMana() - amount);
            case BASE_MELEE -> damageStats.setBaseMeleeDmg(damageStats.getBaseMelee() - amount);
            case BASE_MAGIC -> damageStats.setBaseMagicDmg(damageStats.getBaseMagic() - amount);
            case BASE_RANGE -> damageStats.setBaseRangeDmg(damageStats.getBaseRange() - amount);
            case PERCENT_MELEE -> damageStats.setPercentMeleeDmg(damageStats.getPercentMelee() - amount);
            case PERCENT_MAGIC -> damageStats.setPercentMagicDmg(damageStats.getPercentMagic() - amount);
            case PERCENT_RANGE -> damageStats.setPercentRangeDmg(damageStats.getPercentRange() - amount);
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
            case FARMING_LOOT -> lootStats.setBonusFarmingLoot(lootStats.getBonusFarmingLoot() - amount);
            case FISHING_LOOT -> lootStats.setFishingLuck(lootStats.getFishingLuck() - amount);
        }
    }

}
