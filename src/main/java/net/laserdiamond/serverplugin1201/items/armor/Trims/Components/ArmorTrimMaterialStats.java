package net.laserdiamond.serverplugin1201.items.armor.Trims.Components;

public class ArmorTrimMaterialStats {

    private double[] materialStatArray = new double[11];
    private double copperSpeed;
    private double goldSaturationChance;
    private double ironHealthBoost;
    private double lapisBonusExp;
    private double quartzBonusMiningExp;
    private double redstoneBonusPotion;
    private double emeraldBonusLuck;
    private double amethystBonusDamage;
    private double diamondBonusMana;
    private double netheriteBonusDefense;
    private double netheriteBonusFireDefense;

    public ArmorTrimMaterialStats(double copperSpeed, double goldSaturationChance, double ironHealthBoost, double lapisBonusExp, double quartzBonusMiningExp, double redstoneBonusPotion, double emeraldBonusLuck, double amethystBonusDamage, double diamondBonusMana, double netheriteBonusDefense, double netheriteBonusFireDefense) {
        this.copperSpeed = copperSpeed;
        this.goldSaturationChance = goldSaturationChance;
        this.ironHealthBoost = ironHealthBoost;
        this.lapisBonusExp = lapisBonusExp;
        this.quartzBonusMiningExp = quartzBonusMiningExp;
        this.redstoneBonusPotion = redstoneBonusPotion;
        this.emeraldBonusLuck = emeraldBonusLuck;
        this.amethystBonusDamage = amethystBonusDamage;
        this.diamondBonusMana = diamondBonusMana;
        this.netheriteBonusDefense = netheriteBonusDefense;
        this.netheriteBonusFireDefense = netheriteBonusFireDefense;
    }

    public double getCopperSpeed() {
        return copperSpeed;
    }

    public void setCopperSpeed(double copperSpeed) {
        this.copperSpeed = copperSpeed;
    }

    public double getGoldSaturationChance() {
        return goldSaturationChance;
    }

    public void setGoldSaturationChance(double goldSaturationChance) {
        this.goldSaturationChance = goldSaturationChance;
    }

    public double getIronHealthBoost() {
        return ironHealthBoost;
    }

    public void setIronHealthBoost(double ironHealthBoost) {
        this.ironHealthBoost = ironHealthBoost;
    }

    public double getLapisBonusExp() {
        return lapisBonusExp;
    }

    public void setLapisBonusExp(double lapisBonusExp) {
        this.lapisBonusExp = lapisBonusExp;
    }

    public double getQuartzBonusMiningExp() {
        return quartzBonusMiningExp;
    }

    public void setQuartzBonusMiningExp(double quartzBonusMiningExp) {
        this.quartzBonusMiningExp = quartzBonusMiningExp;
    }

    public double getRedstoneBonusPotion() {
        return redstoneBonusPotion;
    }

    public void setRedstoneBonusPotion(double redstoneBonusPotion) {
        this.redstoneBonusPotion = redstoneBonusPotion;
    }

    public double getEmeraldBonusLuck() {
        return emeraldBonusLuck;
    }

    public void setEmeraldBonusLuck(double emeraldBonusLuck) {
        this.emeraldBonusLuck = emeraldBonusLuck;
    }

    public double getAmethystBonusDamage() {
        return amethystBonusDamage;
    }

    public void setAmethystBonusDamage(double amethystBonusDamage) {
        this.amethystBonusDamage = amethystBonusDamage;
    }

    public double getDiamondBonusMana() {
        return diamondBonusMana;
    }

    public void setDiamondBonusMana(double diamondBonusMana) {
        this.diamondBonusMana = diamondBonusMana;
    }

    public double getNetheriteBonusDefense() {
        return netheriteBonusDefense;
    }

    public void setNetheriteBonusDefense(double netheriteBonusDefense) {
        this.netheriteBonusDefense = netheriteBonusDefense;
    }

    public double getNetheriteBonusFireDefense() {
        return netheriteBonusFireDefense;
    }

    public void setNetheriteBonusFireDefense(double netheriteBonusFireDefense) {
        this.netheriteBonusFireDefense = netheriteBonusFireDefense;
    }
}
