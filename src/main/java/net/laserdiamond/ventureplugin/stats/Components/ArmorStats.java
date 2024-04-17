package net.laserdiamond.ventureplugin.stats.Components;

public class ArmorStats {

    private double
            health,
            defense,
            fireDefense,
            explosionDefense,
            projectileDefense,
            magicDefense,
            toughness,
            fortitude,
            speed,
            mana,
            percentMeleeDamage,
            percentMagicDamage,
            percentRangeDamage,
            mobFortune,
            miningFortune,
            foragingFortune,
            farmingFortune,
            fishingLuck,
            luck,
            longevity,
            caffeination;

    public ArmorStats(double health, double defense, double fireDefense, double explosionDefense, double projectileDefense, double magicDefense, double toughness, double fortitude, double speed, double mana, double percentMeleeDamage, double percentMagicDamage, double percentRangeDamage, double mobFortune, double miningFortune, double foragingFortune, double farmingFortune, double fishingLuck, double luck, double longevity, double caffeination)
    {
        this.health = health;
        this.defense = defense;
        this.fireDefense = fireDefense;
        this.explosionDefense = explosionDefense;
        this.projectileDefense = projectileDefense;
        this.magicDefense = magicDefense;
        this.toughness = toughness;
        this.fortitude = fortitude;
        this.speed = speed;
        this.mana = mana;
        this.percentMeleeDamage = percentMeleeDamage;
        this.percentMagicDamage = percentMagicDamage;
        this.percentRangeDamage = percentRangeDamage;
        this.mobFortune = mobFortune;
        this.miningFortune = miningFortune;
        this.foragingFortune = foragingFortune;
        this.farmingFortune = farmingFortune;
        this.fishingLuck = fishingLuck;
        this.luck = luck;
        this.longevity = longevity;
        this.caffeination = caffeination;
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

    public double getFortitude() {
        return fortitude;
    }

    public void setFortitude(double fortitude) {
        this.fortitude = fortitude;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getMana() {
        return mana;
    }

    public void setMana(double mana) {
        this.mana = mana;
    }

    public double getPercentMeleeDamage() {
        return percentMeleeDamage;
    }

    public void setPercentMeleeDamage(double percentMeleeDamage) {
        this.percentMeleeDamage = percentMeleeDamage;
    }

    public double getPercentMagicDamage() {
        return percentMagicDamage;
    }

    public void setPercentMagicDamage(double percentMagicDamage) {
        this.percentMagicDamage = percentMagicDamage;
    }

    public double getPercentRangeDamage() {
        return percentRangeDamage;
    }

    public void setPercentRangeDamage(double percentRangeDamage) {
        this.percentRangeDamage = percentRangeDamage;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getMobFortune() {
        return mobFortune;
    }

    public void setMobFortune(double mobFortune) {
        this.mobFortune = mobFortune;
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

    public double getLuck() {
        return luck;
    }

    public void setLuck(double luck) {
        this.luck = luck;
    }

    public double getLongevity() {
        return longevity;
    }

    public void setLongevity(double longevity) {
        this.longevity = longevity;
    }

    public double getCaffeination() {
        return caffeination;
    }

    public void setCaffeination(double caffeination) {
        this.caffeination = caffeination;
    }
}
