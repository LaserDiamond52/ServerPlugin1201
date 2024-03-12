package net.laserdiamond.serverplugin1201.tunement.Components;

public class TunementPoints {

    private int tuningPoints;
    private int healthPoints;
    private int defensePoints;
    private int speedPoints;
    private int manaPoints;
    private int meleePoints;
    private int magicPoints;
    private int rangePoints;

    public TunementPoints(int tuningPoints, int healthPoints, int defensePoints, int speedPoints, int manaPoints, int meleePoints, int magicPoints, int rangePoints)
    {
        this.tuningPoints = tuningPoints;
        this.healthPoints = healthPoints;
        this.defensePoints = defensePoints;
        this.speedPoints = speedPoints;
        this.manaPoints = manaPoints;
        this.meleePoints = meleePoints;
        this.magicPoints = magicPoints;
        this.rangePoints = rangePoints;
    }

    public int getTuningPoints() {
        return tuningPoints;
    }

    public void setTuningPoints(int tuningPoints) {
        this.tuningPoints = tuningPoints;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getDefensePoints() {
        return defensePoints;
    }

    public void setDefensePoints(int defensePoints) {
        this.defensePoints = defensePoints;
    }

    public int getSpeedPoints() {
        return speedPoints;
    }

    public void setSpeedPoints(int speedPoints) {
        this.speedPoints = speedPoints;
    }

    public int getManaPoints() {
        return manaPoints;
    }

    public void setManaPoints(int manaPoints) {
        this.manaPoints = manaPoints;
    }

    public int getMeleePoints() {
        return meleePoints;
    }

    public void setMeleePoints(int meleePoints) {
        this.meleePoints = meleePoints;
    }

    public int getMagicPoints() {
        return magicPoints;
    }

    public void setMagicPoints(int magicPoints) {
        this.magicPoints = magicPoints;
    }

    public int getRangePoints() {
        return rangePoints;
    }

    public void setRangePoints(int rangePoints) {
        this.rangePoints = rangePoints;
    }
}
