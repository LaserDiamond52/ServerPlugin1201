package net.laserdiamond.serverplugin1201.events.Stats;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.stats.Components.DefenseStats;
import net.laserdiamond.serverplugin1201.stats.Components.Stats;
import net.laserdiamond.serverplugin1201.stats.Manager.StatProfileManager;
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

public class DefenseEvent implements Listener {

    private final ServerPlugin1201 plugin;
    private final StatProfileManager statProfileManager;
    public DefenseEvent(ServerPlugin1201 plugin) {
        this.plugin = plugin;
        this.statProfileManager = plugin.getStatProfileManager();
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void defenseEvent(EntityDamageEvent event) {

        if (event.getEntity() instanceof Player player) {

            DefenseStats defenseStats = statProfileManager.getStatProfile(player.getUniqueId()).defenseStats();
            double armor = defenseStats.getDefense();
            double fire_armor = defenseStats.getFireDefense();
            double explosion_armor = defenseStats.getExplosionDefense();
            double projectile_armor = defenseStats.getProjectileDefense();
            double magic_armor = defenseStats.getMagicDefense();
            double toughness = defenseStats.getToughness();

            double originalDamage = event.getDamage();

            double finalArmor = 0;

            if (fireArmorProtection.contains(event.getCause())) {
                // Fire damage

                // TODO USE FIRE ARMOR
                finalArmor = armor + fire_armor;
                //player.sendMessage("Fire armor: " + finalArmor);


            } else if (explosionArmorProtection.contains(event.getCause())) {
                // Explosion damage

                // TODO USE EXPLOSION ARMOR
                finalArmor = armor + explosion_armor;
                //player.sendMessage("Explosion armor: " + finalArmor);


            } else if (projectileArmorProtection.contains(event.getCause())) {
                // Projectile

                // TODO USE PROJECTILE ARMOR
                finalArmor = armor + projectile_armor;
                //player.sendMessage("Projectile armor: " + finalArmor);

            } else if (magicArmorProtection.contains(event.getCause())) {
                // Magic

                // TODO USE MAGIC ARMOR
                finalArmor = armor + magic_armor;
                //player.sendMessage("Magic armor: " + finalArmor);

            } else if (armorProtection.contains(event.getCause())) {
                // Normal

                // TODO USE ARMOR
                //player.sendMessage("Armor: " + armor);
                finalArmor = armor;

            } else if (toughnessProtection.contains(event.getCause())) {
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

    private final List<EntityDamageEvent.DamageCause> fireArmorProtection = new ArrayList<>();
    {
        fireArmorProtection.add(EntityDamageEvent.DamageCause.FIRE);
        fireArmorProtection.add(EntityDamageEvent.DamageCause.FIRE_TICK);
        fireArmorProtection.add(EntityDamageEvent.DamageCause.LAVA);
        fireArmorProtection.add(EntityDamageEvent.DamageCause.HOT_FLOOR);
    }

    private final List<EntityDamageEvent.DamageCause> explosionArmorProtection = new ArrayList<>();
    {
        explosionArmorProtection.add(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION);
        explosionArmorProtection.add(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION);
    }

    private final List<EntityDamageEvent.DamageCause> projectileArmorProtection = new ArrayList<>();
    {
        projectileArmorProtection.add(EntityDamageEvent.DamageCause.PROJECTILE);
    }

    private final List<EntityDamageEvent.DamageCause> magicArmorProtection = new ArrayList<>();
    {
        magicArmorProtection.add(EntityDamageEvent.DamageCause.MAGIC);
    }

    private final List<EntityDamageEvent.DamageCause> armorProtection = new ArrayList<>();
    {
        armorProtection.add(EntityDamageEvent.DamageCause.CONTACT);
        armorProtection.add(EntityDamageEvent.DamageCause.ENTITY_ATTACK);
        armorProtection.add(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK);
        armorProtection.add(EntityDamageEvent.DamageCause.LIGHTNING);
        armorProtection.add(EntityDamageEvent.DamageCause.FALLING_BLOCK);
        armorProtection.add(EntityDamageEvent.DamageCause.THORNS);


        armorProtection.addAll(fireArmorProtection);
        armorProtection.addAll(explosionArmorProtection);
        armorProtection.addAll(projectileArmorProtection);
        armorProtection.addAll(magicArmorProtection);
    }

    private final List<EntityDamageEvent.DamageCause> toughnessProtection = new ArrayList<>();
    {
        toughnessProtection.add(EntityDamageEvent.DamageCause.SONIC_BOOM);

        toughnessProtection.addAll(armorProtection);
    }

    private final HashMap<EntityDamageEvent.DamageCause, String> deathNames = new HashMap<>();
    {
        deathNames.put(EntityDamageEvent.DamageCause.KILL, " was subject to god's judgement");
        deathNames.put(EntityDamageEvent.DamageCause.WORLD_BORDER, " wondered outside the confinements of the world");
        deathNames.put(EntityDamageEvent.DamageCause.CONTACT, " died to a block");
        deathNames.put(EntityDamageEvent.DamageCause.ENTITY_ATTACK, " was killed by an entity");
        deathNames.put(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK, " was killed by an entity's sweep");
        deathNames.put(EntityDamageEvent.DamageCause.PROJECTILE, " was shot");
        deathNames.put(EntityDamageEvent.DamageCause.SUFFOCATION, " suffocated in a wall");
        deathNames.put(EntityDamageEvent.DamageCause.FALL, " hit the ground too hard");
        deathNames.put(EntityDamageEvent.DamageCause.FIRE, " was roasted alive");
        deathNames.put(EntityDamageEvent.DamageCause.FIRE_TICK, " was roasted alive");
        deathNames.put(EntityDamageEvent.DamageCause.MELTING, " melted");
        deathNames.put(EntityDamageEvent.DamageCause.LAVA, " tried to swim in lava");
        deathNames.put(EntityDamageEvent.DamageCause.DROWNING, " forgot how to swim");
        deathNames.put(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, " blew up");
        deathNames.put(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, " blew up");
        deathNames.put(EntityDamageEvent.DamageCause.VOID, " fell out of the world");
        deathNames.put(EntityDamageEvent.DamageCause.LIGHTNING, " was struck by lightning");
        deathNames.put(EntityDamageEvent.DamageCause.SUICIDE, " killed themself");
        deathNames.put(EntityDamageEvent.DamageCause.STARVATION, " starved to death");
        deathNames.put(EntityDamageEvent.DamageCause.POISON, " was poisoned");
        deathNames.put(EntityDamageEvent.DamageCause.MAGIC, " was subject to witchcraft");
        deathNames.put(EntityDamageEvent.DamageCause.WITHER, " withered away");
        deathNames.put(EntityDamageEvent.DamageCause.FALLING_BLOCK, " was crushed by a block");
        deathNames.put(EntityDamageEvent.DamageCause.THORNS, " got NO-YOU'D!");
        deathNames.put(EntityDamageEvent.DamageCause.DRAGON_BREATH, " was roasted alive");
        deathNames.put(EntityDamageEvent.DamageCause.CUSTOM, " ???");
        deathNames.put(EntityDamageEvent.DamageCause.FLY_INTO_WALL, " experienced kinetic energy");
        deathNames.put(EntityDamageEvent.DamageCause.HOT_FLOOR, " didn't want to play 'The Floor is Lava' anymore");
        deathNames.put(EntityDamageEvent.DamageCause.CRAMMING, " was squeezed to death");
        deathNames.put(EntityDamageEvent.DamageCause.FREEZE, " froze");
        deathNames.put(EntityDamageEvent.DamageCause.SONIC_BOOM, " got their eardrums ruptured");
    }
}
