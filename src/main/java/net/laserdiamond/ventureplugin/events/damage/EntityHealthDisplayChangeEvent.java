package net.laserdiamond.ventureplugin.events.damage;

import net.laserdiamond.ventureplugin.entities.management.Mobs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

/**
 * This event is called when the health in the display name of a mob changes due to healing, damage, etc.
 */
public class EntityHealthDisplayChangeEvent extends EntityEvent implements Cancellable {


    private final double amount;
    private final boolean isDamage;
    private boolean isCancelled;
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public EntityHealthDisplayChangeEvent(final Entity entity, double amount, boolean isDamage) {
        super(entity);
        this.amount = amount;
        this.isDamage = isDamage;
    }

    /**
     * A method that runs the event's intended function
     * @param entity The entity to run this event on
     * @param amount The amount of health changed on the entity
     * @param isDamage Was the amount of health changed damage or healing?
     */
    public static void run(final Entity entity, double amount, boolean isDamage)
    {
        DecimalFormat doubleDecimalPlace = new DecimalFormat("0.00");

        if (entity instanceof Mob mob)
        {
            if (Mobs.vanillaMobs.isOfMob(mob.getType()))
            {
                double health = mob.getHealth();
                double maxHealth = mob.getMaxHealth();

                String name = Mobs.vanillaMobs.getMobName(mob.getType());

                if (isDamage)
                {
                    mob.setCustomName(name + ChatColor.BLACK + " | " + ChatColor.YELLOW + doubleDecimalPlace.format(Math.max(0, health - amount)) + ChatColor.WHITE + "/" + ChatColor.RED + doubleDecimalPlace.format(maxHealth));
                } else
                {
                    mob.setCustomName(name + ChatColor.BLACK + " | " + ChatColor.YELLOW + doubleDecimalPlace.format(Math.min(maxHealth, health + amount)) + ChatColor.WHITE + "/" + ChatColor.RED + doubleDecimalPlace.format(maxHealth));
                }
            }
        }
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    /**
     * Gets the amount of health changed in the entity's health name tag
     * @return The amount of health changed
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Was the result of the event from damage or healing?
     * @return True if damage; false if healing
     */
    public boolean isDamage() {
        return isDamage;
    }
}
