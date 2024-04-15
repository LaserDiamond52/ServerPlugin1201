package net.laserdiamond.ventureplugin.events.effects.Components;

import io.papermc.paper.event.entity.EntityMoveEvent;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.events.effects.Components.Timers.ManaFreezeTimer;
import net.laserdiamond.ventureplugin.events.effects.Components.Timers.NecrosisTimer;
import net.laserdiamond.ventureplugin.events.effects.Components.Timers.ParalyzeTimer;
import net.laserdiamond.ventureplugin.events.effects.Components.Timers.VulnerableTimer;
import net.laserdiamond.ventureplugin.events.effects.EffectBoolean;
import net.laserdiamond.ventureplugin.events.effects.Managers.EffectManager;
import net.laserdiamond.ventureplugin.stats.Components.PotionStats;
import net.laserdiamond.ventureplugin.util.EffectKeys;
import net.laserdiamond.ventureplugin.util.Randomizer;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class EffectEvents implements Listener {

    private VenturePlugin plugin;
    private EffectManager effectManager;

    public EffectEvents(VenturePlugin plugin) {
        this.plugin = plugin;
        effectManager = plugin.getEffectManager();
    }

    @EventHandler
    private void milkRemoveEffect(PlayerItemConsumeEvent event) {

        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();

        EffectDurations effectDurations = effectManager.getEffectProfile(player.getUniqueId()).getEffectDurations();
        int necrosisDuration = effectDurations.getNecrosisDuration();
        int manaFreezeDuration = effectDurations.getManaFreezeDuration();
        int vulnerableDuration = effectDurations.getVulnerableDuration();

        if (itemStack.getType().equals(Material.MILK_BUCKET)) {
            if (!ManaFreezeTimer.hasNoEffect(player)) {
                //effectDurations.setManaFreezeDuration(0);
                ManaFreezeTimer.setTimer(player, 0, 0);
            }
            if (!NecrosisTimer.hasNoEffect(player)) {
                //effectDurations.setNecrosisDuration(0);
                NecrosisTimer.setTimer(player, 0,0);
            }
            if (!VulnerableTimer.hasNoEffect(player)) {
                //effectDurations.setVulnerableDuration(0);
                VulnerableTimer.setTimer(player, 0,0);
            }
        }
    }

    @EventHandler
    private void EntityDamage(EntityDamageEvent event) {

        double damage = event.getDamage();

        if (event.getEntity() instanceof LivingEntity livingEntity) {

            if (!VulnerableTimer.hasNoEffect(livingEntity)) {
                event.setDamage(damage * 1.25);
            }

            if (EffectBoolean.hasEffect(livingEntity, EffectKeys.GOD)) {
                event.setCancelled(true);
            }
        }

        /*
        if (event.getEntity() instanceof Player player) {
            if (EffectBoolean.hasEffect(player, effectKeys.GOD)) {
                double maxHealth = player.getMaxHealth();
                player.setMaxHealth(maxHealth);
                event.setCancelled(true);
            }
            if (EffectBoolean.hasEffect(player, effectKeys.VULNERABLE)) {
                EffectDurations effectDurations = effectManager.getEffectProfile(player.getUniqueId()).getEffectDurations();
                int vulnerableDuration = effectDurations.getVulnerableDuration();
                if (vulnerableDuration > 0) {
                    event.setDamage(damage * 1.25);
                }
            }
        } else if (event.getEntity() instanceof Mob mob) {
            if (EffectBoolean.hasEffectKey(mob, effectKeys.VULNERABLE)) {
                if (EffectBoolean.hasEffect(mob, effectKeys.VULNERABLE)) {
                    event.setDamage(damage * 1.25);
                }
            }
        }

         */
    }

    private static final List<EntityPotionEffectEvent.Cause> EFFECT_CAUSES = new ArrayList<>();
    static
    {
        EFFECT_CAUSES.add(EntityPotionEffectEvent.Cause.BEACON);
        EFFECT_CAUSES.add(EntityPotionEffectEvent.Cause.COMMAND);
        EFFECT_CAUSES.add(EntityPotionEffectEvent.Cause.CONDUIT);
        EFFECT_CAUSES.add(EntityPotionEffectEvent.Cause.PLUGIN);
        EFFECT_CAUSES.add(EntityPotionEffectEvent.Cause.TURTLE_HELMET);
        EFFECT_CAUSES.add(EntityPotionEffectEvent.Cause.UNKNOWN);
    }


    @EventHandler
    private void effectGain(EntityPotionEffectEvent event)
    {
        if (event.getEntity() instanceof Player player)
        {
            StatPlayer statPlayer = new StatPlayer(player);
            PotionStats potionStats = statPlayer.getPotionStats();

            PotionEffect newEffect = event.getNewEffect();
            if (newEffect != null)
            {
                int duration = (int) (newEffect.getDuration() * (1 + (potionStats.getLongevity() * 0.01)));
                int amplifier = newEffect.getAmplifier();
                PotionEffectType.Category category = newEffect.getType().getEffectCategory();

                if (EFFECT_CAUSES.contains(event.getCause()))
                {
                    return;
                }
                if (category == PotionEffectType.Category.BENEFICIAL && !newEffect.isInfinite())
                {
                    event.setCancelled(true);
                    PotionEffectType potionEffectType = event.getModifiedType();

                    int baseCaffeinatedAmp = ((int) potionStats.getCaffeination()) / 100; // Gets the base amplifier increase from the Caffeination Stat

                    // TODO: chance with remaining caffeination points
                    int bonusChance = ((int) potionStats.getCaffeination()) % 100; // Get the % chance of adding +1 to the amplifier

                    Integer randomChoice = Randomizer.getRandomInteger(bonusChance); // Random number rolled

                    int bonusAmp;
                    if (randomChoice <= bonusChance) // Determine if player should have an extra amplifier level
                    {
                        bonusAmp = 1;
                    } else
                    {
                        bonusAmp = 0;
                    }

                    int finalAmplifier = amplifier + baseCaffeinatedAmp + bonusAmp;

                    player.addPotionEffect(potionEffectType.createEffect(duration, finalAmplifier));
                }
            }
        }
    }

    @EventHandler
    private void PotionEffect(EntityPotionEffectEvent event) {

        if (event.getEntity() instanceof Player player) {
            if (EffectBoolean.hasEffect(player, EffectKeys.GOD)) {
                if (event.getNewEffect() != null) {
                    PotionEffectType incomingEffect = event.getNewEffect().getType();
                    if (incomingEffect.equals(PotionEffectType.POISON) ||
                            incomingEffect.equals(PotionEffectType.BLINDNESS) ||
                            incomingEffect.equals(PotionEffectType.DARKNESS) ||
                            incomingEffect.equals(PotionEffectType.HARM) ||
                            incomingEffect.equals(PotionEffectType.WITHER) ||
                            incomingEffect.equals(PotionEffectType.SLOW) ||
                            incomingEffect.equals(PotionEffectType.SLOW_DIGGING) ||
                            incomingEffect.equals(PotionEffectType.HUNGER) ||
                            incomingEffect.equals(PotionEffectType.WEAKNESS) ||
                            incomingEffect.equals(PotionEffectType.HEAL))
                    {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    private void godImmunePotionSplash(PotionSplashEvent event) {

        for (LivingEntity livingEntity : event.getAffectedEntities()) {
            if (livingEntity instanceof Player player) {
                if (EffectBoolean.hasEffect(player, EffectKeys.GOD)) {
                    event.setIntensity(player, 0);
                }
            }

        }

    }

    @EventHandler
    private void godImmuneAreaEffectCloud(AreaEffectCloudApplyEvent event) {

        for (LivingEntity livingEntity : event.getAffectedEntities()) {
            if (livingEntity instanceof Player player) {
                if (EffectBoolean.hasEffect(player, EffectKeys.GOD)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    private void godDeath(PlayerDeathEvent event) {

        Player player = event.getPlayer();

        if (EffectBoolean.hasEffect(player, EffectKeys.GOD)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void cancelGodTP(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        if (EffectBoolean.hasEffect(player, EffectKeys.GOD)) {
            if (event.getCause() == PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT) {
                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    private void disableHealingNecrosis(EntityRegainHealthEvent event) {

        if (event.getEntity() instanceof LivingEntity livingEntity) {
            if (!NecrosisTimer.hasNoEffect(livingEntity)) {
                event.setCancelled(true);
            }
        }
        /*
        if (event.getEntity() instanceof Mob mob) {
            if (EffectBoolean.hasEffect(mob, effectKeys.NECROSIS)) {
                if (!event.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.WITHER_SPAWN)) {
                    event.setCancelled(true);
                }
            }
        } else if (event.getEntity() instanceof Player player) {
            EffectDurations effectDuration = effectManager.getEffectProfile(player.getUniqueId()).getEffectDurations();
            double necrosisDuration = effectDuration.getNecrosisDuration();
            if (necrosisDuration > 0) {
                event.setCancelled(true);
            }
        }

         */
    }

    @EventHandler
    private void paralyzeMovementEntity(EntityMoveEvent event) {

        if (!ParalyzeTimer.hasNoEffect(event.getEntity())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void paralyzeMovementPlayer(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!ParalyzeTimer.hasNoEffect(player)) {
            event.setCancelled(true);
        }
        /*
        EffectDurations effectDurations = effectManager.getEffectProfile(player.getUniqueId()).getEffectDurations();
        int paralyzeDuration = effectDurations.getParalyzeDuration();
        if (paralyzeDuration > 0) {
            event.setCancelled(true);
        }

         */
    }
}
