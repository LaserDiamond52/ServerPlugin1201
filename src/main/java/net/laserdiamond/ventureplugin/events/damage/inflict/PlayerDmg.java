package net.laserdiamond.ventureplugin.events.damage.inflict;

import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.events.damage.ApplyDefense;
import net.laserdiamond.ventureplugin.events.damage.PlayerMagicDamageEvent;
import net.laserdiamond.ventureplugin.events.damage.PlayerMeleeDamageEvent;
import net.laserdiamond.ventureplugin.events.damage.PlayerRangeDamageEvent;
import net.laserdiamond.ventureplugin.stats.Components.DamageStats;
import net.laserdiamond.ventureplugin.stats.Components.DefenseStats;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDmg implements Listener {

    @EventHandler
    private void meleeDmg(PlayerMeleeDamageEvent event)
    {
        Player player = event.getPlayer();
        LivingEntity target = event.getTarget();
        double damage = event.getDamage();
        StatPlayer statPlayer = new StatPlayer(player);
        DamageStats damageStats = statPlayer.getDamageStats();
        double baseMelee = damageStats.getBaseMelee();
        double percentMelee = 1 + (damageStats.getPercentMelee() * 0.01);

        double meleeDmg = (baseMelee + damage) * percentMelee;

        if (target instanceof Player playerTarget)
        {
            StatPlayer statPlayerTarget = new StatPlayer(playerTarget);
            DefenseStats defenseStats = statPlayerTarget.getDefenseStats();
            double defense = defenseStats.getDefense();
            meleeDmg = ApplyDefense.finalDamage(defense, meleeDmg);
        }

        if (event.isInflictDamage())
        {
            target.damage(meleeDmg, player);
        }
    }

    @EventHandler
    private void magicDmg(PlayerMagicDamageEvent event)
    {
        Player player = event.getPlayer();
        LivingEntity target = event.getTarget();
        double damage = event.getDamage();
        StatPlayer statPlayer = new StatPlayer(player);
        DamageStats damageStats = statPlayer.getDamageStats();
        double baseMagic = damageStats.getBaseMagic();
        double percentMagic = 1 + (damageStats.getPercentMagic() * 0.01);

        double magicDmg = (baseMagic + damage) * percentMagic;

        if (target instanceof Player playerTarget)
        {
            StatPlayer statPlayerTarget = new StatPlayer(playerTarget);
            DefenseStats defenseStats = statPlayerTarget.getDefenseStats();
            double defense = defenseStats.getDefense();
            double magicDefense = defenseStats.getMagicDefense();
            magicDmg = ApplyDefense.finalDamage(defense + magicDefense, magicDmg);
        }

        if (event.isInflictDamage())
        {
            target.damage(magicDmg, player);
        }
    }

    @EventHandler
    private void rangeDmg(PlayerRangeDamageEvent event)
    {
        Player player = event.getPlayer();
        LivingEntity target = event.getTarget();
        double damage = event.getDamage();
        StatPlayer statPlayer = new StatPlayer(player);
        DamageStats damageStats = statPlayer.getDamageStats();
        double baseRange = damageStats.getBaseRange();
        double percentRange = 1 + (damageStats.getPercentRange() * 0.01);

        double rangeDmg = (baseRange + damage) * percentRange;

        //player.sendMessage("event damage: " + rangeDmg);

        if (target instanceof Player playerTarget)
        {
            StatPlayer statPlayerTarget = new StatPlayer(playerTarget);
            DefenseStats defenseStats = statPlayerTarget.getDefenseStats();
            double defense = defenseStats.getDefense();
            double projectileDefense = defenseStats.getProjectileDefense();
            rangeDmg = ApplyDefense.finalDamage(defense + projectileDefense, rangeDmg);
        }

        if (event.isInflictDamage())
        {
            target.damage(rangeDmg, player);
        }
    }

    @EventHandler
    private void combatSkillDamage(EntityDamageByEntityEvent event)
    {
        if (event.getDamager() instanceof Player player)
        {
            if (event.getEntity() instanceof LivingEntity)
            {
                StatPlayer statPlayer = new StatPlayer(player);
                double damage = event.getDamage();

                double combatDamageBonus = 1 + (statPlayer.getSkillsReward().getCombatDamageBonus() * 0.01);
                event.setDamage(damage * combatDamageBonus);
            }
        }
    }
}
