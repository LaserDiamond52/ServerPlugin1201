package net.laserdiamond.ventureplugin.items.menuItems.skills;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.events.skills.SkillsExpGainEvent;
import net.laserdiamond.ventureplugin.items.menuItems.util.VentureSkillProgressItem;

public final class SkillsProgressMenuItems {

    private static final VenturePlugin PLUGIN = VenturePlugin.getInstance();

    public static final VentureSkillProgressItem COMBAT_PROGRESS = new VentureSkillProgressItem(PLUGIN) {
        @Override
        public SkillsExpGainEvent.Skill getSkill() {
            return SkillsExpGainEvent.Skill.COMBAT;
        }
    };

    public static final VentureSkillProgressItem MINING_PROGRESS = new VentureSkillProgressItem(PLUGIN) {
        @Override
        public SkillsExpGainEvent.Skill getSkill() {
            return SkillsExpGainEvent.Skill.MINING;
        }
    };

    public static final VentureSkillProgressItem FORAGING_PROGRESS = new VentureSkillProgressItem(PLUGIN) {
        @Override
        public SkillsExpGainEvent.Skill getSkill() {
            return SkillsExpGainEvent.Skill.FORAGING;
        }
    };

    public static final VentureSkillProgressItem FARMING_PROGRESS = new VentureSkillProgressItem(PLUGIN) {
        @Override
        public SkillsExpGainEvent.Skill getSkill() {
            return SkillsExpGainEvent.Skill.FARMING;
        }
    };

    public static final VentureSkillProgressItem ENCHANTING_PROGRESS = new VentureSkillProgressItem(PLUGIN) {
        @Override
        public SkillsExpGainEvent.Skill getSkill() {
            return SkillsExpGainEvent.Skill.ENCHANTING;
        }
    };

    public static final VentureSkillProgressItem FISHING_PROGRESS = new VentureSkillProgressItem(PLUGIN) {
        @Override
        public SkillsExpGainEvent.Skill getSkill() {
            return SkillsExpGainEvent.Skill.FISHING;
        }
    };

    public static final VentureSkillProgressItem BREWING_PROGRESS = new VentureSkillProgressItem(PLUGIN) {
        @Override
        public SkillsExpGainEvent.Skill getSkill() {
            return SkillsExpGainEvent.Skill.BREWING;
        }
    };
}
