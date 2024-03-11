package net.laserdiamond.serverplugin1201.events.damage;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.entities.management.Mobs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class EntityHealthDisplayChangeEvent extends EntityEvent implements Cancellable {

    private final ServerPlugin1201 plugin = ServerPlugin1201.getInstance();
    private double amount; // Amount we want to change current health by
    private boolean isDamage; // true = damage; false = heal
    private boolean isCancelled;
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public EntityHealthDisplayChangeEvent(Entity entity, double amount, boolean isDamage) {
        super(entity);
        this.amount = amount;
        this.isDamage = isDamage;

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isDamage() {
        return isDamage;
    }

    public void setDamage(boolean damage) {
        isDamage = damage;
    }
}
