package net.laserdiamond.serverplugin1201.events.damage;

import net.laserdiamond.serverplugin1201.entities.player.StatPlayer;
import net.laserdiamond.serverplugin1201.stats.Components.DamageStats;
import net.laserdiamond.serverplugin1201.stats.Components.DefenseStats;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerRangeDamageEvent extends PlayerEvent implements Cancellable {

    /**
     * Event that increases Player damage by their range damage modifiers. If the target is a player, their defense points will reduce damage accordingly
     * <p>
     * Can choose whether the damage should be inflicted or not, but does not cancel the event
     */
    private LivingEntity target;
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private boolean isCancelled;
    private boolean inflictDamage;

    public PlayerRangeDamageEvent(final Player player, LivingEntity target, double damage, boolean inflictDamage)
    {
        super(player);
        this.target = target;
        this.isCancelled = false;
        this.inflictDamage = inflictDamage;
    }

    public static void run(final Player player, LivingEntity target, double damage, boolean inflictDamage)
    {
        StatPlayer statPlayer = new StatPlayer(player);
        DamageStats damageStats = statPlayer.getDamageStats();
        double bRange = damageStats.getbRangeDmg();
        double pRangeIncrease = 1 + (damageStats.getpRangeDmg() * 0.02);

        double rangeDamage = (bRange + damage) * pRangeIncrease;

        if (target instanceof Player playerTarget)
        {
            StatPlayer statPlayerTarget = new StatPlayer(playerTarget);
            DefenseStats defenseStats = statPlayerTarget.getDefenseStats();
            double defense = defenseStats.getDefense();
            double projectileDefense = defenseStats.getProjectileDefense();
            rangeDamage = ApplyDefense.finalDamage(defense + projectileDefense, rangeDamage); // Apply projectile defense armor
        }

        if (inflictDamage)
        {
            target.damage(rangeDamage, player);
        }
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

    public LivingEntity getTarget() {
        return target;
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    public boolean isInflictDamage() {
        return inflictDamage;
    }

    public void setInflictDamage(boolean inflictDamage) {
        this.inflictDamage = inflictDamage;
    }
}
