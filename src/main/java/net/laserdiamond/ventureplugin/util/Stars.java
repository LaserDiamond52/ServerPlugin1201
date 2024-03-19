package net.laserdiamond.ventureplugin.util;

public enum Stars {

    STARS (0.1);
    private final double boostPerStar;
    Stars(double boostPerStar) {
        this.boostPerStar = boostPerStar;
    }
    public double getBoostPerStar() {
        return boostPerStar;
    }
}
