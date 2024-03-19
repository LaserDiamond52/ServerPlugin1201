package net.laserdiamond.ventureplugin.events.mana;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.stats.Components.Stats;
import net.laserdiamond.ventureplugin.stats.Manager.StatProfileManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerManaRegenEvent extends PlayerEvent implements Cancellable {

    /**
     * This event is called when mana regeneration occurs
     */
    private static final VenturePlugin PLUGIN = VenturePlugin.getInstance();
    private static final StatProfileManager STAT_PROFILE_MANAGER = PLUGIN.getStatProfileManager();
    private final ManaRegenReason manaRegenReason;
    private double manaRegenAmount;
    private boolean isCancelled;
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public PlayerManaRegenEvent(final Player player, ManaRegenReason manaRegenReason, double manaRegenAmount)
    {
        super(player);
        this.manaRegenReason = manaRegenReason;
        this.manaRegenAmount = manaRegenAmount;
    }

    /**
     * A method that runs the event's intended function
     * @param player The player to run the event on
     * @param manaRegenAmount The amount of mana to grant to the player
     */
    public static void run(final Player player, double manaRegenAmount)
    {
        Stats stats = STAT_PROFILE_MANAGER.getStatProfile(player.getUniqueId()).stats();
        double availableMana = stats.getAvailableMana();
        double maxMana = stats.getMaxMana();

        stats.setAvailableMana(Math.max(0, Math.min(availableMana + manaRegenAmount, maxMana)));
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    /**
     * Gets the amount of regained mana
     * @return The amount of mana regained
     */
    public double getManaRegenAmount() {
        return manaRegenAmount;
    }

    /**
     * Sets the amount of regained mana
     * @param manaRegenAmount the amount of mana the player will regain
     */
    public void setManaRegenAmount(double manaRegenAmount) {
        this.manaRegenAmount = manaRegenAmount;
    }

    /**
     * Gets the reason for why the player is regaining mana
     * @return A ManaRegenReason representing the reason for the mana regen
     */
    public ManaRegenReason getManaRegenReason() {
        return manaRegenReason;
    }

    /**
     * Enum that specifies the type of mana regeneration that is occurring
     */
    public enum ManaRegenReason
    {
        /**
         * Player is naturally regenerating mana
         */
        REGEN,
        /**
         * Player regenerates mana from using an ability/spell
         */
        ABILITY,
        /**
         * Player regenerates mana from drinking/consuming a potion or consumable item
         */
        POTION,
        /**
         * Player regenerates mana from attacking another entity
         */
        ATTACK,
        /**
         * Any other reason not specified here
         */
        CUSTOM
    }
}
