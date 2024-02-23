package net.laserdiamond.serverplugin1201.events.effects.Components;

public class EffectDurations {

    private int paralyzeDuration;
    private int manaFreezeDuration;
    private int necrosisDuration;
    private int vulnerableDuration;

    public EffectDurations(int paralyzeDuration, int manaFreezeDuration, int necrosisDuration, int vulnerableDuration) {
        this.paralyzeDuration = paralyzeDuration;
        this.manaFreezeDuration = manaFreezeDuration;
        this.necrosisDuration = necrosisDuration;
        this.vulnerableDuration = vulnerableDuration;
    }


    public int getParalyzeDuration() {
        return paralyzeDuration;
    }

    public void setParalyzeDuration(int paralyzeDuration) {
        this.paralyzeDuration = paralyzeDuration;
    }

    public int getManaFreezeDuration() {
        return manaFreezeDuration;
    }

    public void setManaFreezeDuration(int manaFreezeDuration) {
        this.manaFreezeDuration = manaFreezeDuration;
    }

    public int getNecrosisDuration() {
        return necrosisDuration;
    }

    public void setNecrosisDuration(int necrosisDuration) {
        this.necrosisDuration = necrosisDuration;
    }

    public int getVulnerableDuration() {
        return vulnerableDuration;
    }

    public void setVulnerableDuration(int vulnerableDuration) {
        this.vulnerableDuration = vulnerableDuration;
    }

}
