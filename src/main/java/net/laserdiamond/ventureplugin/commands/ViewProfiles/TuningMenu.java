package net.laserdiamond.ventureplugin.commands.ViewProfiles;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.items.menuItems.misc.MiscMenuItems;
import net.laserdiamond.ventureplugin.items.menuItems.tuning.TuningMenuItems;
import net.laserdiamond.ventureplugin.items.util.ItemRegistry;
import net.laserdiamond.ventureplugin.stats.Components.DamageStats;
import net.laserdiamond.ventureplugin.stats.Components.DefenseStats;
import net.laserdiamond.ventureplugin.stats.Components.Stats;
import net.laserdiamond.ventureplugin.tuning.Components.TuningPoints;
import net.laserdiamond.ventureplugin.tuning.Components.TuningStats;
import net.laserdiamond.ventureplugin.util.Config.PlayerConfig;
import net.laserdiamond.ventureplugin.util.Permissions;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TuningMenu implements CommandExecutor, Listener {

    private final PlayerConfig baseStatsConfig;

    public TuningMenu(VenturePlugin plugin)
    {
        plugin.getCommand("tuning").setExecutor(this);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        baseStatsConfig = plugin.getBaseStatsConfig();
    }

    private Inventory tuningInventory(Player player)
    {
        Inventory tuningInventory = Bukkit.createInventory(null, 54, ChatColor.AQUA + player.getName() + "'s Tuning");

        for (TuningMenuItems.TuningItemSlots tuningItemSlots : TuningMenuItems.TuningItemSlots.values())
        {
            ItemStack tuningItemStack = tuningItemSlots.getVentureMenuItem().createItem(player).toItemStack();
            int inventorySlot = tuningItemSlots.getInventorySlot();
            tuningInventory.setItem(inventorySlot, tuningItemStack);
        }

        MiscMenuItems.placeExitButton(player, tuningInventory);
        MiscMenuItems.fillBlankSlotsPlayerInv(player, tuningInventory);
        return tuningInventory;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if (sender instanceof Player player)
        {
            if (sender.hasPermission(Permissions.TUNING_MENU.getPermission()))
            {
                player.openInventory(tuningInventory(player));
            } else
            {
                player.sendMessage(Messages.notAllowedCommand());
            }
        } else
        {
            sender.sendMessage(Messages.notPlayerCommand());
        }
        return true;
    }

    public static final String TUNING_INV_TITLE = "'s Tuning";

    @EventHandler
    public void clickInsideInv(InventoryClickEvent event)
    {
        HumanEntity humanEntity = event.getWhoClicked();
        Inventory clickedInv = event.getClickedInventory();
        if (humanEntity instanceof Player player)
        {
            if (clickedInv != null)
            {
                double healthValue = baseStatsConfig.getDouble("healthValue"),
                        defenseValue = baseStatsConfig.getDouble("defenseValue"),
                        speedValue = baseStatsConfig.getDouble("speedValue"),
                        manaValue = baseStatsConfig.getDouble("manaValue"),
                        meleeValue = baseStatsConfig.getDouble("meleeValue"),
                        magicValue = baseStatsConfig.getDouble("magicValue"),
                        rangeValue = baseStatsConfig.getDouble("rangeValue");

                String invTitle = event.getView().getTitle();
                int clickedSlot = event.getSlot();
                if (invTitle.contains(TUNING_INV_TITLE))
                {
                    event.setCancelled(true);

                    // Left click to increase
                    // Right click to decrease
                    ClickType clickType = event.getClick();
                    if (clickedSlot == TuningMenuItems.TuningItemSlots.HEALTH.getInventorySlot())
                    {
                        manipulateTuning(player, clickType, TuningMenuItems.TuningItemSlots.HEALTH, healthValue);

                    } else if (clickedSlot == TuningMenuItems.TuningItemSlots.DEFENSE.getInventorySlot())
                    {
                        manipulateTuning(player, clickType, TuningMenuItems.TuningItemSlots.DEFENSE, defenseValue);

                    } else if (clickedSlot == TuningMenuItems.TuningItemSlots.SPEED.getInventorySlot())
                    {
                        manipulateTuning(player, clickType, TuningMenuItems.TuningItemSlots.SPEED, speedValue);

                    } else if (clickedSlot == TuningMenuItems.TuningItemSlots.MANA.getInventorySlot())
                    {
                        manipulateTuning(player, clickType, TuningMenuItems.TuningItemSlots.MANA, manaValue);

                    } else if (clickedSlot == TuningMenuItems.TuningItemSlots.MELEE.getInventorySlot())
                    {
                        manipulateTuning(player, clickType, TuningMenuItems.TuningItemSlots.MELEE, meleeValue);

                    } else if (clickedSlot == TuningMenuItems.TuningItemSlots.MAGIC.getInventorySlot())
                    {
                        manipulateTuning(player, clickType, TuningMenuItems.TuningItemSlots.MAGIC, magicValue);

                    } else if (clickedSlot == TuningMenuItems.TuningItemSlots.RANGE.getInventorySlot())
                    {
                        manipulateTuning(player, clickType, TuningMenuItems.TuningItemSlots.RANGE, rangeValue);

                    }

                    ItemRegistry.renewItemNew(event.getCurrentItem(), player);
                    ItemRegistry.renewItemNew(clickedInv.getItem(TuningMenuItems.TuningItemSlots.POINTS.getInventorySlot()), player);
                }
            }
        }
    }



    private void manipulateTuning(Player player, ClickType clickType, TuningMenuItems.TuningItemSlots tuningStat, double statValue)
    {
        StatPlayer statPlayer = new StatPlayer(player);
        Stats stats = statPlayer.getStats();
        DamageStats damageStats = statPlayer.getDamageStats();
        DefenseStats defenseStats = statPlayer.getDefenseStats();

        TuningStats tuningStats = statPlayer.getTuningStats();
        TuningPoints tuningPoints = statPlayer.getTuningPointStats();

        int availablePoints = tuningPoints.getTuningPoints(),
                healthPoints = tuningPoints.getHealthPoints(),
                defensePoints = tuningPoints.getDefensePoints(),
                speedPoints = tuningPoints.getSpeedPoints(),
                manaPoints = tuningPoints.getManaPoints(),
                meleePoints = tuningPoints.getMeleePoints(),
                magicPoints = tuningPoints.getMagicPoints(),
                rangePoints = tuningPoints.getRangePoints();

        double tuningHealth = tuningStats.getHealth(),
                tuningDefense = tuningStats.getDefense(),
                tuningSpeed = tuningStats.getSpeed(),
                tuningMana = tuningStats.getMana(),
                tuningMelee = tuningStats.getMelee(),
                tuningMagic = tuningStats.getMagic(),
                tuningRange = tuningStats.getRange();

        int maxTuningHP = baseStatsConfig.getInt("healthMax"),
                maxTuningDefense = baseStatsConfig.getInt("defenseMax"),
                maxTuningSpeed = baseStatsConfig.getInt("speedMax"),
                maxTuningMana = baseStatsConfig.getInt("manaMax"),
                maxTuningMelee = baseStatsConfig.getInt("meleeMax"),
                maxTuningMagic = baseStatsConfig.getInt("magicMax"),
                maxTuningRange = baseStatsConfig.getInt("rangeMax");

        boolean hasPoints = availablePoints > 0;

        boolean maxedTuning;

        switch (tuningStat)
        {
            case HEALTH ->
            {
                maxedTuning = healthPoints + 1 <= maxTuningHP;

                if (clickType.isLeftClick())
                {
                    if (!hasPoints)
                    {
                        player.sendMessage(Messages.NO_TUNING_POINTS);
                        return;
                    }
                    if (!maxedTuning)
                    {
                        player.sendMessage(Messages.maxedStat(tuningStat));
                        return;
                    }
                    stats.setHealth(stats.getHealth() + statValue);
                    tuningStats.setHealth(tuningHealth + statValue);
                    tuningPoints.setHealthPoints(healthPoints + 1);
                    tuningPoints.setTuningPoints(availablePoints - 1);

                } else if (clickType.isRightClick())
                {
                    if (!(healthPoints > 0)) // Player does not have points invested in this stat
                    {
                        player.sendMessage(Messages.NO_INVESTED_POINTS);
                        return;
                    }
                    stats.setHealth(stats.getHealth() - statValue);
                    tuningStats.setHealth(tuningHealth - statValue);
                    tuningPoints.setHealthPoints(healthPoints - 1);
                    tuningPoints.setTuningPoints(availablePoints + 1);
                }
            }
            case DEFENSE ->
            {
                maxedTuning = defensePoints + 1 <= maxTuningDefense;

                if (clickType.isLeftClick())
                {
                    if (!hasPoints)
                    {
                        player.sendMessage(Messages.NO_TUNING_POINTS);
                        return;
                    }
                    if (!maxedTuning)
                    {
                        player.sendMessage(Messages.maxedStat(tuningStat));
                        return;
                    }
                    defenseStats.setDefense(defenseStats.getDefense() + statValue);
                    tuningStats.setDefense(tuningDefense + statValue);
                    tuningPoints.setDefensePoints(defensePoints + 1);
                    tuningPoints.setTuningPoints(availablePoints - 1);
                } else if (clickType.isRightClick())
                {
                    if (!(defensePoints > 0))
                    {
                        player.sendMessage(Messages.NO_INVESTED_POINTS);
                        return;
                    }
                    defenseStats.setDefense(defenseStats.getDefense() - statValue);
                    tuningStats.setDefense(tuningDefense - statValue);
                    tuningPoints.setDefensePoints(defensePoints - 1);
                    tuningPoints.setTuningPoints(availablePoints + 1);
                }
            }
            case SPEED ->
            {
                maxedTuning = speedPoints + 1 <= maxTuningSpeed;

                if (clickType.isLeftClick())
                {
                    if (!hasPoints)
                    {
                        player.sendMessage(Messages.NO_TUNING_POINTS);
                        return;
                    }
                    if (!maxedTuning)
                    {
                        player.sendMessage(Messages.maxedStat(tuningStat));
                        return;
                    }
                    stats.setSpeed(stats.getSpeed() + statValue);
                    tuningStats.setSpeed(tuningSpeed + statValue);
                    tuningPoints.setSpeedPoints(speedPoints + 1);
                    tuningPoints.setTuningPoints(availablePoints - 1);
                } else if (clickType.isRightClick())
                {
                    if (!(speedPoints > 0))
                    {
                        player.sendMessage(Messages.NO_INVESTED_POINTS);
                        return;
                    }
                    stats.setSpeed(stats.getSpeed() - statValue);
                    tuningStats.setSpeed(tuningSpeed - statValue);
                    tuningPoints.setSpeedPoints(speedPoints - 1);
                    tuningPoints.setTuningPoints(availablePoints + 1);
                }
            }
            case MANA ->
            {
                maxedTuning = manaPoints + 1 <= maxTuningMana;

                if (clickType.isLeftClick())
                {
                    if (!hasPoints)
                    {
                        player.sendMessage(Messages.NO_TUNING_POINTS);
                        return;
                    }
                    if (!maxedTuning)
                    {
                        player.sendMessage(Messages.maxedStat(tuningStat));
                        return;
                    }
                    stats.setMaxMana(stats.getMaxMana() + statValue);
                    tuningStats.setMana(tuningMana + statValue);
                    tuningPoints.setManaPoints(manaPoints + 1);
                    tuningPoints.setTuningPoints(availablePoints - 1);
                } else if (clickType.isRightClick())
                {
                    if (!(manaPoints > 0))
                    {
                        player.sendMessage(Messages.NO_INVESTED_POINTS);
                        return;
                    }
                    stats.setMaxMana(stats.getMaxMana() - statValue);
                    tuningStats.setMana(tuningMana - statValue);
                    tuningPoints.setManaPoints(manaPoints - 1);
                    tuningPoints.setTuningPoints(availablePoints + 1);
                }
            }
            case MELEE ->
            {
                maxedTuning = meleePoints + 1 <= maxTuningMelee;

                if (clickType.isLeftClick())
                {
                    if (!hasPoints)
                    {
                        player.sendMessage(Messages.NO_TUNING_POINTS);
                        return;
                    }
                    if (!maxedTuning)
                    {
                        player.sendMessage(Messages.maxedStat(tuningStat));
                        return;
                    }
                    damageStats.setBaseMeleeDmg(damageStats.getBaseMelee() + statValue);
                    tuningStats.setMelee(tuningMelee + statValue);
                    tuningPoints.setMeleePoints(meleePoints + 1);
                    tuningPoints.setTuningPoints(availablePoints - 1);
                } else if (clickType.isRightClick())
                {
                    if (!(meleePoints > 0))
                    {
                        player.sendMessage(Messages.NO_INVESTED_POINTS);
                        return;
                    }
                    damageStats.setBaseMeleeDmg(damageStats.getBaseMelee() - statValue);
                    tuningStats.setMelee(tuningMelee - statValue);
                    tuningPoints.setMeleePoints(meleePoints - 1);
                    tuningPoints.setTuningPoints(availablePoints + 1);
                }
            }
            case MAGIC ->
            {
                maxedTuning = magicPoints + 1 <= maxTuningMagic;

                if (clickType.isLeftClick())
                {
                    if (!hasPoints)
                    {
                        player.sendMessage(Messages.NO_TUNING_POINTS);
                        return;
                    }
                    if (!maxedTuning)
                    {
                        player.sendMessage(Messages.maxedStat(tuningStat));
                        return;
                    }
                    damageStats.setBaseMagicDmg(damageStats.getBaseMagic() + statValue);
                    tuningStats.setMagic(tuningMagic + statValue);
                    tuningPoints.setMagicPoints(magicPoints + 1);
                    tuningPoints.setTuningPoints(availablePoints - 1);
                } else if (clickType.isRightClick())
                {
                    if (!(magicPoints > 0))
                    {
                        player.sendMessage(Messages.NO_INVESTED_POINTS);
                        return;
                    }
                    damageStats.setBaseMagicDmg(damageStats.getBaseMagic() - statValue);
                    tuningStats.setMagic(tuningMagic - statValue);
                    tuningPoints.setMagicPoints(magicPoints - 1);
                    tuningPoints.setTuningPoints(availablePoints + 1);
                }
            }
            case RANGE ->
            {
                maxedTuning = rangePoints + 1 <= maxTuningRange;

                if (clickType.isLeftClick())
                {
                    if (!hasPoints)
                    {
                        player.sendMessage(Messages.NO_TUNING_POINTS);
                        return;
                    }
                    if (!maxedTuning)
                    {
                        player.sendMessage(Messages.maxedStat(tuningStat));
                        return;
                    }
                    damageStats.setBaseRangeDmg(damageStats.getBaseRange() + statValue);
                    tuningStats.setRange(tuningRange + statValue);
                    tuningPoints.setRangePoints(rangePoints + 1);
                    tuningPoints.setTuningPoints(availablePoints - 1);
                } else if (clickType.isRightClick())
                {
                    if (!(rangePoints > 0))
                    {
                        player.sendMessage(Messages.NO_INVESTED_POINTS);
                        return;
                    }
                    damageStats.setBaseRangeDmg(damageStats.getBaseRange() - statValue);
                    tuningStats.setRange(tuningRange - statValue);
                    tuningPoints.setRangePoints(rangePoints - 1);
                    tuningPoints.setTuningPoints(availablePoints + 1);
                }
            }
        }
    }
}
