package net.laserdiamond.ventureplugin.items.misc.trimTemplate;

import net.laserdiamond.ventureplugin.items.util.ItemForger;
import org.bukkit.Material;

@Deprecated
public class TrimTemplateManager {

    public static ItemForger createTemplate(Trims trims) {

        ItemForger itemForger = new ItemForger(trims.getMaterial());
        itemForger.setCustomModelData(trims.getCmd());
        return itemForger;
    }

    public enum Trims {
        SENTRY (Material.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, 21),
        VEX (Material.VEX_ARMOR_TRIM_SMITHING_TEMPLATE, 22),
        WILD (Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, 23),
        COAST (Material.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, 24),
        DUNE (Material.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, 25),
        WAYFINDER (Material.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, 26),
        RAISER (Material.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, 27),
        SHAPER (Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, 28),
        HOST (Material.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, 29),
        WARD (Material.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, 30),
        SILENCE (Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 31),
        TIDE (Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, 32),
        SNOUT (Material.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, 33),
        RIB (Material.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, 34),
        EYE (Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, 35),
        SPIRE (Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, 36);

        private final Material material;
        private final int cmd;
        Trims(Material material, int cmd) {
            this.material = material;
            this.cmd = cmd;
        }

        public Material getMaterial() {
            return material;
        }

        public int getCmd() {
            return cmd;
        }
    }
}
