package net.laserdiamond.ventureplugin.events.damage;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.management.Mobs;
import net.laserdiamond.ventureplugin.entities.util.VentureMobBuilder;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.stats.Components.DamageStats;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;

/**
 * Class that handles events regarding mob health tags and damage indicators
 */
public class DamageDisplays implements Listener {

    private final VenturePlugin plugin;

    public DamageDisplays(VenturePlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Formats the mob's name to display their name, current health, and max health
     * @param mobName The mob's intended name
     * @param currentHealth The current health of the mob. Cannot be less than 0
     * @param maxHealth  The max health of the mob
     * @return A string containing the mob's name, current health, and max health
     */
    public static String mobHealthNameFormat(String mobName, double currentHealth, double maxHealth)
    {
        DecimalFormat doubleDecimalPlace = new DecimalFormat("0.00");
        return mobName + ChatColor.BLACK + " | " + ChatColor.YELLOW + doubleDecimalPlace.format(Math.max(0, currentHealth)) + ChatColor.WHITE + "/" + ChatColor.RED + doubleDecimalPlace.format(maxHealth);
    }


    @EventHandler
    private void nameOnSpawn(CreatureSpawnEvent event)
    {
        LivingEntity livingEntity = event.getEntity();
        double health = livingEntity.getHealth();
        double maxHealth = livingEntity.getMaxHealth();

        if (livingEntity instanceof Mob mob)
        {
            VentureMobBuilder<?> ventureMobBuilder = new VentureMobBuilder<>(mob);
            String name;
            if (ventureMobBuilder.isVentureMobType())
            {
                name = ventureMobBuilder.getVentureType().getDisplayName();
            } else if (Mobs.vanillaMobs.isOfMob(livingEntity.getType()))
            {
                name = Mobs.vanillaMobs.getMobName(livingEntity.getType());
            } else
            {
                throw new IllegalStateException(ChatColor.RED + "ERROR TRYING TO GET MOB NAME!");
            }
            livingEntity.setCustomName(mobHealthNameFormat(name, health, maxHealth));
        }
    }

    @EventHandler
    private void entityHealthDisplayChange(final EntityHealthDisplayChangeEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }

        Entity entity = event.getEntity();

        if (entity instanceof Mob mob)
        {
            VentureMobBuilder<?> ventureMobBuilder = new VentureMobBuilder<>(mob);
            String name;
            double health = mob.getHealth();
            double maxHealth = mob.getMaxHealth();

            if (ventureMobBuilder.isVentureMobType())
            {
                name = ventureMobBuilder.getVentureType().getDisplayName();
            } else if (Mobs.vanillaMobs.isOfMob(mob.getType()))
            {
                name = Mobs.vanillaMobs.getMobName(mob.getType());
            } else
            {
                throw new IllegalStateException(ChatColor.RED + "ERROR TRYING TO GET MOB NAME!");
            }

            if (event.isDamage())
            {
                mob.setCustomName(mobHealthNameFormat(name, health - event.getAmount(), maxHealth));
            } else
            {
                mob.setCustomName(mobHealthNameFormat(name, health + event.getAmount(), maxHealth));
            }
        }
    }

    @EventHandler
    private void entityHealthChangeTagEvent(final EntityHealthChangeTagEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }

        Entity target = event.getEntity();
        if (target instanceof LivingEntity livingEntity)
        {
            Location loc = livingEntity.getEyeLocation().add(0,0.5,0);

            int textLifeSpan = plugin.getConfig().getInt("damageDisplayLifeSpanTicks");
            TextDisplay damageDisplay = loc.getWorld().spawn(loc, TextDisplay.class);
            DecimalFormat doubleDecimal = new DecimalFormat("0.00");
            damageDisplay.setSeeThrough(true);
            damageDisplay.setAlignment(TextDisplay.TextAlignment.CENTER);
            damageDisplay.setBillboard(Display.Billboard.CENTER);

            String displayText;

            EntityDamageEvent.DamageCause damageCause = event.getDamageCause();

            if (event.isDamage())
            {
                String damageChar = switch (damageCause) {
                    case PROJECTILE -> ChatColor.DARK_PURPLE + "➶";
                    case MAGIC -> ChatColor.AQUA + "⚝";
                    case ENTITY_ATTACK, ENTITY_SWEEP_ATTACK -> ChatColor.RED + "🗡";
                    case FIRE, LAVA, FIRE_TICK, HOT_FLOOR -> ChatColor.GOLD + "\uD83D\uDD25";
                    case KILL, WORLD_BORDER, VOID -> ChatColor.DARK_PURPLE + "⚝";
                    case CONTACT -> ChatColor.RED + "\uD83C\uDF35";
                    case CRAMMING -> ChatColor.DARK_RED + "❤";
                    case SUFFOCATION, DRYOUT -> ChatColor.GRAY + "\uD83E\uDEE7";
                    case FALL, FLY_INTO_WALL -> ChatColor.WHITE + "\uD83E\uDDB4";
                    case MELTING -> ChatColor.WHITE + "\uD83D\uDD25";
                    case DROWNING -> ChatColor.BLUE + "\uD83E\uDEE7";
                    case BLOCK_EXPLOSION, ENTITY_EXPLOSION -> ChatColor.GOLD + "\uD83D\uDCA5";
                    case LIGHTNING -> ChatColor.AQUA + "\uD83D\uDDF2";
                    case SUICIDE -> ChatColor.BLACK + "\uD80C\uDF6F";
                    case POISON -> ChatColor.GREEN + "☠";
                    case WITHER -> ChatColor.BLACK + "☠";
                    case FALLING_BLOCK -> ChatColor.GRAY + "\uD83E\uDEA8";
                    case THORNS -> ChatColor.LIGHT_PURPLE + "\uD83C\uDF35";
                    case FREEZE -> ChatColor.AQUA + "❄";
                    case SONIC_BOOM -> ChatColor.DARK_AQUA + "\uD83D\uDD6A";
                    case STARVATION -> ChatColor.YELLOW + "\uD83C\uDF57";
                    case DRAGON_BREATH -> ChatColor.LIGHT_PURPLE + "⚝";
                    default -> ChatColor.RED + "❤";
                };

                displayText = "-" + ChatColor.RED + doubleDecimal.format(event.getAmount()) + damageChar;
            } else
            {
                displayText = "+" + ChatColor.GREEN + doubleDecimal.format(event.getAmount()) + ChatColor.RED + "❤";
            }


            damageDisplay.setText(displayText);

            new BukkitRunnable() {
                int i = 0;
                @Override
                public void run() {
                    i++;
                    if (i >= textLifeSpan || Bukkit.getServer().isStopping())
                    {
                        damageDisplay.remove();
                    }
                }
            }.runTaskTimer(plugin, 0L, 1L);
        }
    }

    @EventHandler
    private void arrowDamage(EntityDamageByEntityEvent event)
    {
        if (event.getDamager() instanceof Arrow arrow)
        {
            if (arrow.getShooter() instanceof Player player)
            {
                double damage = event.getDamage();

                StatPlayer statPlayer = new StatPlayer(player);
                DamageStats damageStats = statPlayer.getDamageStats();
                double baseRange = damageStats.getBaseRange();
                double percentRange = 1 + (damageStats.getPercentRange() * 0.01);
                double finalArrowDamage = (baseRange + damage) * percentRange;

                if (event.getEntity() instanceof LivingEntity)
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1); // Arrow ping sound
                    event.setDamage(finalArrowDamage);
                }
            }
        }
    }


    @EventHandler (priority = EventPriority.HIGHEST)
    private void damageDisplay(EntityDamageEvent event) {

        Entity entity = event.getEntity();
        EntityDamageEvent.DamageCause cause = event.getCause();
        double finalDamage = event.getFinalDamage();

        EntityHealthChangeTagEvent healthChangeTagEvent = new EntityHealthChangeTagEvent(entity, cause, finalDamage, true);
        Bukkit.getPluginManager().callEvent(healthChangeTagEvent);

        EntityHealthDisplayChangeEvent healthDisplayChangeEvent = new EntityHealthDisplayChangeEvent(entity, finalDamage, true);
        Bukkit.getPluginManager().callEvent(healthDisplayChangeEvent);
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    private void healDisplay(EntityRegainHealthEvent event) {

        Entity entity = event.getEntity();
        double amount = event.getAmount();

        EntityHealthChangeTagEvent healthChangeTagEvent = new EntityHealthChangeTagEvent(entity, amount, false);
        Bukkit.getPluginManager().callEvent(healthChangeTagEvent);

        EntityHealthDisplayChangeEvent healthDisplayChangeEvent = new EntityHealthDisplayChangeEvent(entity, amount, false);
        Bukkit.getPluginManager().callEvent(healthDisplayChangeEvent);
    }
}
