package net.laserdiamond.ventureplugin.stats.Components;

public class PotionStats {

    private double longevity;
    private double caffeination;

    public PotionStats(double longevity, double caffeination)
    {
        this.longevity = longevity;
        this.caffeination = caffeination;
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
