package net.laserdiamond.serverplugin1201.tunement.Components;

public class TunementStats {

    private double health;
    private double defense;
    private double speed;
    private double mana;
    private double melee;
    private double magic;
    private double range;

    public TunementStats(double health, double defense, double speed, double mana, double melee, double magic, double range)
    {
        this.health = health;
        this.defense = defense;
        this.speed = speed;
        this.mana = mana;
        this.melee = melee;
        this.magic = magic;
        this.range = range;
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

    public double getMelee() {
        return melee;
    }

    public void setMelee(double melee) {
        this.melee = melee;
    }

    public double getMagic() {
        return magic;
    }

    public void setMagic(double magic) {
        this.magic = magic;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }
}
