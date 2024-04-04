package net.laserdiamond.ventureplugin.stats.Components;

import net.laserdiamond.ventureplugin.VenturePlugin;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class Stats {

    private final VenturePlugin PLUGIN = VenturePlugin.getInstance();
    private final Player player;
    private double health;
    private double availableMana;
    private double maxMana;
    private double speed;
    private double starvationRate;
    private double luck;
    private final double BASE_SPEED = PLUGIN.getBaseStatsConfig().getDouble("baseSpeed");

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
