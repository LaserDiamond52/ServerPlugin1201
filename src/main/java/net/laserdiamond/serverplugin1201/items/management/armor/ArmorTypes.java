package net.laserdiamond.serverplugin1201.items.management.armor;

import org.bukkit.ChatColor;
import org.bukkit.inventory.EquipmentSlot;

public enum ArmorTypes {


    HELMET ("helmet", EquipmentSlot.HEAD),
    CHESTPLATE ("chestplate", EquipmentSlot.CHEST),
    LEGGINGS ("leggings", EquipmentSlot.LEGS),
    BOOTS ("boots", EquipmentSlot.FEET);

    private final String name;
    private final EquipmentSlot equipmentSlot;

    ArmorTypes(String name, EquipmentSlot equipmentSlot) {
        this.name = name;
        this.equipmentSlot = equipmentSlot;
    }

    public static String of(String inputName) {
        for (ArmorTypes armorTypes : values()) {
            if (armorTypes.name.equals(inputName)) {
                return armorTypes.name;
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
