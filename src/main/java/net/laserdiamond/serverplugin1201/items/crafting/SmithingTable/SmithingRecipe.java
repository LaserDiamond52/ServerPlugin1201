package net.laserdiamond.serverplugin1201.items.crafting.SmithingTable;

import org.bukkit.inventory.ItemStack;

public record SmithingRecipe(ItemStack equipmentItem, ItemStack materialItem, ItemStack templateItem) {

    public ItemStack[] toArray() // Converts recipe ingredients into array of ItemStacks
    {
        ItemStack equipment = this.equipmentItem; // First in array (index 0)
        ItemStack material = this.materialItem; // Second in array (index 1)
        ItemStack template = this.templateItem; // Third in array (index 2)

        return new ItemStack[]{equipment, material, template};
    }
}
