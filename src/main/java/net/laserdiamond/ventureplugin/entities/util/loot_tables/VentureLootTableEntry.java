package net.laserdiamond.ventureplugin.entities.util.loot_tables;

import org.bukkit.inventory.ItemStack;

/**
 * Record class used to create Venture loot table entries. Each loot table entry is to contain an item, numerator and denominator for the drop chance, a minimum/maximum drop count, and a looting bonus based on the player's looting stat. If the numerator and denominator as a fraction return a result greater than 1, the drop will be guaranteed. If the minimum and maximum drop count are equivalent, the drop will always drop in that quantity. Quantities defined in the item entry as part of the ItemStack class are ignored, and the quantities from the loot table entry will be used.
 * @param item The item to be dropped/acquired
 * @param numeratorChance The probability of the drop occurring. Values less than 0 will be set to 0, thus resulting in the drop not happening.
 * @param denominatorChance The total amount of possible outcomes for the drop to happen. The denominator cannot be less than the numerator. If a value less than the numerator is entered, the denominator will be set to the value of the numerator.
 * @param minDropCount The minimal quantity of the item that can be dropped. Values less than 0 will be set to 0. Values greater than maxDropCount will be set to the value of the maxDropCount.
 * @param maxDropCount The maximum quantity of the item that can be dropped. Values less than 0 will be set to 0. Values less than the minDropCount will be set to the value of the minDropCount.
 * @param lootingBonus The bonus amount of items to be dropped. Determined by the player's looting stat. If the mob is not killed by the player, no looting bonus will occur.
 */
public record VentureLootTableEntry(ItemStack item, int numeratorChance, int denominatorChance,int minDropCount, int maxDropCount, int lootingBonus) {
}
