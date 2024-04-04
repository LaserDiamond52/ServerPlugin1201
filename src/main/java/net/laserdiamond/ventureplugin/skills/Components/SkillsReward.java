package net.laserdiamond.ventureplugin.skills.Components;

public class SkillsReward {

    private double combatDamageBonus;
    private double miningDefenseBonus;
    private double miningFortuneBonus;
    private double foragingFortuneBonus;
    private double farmingFortuneBonus;
    private double enchantingManaBonus;
    private double fishingLuckBonus;
    private double brewingPotionDurationBonus;

    public SkillsReward(double combatDamageBonus, double miningDefenseBonus, double miningFortuneBonus, double foragingFortuneBonus, double farmingFortuneBonus, double enchantingManaBonus, double fishingLuckBonus, double brewingPotionDurationBonus)
    {
        this.combatDamageBonus = combatDamageBonus;
        this.miningDefenseBonus = miningDefenseBonus;
        this.miningFortuneBonus = miningFortuneBonus;
        this.foragingFortuneBonus = foragingFortuneBonus;
        this.farmingFortuneBonus = farmingFortuneBonus;
        this.enchantingManaBonus = enchantingManaBonus;
        this.fishingLuckBonus = fishingLuckBonus;
        this.brewingPotionDurationBonus = brewingPotionDurationBonus;
    }

    public double getCombatDamageBonus() {
        return combatDamageBonus;
    }

    public void setCombatDamageBonus(double combatDamageBonus) {
        this.combatDamageBonus = combatDamageBonus;
    }

    public double getMiningFortuneBonus() {
        return miningFortuneBonus;
    }

    public void setMiningFortuneBonus(double miningFortuneBonus) {
        this.miningFortuneBonus = miningFortuneBonus;
    }

    public double getForagingFortuneBonus() {
        return foragingFortuneBonus;
    }

    public void setForagingFortuneBonus(double foragingFortuneBonus) {
        this.foragingFortuneBonus = foragingFortuneBonus;
    }

    public double getFarmingFortuneBonus() {
        return farmingFortuneBonus;
    }

    public void setFarmingFortuneBonus(double farmingFortuneBonus) {
        this.farmingFortuneBonus = farmingFortuneBonus;
    }

    public double getEnchantingManaBonus() {
        return enchantingManaBonus;
    }

    public void setEnchantingManaBonus(double enchantingManaBonus) {
        this.enchantingManaBonus = enchantingManaBonus;
    }

    public double getFishingLuckBonus() {
        return fishingLuckBonus;
    }

    public void setFishingLuckBonus(double fishingLuckBonus) {
        this.fishingLuckBonus = fishingLuckBonus;
    }

    public double getBrewingPotionDurationBonus() {
        return brewingPotionDurationBonus;
    }

    public void setBrewingPotionDurationBonus(double brewingPotionDurationBonus) {
        this.brewingPotionDurationBonus = brewingPotionDurationBonus;
    }

    public double getMiningDefenseBonus() {
        return miningDefenseBonus;
    }

    public void setMiningDefenseBonus(double miningDefenseBonus) {
        this.miningDefenseBonus = miningDefenseBonus;
    }
}
