package net.laserdiamond.ventureplugin.stats.Components;

public class DamageStats {

    private double bMeleeDmg;
    private double bMagicDmg;
    private double bRangeDmg;
    private double pMeleeDmg;
    private double pMagicDmg;
    private double pRangeDmg;
    private double reach;

    public DamageStats(double bMeleeDmg, double bMagicDmg, double bRangeDmg, double pMeleeDmg, double pMagicDmg, double pRangeDmg, double reach)
    {
        this.bMeleeDmg = bMeleeDmg;
        this.bMagicDmg = bMagicDmg;
        this.bRangeDmg = bRangeDmg;
        this.pMeleeDmg = pMeleeDmg;
        this.pMagicDmg = pMagicDmg;
        this.pRangeDmg = pRangeDmg;
        this.reach = reach;
    }

    public double getBaseMelee() {
        return bMeleeDmg;
    }

    public void setbMeleeDmg(double bMeleeDmg) {
        this.bMeleeDmg = bMeleeDmg;
    }

    public double getBaseMagic() {
        return bMagicDmg;
    }

    public void setbMagicDmg(double bMagicDmg) {
        this.bMagicDmg = bMagicDmg;
    }

    public double getBaseRange() {
        return bRangeDmg;
    }

    public void setbRangeDmg(double bRangeDmg) {
        this.bRangeDmg = bRangeDmg;
    }

    public double getPercentMelee() {
        return pMeleeDmg;
    }

    public void setPercentMeleeDmg(double pMeleeDmg) {
        this.pMeleeDmg = pMeleeDmg;
    }

    public double getPercentMagic() {
        return pMagicDmg;
    }

    public void setPercentMagicDmg(double pMagicDmg) {
        this.pMagicDmg = pMagicDmg;
    }

    public double getPercentRange() {
        return pRangeDmg;
    }

    public void setPercentRangeDmg(double pRangeDmg) {
        this.pRangeDmg = pRangeDmg;
    }

    public double getReach() {
        return reach;
    }

    public void setReach(double reach) {
        this.reach = reach;
    }
}
