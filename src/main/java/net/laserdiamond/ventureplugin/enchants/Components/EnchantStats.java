package net.laserdiamond.ventureplugin.enchants.Components;

public class EnchantStats {

    private double defense;
    private double fireDefense;
    private double explosionDefense;
    private double projectileDefense;
    private double magicDefense;
    private double toughness;
    private double baseMelee;
    private double baseMagic;
    private double baseRange;
    private double mana;
    private double health;
    private double reach;
    private double speed;
    private double mobLoot;
    private double miningFortune;
    private double foragingFortune;
    private double farmingFortune;
    private double fishingLuck;

    public EnchantStats(double defense, double fireDefense, double explosionDefense, double projectileDefense, double magicDefense, double toughness, double baseMelee, double baseMagic, double baseRange, double mana, double health, double reach, double speed, double mobLoot, double miningFortune, double foragingFortune, double farmingFortune, double fishingLuck) {
        this.defense = defense;
        this.fireDefense = fireDefense;
        this.explosionDefense = explosionDefense;
        this.projectileDefense = projectileDefense;
        this.magicDefense = magicDefense;
        this.toughness = toughness;
        this.baseMelee = baseMelee;
        this.baseMagic = baseMagic;
        this.baseRange = baseRange;
        this.mana = mana;
        this.health = health;
        this.reach = reach;
        this.speed = speed;
        this.mobLoot = mobLoot;
        this.miningFortune = miningFortune;
        this.foragingFortune = foragingFortune;
        this.farmingFortune = farmingFortune;
        this.fishingLuck = fishingLuck;
    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public double getFireDefense() {
        return fireDefense;
    }

    public void setFireDefense(double fireDefense) {
        this.fireDefense = fireDefense;
    }

    public double getExplosionDefense() {
        return explosionDefense;
    }

    public void setExplosionDefense(double explosionDefense) {
        this.explosionDefense = explosionDefense;
    }

    public double getProjectileDefense() {
        return projectileDefense;
    }

    public void setProjectileDefense(double projectileDefense) {
        this.projectileDefense = projectileDefense;
    }

    public double getMagicDefense() {
        return magicDefense;
    }

    public void setMagicDefense(double magicDefense) {
        this.magicDefense = magicDefense;
    }

    public double getToughness() {
        return toughness;
    }

    public void setToughness(double toughness) {
        this.toughness = toughness;
    }

    public double getBaseMelee() {
        return baseMelee;
    }

    public void setBaseMelee(double baseMelee) {
        this.baseMelee = baseMelee;
    }

    public double getBaseMagic() {
        return baseMagic;
    }

    public void setBaseMagic(double baseMagic) {
        this.baseMagic = baseMagic;
    }

    public double getBaseRange() {
        return baseRange;
    }

    public void setBaseRange(double baseRange) {
        this.baseRange = baseRange;
    }

    public double getMana() {
        return mana;
    }

    public void setMana(double mana) {
        this.mana = mana;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getReach() {
        return reach;
    }

    public void setReach(double reach) {
        this.reach = reach;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
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

    public double getFishingLuck() {
        return fishingLuck;
    }

    public void setFishingLuck(double fishingLuck) {
        this.fishingLuck = fishingLuck;
    }

    public double getMobLoot() {
        return mobLoot;
    }

    public void setMobLoot(double mobLoot) {
        this.mobLoot = mobLoot;
    }

    public double getFarmingFortune() {
        return farmingFortune;
    }

    public void setFarmingFortune(double farmingFortune) {
        this.farmingFortune = farmingFortune;
    }
}
