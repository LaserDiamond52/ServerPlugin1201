package net.laserdiamond.ventureplugin.items.util.armor;


import org.bukkit.Color;

public enum ArmorColors {

    EMERALD_ARMOR (Color.fromRGB(19, 240, 63), Color.fromRGB(19, 240, 63), Color.fromRGB(19, 240, 63), Color.fromRGB(19, 240, 63)),
    GUARDIAN_ARMOR (null, Color.fromRGB(40,171,138), Color.fromRGB(27,152,123), Color.fromRGB(17,135,107)),
    STORM_ARMOR (null, Color.fromRGB(0,213,255), Color.fromRGB(0,160,191), Color.fromRGB(0,124,148)),
    BLAZE_ARMOR (null, Color.fromRGB(255, 205, 3), Color.fromRGB(255, 205, 3), Color.fromRGB(255, 205, 3)),
    ASSASSIN_ARMOR (null, Color.fromRGB(77,77,77), Color.fromRGB(77,77,77), Color.fromRGB(77,77,77));

    private final Color helmetColor, chestplateColor, leggingsColor, bootsColor;


    ArmorColors(Color helmetColor, Color chestplateColor, Color leggingsColor, Color bootsColor) {
        this.helmetColor = helmetColor;
        this.chestplateColor = chestplateColor;
        this.leggingsColor = leggingsColor;
        this.bootsColor = bootsColor;
    }

    public Color getHelmetColor() {
        return helmetColor;
    }

    public Color getChestplateColor() {
        return chestplateColor;
    }

    public Color getLeggingsColor() {
        return leggingsColor;
    }

    public Color getBootsColor() {
        return bootsColor;
    }
}
