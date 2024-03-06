package net.laserdiamond.serverplugin1201.stats.Components;

public class Stats {

    private double health;
    private double meleeDamage;
    private double rangeDamage;
    private double magicDamage;
    private double availableMana;
    private double maxMana;
    private double baseMeleeDamage;
    private double baseMagicDamage;
    private double baseRangeDamage;
    private double defense;
    private double fireDefense;
    private double explosionDefense;
    private double projectileDefense;
    private double magicDefense;
    private double toughness;
    private double fortitude;

    public Stats(double health, double meleeDamage, double magicDamage, double rangeDamage, double availableMana, double maxMana, double baseMeleeDamage, double baseMagicDamage, double baseRangeDamage, double defense, double fireDefense, double explosionDefense, double projectileDefense, double magicDefense, double toughness, double fortitude) {
        this.health = health;
        this.meleeDamage = meleeDamage;
        this.magicDamage = magicDamage;
        this.rangeDamage = rangeDamage;
        this.availableMana = availableMana;
        this.maxMana = maxMana;
        this.baseMeleeDamage = baseMeleeDamage;
        this.baseMagicDamage = baseMagicDamage;
        this.baseRangeDamage = baseRangeDamage;
        this.defense = defense;
        this.fireDefense = fireDefense;
        this.explosionDefense = explosionDefense;
        this.projectileDefense = projectileDefense;
        this.magicDefense = magicDefense;
        this.toughness = toughness;
        this.fortitude = fortitude;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getMeleeDamage() {
        return meleeDamage;
    }

    public void setMeleeDamage(double meleeDamage) {
        this.meleeDamage = meleeDamage;
    }

    public double getMagicDamage() {
        return magicDamage;
    }

    public void setMagicDamage(double magicDamage) {
        this.magicDamage = magicDamage;
    }

    public double getRangeDamage() {
        return rangeDamage;
    }

    public void setRangeDamage(double rangeDamage) {
        this.rangeDamage = rangeDamage;
    }

    public double getAvailableMana() {
        return availableMana;
    }

    public void setAvailableMana(double availableMana) {
        this.availableMana = availableMana;
    }

    public double getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(double maxMana) {
        this.maxMana = maxMana;
    }

    public double getBaseMeleeDamage() {
        return baseMeleeDamage;
    }

    public void setBaseMeleeDamage(double baseMeleeDamage) {
        this.baseMeleeDamage = baseMeleeDamage;
    }

    public double getBaseMagicDamage() {
        return baseMagicDamage;
    }

    public void setBaseMagicDamage(double baseMagicDamage) {
        this.baseMagicDamage = baseMagicDamage;
    }

    public double getBaseRangeDamage() {
        return baseRangeDamage;
    }

    public void setBaseRangeDamage(double baseRangeDamage) {
        this.baseRangeDamage = baseRangeDamage;
    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public double getToughness() {
        return toughness;
    }

    public void setToughness(double toughness) {
        this.toughness = toughness;
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

    public double getFortitude() {
        return fortitude;
    }

    public void setFortitude(double fortitude) {
        this.fortitude = fortitude;
    }
}
