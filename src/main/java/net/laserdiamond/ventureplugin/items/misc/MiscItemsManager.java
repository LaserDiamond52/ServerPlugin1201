package net.laserdiamond.ventureplugin.items.misc;

import net.laserdiamond.ventureplugin.VenturePlugin;

public class MiscItemsManager {

    private static final VenturePlugin PLUGIN = VenturePlugin.getInstance();

    /**
     * Mana Shard item
     */
    public static final VentureMiscItem MANA_SHARD = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Mana Shard";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.MANA_SHARD;
        }
    };

    /**
     * Magic Sphere item
     */
    public static final VentureMiscItem MAGIC_SPHERE = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Magic Sphere";
        }

        @Override
        protected String playerHeadSkin() {
            return "http://textures.minecraft.net/texture/64b54f3e6a7016a14e76eedc53040f4960581bacac814ffb5816fae1a14b018d";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.MAGIC_SPHERE;
        }
    };

    /**
     * Blaze Core item
     */
    public static final VentureMiscItem BLAZE_CORE = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Blaze Core";
        }

        @Override
        protected String playerHeadSkin() {
            return "http://textures.minecraft.net/texture/64b1b9ce2e9a6ce8a985d39776e2908077b82e6a333d2a81a441438eab39f8e1";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.BLAZE_CORE;
        }
    };

    /**
     * Blaze Flames item
     */
    public static final VentureMiscItem BLAZE_FLAMES = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Blaze Flames";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.BLAZE_FLAMES;
        }
    };

    /**
     * Blaze Ashes item
     */
    public static final VentureMiscItem BLAZE_ASHES = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Blaze Ashes";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.BLAZE_ASHES;
        }
    };

    /**
     * Creeper Heart item
     */
    public static final VentureMiscItem CREEPER_HEART = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Queen Creeperia's Heart";
        }

        @Override
        protected String playerHeadSkin() {
            return "http://textures.minecraft.net/texture/48179b175daa79f73c665b61163364f6627e3d02b7253d427ebd2ff6818de6ce";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.CREEPER_HEART;
        }
    };

    /**
     * Super-Charged Gunpowder item
     */
    public static final VentureMiscItem SUPER_CHARGED_GUNPOWDER = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Super-Charged Gunpowder";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.SUPERCHARGED_GUNPOWDER;
        }
    };

    /**
     * Iron-Armored Elytra Blueprint item
     */
    public static final VentureMiscItem IRON_ELYTRA_BLUEPRINT = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Iron-Armored Elytra Blueprint";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.IRON_ELYTRA_BLUEPRINT;
        }
    };

    /**
     * Diamond-Armored Elytra Blueprint item
     */
    public static final VentureMiscItem DIAMOND_ELYTRA_BLUEPRINT = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Diamond-Armored Elytra Blueprint";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.DIAMOND_ELYTRA_BLUEPRINT;
        }
    };

    /**
     * Netherite-Armored Elytra Blueprint item
     */
    public static final VentureMiscItem NETHERITE_ELYTRA_BLUEPRINT = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Netherite-Armored Elytra Blueprint";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.NETHERITE_ELYTRA_BLUEPRINT;
        }
    };

    /**
     * Machine Gun Bow Blueprint item
     */
    public static final VentureMiscItem MACHINE_GUN_BOW_BLUEPRINT = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Machine Gun Bow Blueprint";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.MACHINE_GUN_BOW_BLUEPRINT;
        }
    };

    /**
     * Condensed Blaze Rod item
     */
    public static final VentureMiscItem CONDENSED_BLAZE_ROD = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Condensed Blaze Rod";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.CONDENSED_BLAZE_ROD;
        }
    };

    /**
     * Spider Silk item
     */
    public static final VentureMiscItem SPIDER_SILK = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Spider Silk";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.SPIDER_SILK;
        }
    };

    /**
     * Zombie Intestines item
     */
    public static final VentureMiscItem ZOMBIE_INTESTINES = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Zombie Intestines";
        }

        @Override
        protected String playerHeadSkin() {
            return "http://textures.minecraft.net/texture/79c08e7447aecda5c9ab6113eb0efbabfa1bd9f811811208d89875c146ee8844";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.ZOMBIE_INTESTINES;
        }
    };

    /**
     * Hyper-Condensed Blaze Rod item
     */
    public static final VentureMiscItem HYPER_CONDENSED_BLAZE_ROD = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Hyper Condensed Blaze Rod";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.HYPER_CONDENSED_BLAZE_ROD;
        }
    };

    /**
     * Cracked Ribs item
     */
    public static final VentureMiscItem CRACKED_RIBS = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Cracked Ribs";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.CRACKED_RIBS;
        }
    };

    /**
     * Magma Gel item
     */
    public static final VentureMiscItem MAGMA_GEL = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Magma Gel";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.MAGMA_GEL;
        }
    };

    /**
     * Enhanced Blaze Powder item
     */
    public static final VentureMiscItem ENHANCED_BLAZE_POWDER = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Enhanced Blaze Powder";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.ENHANCED_BLAZE_POWDER;
        }
    };

    /**
     * Ribs item
     */
    public static final VentureMiscItem RIBS = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Ribs";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.RIBS;
        }
    };

    /**
     * Blazium Ingot item
     */
    public static final VentureMiscItem BLAZIUM_INGOT = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Blazium Ingot";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.BLAZIUM_INGOT;
        }
    };

    /**
     * Magma Cream Cube item
     */
    public static final VentureMiscItem MAGMA_CREAM_CUBE = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Magma Cream Cube";
        }

        @Override
        protected String playerHeadSkin() {
            return "http://textures.minecraft.net/texture/43093a5b77642d4092112f46ea68140fb5ae04bbd231cda1066a04c18b89c94e";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.MAGMA_CREAM_CUBE;
        }
    };

    /**
     * Ghost Fragment item
     */
    public static final VentureMiscItem GHOST_FRAGMENT = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Ghost Fragment";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.GHOST_FRAGMENT;
        }
    };

    /**
     * Health Elixir item
     */
    public static final VentureMiscItem HEALTH_ELIXIR = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Health Elixir";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.HEALTH_ELIXIR;
        }
    };

    /**
     * Heart of Atlantis item
     */
    public static final VentureMiscItem HEART_OF_ATLANTIS = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Heart of Atlantis";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.HEART_OF_ATLANTIS;
        }
    };

    /**
     * Atlantian Gold item
     */
    public static final VentureMiscItem ATLANTIAN_GOLD = new VentureMiscItem(PLUGIN) {
        @Override
        protected String itemName() {
            return "Atlantian Gold";
        }

        @Override
        protected VentureMiscMaterials ventureMiscMaterials() {
            return VentureMiscMaterials.ATLANTIAN_GOLD;
        }
    };

    /**
     * Registers all the miscellaneous items for this plugin
     */
    public static void registerItems()
    {
        MANA_SHARD.registerItem();
        MAGIC_SPHERE.registerItem();
        BLAZE_CORE.registerItem();
        BLAZE_FLAMES.registerItem();
        BLAZE_ASHES.registerItem();
        CREEPER_HEART.registerItem();
        SUPER_CHARGED_GUNPOWDER.registerItem();
        IRON_ELYTRA_BLUEPRINT.registerItem();
        DIAMOND_ELYTRA_BLUEPRINT.registerItem();
        NETHERITE_ELYTRA_BLUEPRINT.registerItem();
        MACHINE_GUN_BOW_BLUEPRINT.registerItem();
        CONDENSED_BLAZE_ROD.registerItem();
        SPIDER_SILK.registerItem();
        ZOMBIE_INTESTINES.registerItem();
        HYPER_CONDENSED_BLAZE_ROD.registerItem();
        CRACKED_RIBS.registerItem();
        MAGMA_GEL.registerItem();
        ENHANCED_BLAZE_POWDER.registerItem();
        RIBS.registerItem();
        BLAZIUM_INGOT.registerItem();
        MAGMA_CREAM_CUBE.registerItem();
        GHOST_FRAGMENT.registerItem();
        HEALTH_ELIXIR.registerItem();
        HEART_OF_ATLANTIS.registerItem();
        ATLANTIAN_GOLD.registerItem();
    }
}
