package net.laserdiamond.ventureplugin.items.armor;

import org.bukkit.ChatColor;
import org.bukkit.inventory.EquipmentSlot;

public enum ArmorPieceTypes {


    HELMET ("helmet", EquipmentSlot.HEAD),
    CHESTPLATE ("chestplate", EquipmentSlot.CHEST),
    LEGGINGS ("leggings", EquipmentSlot.LEGS),
    BOOTS ("boots", EquipmentSlot.FEET);

    private final String name;
    private final EquipmentSlot equipmentSlot;

    ArmorPieceTypes(String name, EquipmentSlot equipmentSlot) {
        this.name = name;
        this.equipmentSlot = equipmentSlot;
    }

    public static String of(String inputName) {
        for (ArmorPieceTypes armorPieceTypes : values()) {
            if (armorPieceTypes.name.equals(inputName)) {
                return armorPieceTypes.name;
            }
        }
        throw new IllegalArgumentException(ChatColor.RED + "No such item type: " + inputName);
    }

    public String getName() {
        return name;
    }

    public EquipmentSlot getEquipmentSlot() {
        return equipmentSlot;
    }




}
