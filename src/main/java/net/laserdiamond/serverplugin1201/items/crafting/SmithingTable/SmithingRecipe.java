package net.laserdiamond.serverplugin1201.items.crafting.SmithingTable;

import org.bukkit.inventory.ItemStack;

public class SmithingRecipe {

    private ItemStack equipmentItem;
    private ItemStack materialItem;
    private ItemStack templateItem;
    private ItemStack resultItem;

    public SmithingRecipe(ItemStack equipmentItem, ItemStack materialItem, ItemStack templateItem, ItemStack resultItem) {
        this.equipmentItem = equipmentItem;
        this.materialItem = materialItem;
        this.templateItem = templateItem;
        this.resultItem = resultItem;
    }

    public SmithingRecipe(ItemStack equipmentItem, ItemStack materialItem, ItemStack templateItem) {
        this.equipmentItem = equipmentItem;
        this.materialItem = materialItem;
        this.templateItem = templateItem;
    }

    public ItemStack getEquipmentItem() {
        return equipmentItem;
    }

    public void setEquipmentItem(ItemStack equipmentItem) {
        this.equipmentItem = equipmentItem;
    }

    public ItemStack getMaterialItem() {
        return materialItem;
    }

    public void setMaterialItem(ItemStack materialItem) {
        this.materialItem = materialItem;
    }

    public ItemStack getTemplateItem() {
        return templateItem;
    }

    public void setTemplateItem(ItemStack templateItem) {
        this.templateItem = templateItem;
    }

    public ItemStack getResultItem() {
        return resultItem;
    }

    public void setResultItem(ItemStack resultItem) {
        this.resultItem = resultItem;
    }
}
