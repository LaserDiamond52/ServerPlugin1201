package net.laserdiamond.ventureplugin.entities.management;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;

public class Mobs
{

    public enum vanillaMobs
    {
        ALLAY (ChatColor.AQUA + "Allay", EntityType.ALLAY),
        AXOLOTL (ChatColor.LIGHT_PURPLE + "Axolotl", EntityType.AXOLOTL),
        BAT (ChatColor.BLACK + "Bat", EntityType.BAT),
        CAMEL (ChatColor.GOLD + "Camel", EntityType.CAMEL),
        CAT (ChatColor.GRAY + "Cat", EntityType.CAT),
        CHICKEN (ChatColor.WHITE + "Chicken", EntityType.CHICKEN),
        COD (ChatColor.WHITE + "Cod", EntityType.COD),
        COW (ChatColor.WHITE + "Cow", EntityType.COW),
        DONKEY (ChatColor.WHITE + "Donkey", EntityType.DONKEY),
        FROG (ChatColor.DARK_GREEN + "Fox", EntityType.FOX),
        GLOW_SQUID (ChatColor.DARK_AQUA + "Glow Squid", EntityType.GLOW_SQUID),
        HORSE (ChatColor.WHITE + "Horse", EntityType.HORSE),
        MOOSHROOM (ChatColor.RED + "Mooshroom", EntityType.MUSHROOM_COW),
        MULE (ChatColor.WHITE + "Mule", EntityType.MULE),
        OCELOT (ChatColor.GOLD + "Ocelot", EntityType.OCELOT),
        PARROT (ChatColor.WHITE + "Parrot", EntityType.PARROT),
        PIG (ChatColor.WHITE + "Pig", EntityType.PIG),
        PUFFERFISH (ChatColor.WHITE + "Pufferfish", EntityType.PUFFERFISH),
        RABBIT (ChatColor.WHITE + "Rabbit", EntityType.RABBIT),
        SALMON (ChatColor.WHITE + "Salmon", EntityType.SALMON),
        SHEEP (ChatColor.WHITE + "Sheep", EntityType.SHEEP),
        SKELETON_HORSE (ChatColor.WHITE + "Skeleton Horse", EntityType.SKELETON_HORSE),
        SNIFFER (ChatColor.RED + "Sniffer", EntityType.SNIFFER),
        SNOW_GOLEM (ChatColor.WHITE + "Snow Golem", EntityType.SNOWMAN),
        SQUID (ChatColor.BLUE + "Squid", EntityType.SQUID),
        STRIDER (ChatColor.RED + "Strider", EntityType.STRIDER),
        TADPOLE (ChatColor.WHITE + "Tadpole", EntityType.TADPOLE),
        TROPICAL_FISH (ChatColor.WHITE + "Tropical Fish", EntityType.TROPICAL_FISH),
        TURTLE (ChatColor.GREEN + "Turtle", EntityType.TURTLE),
        VILLAGER (ChatColor.WHITE + "Villager", EntityType.VILLAGER),
        WANDERING_TRADER (ChatColor.BLUE + "Wandering Trader", EntityType.WANDERING_TRADER),
        BEE (ChatColor.GOLD + "Bee", EntityType.BEE),
        CAVE_SPIDER (ChatColor.DARK_BLUE + "Cave Spider", EntityType.CAVE_SPIDER),
        DOLPHIN (ChatColor.GRAY + "Dolphin", EntityType.DOLPHIN),
        DROWNED (ChatColor.DARK_AQUA + "Drowned", EntityType.DROWNED),
        ENDERMAN (ChatColor.DARK_PURPLE + "Enderman", EntityType.ENDERMAN),
        FOX (ChatColor.GOLD + "Fox", EntityType.FOX),
        GOAT (ChatColor.WHITE + "Goat", EntityType.GOAT),
        IRON_GOLEM (ChatColor.WHITE + "Iron Golem", EntityType.IRON_GOLEM),
        LLAMA (ChatColor.WHITE + "Llama", EntityType.LLAMA),
        PANDA (ChatColor.WHITE + "Panda", EntityType.PANDA),
        PIGLIN (ChatColor.GOLD + "Piglin", EntityType.PIGLIN),
        POLAR_BEAR (ChatColor.WHITE + "Polar Bear", EntityType.POLAR_BEAR),
        SPIDER (ChatColor.DARK_GRAY + "Spider", EntityType.SPIDER),
        TRADER_LLAMA (ChatColor.BLUE + "Trader Llama", EntityType.TRADER_LLAMA),
        WOLF (ChatColor.GRAY + "Wolf", EntityType.WOLF),
        ZOMBIFIED_PIGLIN (ChatColor.DARK_GREEN + "Zombified Piglin", EntityType.ZOMBIFIED_PIGLIN),
        BLAZE (ChatColor.GOLD + "Blaze", EntityType.BLAZE),
        CREEPER (ChatColor.GREEN + "Creeper", EntityType.CREEPER),
        ELDER_GUARDIAN (ChatColor.DARK_AQUA + "Elder Guardian", EntityType.ELDER_GUARDIAN),
        ENDERMITE (ChatColor.DARK_PURPLE + "Endermite", EntityType.ENDERMITE),
        ENDER_DRAGON (ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Ender Dragon", EntityType.ENDER_DRAGON),
        EVOKER (ChatColor.GRAY + "Evoker", EntityType.EVOKER),
        GHAST (ChatColor.WHITE + "Ghast", EntityType.GHAST),
        GUARDIAN (ChatColor.DARK_AQUA + "Guardian", EntityType.GUARDIAN),
        HOGLIN (ChatColor.WHITE + "Hoglin", EntityType.HOGLIN),
        HUSK (ChatColor.DARK_GREEN + "Husk", EntityType.HUSK),
        MAGMA_CUBE (ChatColor.DARK_RED + "Magma Cube", EntityType.MAGMA_CUBE),
        PHANTOM (ChatColor.BLUE + "Phantom", EntityType.PHANTOM),
        PIGLIN_BRUTE (ChatColor.GOLD + "Piglin Brute", EntityType.PIGLIN_BRUTE),
        PILLAGER (ChatColor.GRAY + "Pillager", EntityType.PILLAGER),
        RAVAGER (ChatColor.GRAY + "Ravager", EntityType.RAVAGER),
        SHULKER (ChatColor.WHITE + "Shulker", EntityType.SHULKER),
        SILVERFISH (ChatColor.DARK_GRAY + "Silverfish", EntityType.SILVERFISH),
        SKELETON (ChatColor.WHITE + "Skeleton", EntityType.SKELETON),
        SLIME (ChatColor.GREEN + "Slime", EntityType.SLIME),
        STRAY (ChatColor.GRAY + "Stray", EntityType.STRAY),
        VEX (ChatColor.WHITE + "Vex", EntityType.VEX),
        VINDICATOR (ChatColor.DARK_GRAY + "Vindicator", EntityType.VINDICATOR),
        WARDEN (ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Warden", EntityType.WARDEN),
        WITCH (ChatColor.WHITE + "Witch", EntityType.WITCH),
        WITHER (ChatColor.BLACK + "" + ChatColor.BOLD + "Wither", EntityType.WITHER),
        WITHER_SKELETON (ChatColor.BLACK + "Wither Skeleton", EntityType.WITHER_SKELETON),
        ZOGLIN (ChatColor.DARK_GREEN + "Zoglin", EntityType.ZOGLIN),
        ZOMBIE (ChatColor.DARK_GREEN + "Zombie", EntityType.ZOMBIE),
        ZOMBIE_VILLAGER (ChatColor.DARK_GREEN + "Zombie Villager", EntityType.ZOMBIE_VILLAGER),
        GIANT (ChatColor.DARK_GREEN + "Giant", EntityType.GIANT),
        ZOMBIE_HORSE (ChatColor.DARK_GREEN + "Zombie Horse", EntityType.ZOMBIE_HORSE),
        ILLUSIONER (ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Illusioner", EntityType.ILLUSIONER);

        private final String name;

        private final EntityType entityType;
        vanillaMobs(String name, EntityType entityType)
        {
            this.name = name;
            this.entityType = entityType;
        }

        public String getName()
        {
            return name;
        }

        public EntityType getEntityType()
        {
            return entityType;
        }

        public static boolean isOfMob(EntityType entityType)
        {
            for (vanillaMobs vanillaMobs : values())
            {
                if (entityType.equals(vanillaMobs.entityType))
                {
                    return true;
                }
            }
            return false;
        }

        public static String getMobName(EntityType entityType)
        {
            for (vanillaMobs vanillaMobs : values())
            {
                if (entityType.equals(vanillaMobs.entityType))
                {
                    return vanillaMobs.name;
                }
            }

            return null;
        }
    }
}
