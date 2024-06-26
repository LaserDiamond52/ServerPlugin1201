package net.laserdiamond.ventureplugin.stats.Components.PlayerStatKeys;

import net.laserdiamond.ventureplugin.items.armor.trims.Components.ArmorTrimMaterialStats;
import net.laserdiamond.ventureplugin.stats.Components.DamageStats;
import net.laserdiamond.ventureplugin.stats.Components.DefenseStats;
import net.laserdiamond.ventureplugin.stats.Components.StatProfile;
import net.laserdiamond.ventureplugin.stats.Components.Stats;
import org.jetbrains.annotations.NotNull;

public enum PlayerTrimMaterialStatKeys {

    COPPER,
    GOLD,
    IRON,
    LAPIS,
    QUARTZ,
    REDSTONE,
    EMERALD,
    AMETHYST,
    DIAMOND,
    NETHERITE;

    public static void add(@NotNull StatProfile statProfile, @NotNull PlayerTrimMaterialStatKeys materialStatKey, double amount)
    {
        Stats stats = statProfile.stats();
        DamageStats damageStats = statProfile.damageStats();
        DefenseStats defenseStats = statProfile.defenseStats();
        ArmorTrimMaterialStats trimMaterialStats = statProfile.armorTrimStats().armorTrimMaterialStats();

        switch (materialStatKey)
        {
            case COPPER -> {
                trimMaterialStats.setCopperSpeed(trimMaterialStats.getCopperSpeed() + amount);
                stats.setSpeed(stats.getSpeed() + amount);
            }
            case GOLD -> trimMaterialStats.setGoldSaturationChance(trimMaterialStats.getGoldSaturationChance() + amount);
            case IRON -> {
                trimMaterialStats.setIronHealthBoost(trimMaterialStats.getIronHealthBoost() + amount);
                stats.setHealth(stats.getHealth() + amount);
            }
            case LAPIS -> trimMaterialStats.setLapisBonusExp(trimMaterialStats.getLapisBonusExp() + amount);
            case QUARTZ -> trimMaterialStats.setQuartzBonusMiningExp(trimMaterialStats.getQuartzBonusMiningExp() + amount);
            case REDSTONE -> trimMaterialStats.setRedstoneLongevity(trimMaterialStats.getRedstoneLongevity() + amount);
            case EMERALD -> {
                trimMaterialStats.setEmeraldBonusLuck(trimMaterialStats.getEmeraldBonusLuck() + amount);
                stats.setLuck(stats.getLuck() + amount);
            }
            case AMETHYST -> {
                trimMaterialStats.setAmethystBonusDamage(trimMaterialStats.getAmethystBonusDamage() + amount);
                damageStats.setPercentMeleeDmg(damageStats.getPercentMelee() + amount);
                damageStats.setPercentMagicDmg(damageStats.getPercentMagic() + amount);
                damageStats.setPercentRangeDmg(damageStats.getPercentRange() + amount);
            }
            case DIAMOND -> {
                trimMaterialStats.setDiamondBonusMana(trimMaterialStats.getDiamondBonusMana() + amount);
                stats.setMaxMana(stats.getMaxMana() + amount);
            }
            case NETHERITE -> {
                trimMaterialStats.setNetheriteBonusDefense(trimMaterialStats.getNetheriteBonusDefense() + amount);
                trimMaterialStats.setNetheriteBonusFireDefense(trimMaterialStats.getNetheriteBonusFireDefense() + amount);
                defenseStats.setDefense(defenseStats.getDefense() + amount);
                defenseStats.setFireDefense(defenseStats.getFireDefense() + amount);
            }


        }
    }

    public static void remove(@NotNull StatProfile statProfile, @NotNull PlayerTrimMaterialStatKeys materialStatKey, double amount)
    {
        Stats stats = statProfile.stats();
        DamageStats damageStats = statProfile.damageStats();
        DefenseStats defenseStats = statProfile.defenseStats();
        ArmorTrimMaterialStats trimMaterialStats = statProfile.armorTrimStats().armorTrimMaterialStats();

        switch (materialStatKey)
        {
            case COPPER -> {
                trimMaterialStats.setCopperSpeed(trimMaterialStats.getCopperSpeed() - amount);
                stats.setSpeed(stats.getSpeed() - amount);
            }
            case GOLD -> trimMaterialStats.setGoldSaturationChance(trimMaterialStats.getGoldSaturationChance() - amount);
            case IRON -> {
                trimMaterialStats.setIronHealthBoost(trimMaterialStats.getIronHealthBoost() - amount);
                stats.setHealth(stats.getHealth() - amount);
            }
            case LAPIS -> trimMaterialStats.setLapisBonusExp(trimMaterialStats.getLapisBonusExp() - amount);
            case QUARTZ -> trimMaterialStats.setQuartzBonusMiningExp(trimMaterialStats.getQuartzBonusMiningExp() - amount);
            case REDSTONE -> trimMaterialStats.setRedstoneLongevity(trimMaterialStats.getRedstoneLongevity() - amount);
            case EMERALD -> {
                trimMaterialStats.setEmeraldBonusLuck(trimMaterialStats.getEmeraldBonusLuck() - amount);
                stats.setLuck(stats.getLuck() - amount);
            }
            case AMETHYST -> {
                trimMaterialStats.setAmethystBonusDamage(trimMaterialStats.getAmethystBonusDamage() - amount);
                damageStats.setPercentMeleeDmg(damageStats.getPercentMelee() - amount);
                damageStats.setPercentMagicDmg(damageStats.getPercentMagic() - amount);
                damageStats.setPercentRangeDmg(damageStats.getPercentRange() - amount);
            }
            case DIAMOND -> {
                trimMaterialStats.setDiamondBonusMana(trimMaterialStats.getDiamondBonusMana() - amount);
                stats.setMaxMana(stats.getMaxMana() - amount);
            }
            case NETHERITE -> {
                trimMaterialStats.setNetheriteBonusDefense(trimMaterialStats.getNetheriteBonusDefense() - amount);
                trimMaterialStats.setNetheriteBonusFireDefense(trimMaterialStats.getNetheriteBonusFireDefense() - amount);
                defenseStats.setDefense(defenseStats.getDefense() - amount);
                defenseStats.setFireDefense(defenseStats.getFireDefense() - amount);
            }
        }
    }
}
