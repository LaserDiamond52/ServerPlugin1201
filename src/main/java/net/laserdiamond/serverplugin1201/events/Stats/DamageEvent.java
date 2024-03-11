package net.laserdiamond.serverplugin1201.events.Stats;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.stats.Components.DamageStats;
import net.laserdiamond.serverplugin1201.stats.Components.Stats;
import net.laserdiamond.serverplugin1201.stats.Manager.StatProfileManager;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;

public class DamageEvent implements Listener {

    private final ServerPlugin1201 plugin;
    private final StatProfileManager statProfileManager;
    public static final NamespacedKey magicDmgKey = new NamespacedKey("damage","magic");

    public DamageEvent(ServerPlugin1201 plugin) {
        this.plugin = plugin;
        statProfileManager = plugin.getStatProfileManager();
    }

    @EventHandler
    public void damageStat(EntityDamageByEntityEvent event) {

        double damage = event.getDamage();

        // Melee Damage
        if (event.getDamager() instanceof Player player) {

            DamageStats damageStats = statProfileManager.getStatProfile(player.getUniqueId()).damageStats();

            double baseMelee = damageStats.getbMeleeDmg();

            double meleeIncrease = 1 + damageStats.getpMeleeDmg() * 0.01;

            EntityDamageEvent.DamageCause damageCause = event.getCause();

            if (damageCause.equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) ||
                    damageCause.equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) {
                double finalMeleeDamage = (baseMelee + damage) * meleeIncrease;
                event.setDamage(finalMeleeDamage);
            }

        }

        // Magic Damage
        if (event.getDamager() instanceof ThrownPotion thrownPotion)
        {
            if (thrownPotion.getShooter() instanceof Player player) // Check if shooter is a player
            {
                PotionMeta potionMeta = thrownPotion.getPotionMeta();
                if (potionMeta.getPersistentDataContainer().get(magicDmgKey, PersistentDataType.DOUBLE) != null &&
                        potionMeta.getPersistentDataContainer().has(magicDmgKey))
                {
                    // Increase damage based on magic stats
                    DamageStats damageStats = statProfileManager.getStatProfile(player.getUniqueId()).damageStats();
                    //double baseSpellDmg = potionMeta.getPersistentDataContainer().get(magicDmgKey, PersistentDataType.DOUBLE);
                    double baseMagic = damageStats.getbMagicDmg();
                    double magicIncrease = 1 + damageStats.getpMagicDmg() * 0.01;
                    double finalMagicDamage = (baseMagic + damage) * magicIncrease;

                    if (event.getEntity() instanceof LivingEntity)
                    {
                        event.setDamage(finalMagicDamage); // Set final damage
                    }
                }

            }
        }

        // Range Damage
        if (event.getDamager() instanceof Arrow arrow)
        {
            if (arrow.getShooter() instanceof Player player) // Check if shooter of arrow is a player
            {
                // Increase damage based on range stats
                DamageStats damageStats = statProfileManager.getStatProfile(player.getUniqueId()).damageStats();
                double baseRange = damageStats.getbRangeDmg();
                double rangeIncrease = 1 + damageStats.getpRangeDmg() * 0.01;
                double finalArrowDamage = (baseRange + damage) * rangeIncrease;

                if (event.getEntity() instanceof LivingEntity) // Check if hit entity is a living entity
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1); // Arrow ping sound

                    event.setDamage(finalArrowDamage); // Set final damage

                }
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void damageDisplay(EntityDamageEvent event) {

        if (event.getEntity() instanceof LivingEntity livingEntity) {
            if (!(livingEntity instanceof ArmorStand)) {

                if (!event.isCancelled()) {
                    Location location = livingEntity.getEyeLocation().add(0,0.5,0);
                    double damage = event.getFinalDamage();

                    int textLifeSpan = plugin.getConfig().getInt("damageDisplayLifeSpanTicks");
                    TextDisplay damageDisplay = location.getWorld().spawn(location, TextDisplay.class);
                    DecimalFormat doubleDecimal = new DecimalFormat("0.00");
                    damageDisplay.setSeeThrough(true);
                    damageDisplay.setBillboard(Display.Billboard.CENTER);

                    EntityDamageEvent.DamageCause damageCause = event.getCause();

                    String damageChar;
                    if (damageCause.equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) ||
                            damageCause.equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK))
                    {
                        damageChar = ChatColor.RED + "🗡";
                    } else if (damageCause.equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
                        damageChar = ChatColor.DARK_PURPLE + "➶";
                    } else if (damageCause.equals(EntityDamageEvent.DamageCause.MAGIC)) {
                        damageChar = ChatColor.AQUA + "⚝";
                    } else {
                        damageChar = ChatColor.RED + "❤";
                    }

                    damageDisplay.setText("-" + doubleDecimal.format(damage) + damageChar);

                    new BukkitRunnable() {

                        int i = 0;

                        @Override
                        public void run() {
                            i++;
                            if (i >= textLifeSpan) {
                                damageDisplay.remove();
                            }
                        }
                    }.runTaskTimer(plugin, 0L, 1L);

                }

            }
        }
    }

}
