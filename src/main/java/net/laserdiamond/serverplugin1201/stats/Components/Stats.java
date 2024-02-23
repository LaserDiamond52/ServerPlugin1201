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
    private double fire_defense;
    private double explosion_defense;
    private double projectile_defense;
    private double magic_defense;
    private double toughness;
    private double fortitude;

    public Stats(double health, double meleeDamage, double magicDamage, double rangeDamage, double availableMana, double maxMana, double baseMeleeDamage, double baseMagicDamage, double baseRangeDamage, double defense, double fire_defense, double explosion_defense, double projectile_defense, double magic_defense, double toughness, double fortitude) {
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
        this.fire_defense = fire_defense;
        this.explosion_defense = explosion_defense;
        this.projectile_defense = projectile_defense;
        this.magic_defense = magic_defense;
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

    public double getFire_defense() {
        return fire_defense;
    }

    public void setFire_defense(double fire_defense) {
        this.fire_defense = fire_defense;
    }

    public double getExplosion_defense() {
        return explosion_defense;
    }

    public void setExplosion_defense(double explosion_defense) {
        this.explosion_defense = explosion_defense;
    }

    public double getProjectile_defense() {
        return projectile_defense;
    }

    public void setProjectile_defense(double projectile_defense) {
        this.projectile_defense = projectile_defense;
    }

    public double getMagic_defense() {
        return magic_defense;
    }

    public void setMagic_defense(double magic_defense) {
        this.magic_defense = magic_defense;
    }

    public double getFortitude() {
        return fortitude;
    }

    public void setFortitude(double fortitude) {
        this.fortitude = fortitude;
    }
}
