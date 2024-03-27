package net.laserdiamond.ventureplugin.events.damage;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.stats.Components.DamageStats;
import net.laserdiamond.ventureplugin.stats.Manager.StatProfileManager;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class DamageEvent implements Listener {

    private final VenturePlugin plugin;
    private final StatProfileManager statProfileManager;
    public static final NamespacedKey magicDmgKey = new NamespacedKey("damage","magic");

    public DamageEvent(VenturePlugin plugin) {
        this.plugin = plugin;
        statProfileManager = plugin.getStatProfileManager();
    }

    /*
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
                    damageCause.equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK))
            {
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

                    /*
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
    */

    @EventHandler
    public void arrowDamage(EntityDamageByEntityEvent event)
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
    public void damageDisplay(EntityDamageEvent event) {

        Entity entity = event.getEntity();
        EntityDamageEvent.DamageCause cause = event.getCause();
        double finalDamage = event.getFinalDamage();

        EntityHealthChangeTagEvent healthChangeTagEvent = new EntityHealthChangeTagEvent(entity, cause, finalDamage, true);
        Bukkit.getPluginManager().callEvent(healthChangeTagEvent);
        if (!healthChangeTagEvent.isCancelled())
        {
            EntityHealthChangeTagEvent.run(entity, cause, finalDamage, true);
        }

        runHealthDisplayChange(entity, finalDamage, true);

        /*
        EntityHealthDisplayChangeEvent healthDisplayChangeEvent = new EntityHealthDisplayChangeEvent(entity, finalDamage, true);
        Bukkit.getPluginManager().callEvent(healthDisplayChangeEvent);
        if (!healthDisplayChangeEvent.isCancelled())
        {
            EntityHealthDisplayChangeEvent.run(entity, finalDamage, false);
        }

         */

        /*
        EntityHealthChangeTagEvent damageDisplayEvent = new EntityHealthChangeTagEvent(event.getEntity(), event.getCause(), event.getFinalDamage(), true);
        Bukkit.getPluginManager().callEvent(damageDisplayEvent);

        EntityHealthDisplayChangeEvent healthDisplayChangeEvent = new EntityHealthDisplayChangeEvent(event.getEntity(), event.getFinalDamage(), true);
        Bukkit.getPluginManager().callEvent(healthDisplayChangeEvent);

         */
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void healDisplay(EntityRegainHealthEvent event) {

        Entity entity = event.getEntity();
        double amount = event.getAmount();

        EntityHealthChangeTagEvent healthChangeTagEvent = new EntityHealthChangeTagEvent(entity, amount, false);
        Bukkit.getPluginManager().callEvent(healthChangeTagEvent);
        if (!healthChangeTagEvent.isCancelled())
        {
            EntityHealthChangeTagEvent.run(entity, amount, false);
        }

        runHealthDisplayChange(entity, amount, false);

        /*
        EntityHealthDisplayChangeEvent healthDisplayChangeEvent = new EntityHealthDisplayChangeEvent(entity, amount, false);
        Bukkit.getPluginManager().callEvent(healthDisplayChangeEvent);
        if (!healthDisplayChangeEvent.isCancelled())
        {
            EntityHealthDisplayChangeEvent.run(entity, amount, false);
        }

         */

        /*
        EntityHealthChangeTagEvent damageTagEvent = new EntityHealthChangeTagEvent(event.getEntity(), event.getAmount(), false);
        Bukkit.getPluginManager().callEvent(damageTagEvent);

        EntityHealthDisplayChangeEvent healthDisplayChangeEvent = new EntityHealthDisplayChangeEvent(event.getEntity(), event.getAmount(), false);
        Bukkit.getPluginManager().callEvent(healthDisplayChangeEvent);

         */
    }

    private void runHealthDisplayChange(final Entity entity, double amount, boolean isDamage)
    {
        EntityHealthDisplayChangeEvent healthDisplayChangeEvent = new EntityHealthDisplayChangeEvent(entity, amount, isDamage);
        Bukkit.getPluginManager().callEvent(healthDisplayChangeEvent);
        if (!healthDisplayChangeEvent.isCancelled())
        {
            EntityHealthDisplayChangeEvent.run(entity, amount, isDamage);
        }
    }

    /*
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

     */
}
