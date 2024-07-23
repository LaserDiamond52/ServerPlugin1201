package net.laserdiamond.ventureplugin.entities.mobs;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.entities.util.VentureMobBuilder;
import net.laserdiamond.ventureplugin.entities.util.loot_tables.VentureLootTableEntry;
import net.laserdiamond.ventureplugin.util.VentureMath;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class LootTableListener implements Listener {

    private final VenturePlugin plugin = VenturePlugin.getInstance();
    private final HashMap<VentureMobType, List<VentureLootTableEntry>> ventureMobLootTables = plugin.getVentureMobLootTables();

    public LootTableListener(VenturePlugin plugin)
    {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onDeath(EntityDeathEvent event)
    {
        if (event.getEntity() instanceof Mob mob)
        {
            VentureMobBuilder<?> mobBuilder = new VentureMobBuilder<>(mob);

            if (mobBuilder.isVentureMobType())
            {
                VentureMobType ventureMobType = mobBuilder.getVentureType();

                // TODO: Check if vanilla drops should be cleared
                // TODO: Clear drops if true
                // TODO: Drop items in appropriate quantity for each loot table entry

                if (!mobBuilder.getDropVanillaDrops())
                {
                    event.getDrops().clear(); // Clear drops if vanilla drops should be cleared
                }

                List<VentureLootTableEntry> lootTable = ventureMobLootTables.get(ventureMobType);
                if (lootTable.isEmpty())
                {
                    return;
                }
                for (VentureLootTableEntry lootTableEntry : lootTable)
                {
                    ItemStack item = lootTableEntry.item();

                    int numerator = Math.max(0, lootTableEntry.numeratorChance()); // Numerator for drop chance. Cannot be less than 0
                    int denominator = Math.max(numerator, lootTableEntry.denominatorChance()); // Denominator for drop chance. Cannot be less than the numerator

                    int chance = VentureMath.getRandomInteger(denominator); // Chance rolled based on the denominator
                    if (!(chance <= numerator))
                    {
                        continue; // If the number rolled is NOT less than or equal to the numerator (meaning it is greater than the numerator), skip this iteration.
                    }

                    int minCount = Math.max(0, lootTableEntry.minDropCount()); // Min count cannot be less than 0
                    int maxCount = Math.max(minCount, lootTableEntry.maxDropCount()); // Max count cannot be less than min count
                    int lootingBonus = Math.max(0, lootTableEntry.lootingBonus()); // Looting bonus cannot be less than 0

                    int randomRangeDiff = 0; // Random range is defaulted to 0

                    if (!(maxCount - minCount == 0)) // If the difference of the max and the min are not 0, create a random range
                    {
                        randomRangeDiff = VentureMath.getRandomInteger(maxCount - minCount); // Generate random number based off the difference of the max and the min
                    }

                    int dropCount = minCount + randomRangeDiff/* + lootingBonus*/; // Drop count is determined by the min drop count, plus the random amount, plus the looting bonus

                    if (mob.getKiller() != null)
                    {
                        Player player = mob.getKiller();
                        StatPlayer statPlayer = new StatPlayer(player);
                        double playerMobLoot = statPlayer.getLootStats().getMobLooting();

                        if (playerMobLoot < 0)
                        {
                            int guaranteed = VentureMath.getGuaranteedFromChance(playerMobLoot);
                            int extraLootNumerator = VentureMath.getLastTwoDigitsChanceFromChance(playerMobLoot);

                            dropCount += (guaranteed * lootingBonus); // Add guaranteed bonus

                            int extraLootDenominator = VentureMath.getRandomInteger(100); // Denominator is 100

                            if (extraLootNumerator <= extraLootDenominator)
                            {
                                dropCount += lootingBonus; // Add bonus loot if number rolled is less than or equal to numerator (extraLootChance)
                            }
                        }
                    }

                    item.setAmount(dropCount); // Set the amount of the item to drop
                    event.getDrops().add(item); // Drop item
                }
            }
        }
    }
}
