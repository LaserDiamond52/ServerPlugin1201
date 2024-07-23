package net.laserdiamond.ventureplugin.stats.Components;

import org.bukkit.entity.Player;

public class MainhandStats {

    private final Player player;
    private double health;
    private double defense;
    private double fireDefense;
    private double explosionDefense;
    private double projectileDefense;
    private double magicDefense;
    private double toughness;
    private double fortitude;
    private double speed;
    private double mana;
    private double mobLooting;
    private double miningFortune;
    private double foragingFortune;
    private double farmingFortune;
    private double fishingLuck;
    private double luck;

    public MainhandStats(Player player, double health, double defense, double fireDefense, double explosionDefense, double projectileDefense, double magicDefense, double toughness, double fortitude, double speed, double mana, double mobLooting, double miningFortune, double foragingFortune, double farmingFortune, double fishingLuck, double luck) {
        this.player = player;
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
        this.mobLooting = mobLooting;
        this.miningFortune = miningFortune;
        this.foragingFortune = foragingFortune;
        this.farmingFortune = farmingFortune;
        this.fishingLuck = fishingLuck;
        this.luck = luck;
    }


    public Player getPlayer() {
        return player;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
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

    public double getLuck() {
        return luck;
    }

    public void setLuck(double luck) {
        this.luck = luck;
    }
}
