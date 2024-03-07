package net.laserdiamond.serverplugin1201.stats.Components;

public class DamageStats {

    private double bMeleeDmg;
    private double bMagicDmg;
    private double bRangeDmg;
    private double pMeleeDmg;
    private double pMagicDmg;
    private double pRangeDmg;

    public DamageStats(double bMeleeDmg, double bMagicDmg, double bRangeDmg, double pMeleeDmg, double pMagicDmg, double pRangeDmg)
    {
        this.bMeleeDmg = bMeleeDmg;
        this.bMagicDmg = bMagicDmg;
        this.bRangeDmg = bRangeDmg;
        this.pMeleeDmg = pMeleeDmg;
        this.pMagicDmg = pMagicDmg;
        this.pRangeDmg = pRangeDmg;
    }

    public double getbMeleeDmg() {
        return bMeleeDmg;
    }

    public void setbMeleeDmg(double bMeleeDmg) {
        this.bMeleeDmg = bMeleeDmg;
    }

    public double getbMagicDmg() {
        return bMagicDmg;
    }

    public void setbMagicDmg(double bMagicDmg) {
        this.bMagicDmg = bMagicDmg;
    }

    public double getbRangeDmg() {
        return bRangeDmg;
    }

    public void setbRangeDmg(double bRangeDmg) {
        this.bRangeDmg = bRangeDmg;
    }

    public double getpMeleeDmg() {
        return pMeleeDmg;
    }

    public void setpMeleeDmg(double pMeleeDmg) {
        this.pMeleeDmg = pMeleeDmg;
    }

    public double getpMagicDmg() {
        return pMagicDmg;
    }

    public void setpMagicDmg(double pMagicDmg) {
        this.pMagicDmg = pMagicDmg;
    }

    public double getpRangeDmg() {
        return pRangeDmg;
    }

    public void setpRangeDmg(double pRangeDmg) {
        this.pRangeDmg = pRangeDmg;
    }
}
