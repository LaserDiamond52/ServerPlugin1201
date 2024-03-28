package net.laserdiamond.ventureplugin.skills.Manager;

public class SkillItemsManager {



    public enum SkillItems {

        COMBAT_SKILL (100, 101, "Combat"),
        MINING_SKILL (102, 103, "Mining"),
        FORAGING (104, 105, "Foraging"),
        FARMING (106, 107, "Farming"),
        FISHING (108, 109, "Fishing"),
        ENCHANTING (110, 111, "Enchanting"),
        BREWING (112, 113, "Brewing"),
        TRADING (114, 115, "Trading");

        private final int baseCMD, maxCMD;
        private final String skillName;

        SkillItems(int baseCMD, int maxCMD, String skillName) {
            this.baseCMD = baseCMD;
            this.maxCMD = maxCMD;
            this.skillName = skillName;
        }

        public int getBaseCMD() {
            return baseCMD;
        }

        public int getMaxCMD() {
            return maxCMD;
        }

        public String getSkillName() {
            return skillName;
        }
    }
}
