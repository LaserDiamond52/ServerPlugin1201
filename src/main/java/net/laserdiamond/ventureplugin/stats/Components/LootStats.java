package net.laserdiamond.ventureplugin.stats.Components;

public class LootStats {

    private double bonusMobLoot;
    private double bonusOreLoot;
    private double bonusWoodLoot;
    private double bonusFarmingLoot;
    private double fishingLuck;

    public LootStats(double bonusMobLoot, double bonusOreLoot, double bonusWoodLoot, double bonusFarmingLoot, double fishingLuck)
    {
        this.bonusMobLoot = bonusMobLoot;
        this.bonusOreLoot = bonusOreLoot;
        this.bonusWoodLoot = bonusWoodLoot;
        this.bonusFarmingLoot = bonusFarmingLoot;
        this.fishingLuck = fishingLuck;
    }

    public double getBonusMobLoot() {
        return bonusMobLoot;
    }

    public void setBonusMobLoot(double bonusMobLoot) {
        this.bonusMobLoot = bonusMobLoot;
    }

    public double getBonusOreLoot() {
        return bonusOreLoot;
    }

    public void setBonusOreLoot(double bonusOreLoot) {
        this.bonusOreLoot = bonusOreLoot;
    }

    public double getBonusWoodLoot() {
        return bonusWoodLoot;
    }

    public void setBonusWoodLoot(double bonusWoodLoot) {
        this.bonusWoodLoot = bonusWoodLoot;
    }

    public double getBonusFarmingLoot() {
        return bonusFarmingLoot;
    }

    public void setBonusFarmingLoot(double bonusFarmingLoot) {
        this.bonusFarmingLoot = bonusFarmingLoot;
    }

    public double getFishingLuck() {
        return fishingLuck;
    }

    public void setFishingLuck(double fishingLuck) {
        this.fishingLuck = fishingLuck;
    }
}
