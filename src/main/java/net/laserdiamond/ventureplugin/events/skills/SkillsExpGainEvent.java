package net.laserdiamond.ventureplugin.events.skills;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class SkillsExpGainEvent extends PlayerEvent implements Cancellable {

    private double expAmount;
    private Skill skill;
    private boolean isCancelled;
    private static final HandlerList HANDLER_LIST = new HandlerList();
    public SkillsExpGainEvent(@NotNull Player who, double expAmount, Skill skill) {
        super(who);
        this.expAmount = expAmount;
        this.skill = skill;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList()
    {
        return HANDLER_LIST;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    /**
     * Gets the amount of skill exp the player is gaining
     * @return The amount of skill exp
     */
    public double getExpAmount() {
        return expAmount;
    }

    /**
     * Sets the amount of skill exp the player is gaining
     * @param expAmount The amount of skill exp to gain
     */
    public void setExpAmount(double expAmount) {
        this.expAmount = expAmount;
    }

    /**
     * Gets the skill the player is gaining exp for
     * @return The skill the player is gaining exp for
     */
    public Skill getSkill() {
        return skill;
    }

    /**
     * Sets the skill the player is gaining skill exp for
     * @param skill The skill the player is gaining exp for
     */
    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public enum Skill
    {
        COMBAT,
        MINING,
        FORAGING,
        FARMING,
        ENCHANTING,
        FISHING,
        BREWING
    }
}
