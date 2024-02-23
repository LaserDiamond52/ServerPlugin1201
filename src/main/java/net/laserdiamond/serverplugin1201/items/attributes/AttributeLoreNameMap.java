package net.laserdiamond.serverplugin1201.items.attributes;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

public enum AttributeLoreNameMap {

    @Deprecated
    MAX_HEALTH (Attribute.GENERIC_MAX_HEALTH,ChatColor.BLUE + "Health: +", ChatColor.RED),
    ATTACK_DAMAGE (Attribute.GENERIC_ATTACK_DAMAGE,ChatColor.GRAY + "Attack Damage: +", ChatColor.LIGHT_PURPLE),
    ATTACK_SPEED (Attribute.GENERIC_ATTACK_SPEED, ChatColor.GRAY + "Attack Speed: ", ChatColor.YELLOW),
    @Deprecated
    LUCK (Attribute.GENERIC_LUCK, ChatColor.BLUE + "Luck: ", ChatColor.GREEN),
    @Deprecated
    ARMOR (Attribute.GENERIC_ARMOR,ChatColor.BLUE + "Armor: +", ChatColor.GREEN),
    @Deprecated
    ARMOR_TOUGHNESS (Attribute.GENERIC_ARMOR_TOUGHNESS,ChatColor.BLUE + "Toughness: +", ChatColor.GREEN),
    @Deprecated
    KNOCKBACK_RESISTANCE (Attribute.GENERIC_KNOCKBACK_RESISTANCE,ChatColor.GRAY + "Fortitude: +", ChatColor.DARK_GREEN),
    @Deprecated
    MOVEMENT_SPEED (Attribute.GENERIC_MOVEMENT_SPEED,ChatColor.BLUE + "Speed: +", ChatColor.WHITE);

    private final Attribute attribute;
    private final String displayName;
    private final ChatColor displayColor;
    AttributeLoreNameMap(Attribute attribute, String displayName, ChatColor displayColor) {
        this.attribute = attribute;
        this.displayName = displayName;
        this.displayColor = displayColor;
    }

    public static String ofDisplayName(Attribute attribute) {

        for (AttributeLoreNameMap attributeLoreNameMap : AttributeLoreNameMap.values()) {
            if (attributeLoreNameMap.attribute.equals(attribute)) {
                return attributeLoreNameMap.displayName;
            }
        }
        throw new IllegalArgumentException(ChatColor.RED + "No mapping for attribute: " + attribute.getKey().getKey());
    }

    public static ChatColor ofDisplayColor(Attribute attribute) {

        for (AttributeLoreNameMap attributeLoreNameMap : AttributeLoreNameMap.values()) {
            if (attributeLoreNameMap.attribute.equals(attribute)) {
                return attributeLoreNameMap.displayColor;
            }
        }
        throw new IllegalArgumentException(ChatColor.RED + "No mapping for attribute: " + attribute.getKey().getKey());
    }

    public Attribute getAttribute() {
        return attribute;
    }
    public String getDisplayName() {
        return displayName;
    }

    public ChatColor getDisplayColor() {
        return displayColor;
    }
}
