package net.laserdiamond.ventureplugin.skills.Components;

public class SkillsEXP {

    private double totalCombatEXP;
    private double combatExpToNextLevel;
    private double requiredCombatExpToNextLevel;

    private double totalMiningEXP;
    private double miningExpToNextLevel;
    private double requiredMiningExpToNextLevel;

    private double totalForagingEXP;
    private double foragingExpToNextLevel;
    private double requiredForagingExpToNextLevel;

    private double totalFarmingEXP;
    private double farmingExpToNextLevel;
    private double requiredFarmingExpToNextLevel;

    private double totalEnchantingEXP;
    private double enchantingExpToNextLevel;
    private double requiredEnchantingExpToNextLevel;

    private double totalFishingExp;
    private double fishingExpToNextLevel;
    private double requiredFishingExpToNextLevel;

    private double totalBrewingExp;
    private double brewingExpToNextLevel;
    private double requiredBrewingExpToNextLevel;

    public SkillsEXP(double totalCombatEXP, double combatExpToNextLevel, double requiredCombatExpToNextLevel,
                     double totalMiningEXP, double miningExpToNextLevel, double requiredMiningExpToNextLevel,
                     double totalForagingEXP, double foragingExpToNextLevel, double requiredForagingExpToNextLevel,
                     double totalFarmingEXP, double farmingExpToNextLevel, double requiredFarmingExpToNextLevel,
                     double totalEnchantingEXP, double enchantingExpToNextLevel, double requiredEnchantingExpToNextLevel,
                     double totalFishingExp, double fishingExpToNextLevel, double requiredFishingExpToNextLevel,
                     double totalBrewingExp, double brewingExpToNextLevel, double requiredBrewingExpToNextLevel)
    {
        this.totalCombatEXP = totalCombatEXP;
        this.combatExpToNextLevel = combatExpToNextLevel;
        this.requiredCombatExpToNextLevel = requiredCombatExpToNextLevel;

        this.totalMiningEXP = totalMiningEXP;
        this.miningExpToNextLevel = miningExpToNextLevel;
        this.requiredMiningExpToNextLevel = requiredMiningExpToNextLevel;

        this.totalForagingEXP = totalForagingEXP;
        this.foragingExpToNextLevel = foragingExpToNextLevel;
        this.requiredForagingExpToNextLevel = requiredForagingExpToNextLevel;

        this.totalFarmingEXP = totalFarmingEXP;
        this.farmingExpToNextLevel = farmingExpToNextLevel;
        this.requiredFarmingExpToNextLevel = requiredFarmingExpToNextLevel;

        this.totalEnchantingEXP = totalEnchantingEXP;
        this.enchantingExpToNextLevel = enchantingExpToNextLevel;
        this.requiredEnchantingExpToNextLevel = requiredEnchantingExpToNextLevel;

        this.totalFishingExp = totalFishingExp;
        this.fishingExpToNextLevel = fishingExpToNextLevel;
        this.requiredFishingExpToNextLevel = requiredFishingExpToNextLevel;

        this.totalBrewingExp = totalBrewingExp;
        this.brewingExpToNextLevel = brewingExpToNextLevel;
        this.requiredBrewingExpToNextLevel = requiredBrewingExpToNextLevel;
    }

    public double getTotalCombatEXP() {
        return totalCombatEXP;
    }

    public void setTotalCombatEXP(double totalCombatEXP) {
        this.totalCombatEXP = totalCombatEXP;
    }

    public double getCombatExpToNextLevel() {
        return combatExpToNextLevel;
    }

    public void setCombatExpToNextLevel(double combatExpToNextLevel) {
        this.combatExpToNextLevel = combatExpToNextLevel;
    }

    public double getRequiredCombatExpToNextLevel() {
        return requiredCombatExpToNextLevel;
    }

    public void setRequiredCombatExpToNextLevel(double requiredCombatExpToNextLevel) {
        this.requiredCombatExpToNextLevel = requiredCombatExpToNextLevel;
    }

    public double getTotalMiningEXP() {
        return totalMiningEXP;
    }

    public void setTotalMiningEXP(double totalMiningEXP) {
        this.totalMiningEXP = totalMiningEXP;
    }

    public double getMiningExpToNextLevel() {
        return miningExpToNextLevel;
    }

    public void setMiningExpToNextLevel(double miningExpToNextLevel) {
        this.miningExpToNextLevel = miningExpToNextLevel;
    }

    public double getRequiredMiningExpToNextLevel() {
        return requiredMiningExpToNextLevel;
    }

    public void setRequiredMiningExpToNextLevel(double requiredMiningExpToNextLevel) {
        this.requiredMiningExpToNextLevel = requiredMiningExpToNextLevel;
    }

    public double getTotalForagingEXP() {
        return totalForagingEXP;
    }

    public void setTotalForagingEXP(double totalForagingEXP) {
        this.totalForagingEXP = totalForagingEXP;
    }

    public double getForagingExpToNextLevel() {
        return foragingExpToNextLevel;
    }

    public void setForagingExpToNextLevel(double foragingExpToNextLevel) {
        this.foragingExpToNextLevel = foragingExpToNextLevel;
    }

    public double getRequiredForagingExpToNextLevel() {
        return requiredForagingExpToNextLevel;
    }

    public void setRequiredForagingExpToNextLevel(double requiredForagingExpToNextLevel) {
        this.requiredForagingExpToNextLevel = requiredForagingExpToNextLevel;
    }

    public double getTotalFarmingEXP() {
        return totalFarmingEXP;
    }

    public void setTotalFarmingEXP(double totalFarmingEXP) {
        this.totalFarmingEXP = totalFarmingEXP;
    }

    public double getFarmingExpToNextLevel() {
        return farmingExpToNextLevel;
    }

    public void setFarmingExpToNextLevel(double farmingExpToNextLevel) {
        this.farmingExpToNextLevel = farmingExpToNextLevel;
    }

    public double getRequiredFarmingExpToNextLevel() {
        return requiredFarmingExpToNextLevel;
    }

    public void setRequiredFarmingExpToNextLevel(double requiredFarmingExpToNextLevel) {
        this.requiredFarmingExpToNextLevel = requiredFarmingExpToNextLevel;
    }

    public double getTotalEnchantingEXP() {
        return totalEnchantingEXP;
    }

    public void setTotalEnchantingEXP(double totalEnchantingEXP) {
        this.totalEnchantingEXP = totalEnchantingEXP;
    }

    public double getEnchantingExpToNextLevel() {
        return enchantingExpToNextLevel;
    }

    public void setEnchantingExpToNextLevel(double enchantingExpToNextLevel) {
        this.enchantingExpToNextLevel = enchantingExpToNextLevel;
    }

    public double getRequiredEnchantingExpToNextLevel() {
        return requiredEnchantingExpToNextLevel;
    }

    public void setRequiredEnchantingExpToNextLevel(double requiredEnchantingExpToNextLevel) {
        this.requiredEnchantingExpToNextLevel = requiredEnchantingExpToNextLevel;
    }

    public double getTotalFishingExp() {
        return totalFishingExp;
    }

    public void setTotalFishingExp(double totalFishingExp) {
        this.totalFishingExp = totalFishingExp;
    }

    public double getFishingExpToNextLevel() {
        return fishingExpToNextLevel;
    }

    public void setFishingExpToNextLevel(double fishingExpToNextLevel) {
        this.fishingExpToNextLevel = fishingExpToNextLevel;
    }

    public double getRequiredFishingExpToNextLevel() {
        return requiredFishingExpToNextLevel;
    }

    public void setRequiredFishingExpToNextLevel(double requiredFishingExpToNextLevel) {
        this.requiredFishingExpToNextLevel = requiredFishingExpToNextLevel;
    }

    public double getTotalBrewingExp() {
        return totalBrewingExp;
    }

    public void setTotalBrewingExp(double totalBrewingExp) {
        this.totalBrewingExp = totalBrewingExp;
    }

    public double getBrewingExpToNextLevel() {
        return brewingExpToNextLevel;
    }

    public void setBrewingExpToNextLevel(double brewingExpToNextLevel) {
        this.brewingExpToNextLevel = brewingExpToNextLevel;
    }

    public double getRequiredBrewingExpToNextLevel() {
        return requiredBrewingExpToNextLevel;
    }

    public void setRequiredBrewingExpToNextLevel(double requiredBrewingExpToNextLevel) {
        this.requiredBrewingExpToNextLevel = requiredBrewingExpToNextLevel;
    }
}
