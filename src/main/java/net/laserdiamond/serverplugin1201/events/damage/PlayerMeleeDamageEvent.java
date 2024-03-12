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
import org.jetbrains.annotations.NotNull;

public class PlayerMeleeDamageEvent extends PlayerEvent implements Cancellable {

    /**
     * Event that increases Player damage by their melee damage modifiers. If the target is a player, their defense points will reduce damage accordingly
     * <p>
     * Can choose whether the damage should be inflicted or not, but does not cancel the event
     * <p>
     * This event is mainly for melee attacks that involve ranged moves that scale with melee damage
     */
    private LivingEntity target;
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

        StatPlayer statPlayer = new StatPlayer(player);
        DamageStats damageStats = statPlayer.getDamageStats();
        double pMelee = damageStats.getbMeleeDmg();
        double pMeleeIncrease = 1 + (damageStats.getpMeleeDmg() * 0.01);

        double meleeDamage = (pMelee + damage) * pMeleeIncrease;

        if (target instanceof Player playerTarget) // Check if target is a player
        {
            StatPlayer statPlayerTarget = new StatPlayer(playerTarget);
            DefenseStats defenseStats = statPlayerTarget.getDefenseStats();
            double defense = defenseStats.getDefense();
            meleeDamage = ApplyDefense.finalDamage(defense, meleeDamage); // Apply defense
        } else if (target instanceof Mob mob)
        {
            if (player.getGameMode() != GameMode.CREATIVE || player.getGameMode() != GameMode.SPECTATOR)
            {
                if (mob instanceof IronGolem ironGolem)
                {
                    if (!ironGolem.isPlayerCreated())
                    {
                        mob.setTarget(player);
                    }
                } else {
                    mob.setTarget(player);
                }
            }
        }

        EntityHealthDisplayChangeEvent entityHealthDisplayChangeEvent = new EntityHealthDisplayChangeEvent(target, meleeDamage, true);
        Bukkit.getPluginManager().callEvent(entityHealthDisplayChangeEvent);

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

    public void setTarget(LivingEntity target) {
        this.target = target;
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