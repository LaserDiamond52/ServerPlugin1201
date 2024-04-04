package net.laserdiamond.ventureplugin.skills.Components;

public class SkillsLevel {

    private int combatLevel;
    private int miningLevel;
    private int foragingLevel;
    private int farmingLevel;
    private int enchantingLevel;
    private int fishingLevel;
    private int brewingLevel;

    public SkillsLevel(int combatLevel, int miningLevel, int foragingLevel, int farmingLevel, int enchantingLevel, int fishingLevel, int brewingLevel)
    {
        this.combatLevel = combatLevel;
        this.miningLevel = miningLevel;
        this.foragingLevel = foragingLevel;
        this.farmingLevel = farmingLevel;
        this.enchantingLevel = enchantingLevel;
        this.fishingLevel = fishingLevel;
        this.brewingLevel = brewingLevel;
    }

    public int getCombatLevel() {
        return combatLevel;
    }

    public void setCombatLevel(int combatLevel) {
        this.combatLevel = combatLevel;
    }

    public int getMiningLevel() {
        return miningLevel;
    }

    public void setMiningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
    }

    public int getForagingLevel() {
        return foragingLevel;
    }

    public void setForagingLevel(int foragingLevel) {
        this.foragingLevel = foragingLevel;
    }

    public int getFarmingLevel() {
        return farmingLevel;
    }

    public void setFarmingLevel(int farmingLevel) {
        this.farmingLevel = farmingLevel;
    }

    public int getEnchantingLevel() {
        return enchantingLevel;
    }

    public void setEnchantingLevel(int enchantingLevel) {
        this.enchantingLevel = enchantingLevel;
    }

    public int getFishingLevel() {
        return fishingLevel;
    }

    public void setFishingLevel(int fishingLevel) {
        this.fishingLevel = fishingLevel;
    }

    public int getBrewingLevel() {
        return brewingLevel;
    }

    public void setBrewingLevel(int brewingLevel) {
        this.brewingLevel = brewingLevel;
    }
}
