package net.laserdiamond.ventureplugin.events.damage;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.stats.Components.DamageStats;
import net.laserdiamond.ventureplugin.stats.Manager.StatProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

public class MagicDamageEvent extends EntityDamageByEntityEvent implements Cancellable {

    private final VenturePlugin plugin = VenturePlugin.getInstance();
    private final StatProfileManager statProfileManager = plugin.getStatProfileManager();
    private Entity damager;
    private Entity target;
    private double damage;
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private boolean isCancelled;

    @Deprecated
    public MagicDamageEvent(@NotNull Entity damager, @NotNull Entity damagee, @NotNull EntityDamageEvent.DamageCause cause, double damage) {
        super(damager, damagee, cause, damage);
        cause = DamageCause.MAGIC;

        if (damager instanceof Player player)
        {
            if (damagee instanceof LivingEntity livingEntity)
            {
                DamageStats damageStats = statProfileManager.getStatProfile(player.getUniqueId()).damageStats();
                double bMagic = damageStats.getbMagicDmg();
                double pMagicIncrease = 1 + (damageStats.getpMagicDmg() * 0.01);

                damage = (bMagic + damage) * pMagicIncrease;

                this.setDamage(damage);
                EntityDamageEvent magicTag = new EntityDamageEvent(livingEntity, cause, damage);
                livingEntity.setLastDamageCause(magicTag);
                Bukkit.getPluginManager().callEvent(magicTag);


                if (livingEntity.getLastDamageCause() != null) {
                    if (livingEntity.getLastDamageCause().getCause() == cause) {
                        player.sendMessage("magic damage");
                    }
                }
                
            }
        }
        
        
    }

    @Override
    public boolean isCancelled()
    {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled)
    {
        this.isCancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers()
    {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList()
    {
        return HANDLER_LIST;
    }
}
