package net.laserdiamond.serverplugin1201.items.crafting.SmithingTable;

import org.bukkit.inventory.ItemStack;

public record SmithingRecipe(ItemStack equipmentItem, ItemStack materialItem, ItemStack templateItem) {

    public ItemStack[] toArray() // Converts recipe ingredients into array of ItemStacks
    {
        return new ItemStack[]{this.equipmentItem, this.materialItem, this.templateItem};
    }
}
