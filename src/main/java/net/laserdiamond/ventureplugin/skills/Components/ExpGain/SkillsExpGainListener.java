package net.laserdiamond.ventureplugin.skills.Components.ExpGain;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.events.skills.SkillsExpGainEvent;
import net.laserdiamond.ventureplugin.skills.Components.ExpGain.combat.mobExp;
import net.laserdiamond.ventureplugin.skills.Components.ExpGain.farming.farmingExp;
import net.laserdiamond.ventureplugin.skills.Components.ExpGain.foraging.foragingExp;
import net.laserdiamond.ventureplugin.skills.Components.ExpGain.mining.miningExp;
import net.laserdiamond.ventureplugin.skills.Components.SkillsEXP;
import net.laserdiamond.ventureplugin.skills.Components.SkillsLevel;
import net.laserdiamond.ventureplugin.skills.Components.SkillsProfile;
import net.laserdiamond.ventureplugin.skills.Components.SkillsReward;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
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

        if (!event.isCancelled())
        {
            switch (skill)
            {
                // TODO: When current skill exp + gained amount is greater than the required amount, level up
                // TODO: Call event when skills exp should be gained

                // TODO: Add to required skill exp when gaining a level
                case COMBAT ->
                {
                    skillsEXP.setTotalCombatEXP(skillsEXP.getTotalCombatEXP() + expAmount);
                    skillsEXP.setCombatExpToNextLevel(skillsEXP.getCombatExpToNextLevel() + expAmount);
                    int skillLevel = skillsLevel.getCombatLevel();

                    while (skillsEXP.getCombatExpToNextLevel() >= skillsEXP.getRequiredCombatExpToNextLevel() && skillLevel < 50) // Check if player has acquired an adequate amount of exp to level up and is under the max level
                    {
                        skillLevel = skillsLevel.getCombatLevel();
                        double damageBonus = skillsReward.getCombatDamageBonus();
                        if (skillLevel < 5) // Previous level is under 5
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(skillsEXP.getRequiredCombatExpToNextLevel() + 100);
                        } else if (skillLevel < 10) // Previous level is under 10
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(skillsEXP.getRequiredCombatExpToNextLevel() + 250);
                        } else if (skillLevel < 15) // Previous level is under 15
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(skillsEXP.getRequiredCombatExpToNextLevel() + 500);
                        } else if (skillLevel < 20) // Previous level is under 20
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(skillsEXP.getRequiredCombatExpToNextLevel() + 1000);
                        } else if (skillLevel < 25) // Previous level is under 25
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(skillsEXP.getRequiredCombatExpToNextLevel() + 2000);
                        } else if (skillLevel < 30) // Previous level is under 30
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(skillsEXP.getRequiredCombatExpToNextLevel() + 5000);
                        } else if (skillLevel < 40) // Previous level is under 40
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(skillsEXP.getRequiredCombatExpToNextLevel() + 10000);
                        } else { // Previous level is under 50
                            skillsEXP.setRequiredCombatExpToNextLevel(skillsEXP.getRequiredCombatExpToNextLevel() + 17500);
                        }

                        skillsLevel.setCombatLevel(skillLevel + 1); // Update combat level
                        skillsReward.setCombatDamageBonus(damageBonus + 2);

                        if (skillLevel + 1 == 50) // New level is 50
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(0); // Required Exp for level 50+ is 0
                            break;
                        }
                    }
                }
                case MINING ->
                {
                    skillsEXP.setTotalMiningEXP(skillsEXP.getTotalMiningEXP() + expAmount);
                    skillsEXP.setMiningExpToNextLevel(skillsEXP.getMiningExpToNextLevel() + expAmount);
                    int skillLevel = skillsLevel.getMiningLevel();

                    while (skillsEXP.getMiningExpToNextLevel() >= skillsEXP.getRequiredMiningExpToNextLevel() && skillLevel < 50)
                    {
                        skillLevel = skillsLevel.getMiningLevel();
                        double requiredExp = skillsEXP.getRequiredMiningExpToNextLevel();
                        double fortuneBonus = skillsReward.getMiningFortuneBonus();
                        double defenseBonus = skillsReward.getMiningDefenseBonus();

                        if (skillLevel < 5)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 100);
                        } else if (skillLevel < 10)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 250);
                        } else if (skillLevel < 15)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 500);
                        } else if (skillLevel < 20)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 1000);
                        } else if (skillLevel < 25)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 2000);
                        } else if (skillLevel < 30)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 5000);
                        } else if (skillLevel < 40)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 10000);
                        } else if (skillLevel < 50)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 17500);
                        }

                        skillsLevel.setMiningLevel(skillLevel + 1);
                        skillsReward.setMiningFortuneBonus(fortuneBonus + 2);
                        skillsReward.setMiningDefenseBonus(defenseBonus + 2);

                        if (skillLevel + 1 == 50)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(0);
                            break;
                        }
                    }
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

    /**
     * An entity death event that calls the combat exp gain event
     * @param event The EntityDeathEvent
     */
    @EventHandler
    public void combatExpGain(EntityDeathEvent event)
    {
        if (event.getEntity() instanceof Mob mob)
        {
            if (mob.getKiller() != null)
            {
                Player player = mob.getKiller();
                Double combatExp = mobExp.getMobCombatExp(mob);
                SkillsExpGainEvent expGainEvent = new SkillsExpGainEvent(player, combatExp, SkillsExpGainEvent.Skill.COMBAT);
                Bukkit.getPluginManager().callEvent(expGainEvent);
            }
        }
    }

    /**
     * A block break event that calls the skill exp gain events for mining, foraging, farming
     * @param event The BlockBreakEvent
     */
    @EventHandler
    public void blockBreakExpGain(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        Double miningSkillExp = miningExp.getBlockMiningExp(block);
        Double foragingSkillExp = foragingExp.getForagingExp(block);
        Double farmingSkillExp = farmingExp.getFarmingExp(block);

        SkillsExpGainEvent miningExpGainEvent = new SkillsExpGainEvent(player, miningSkillExp, SkillsExpGainEvent.Skill.MINING);
        SkillsExpGainEvent foragingExpGainEvent = new SkillsExpGainEvent(player, foragingSkillExp, SkillsExpGainEvent.Skill.FORAGING);
        SkillsExpGainEvent farmingExpGainEvent = new SkillsExpGainEvent(player, farmingSkillExp, SkillsExpGainEvent.Skill.FARMING);

        Bukkit.getPluginManager().callEvent(miningExpGainEvent);
        Bukkit.getPluginManager().callEvent(foragingExpGainEvent);
        Bukkit.getPluginManager().callEvent(farmingExpGainEvent);

    }
}
