package net.laserdiamond.ventureplugin.skills.Components.ExpGain;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.events.skills.SkillsExpGainEvent;
import net.laserdiamond.ventureplugin.skills.Components.SkillsEXP;
import net.laserdiamond.ventureplugin.skills.Components.SkillsLevel;
import net.laserdiamond.ventureplugin.skills.Components.SkillsProfile;
import net.laserdiamond.ventureplugin.skills.Components.SkillsReward;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class SkillsExpGainListener implements Listener {

    private final VenturePlugin plugin = VenturePlugin.getInstance();

    @EventHandler
    public void onSkillExpGain(SkillsExpGainEvent event)
    {
        Player player = event.getPlayer();
        double expAmount = event.getExpAmount();
        SkillsExpGainEvent.Skill skill = event.getSkill();
        StatPlayer statPlayer = new StatPlayer(player);
        SkillsProfile skillsProfile = statPlayer.getSkillsProfile();
        SkillsEXP skillsEXP = statPlayer.getSkillsEXP();
        SkillsLevel skillsLevel = statPlayer.getSkillsLevel();
        SkillsReward skillsReward = statPlayer.getSkillsReward();

        switch (skill)
        {
            // TODO: When current skill exp + gained amount is greater than the required amount, level up
            // TODO: Call event when skills exp should be gained

            // TODO: Add to required skill exp when gaining a level
            case COMBAT ->
            {
                skillsEXP.setTotalCombatEXP(skillsEXP.getTotalCombatEXP() + expAmount);
                skillsEXP.setCombatExpToNextLevel(skillsEXP.getCombatExpToNextLevel() + expAmount);

                while (skillsEXP.getCombatExpToNextLevel() >= skillsEXP.getRequiredCombatExpToNextLevel())
                {
                    int skillLevel = skillsLevel.getCombatLevel();

                }
                /*
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {

                        if (skillsEXP.getCombatExpToNextLevel() >= skillsEXP.getRequiredCombatExpToNextLevel())
                        {

                        }
                    }
                }.runTaskTimer(plugin, 0L, 1L);

                 */

            }
            case MINING ->
            {
                skillsEXP.setTotalMiningEXP(skillsEXP.getTotalMiningEXP() + expAmount);
                skillsEXP.setMiningExpToNextLevel(skillsEXP.getMiningExpToNextLevel() + expAmount);
            }
            case FORAGING ->
            {
                skillsEXP.setTotalForagingEXP(skillsEXP.getTotalForagingEXP() + expAmount);
                skillsEXP.setForagingExpToNextLevel(skillsEXP.getForagingExpToNextLevel() + expAmount);
            }
            case FARMING ->
            {
                skillsEXP.setTotalFarmingEXP(skillsEXP.getTotalFarmingEXP() + expAmount);
                skillsEXP.setFarmingExpToNextLevel(skillsEXP.getFarmingExpToNextLevel() + expAmount);
            }
            case ENCHANTING ->
            {
                skillsEXP.setTotalEnchantingEXP(skillsEXP.getTotalEnchantingEXP() + expAmount);
                skillsEXP.setEnchantingExpToNextLevel(skillsEXP.getEnchantingExpToNextLevel() + expAmount);
            }
            case FISHING ->
            {
                skillsEXP.setTotalFishingExp(skillsEXP.getTotalFishingExp() + expAmount);
                skillsEXP.setFishingExpToNextLevel(skillsEXP.getFishingExpToNextLevel() + expAmount);
            }
            case BREWING ->
            {
                skillsEXP.setTotalBrewingExp(skillsEXP.getTotalBrewingExp() + expAmount);
                skillsEXP.setBrewingExpToNextLevel(skillsEXP.getBrewingExpToNextLevel() + expAmount);
            }
        }
    }
}
