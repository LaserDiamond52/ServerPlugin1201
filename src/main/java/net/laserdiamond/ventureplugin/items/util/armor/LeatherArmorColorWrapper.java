package net.laserdiamond.ventureplugin.items.util.armor;

import org.bukkit.Color;

public class LeatherArmorColorWrapper {

    private Color helmetColor, chestplateColor, leggingsColor, bootsColor;


    public LeatherArmorColorWrapper(Color helmetColor, Color chestplateColor, Color leggingsColor, Color bootsColor) {
        this.helmetColor = helmetColor;
        this.chestplateColor = chestplateColor;
        this.leggingsColor = leggingsColor;
        this.bootsColor = bootsColor;
    }

    public Color getHelmetColor() {
        return helmetColor;
    }

    public void setHelmetColor(Color helmetColor) {
        this.helmetColor = helmetColor;
    }

    public Color getChestplateColor() {
        return chestplateColor;
    }

    public void setChestplateColor(Color chestplateColor) {
        this.chestplateColor = chestplateColor;
    }

    public Color getLeggingsColor() {
        return leggingsColor;
    }

    public void setLeggingsColor(Color leggingsColor) {
        this.leggingsColor = leggingsColor;
    }

    public Color getBootsColor() {
        return bootsColor;
    }

    public void setBootsColor(Color bootsColor) {
        this.bootsColor = bootsColor;
    }
}
