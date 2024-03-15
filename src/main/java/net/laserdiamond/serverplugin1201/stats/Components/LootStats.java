package net.laserdiamond.serverplugin1201.stats.Components;

public class LootStats {

    private double bonusMobLoot;
    private double bonusOreLoot;
    private double bonusWoodLoot;
    private double bonusDigLoot;
    private double fishingLoot;

    public LootStats(double bonusMobLoot, double bonusOreLoot, double bonusWoodLoot, double bonusDigLoot, double fishingLoot)
    {
        this.bonusMobLoot = bonusMobLoot;
        this.bonusOreLoot = bonusOreLoot;
        this.bonusWoodLoot = bonusWoodLoot;
        this.bonusDigLoot = bonusDigLoot;
        this.fishingLoot = fishingLoot;
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

    public double getBonusDigLoot() {
        return bonusDigLoot;
    }

    public void setBonusDigLoot(double bonusDigLoot) {
        this.bonusDigLoot = bonusDigLoot;
    }

    public double getFishingLoot() {
        return fishingLoot;
    }

    public void setFishingLoot(double fishingLoot) {
        this.fishingLoot = fishingLoot;
    }
}
