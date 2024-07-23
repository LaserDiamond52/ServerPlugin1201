package net.laserdiamond.ventureplugin.stats.Components;

public class LootStats {

    private double mobLooting;
    private double miningFortune;
    private double foragingFortune;
    private double farmingFortune;
    private double fishingLuck;

    public LootStats(double mobLooting, double miningFortune, double foragingFortune, double farmingFortune, double fishingLuck)
    {
        this.mobLooting = mobLooting;
        this.miningFortune = miningFortune;
        this.foragingFortune = foragingFortune;
        this.farmingFortune = farmingFortune;
        this.fishingLuck = fishingLuck;
    }

    public double getMobLooting() {
        return mobLooting;
    }

    public void setMobLooting(double mobLooting) {
        this.mobLooting = mobLooting;
    }

    public double getMiningFortune() {
        return miningFortune;
    }

    public void setMiningFortune(double miningFortune) {
        this.miningFortune = miningFortune;
    }

    public double getForagingFortune() {
        return foragingFortune;
    }

    public void setForagingFortune(double foragingFortune) {
        this.foragingFortune = foragingFortune;
    }

    public double getFarmingFortune() {
        return farmingFortune;
    }

    public void setFarmingFortune(double farmingFortune) {
        this.farmingFortune = farmingFortune;
    }

    public double getFishingLuck() {
        return fishingLuck;
    }

    public void setFishingLuck(double fishingLuck) {
        this.fishingLuck = fishingLuck;
    }
}
