package net.laserdiamond.ventureplugin.items.crafting.SmithingTable;

import org.bukkit.inventory.ItemStack;

public record SmithingRecipe(ItemStack equipmentItem, ItemStack materialItem, ItemStack templateItem) {
}
