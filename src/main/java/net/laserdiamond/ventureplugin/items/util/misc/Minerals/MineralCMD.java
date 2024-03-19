package net.laserdiamond.ventureplugin.items.util.misc.Minerals;

public enum MineralCMD {

    EMERALD (1,11),
    REDSTONE (2,12),
    LAPIS (3,13),
    AMETHYST (4,14),
    QUARTZ (5,15),
    NETHERITE (6,16),
    DIAMOND (7,17),
    GOLD (8,18),
    IRON (9,19),
    COPPER (10,20);

    private final int normalCMD, hyperiteCMD;

    MineralCMD(int normalCMD, int hyperiteCMD) {
        this.normalCMD = normalCMD;
        this.hyperiteCMD = hyperiteCMD;
    }

    public int getNormalCMD() {
        return normalCMD;
    }

    public int getHyperiteCMD() {
        return hyperiteCMD;
    }
}
