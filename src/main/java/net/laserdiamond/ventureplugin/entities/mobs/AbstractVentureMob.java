package net.laserdiamond.ventureplugin.entities.mobs;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.util.VentureMob;
import net.laserdiamond.ventureplugin.entities.util.VentureMobBuilder;
import net.laserdiamond.ventureplugin.entities.util.loot_tables.VentureLootTableEntry;
import net.minecraft.world.BossEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.loot.LootTable;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * The base class that is inherited by all mobs of this plugin. Classes must have a constructor used to register the mob, and a constructor used purely to access the summon method
 * @param <T> The Mob type
 */
public abstract class AbstractVentureMob<T extends Mob> implements VentureMob<T>, Listener {

    private final Class<T> mobType;

    /**
     * Constructor used to register mob. Should only be called when the plugin is starting up and mobs need to be registered
     * @param venturePlugin Instance of the Venture Plugin
     * @param mobType The mob type class
     */
    protected AbstractVentureMob(VenturePlugin venturePlugin, Class<T> mobType)
    {
        venturePlugin.getVentureMobMap().put(ventureMobType().toString(), this); // Register mob
        if (!loot().isEmpty())
        {
            venturePlugin.getVentureMobLootTables().put(ventureMobType(), loot); // Register mob's venture loot table
        }
        this.mobType = mobType;
        venturePlugin.getServer().getPluginManager().registerEvents(this, venturePlugin);
    }

    /**
     * Constructor used to access methods of the class. Should declare the mob type class when being called
     * @param mobType The mob type class
     */
    public AbstractVentureMob(Class<T> mobType)
    {
        this.mobType = mobType;
    }

    protected final Set<AttributeEntry> attributes = new HashSet<>();
    protected final List<VentureLootTableEntry> loot = new ArrayList<>();

    private T mob; // TODO: Create boss bar for boss mobs and use events to handle progress
    private BossBar bossBar = null;

    @Override
    public T summonMob(Location location) {
        VentureMobBuilder<T> ventureMob = new VentureMobBuilder<>(location.getWorld().spawn(location, mobType), ventureMobType())
                .setCombatExp(combatExp())
                .setFishingExp(fishingExp());

        if (!attributes().isEmpty())
        {
            for (AttributeEntry attributeEntry : attributes())
            {
                ventureMob.setAttributeValue(attributeEntry.attribute(), attributeEntry.value());
            }
        }

        if (clearVanillaDrops())
        {
            ventureMob.setDropVanillaDrops(false);
        }

        if (boss())
        {
            ventureMob.setBossMob();
        }

        mob = ventureMob.toMob();

        if (boss())
        {
            bossBar = BossBar.bossBar(Component.text(mob.getCustomName()), (float) (mob.getHealth() / mob.getMaxHealth()), BossBar.Color.PINK, BossBar.Overlay.PROGRESS);

            for (Player player : Bukkit.getOnlinePlayers())
            {
                player.showBossBar(bossBar);
            }

            /*
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    bossBar.progress((float) (mob.getHealth() / mob.getMaxHealth()));

                    if (bossBar.progress() <= 0)
                    {
                        for (Player player : Bukkit.getOnlinePlayers())
                        {
                            player.hideBossBar(bossBar);
                        }
                        cancel();
                    }
                }
            }.runTaskAsynchronously(VenturePlugin.getInstance());
            */
        }



        return mob;
    }

    @Override
    public List<VentureLootTableEntry> loot() {
        return loot;
    }

    @Override
    public boolean clearVanillaDrops() {
        return false;
    }


    @EventHandler
    private void damageBossBarUpdate(EntityDamageEvent event)
    {
        if (bossBar == null)
        {
            return;
        }

        if (event.getEntity() instanceof Mob eventMob)
        {

            if (eventMob == this.mob)
            {
                bossBar.progress((float) (Math.max(0, eventMob.getHealth() - event.getFinalDamage())/ eventMob.getMaxHealth()));
            }
        }
    }

    @EventHandler
    private void healBossBarUpdate(EntityRegainHealthEvent event)
    {
        if (bossBar == null)
        {
            return;
        }

        if (event.getEntity() instanceof Mob eventMob)
        {

            if (eventMob == this.mob)
            {
                bossBar.progress((float) (Math.min(eventMob.getMaxHealth(), eventMob.getHealth() + event.getAmount()) / eventMob.getMaxHealth()));
            }
        }
    }
}
