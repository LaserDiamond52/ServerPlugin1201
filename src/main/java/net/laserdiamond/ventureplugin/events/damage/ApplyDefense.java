package net.laserdiamond.ventureplugin.events.damage;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.stats.Components.DefenseStats;
import net.laserdiamond.ventureplugin.stats.Manager.StatProfileManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApplyDefense implements Listener {

    private final VenturePlugin PLUGIN;
    private final StatProfileManager STAT_PROFILE_MANAGER;
    public ApplyDefense(VenturePlugin plugin) {
        this.PLUGIN = plugin;
        this.STAT_PROFILE_MANAGER = plugin.getStatProfileManager();
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void defenseEvent(EntityDamageEvent event) {

        if (event.getEntity() instanceof Player player) {

            DefenseStats defenseStats = STAT_PROFILE_MANAGER.getStatProfile(player.getUniqueId()).defenseStats();
            double armor = defenseStats.getDefense();
            double fire_armor = defenseStats.getFireDefense();
            double explosion_armor = defenseStats.getExplosionDefense();
            double projectile_armor = defenseStats.getProjectileDefense();
            double magic_armor = defenseStats.getMagicDefense();
            double toughness = defenseStats.getToughness();

            double originalDamage = event.getDamage();

            double finalArmor = 0;

            if (FIRE_DEFENSE_CAUSE.contains(event.getCause())) {
                // Fire damage

                // TODO USE FIRE ARMOR
                finalArmor = armor + fire_armor;
                //player.sendMessage("Fire armor: " + finalArmor);


            } else if (EXPLOSION_DEFENSE_CAUSE.contains(event.getCause())) {
                // Explosion damage

                // TODO USE EXPLOSION ARMOR
                finalArmor = armor + explosion_armor;
                //player.sendMessage("Explosion armor: " + finalArmor);


            } else if (PROJECTILE_DEFENSE_CAUSE.contains(event.getCause())) {
                // Projectile

                // TODO USE PROJECTILE ARMOR
                finalArmor = armor + projectile_armor;
                //player.sendMessage("Projectile armor: " + finalArmor);

            } else if (MAGIC_DEFENSE_CAUSE.contains(event.getCause())) {
                // Magic

                // TODO USE MAGIC ARMOR
                finalArmor = armor + magic_armor;
                //player.sendMessage("Magic armor: " + finalArmor);

            } else if (DEFENSE_CAUSE.contains(event.getCause())) {
                // Normal

                // TODO USE ARMOR
                //player.sendMessage("Armor: " + armor);
                finalArmor = armor;

            } else if (TOUGHNESS_CAUSE.contains(event.getCause())) {
                // Toughness

                // TODO USE TOUGHNESS
                //player.sendMessage("Toughness: " + toughness);
                finalArmor = toughness;
            }

            double currentHeath = player.getHealth();
            double finalDamage = Math.max(0, currentHeath - finalDamage(finalArmor, originalDamage));

            EntityDamageEvent lastDamageCause = player.getLastDamageCause();
            player.setLastDamageCause(lastDamageCause);
            event.setDamage(0.000000000000001);

            ItemStack mainHand = player.getInventory().getItemInMainHand();
            ItemStack offHand = player.getInventory().getItemInOffHand();

            int mainHandAmount = mainHand.getAmount();
            int offHandAmount = offHand.getAmount();

            boolean isFatalDamage = (finalDamage <= 0);

            EntityResurrectEvent totemUseMainHand = new EntityResurrectEvent(player, EquipmentSlot.HAND);
            boolean callEvent = totemUseMainHand.callEvent();
            LivingEntity revivedLivingEntity = totemUseMainHand.getEntity();
            Bukkit.getPluginManager().callEvent(totemUseMainHand);

            if (isFatalDamage) {
                // TODO: Player will take fatal damage

                // TODO: If mainhand or offhand has a totem, revive
                if (mainHand.getType() == Material.TOTEM_OF_UNDYING) {
                    player.setHealth(1);
                    totemDeath(revivedLivingEntity, callEvent);
                    mainHand.setAmount(mainHandAmount - 1);
                } else if (offHand.getType() == Material.TOTEM_OF_UNDYING) {
                    player.setHealth(1);
                    totemDeath(revivedLivingEntity, callEvent);
                    offHand.setAmount(offHandAmount - 1);
                } else {
                    // TODO: Player will die

                    customDamage(player, finalDamage);
                }

            } else {
                // TODO: Player will be damaged as normal
                customDamage(player, finalDamage);
            }

        }
    }

    private void totemDeath(LivingEntity livingEntity, boolean eventCalled) {

        if (eventCalled) {
            livingEntity.playEffect(EntityEffect.TOTEM_RESURRECT);
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 900, 1));
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 800, 0));
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 100, 1));
        }
    }

    private void customDamage(Player player, double finalDamage) {
        player.setHealth(finalDamage);

        EntityDamageEvent lastDamageCause = player.getLastDamageCause();

        // TODO: Check if player is dead upon receiving damage
        if (player.isDead()) {

            // Player is dead, output death message
            String deathMessageString;

            if (lastDamageCause instanceof EntityDamageByEntityEvent entityDamageByEntityEvent) {
                Entity damager = entityDamageByEntityEvent.getDamager();
                deathMessageString = ChatColor.DARK_RED + player.getName() + ChatColor.GRAY + " was killed by " + damager.getName();

            } else if (lastDamageCause instanceof EntityDamageByBlockEvent entityDamageByBlockEvent) {
                Block damager = entityDamageByBlockEvent.getDamager();
                deathMessageString = ChatColor.DARK_RED + player.getName() + ChatColor.GRAY + " was killed by " + damager.getType();

            } else {

                deathMessageString = ChatColor.DARK_RED + player.getName() + ChatColor.GRAY + " died";
            }

            Bukkit.broadcastMessage(deathMessageString);

        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void setDeathMessage(PlayerDeathEvent event) {

        event.setKeepInventory(true);

        event.deathMessage(null);

    }

    public static Double finalDamage(double armor, double damage) {
        double damageReduction = 1 - (armor / (armor + 20));

        return damageReduction * damage;
    }

    private final List<EntityDamageEvent.DamageCause> FIRE_DEFENSE_CAUSE = new ArrayList<>();
    {
        FIRE_DEFENSE_CAUSE.add(EntityDamageEvent.DamageCause.FIRE);
        FIRE_DEFENSE_CAUSE.add(EntityDamageEvent.DamageCause.FIRE_TICK);
        FIRE_DEFENSE_CAUSE.add(EntityDamageEvent.DamageCause.LAVA);
        FIRE_DEFENSE_CAUSE.add(EntityDamageEvent.DamageCause.HOT_FLOOR);
    }

    private final List<EntityDamageEvent.DamageCause> EXPLOSION_DEFENSE_CAUSE = new ArrayList<>();
    {
        EXPLOSION_DEFENSE_CAUSE.add(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION);
        EXPLOSION_DEFENSE_CAUSE.add(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION);
    }

    private final List<EntityDamageEvent.DamageCause> PROJECTILE_DEFENSE_CAUSE = new ArrayList<>();
    {
        PROJECTILE_DEFENSE_CAUSE.add(EntityDamageEvent.DamageCause.PROJECTILE);
    }

    private final List<EntityDamageEvent.DamageCause> MAGIC_DEFENSE_CAUSE = new ArrayList<>();
    {
        MAGIC_DEFENSE_CAUSE.add(EntityDamageEvent.DamageCause.MAGIC);
    }

    private final List<EntityDamageEvent.DamageCause> DEFENSE_CAUSE = new ArrayList<>();
    {
        DEFENSE_CAUSE.add(EntityDamageEvent.DamageCause.CONTACT);
        DEFENSE_CAUSE.add(EntityDamageEvent.DamageCause.ENTITY_ATTACK);
        DEFENSE_CAUSE.add(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK);
        DEFENSE_CAUSE.add(EntityDamageEvent.DamageCause.LIGHTNING);
        DEFENSE_CAUSE.add(EntityDamageEvent.DamageCause.FALLING_BLOCK);
        DEFENSE_CAUSE.add(EntityDamageEvent.DamageCause.THORNS);


        DEFENSE_CAUSE.addAll(FIRE_DEFENSE_CAUSE);
        DEFENSE_CAUSE.addAll(EXPLOSION_DEFENSE_CAUSE);
        DEFENSE_CAUSE.addAll(PROJECTILE_DEFENSE_CAUSE);
        DEFENSE_CAUSE.addAll(MAGIC_DEFENSE_CAUSE);
    }

    private final List<EntityDamageEvent.DamageCause> TOUGHNESS_CAUSE = new ArrayList<>();
    {
        TOUGHNESS_CAUSE.add(EntityDamageEvent.DamageCause.SONIC_BOOM);

        TOUGHNESS_CAUSE.addAll(DEFENSE_CAUSE);
    }

    private final HashMap<EntityDamageEvent.DamageCause, String> DEATH_MESSAGES = new HashMap<>();
    {
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.KILL, " was subject to god's judgement");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.WORLD_BORDER, " wondered outside the confinements of the world");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.CONTACT, " died to a block");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.ENTITY_ATTACK, " was killed by an entity");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK, " was killed by an entity's sweep");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.PROJECTILE, " was shot");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.SUFFOCATION, " suffocated in a wall");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.FALL, " hit the ground too hard");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.FIRE, " was roasted alive");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.FIRE_TICK, " was roasted alive");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.MELTING, " melted");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.LAVA, " tried to swim in lava");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.DROWNING, " forgot how to swim");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, " blew up");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, " blew up");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.VOID, " fell out of the world");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.LIGHTNING, " was struck by lightning");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.SUICIDE, " killed themself");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.STARVATION, " starved to death");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.POISON, " was poisoned");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.MAGIC, " was subject to witchcraft");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.WITHER, " withered away");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.FALLING_BLOCK, " was crushed by a block");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.THORNS, " got NO-YOU'D!");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.DRAGON_BREATH, " was roasted alive");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.CUSTOM, " ???");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.FLY_INTO_WALL, " experienced kinetic energy");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.HOT_FLOOR, " didn't want to play 'The Floor is Lava' anymore");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.CRAMMING, " was squeezed to death");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.FREEZE, " froze");
        DEATH_MESSAGES.put(EntityDamageEvent.DamageCause.SONIC_BOOM, " got their eardrums ruptured");
    }
}
