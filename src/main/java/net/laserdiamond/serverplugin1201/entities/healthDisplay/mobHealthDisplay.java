package net.laserdiamond.serverplugin1201.entities.healthDisplay;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.entities.management.Mobs;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.text.DecimalFormat;

public class mobHealthDisplay implements Listener {

    private final ServerPlugin1201 plugin;

    public mobHealthDisplay(ServerPlugin1201 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void nameOnSpawn(CreatureSpawnEvent event)
    {
        LivingEntity livingEntity = event.getEntity();
        DecimalFormat doubleDecimalPlace = new DecimalFormat("0.00");
        double health = livingEntity.getHealth();
        double maxHealth = livingEntity.getMaxHealth();

        if (Mobs.vanillaMobs.isOfMob(livingEntity.getType()))
        {
            String name = Mobs.vanillaMobs.getMobName(livingEntity.getType());
            livingEntity.setCustomName(name + ChatColor.BLACK + " | " + ChatColor.YELLOW + doubleDecimalPlace.format(health) + ChatColor.WHITE + "/" + ChatColor.RED + doubleDecimalPlace.format(maxHealth));

        }
    }

    /*
    @EventHandler
    public void onDamage(EntityDamageEvent event)
    {
        Entity entity = event.getEntity();
        double damage = event.getFinalDamage();
        DecimalFormat doubleDecimalPlace = new DecimalFormat("0.00");

        if (entity instanceof LivingEntity livingEntity) {
            if (Mobs.vanillaMobs.isOfMob(entity.getType()))
            {
                double health = livingEntity.getHealth();
                double maxHealth = livingEntity.getMaxHealth();

                String name = Mobs.vanillaMobs.getMobName(entity.getType());

                if (!event.isCancelled()) // Check if event is not cancelled, and that mob will receive the damage
                {

                    livingEntity.setCustomName(name + ChatColor.BLACK + " | " + ChatColor.YELLOW + doubleDecimalPlace.format(Math.max(0, health - damage)) + ChatColor.WHITE + "/" + ChatColor.RED + doubleDecimalPlace.format(maxHealth));

                }
            }
        }
    }

    @EventHandler
    public void onHeal(EntityRegainHealthEvent event) {

        Entity entity = event.getEntity();
        double heal = event.getAmount();
        DecimalFormat doubleDecimalPlace = new DecimalFormat("0.00");

        if (entity instanceof LivingEntity livingEntity)
        {
            if (Mobs.vanillaMobs.isOfMob(entity.getType()))
            {
                double health = livingEntity.getHealth();
                double maxHealth = livingEntity.getMaxHealth();

                String name = Mobs.vanillaMobs.getMobName(entity.getType());

                if (!event.isCancelled()) // Check if entity will receive healing
                {
                    livingEntity.setCustomName(name + ChatColor.BLACK + " | " + ChatColor.YELLOW + doubleDecimalPlace.format(health + heal) + ChatColor.WHITE + "/" + ChatColor.RED + doubleDecimalPlace.format(maxHealth));
                }
            }
        }
    }

     */
}
