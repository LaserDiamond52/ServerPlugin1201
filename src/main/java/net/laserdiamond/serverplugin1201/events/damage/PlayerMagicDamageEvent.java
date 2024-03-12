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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerEvent;

public class PlayerMagicDamageEvent extends PlayerEvent implements Cancellable {

    /**
     * Event that increases Player damage by their magic damage modifiers. If the target is a player, their defense points will reduce damage accordingly
     * <p>
     * Can choose whether the damage should be inflicted or not, but does not cancel the event
     */
    private LivingEntity target;
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private double damage;
    private boolean isCancelled;
    private boolean inflictDamage;

    public PlayerMagicDamageEvent(Player player, LivingEntity target, double damage, boolean inflictDamage)
    {
        super(player);
        this.target = target;
        this.damage = damage;
        this.isCancelled = false;
        this.inflictDamage = inflictDamage;

        StatPlayer statPlayer = new StatPlayer(player);
        DamageStats damageStats = statPlayer.getDamageStats();
        double bMagic = damageStats.getbMagicDmg();
        double pMagicIncrease = 1 + (damageStats.getpMagicDmg() * 0.01);

        double magicDamage = (bMagic + damage) * pMagicIncrease;

        if (target instanceof Player playerTarget) // Check if target is a player
        {
            StatPlayer statPlayerTarget = new StatPlayer(playerTarget);
            DefenseStats defenseStats = statPlayerTarget.getDefenseStats();
            double defense = defenseStats.getDefense();
            double magicDefense = defenseStats.getMagicDefense();
            magicDamage = ApplyDefense.finalDamage(defense + magicDefense, magicDamage); // Apply magic protection armor
        }

        EntityHealthDisplayChangeEvent entityHealthDisplayChangeEvent = new EntityHealthDisplayChangeEvent(target, magicDamage, true);
        Bukkit.getPluginManager().callEvent(entityHealthDisplayChangeEvent);

        if (inflictDamage)
        {
            target.damage(magicDamage, player);
        }



        // TODO: Make Magic Damage Accurate + Display as magic damage
        // 1. Check if target is a player
        // 2. Get magic protection defense points and reduce damage accordingly
        // 3. Display damage display
        // 4. Find some way to figure out not to display other damage display?

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

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}