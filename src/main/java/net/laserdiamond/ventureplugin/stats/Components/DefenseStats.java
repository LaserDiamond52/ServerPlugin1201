package net.laserdiamond.ventureplugin.stats.Components;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;


public class DefenseStats {

    private double defense;
    private double fireDefense;
    private double explosionDefense;
    private double projectileDefense;
    private double magicDefense;
    private double toughness;
    private double fortitude;

    public DefenseStats(Player player, double defense, double fireDefense, double explosionDefense, double projectileDefense, double magicDefense, double toughness, double fortitude)
    {
        this.defense = defense;
        this.fireDefense = fireDefense;
        this.explosionDefense = explosionDefense;
        this.projectileDefense = projectileDefense;
        this.magicDefense = magicDefense;
        this.toughness = toughness;

        AttributeInstance kbResInstance = player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
        kbResInstance.setBaseValue(fortitude);
        this.fortitude = fortitude;
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

    public double getFortitude(Player player) {
        AttributeInstance kbResInstance = player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
        fortitude = kbResInstance.getBaseValue();
        return fortitude;
    }

    public void setFortitude(Player player, double fortitude) {
        AttributeInstance kbResInstance = player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
        kbResInstance.setBaseValue(fortitude);
        this.fortitude = fortitude;
    }
}
