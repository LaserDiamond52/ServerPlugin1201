package net.laserdiamond.serverplugin1201.Management;

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
