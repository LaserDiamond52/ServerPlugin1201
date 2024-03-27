package net.laserdiamond.ventureplugin.events.damage;

import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.stats.Components.DamageStats;
import net.laserdiamond.ventureplugin.stats.Components.DefenseStats;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Event that increases Player damage by their melee damage modifiers. If the target is a player, their defense points will reduce damage accordingly
 * <p>
 * Can choose whether the damage should be inflicted or not, but does not cancel the event
 * <p>
 * This event is mainly for melee attacks that involve ranged moves that scale with melee damage
 */
public class PlayerMeleeDamageEvent extends PlayerEvent implements Cancellable {


    private final LivingEntity target;
    private double damage;
    private boolean inflictDamage;
    private boolean isCancelled;
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public PlayerMeleeDamageEvent(final Player player, LivingEntity target, double damage, boolean inflictDamage)
    {
        super(player);
        this.target = target;
        this.damage = damage;
        this.inflictDamage = inflictDamage;
    }

    public static void run(final Player player, LivingEntity target, double damage, boolean inflictDamage)
    {
        StatPlayer statPlayer = new StatPlayer(player);
        DamageStats damageStats = statPlayer.getDamageStats();
        double pMelee = damageStats.getBaseMelee();
        double pMeleeIncrease = 1 + (damageStats.getPercentMelee() * 0.01);

        double meleeDamage = (pMelee + damage) * pMeleeIncrease;

        if (target instanceof Player playerTarget) // Check if target is a player
        {
            StatPlayer statPlayerTarget = new StatPlayer(playerTarget);
            DefenseStats defenseStats = statPlayerTarget.getDefenseStats();
            double defense = defenseStats.getDefense();
            meleeDamage = ApplyDefense.finalDamage(defense, meleeDamage); // Apply defense
        }

        if (inflictDamage)
        {
            target.damage(meleeDamage, player);
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

    public static HandlerList getHandlerList()
    {
        return HANDLER_LIST;
    }


    public LivingEntity getTarget() {
        return target;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public boolean isInflictDamage() {
        return inflictDamage;
    }

    public void setInflictDamage(boolean inflictDamage) {
        this.inflictDamage = inflictDamage;
    }
}
