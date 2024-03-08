package net.laserdiamond.serverplugin1201.events.Stats;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.stats.Components.DamageStats;
import net.laserdiamond.serverplugin1201.stats.Manager.StatProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerRangeDamageEvent extends Event implements Cancellable {

    private final ServerPlugin1201 plugin = ServerPlugin1201.getInstance();
    private final StatProfileManager statProfileManager = plugin.getStatProfileManager();
    private Player player;
    private LivingEntity livingEntity;
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private boolean isCancelled;

    public PlayerRangeDamageEvent(Player player, LivingEntity livingEntity, double damage)
    {
        this.player = player;
        this.livingEntity = livingEntity;
        this.isCancelled = false;

        DamageStats damageStats = statProfileManager.getStatProfile(player.getUniqueId()).damageStats();
        double bRange = damageStats.getbRangeDmg();
        double pRangeIncrease = 1 + (damageStats.getpRangeDmg() * 0.02);

        double finalDamage = (bRange + damage) * pRangeIncrease;
        livingEntity.damage(finalDamage, player);
        EntityDamageEvent dmgEvent = new EntityDamageEvent(livingEntity, EntityDamageEvent.DamageCause.PROJECTILE, finalDamage);
        livingEntity.setLastDamageCause(dmgEvent);
        Bukkit.getPluginManager().callEvent(dmgEvent);
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public LivingEntity  getLivingEntity() {
        return livingEntity;
    }

    public void setLivingEntity(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
    }
}
