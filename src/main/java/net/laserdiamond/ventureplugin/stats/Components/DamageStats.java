package net.laserdiamond.ventureplugin.stats.Components;

public class DamageStats {

    private double baseMeleeDamage;
    private double baseMagicDamage;
    private double baseRangeDamage;
    private double percentMeleeDmg;
    private double percentMagicDmg;
    private double percentRangeDmg;
    private double reach;

    public DamageStats(double baseMeleeDamage, double baseMagicDamage, double baseRangeDamage, double percentMeleeDmg, double percentMagicDmg, double pRangeDmg, double reach)
    {
        this.baseMeleeDamage = baseMeleeDamage;
        this.baseMagicDamage = baseMagicDamage;
        this.baseRangeDamage = baseRangeDamage;
        this.percentMeleeDmg = percentMeleeDmg;
        this.percentMagicDmg = percentMagicDmg;
        this.percentRangeDmg = pRangeDmg;
        this.reach = reach;
    }

    public double getBaseMelee() {
        return baseMeleeDamage;
    }

    public void setBaseMeleeDmg(double bMeleeDmg) {
        this.baseMeleeDamage = bMeleeDmg;
    }

    public double getBaseMagic() {
        return baseMagicDamage;
    }

    public void setBaseMagicDmg(double bMagicDmg) {
        this.baseMagicDamage = bMagicDmg;
    }

    public double getBaseRange() {
        return baseRangeDamage;
    }

    public void setBaseRangeDmg(double bRangeDmg) {
        this.baseRangeDamage = bRangeDmg;
    }

    public double getPercentMelee() {
        return percentMeleeDmg;
    }

    public void setPercentMeleeDmg(double pMeleeDmg) {
        this.percentMeleeDmg = pMeleeDmg;
    }

    public double getPercentMagic() {
        return percentMagicDmg;
    }

    public void setPercentMagicDmg(double pMagicDmg) {
        this.percentMagicDmg = pMagicDmg;
    }

    public double getPercentRange() {
        return percentRangeDmg;
    }

    public void setPercentRangeDmg(double pRangeDmg) {
        this.percentRangeDmg = pRangeDmg;
    }

    public double getReach() {
        return reach;
    }

    public void setReach(double reach) {
        this.reach = reach;
    }
}
