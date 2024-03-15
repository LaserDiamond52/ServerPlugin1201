package net.laserdiamond.serverplugin1201.stats.Components;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class Stats {

    private final ServerPlugin1201 PLUGIN = ServerPlugin1201.getInstance();
    private final Player player;
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
    private double speed;
    private double starvationRate;
    private double luck;
    private final double BASE_SPEED = PLUGIN.getBaseStatsConfig().getDouble("baseSpeed");

    @Deprecated
    public Stats(Player player, double health, double speed, double starvationRate, double luck, double meleeDamage, double magicDamage, double rangeDamage, double availableMana, double maxMana, double baseMeleeDamage, double baseMagicDamage, double baseRangeDamage, double defense, double fireDefense, double explosionDefense, double projectileDefense, double magicDefense, double toughness, double fortitude) {

        this.player = player;

        AttributeInstance healthInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        healthInstance.setBaseValue(health);
        this.health = health;

        AttributeInstance speedInstance = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        speedInstance.setBaseValue(BASE_SPEED * speed * 0.01);
        this.speed = speed;

        player.setStarvationRate((int) starvationRate);
        this.starvationRate = starvationRate;

        AttributeInstance luckInstance = player.getAttribute(Attribute.GENERIC_LUCK);
        luckInstance.setBaseValue(luck);
        this.luck = luck;

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

    public Stats(Player player, double health, double speed, double starvationRate, double luck, double availableMana, double maxMana)
    {
        this.player = player;

        AttributeInstance healthInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        healthInstance.setBaseValue(health);
        this.health = health;

        AttributeInstance speedInstance = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        speedInstance.setBaseValue(BASE_SPEED * speed * 0.01);
        this.speed = speed;

        player.setStarvationRate((int) starvationRate);
        this.starvationRate = starvationRate;

        AttributeInstance luckInstance = player.getAttribute(Attribute.GENERIC_LUCK);
        luckInstance.setBaseValue(luck);
        this.luck = luck;

        this.availableMana = availableMana;
        this.maxMana = maxMana;
    }

    public Player getPlayer()
    {
        return player;
    }

    public double getHealth() {
        AttributeInstance healthInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        health = healthInstance.getBaseValue();
        return health;
    }

    public void setHealth(double health) {
        AttributeInstance healthInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        healthInstance.setBaseValue(health);
        this.health = health;
    }

    @Deprecated
    public double getMeleeDamage() {
        return meleeDamage;
    }

    @Deprecated
    public void setMeleeDamage(double meleeDamage) {
        this.meleeDamage = meleeDamage;
    }

    @Deprecated
    public double getMagicDamage() {
        return magicDamage;
    }

    @Deprecated
    public void setMagicDamage(double magicDamage) {
        this.magicDamage = magicDamage;
    }

    @Deprecated
    public double getRangeDamage() {
        return rangeDamage;
    }

    @Deprecated
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

    @Deprecated
    public double getBaseMeleeDamage() {
        return baseMeleeDamage;
    }

    @Deprecated
    public void setBaseMeleeDamage(double baseMeleeDamage) {
        this.baseMeleeDamage = baseMeleeDamage;
    }

    @Deprecated
    public double getBaseMagicDamage() {
        return baseMagicDamage;
    }

    @Deprecated
    public void setBaseMagicDamage(double baseMagicDamage) {
        this.baseMagicDamage = baseMagicDamage;
    }

    @Deprecated
    public double getBaseRangeDamage() {
        return baseRangeDamage;
    }

    @Deprecated
    public void setBaseRangeDamage(double baseRangeDamage) {
        this.baseRangeDamage = baseRangeDamage;
    }

    @Deprecated
    public double getDefense() {
        return defense;
    }

    @Deprecated
    public void setDefense(double defense) {
        this.defense = defense;
    }

    @Deprecated
    public double getToughness() {
        return toughness;
    }

    @Deprecated
    public void setToughness(double toughness) {
        this.toughness = toughness;
    }

    @Deprecated
    public double getFireDefense() {
        return fireDefense;
    }

    @Deprecated
    public void setFireDefense(double fireDefense) {
        this.fireDefense = fireDefense;
    }

    @Deprecated
    public double getExplosionDefense() {
        return explosionDefense;
    }

    @Deprecated
    public void setExplosionDefense(double explosionDefense) {
        this.explosionDefense = explosionDefense;
    }

    @Deprecated
    public double getProjectileDefense() {
        return projectileDefense;
    }

    @Deprecated
    public void setProjectileDefense(double projectileDefense) {
        this.projectileDefense = projectileDefense;
    }

    @Deprecated
    public double getMagicDefense() {
        return magicDefense;
    }

    @Deprecated
    public void setMagicDefense(double magicDefense) {
        this.magicDefense = magicDefense;
    }

    @Deprecated
    public double getFortitude() {
        return fortitude;
    }

    @Deprecated
    public void setFortitude(double fortitude) {
        this.fortitude = fortitude;
    }

    public double getSpeed() {
        AttributeInstance speedInstance = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        double playerSpeed = speedInstance.getBaseValue();
        speed = (playerSpeed / BASE_SPEED) * 100;
        return speed;
    }

    public void setSpeed(double speed) {
        AttributeInstance speedInstance = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        speedInstance.setBaseValue(BASE_SPEED * speed * 0.01);
        this.speed = speed;
    }

    public double getStarvationRate() {
        starvationRate = player.getStarvationRate();
        return starvationRate;
    }

    public void setStarvationRate(int starvationRate) {
        player.setStarvationRate(starvationRate);
        this.starvationRate = starvationRate;
    }

    public double getLuck() {
        AttributeInstance luckInstance = player.getAttribute(Attribute.GENERIC_LUCK);
        luck = luckInstance.getBaseValue();
        return luck;
    }

    public void setLuck(double luck) {
        AttributeInstance luckInstance = player.getAttribute(Attribute.GENERIC_LUCK);
        luckInstance.setBaseValue(luck);
        this.luck = luck;
    }
}
