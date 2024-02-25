package net.laserdiamond.serverplugin1201.items.management.misc.Minerals.Vanilla;

import net.laserdiamond.serverplugin1201.items.management.ItemForger;
import net.laserdiamond.serverplugin1201.items.management.PluginItemRarity;
import net.laserdiamond.serverplugin1201.items.management.misc.MiscFabricate;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MineralsManager {

    public static ItemForger createMineral(Minerals minerals) {

        ItemForger itemForger = new ItemForger(minerals.getMaterial());
        itemForger.setCustomModelData(minerals.getNormalCMD());
        return itemForger;
    }

    public static ItemForger createHyperiteMineral(Minerals minerals) {

        ItemForger itemForger = new ItemForger(minerals.getMaterial());
        itemForger.setCustomModelData(minerals.getHyperiteCMD());
        itemForger.setRarity(PluginItemRarity.Rarity.COMMON);
        return itemForger;
    }
    public enum Minerals {

        EMERALD (Material.EMERALD,1,11, "Emerald Hyperite"),
        REDSTONE (Material.REDSTONE,2,12, "Redstone Hyperite"),
        LAPIS (Material.LAPIS_LAZULI,3,13, "Lapis Hyperite"),
        AMETHYST (Material.AMETHYST_SHARD,4,14, "Amethyst Hyperite"),
        QUARTZ (Material.QUARTZ,5,15, "Quartz Hyperite"),
        NETHERITE (Material.NETHERITE_INGOT,6,16, "Netherite Hyperite"),
        DIAMOND (Material.DIAMOND,7,17, "Diamond Hyperite"),
        GOLD (Material.GOLD_INGOT,8,18, "Gold Hyperite"),
        IRON (Material.IRON_INGOT,9,19, "Iron Hyperite"),
        COPPER (Material.COPPER_INGOT,10,20, "Copper Hyperite");

        private final Material material;
        private final int normalCMD, hyperiteCMD;
        private final String hyperiteName;

        Minerals(Material material, int normalCMD, int hyperiteCMD, String hyperiteName) {
            this.material = material;
            this.normalCMD = normalCMD;
            this.hyperiteCMD = hyperiteCMD;
            this.hyperiteName = hyperiteName;
        }

        private static final List<Integer> normalMineralCMD = new ArrayList<>();
        static {
            normalMineralCMD.add(EMERALD.getNormalCMD());
            normalMineralCMD.add(REDSTONE.getNormalCMD());
            normalMineralCMD.add(LAPIS.getNormalCMD());
            normalMineralCMD.add(AMETHYST.getNormalCMD());
            normalMineralCMD.add(QUARTZ.getNormalCMD());
            normalMineralCMD.add(NETHERITE.getNormalCMD());
            normalMineralCMD.add(DIAMOND.getNormalCMD());
            normalMineralCMD.add(GOLD.getNormalCMD());
            normalMineralCMD.add(IRON.getNormalCMD());
            normalMineralCMD.add(COPPER.getNormalCMD());
        }

        public static boolean isNormalMineral(int customModelData) {
            if (normalMineralCMD.contains(customModelData)) {
                return true;
            }
            return false;
        }

        private static final List<Integer> hyperiteMineralCMD = new ArrayList<>();
        static {
            hyperiteMineralCMD.add(EMERALD.getHyperiteCMD());
            hyperiteMineralCMD.add(REDSTONE.getHyperiteCMD());
            hyperiteMineralCMD.add(LAPIS.getHyperiteCMD());
            hyperiteMineralCMD.add(AMETHYST.getHyperiteCMD());
            hyperiteMineralCMD.add(QUARTZ.getHyperiteCMD());
            hyperiteMineralCMD.add(NETHERITE.getHyperiteCMD());
            hyperiteMineralCMD.add(DIAMOND.getHyperiteCMD());
            hyperiteMineralCMD.add(GOLD.getHyperiteCMD());
            hyperiteMineralCMD.add(IRON.getHyperiteCMD());
            hyperiteMineralCMD.add(COPPER.getHyperiteCMD());
        }

        public static boolean isHyperiteMineral(int customModelData) {
            if (hyperiteMineralCMD.contains(customModelData)) {
                return true;
            }
            return false;
        }
        public Material getMaterial() {
            return material;
        }

        public int getNormalCMD() {
            return normalCMD;
        }

        public int getHyperiteCMD() {
            return hyperiteCMD;
        }

        public String getHyperiteName() {
            return hyperiteName;
        }
    }

}
