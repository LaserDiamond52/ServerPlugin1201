package net.laserdiamond.serverplugin1201.events.damage;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;

public class EntityHealthChangeTagEvent extends EntityEvent implements Cancellable {

    /**
     * Displays a text display that indicates whether the victim
     * was damaged or healed, and by how much
     * <p>
     * Note: This DOES NOT damage the entity
     */
    private final ServerPlugin1201 PLUGIN = ServerPlugin1201.getInstance();
    private double amount;
    private EntityDamageEvent.DamageCause damageCause;
    private boolean isDamage; // True = amount is damage; false = heal
    private boolean isCancelled;
    private static final HandlerList HANDLER_LIST = new HandlerList();

    /**
     * Displays a text display with damage/heal amount, and a symbol that indicates the cause
     * <p>
     * Note: Entity attacks that involve magic damage, projectile damage, etc. will be displayed as
     * entity damage
     */

    public EntityHealthChangeTagEvent(Entity target, EntityDamageEvent.DamageCause damageCause, double amount, boolean isDamage)
    {
        super(target);
        this.damageCause = damageCause;
        this.amount = amount;
        this.isDamage = isDamage;

        if (target instanceof LivingEntity livingEntity)
        {
            Location loc = livingEntity.getEyeLocation().add(0,0.5,0);

            int textLifeSpan = PLUGIN.getConfig().getInt("damageDisplayLifeSpanTicks");
            TextDisplay damageDisplay = loc.getWorld().spawn(loc, TextDisplay.class);
            DecimalFormat doubleDecimal = new DecimalFormat("0.00");
            damageDisplay.setSeeThrough(true);
            damageDisplay.setAlignment(TextDisplay.TextAlignment.CENTER);
            damageDisplay.setBillboard(Display.Billboard.CENTER);

            String displayText;

            if (isDamage)
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

                displayText = "-" + ChatColor.RED + doubleDecimal.format(amount) + damageChar;
            } else
            {
                displayText = "+" + ChatColor.GREEN + doubleDecimal.format(amount) + ChatColor.RED + "❤";
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
            }.runTaskTimer(PLUGIN, 0L, 1L);
        }

    }

    /**
     * Displays a text display with damage/heal amount. No unique symbol to indicate damage type
     */
    public EntityHealthChangeTagEvent(Entity target, double amount, boolean isDamage) {
        super(target);
        this.amount = amount;
        this.isDamage = isDamage;

        if (target instanceof LivingEntity livingEntity)
        {
            Location loc = livingEntity.getEyeLocation().add(0,0.5,0);

            int textLifeSpan = PLUGIN.getConfig().getInt("damageDisplayLifeSpanTicks");
            TextDisplay damageDisplay = loc.getWorld().spawn(loc, TextDisplay.class);
            DecimalFormat doubleDecimal = new DecimalFormat("0.00");
            damageDisplay.setSeeThrough(true);
            damageDisplay.setAlignment(TextDisplay.TextAlignment.CENTER);
            damageDisplay.setBillboard(Display.Billboard.CENTER);

            String displayText;

            if (isDamage)
            {
                displayText = "-" + ChatColor.RED + doubleDecimal.format(amount) + ChatColor.RED + "❤";
            } else
            {
                displayText = "+" + ChatColor.GREEN + doubleDecimal.format(amount) + ChatColor.RED + "❤";
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
            }.runTaskTimer(PLUGIN, 0L, 1L);
        }
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
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public EntityDamageEvent.DamageCause getDamageCause() {
        return damageCause;
    }

    public void setDamageCause(EntityDamageEvent.DamageCause damageCause) {
        this.damageCause = damageCause;
    }

    public boolean isDamage() {
        return isDamage;
    }

    public void setDamage(boolean damage) {
        isDamage = damage;
    }
}
