package net.laserdiamond.ventureplugin.items.armor.trims.Components;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.armor.trims.Config.ArmorTrimConfig;
import org.bukkit.ChatColor;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.ArrayList;
import java.util.List;

public class TrimLore {

    private static final VenturePlugin plugin = VenturePlugin.getInstance();
    private static final ArmorTrimConfig armorTrimConfig = plugin.getArmorTrimConfig();

    public static List<String> createLore(ArmorMeta armorMeta) {

        if (armorMeta.getTrim() != null) {
            TrimMaterial trimMaterial = armorMeta.getTrim().getMaterial();
            TrimPattern trimPattern = armorMeta.getTrim().getPattern();

            List<String> trimLore = new ArrayList<>();

            trimLore.add(" ");
            //trimLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Armor Trim:");

            //TODO: Patterns
            double sentryEmeraldChance = armorTrimConfig.getDouble("sentryEmerald");
            double vexManaRegen = armorTrimConfig.getDouble("vexManaRegen");
            double wildForagingExp = armorTrimConfig.getDouble("wildForagingExp");
            double coastFishingExp = armorTrimConfig.getDouble("coastFishingExp");
            double duneMiningExp = armorTrimConfig.getDouble("duneMiningExp");
            double wayFinderGlow = armorTrimConfig.getDouble("wayfinderGlow");
            double raiserSusSandDrop = armorTrimConfig.getDouble("raiserSusSandDrop");
            double shaperSusGravelDrop = armorTrimConfig.getDouble("shaperSusGravelDrop");
            double hostBonusExp = armorTrimConfig.getDouble("hostBonusExp");
            double wardCombatExp = armorTrimConfig.getDouble("wardCombatExp");
            double silenceAttackChance = armorTrimConfig.getDouble("silenceAttackChance");
            double tideAquaticDamage = armorTrimConfig.getDouble("tideAquaticDamage");
            double snoutCounterChance = armorTrimConfig.getDouble("snoutCounterChance");
            double ribWitherDuration = armorTrimConfig.getDouble("ribWitherDuration");
            int ribWitherLevel = armorTrimConfig.getInt("ribWitherLevel");
            double eyeMagicDamagePerLevel = armorTrimConfig.getDouble("eyeMagicDamagePerLevel");

            double spireSaveFuelChance = armorTrimConfig.getDouble("spireSaveFuelChance");

            if (trimPattern.equals(TrimPattern.SENTRY)) {
                // Pillagers have a higher chance to drop an emerald
                trimLore.add(PatternDisplayColor.SENTRY.displayColor + "Sentry Trim Pattern");
                trimLore.add(ChatColor.GRAY + " Grants +" + PatternDisplayColor.SENTRY.displayColor + sentryEmeraldChance + ChatColor.GRAY + "% chance for");
                trimLore.add(ChatColor.GRAY + " pillagers to drop " +ChatColor.GREEN + "emeralds");

                //Sentry Trim Pattern
                // Grants +2.5% chance for
                // pillagers to drop emeralds

            } else if (trimPattern.equals(TrimPattern.VEX)) {
                // Bonus Mana Regen

                trimLore.add(PatternDisplayColor.VEX.displayColor + "Vex Trim Pattern");
                trimLore.add(ChatColor.GRAY + " Grants +" + PatternDisplayColor.VEX.displayColor + vexManaRegen + ChatColor.GRAY + "% bonus");
                trimLore.add(ChatColor.GRAY + " mana regeneration");

                //Vex Trim Pattern
                // Grants +2.5% bonus
                // mana regeneration

            } else if (trimPattern.equals(TrimPattern.WILD)) {
                // Grants bonus foraging exp

                trimLore.add(PatternDisplayColor.WILD.displayColor + "Wild Trim Pattern");
                trimLore.add(ChatColor.GRAY + " Grants +" + PatternDisplayColor.WILD.displayColor + wildForagingExp + ChatColor.GRAY + "% bonus");
                trimLore.add(ChatColor.GREEN + " Foraging" + ChatColor.GRAY + " skill EXP");

                //Wild Trim Pattern
                // Grants +7.5% bonus
                // foraging skill EXP

            } else if (trimPattern.equals(TrimPattern.COAST)) {
                // Grants bonus fishing exp

                trimLore.add(PatternDisplayColor.COAST.displayColor + "Coast Trim Pattern");
                trimLore.add(ChatColor.GRAY + " Grants +" + PatternDisplayColor.COAST.displayColor + coastFishingExp + ChatColor.GRAY + "% bonus");
                trimLore.add(ChatColor.AQUA + " Fishing" + ChatColor.GRAY + " skill EXP");

                //Coast Trim Pattern
                // Grants +7.5% bonus
                // fishing skill EXP

            } else if (trimPattern.equals(TrimPattern.DUNE)) {
                // Bonus Mining exp

                trimLore.add(PatternDisplayColor.DUNE.displayColor + "Dune Trim Pattern");
                trimLore.add(ChatColor.GRAY + " Grants +" + PatternDisplayColor.WILD.displayColor + duneMiningExp + ChatColor.GRAY + "% bonus");
                trimLore.add(ChatColor.DARK_BLUE + " Mining" + ChatColor.GRAY + " skill EXP");

                //Dune Trim Pattern
                // Grants +7.5% bonus
                // mining skill EXP

            } else if (trimPattern.equals(TrimPattern.WAYFINDER)) {
                // Melee attacks cause enemies to glow

                trimLore.add(PatternDisplayColor.WAYFINDER.displayColor + "Wayfinder Trim Pattern");
                trimLore.add(ChatColor.GRAY + " Attacks cause enemies");
                trimLore.add(ChatColor.WHITE + " glow" + ChatColor.GRAY + "for +" + PatternDisplayColor.WAYFINDER.displayColor + wayFinderGlow + ChatColor.GRAY + " seconds");

                //Wayfinder Trim Pattern
                // Attacks cause enemies
                // to glow for +5 seconds
            } else if (trimPattern.equals(TrimPattern.RAISER)) {
                // Chance to find suspicious sand drop

                trimLore.add(PatternDisplayColor.RAISER.displayColor + "Raiser Trim Pattern");
                trimLore.add(ChatColor.GRAY + " Grants +" + PatternDisplayColor.RAISER.displayColor + raiserSusSandDrop + ChatColor.GRAY + "% chance to discover");
                trimLore.add(net.md_5.bungee.api.ChatColor.of("#e3e0b8") + "suspicious sand" + ChatColor.GRAY + " drops while mining");

                //Raiser Trim Pattern
                // Grants +3.75% chance to discover
                // suspicious sand drops while mining

            } else if (trimPattern.equals(TrimPattern.SHAPER)) {
                // Chance to find suspicious gravel drop

                trimLore.add(PatternDisplayColor.SHAPER.displayColor + "Shaper Trim Pattern");
                trimLore.add(ChatColor.GRAY + " Grants +" + PatternDisplayColor.SHAPER.displayColor + shaperSusGravelDrop + ChatColor.GRAY + "% chance to discover");
                trimLore.add(ChatColor.GRAY + " suspicious gravel drops while mining");

                //Shaper Trim Pattern
                // Grants +3.75% chance to discover
                // suspicious gravel drops while mining

            } else if (trimPattern.equals(TrimPattern.HOST)) {
                // Bonus skill exp

                trimLore.add(PatternDisplayColor.HOST.displayColor + "Host Trim Pattern");
                trimLore.add(ChatColor.GRAY + " Grants +" + PatternDisplayColor.HOST.displayColor + hostBonusExp + ChatColor.GRAY + "% bonus skill");
                trimLore.add(ChatColor.GRAY + " EXP for all skills");

                //Host Trim Pattern
                // Grants +5% bonus skill
                // EXP for all skills

            } else if (trimPattern.equals(TrimPattern.WARD)) {
                // Bonus Combat exp

                trimLore.add(PatternDisplayColor.WARD.displayColor + "Ward Trim Pattern");
                trimLore.add(ChatColor.GRAY + " Grants +" + PatternDisplayColor.WARD + wardCombatExp + ChatColor.GRAY + "% bonus");
                trimLore.add(ChatColor.DARK_RED + " Combat" + ChatColor.GRAY + " skill EXP");

                //Ward Trim Pattern
                // Grants +7.5% bonus
                // combat skill exp

            } else if (trimPattern.equals(TrimPattern.SILENCE)) {
                // Chance for attacks to hit a second time

                trimLore.add(PatternDisplayColor.SILENCE.displayColor + "Silence Trim Pattern");
                trimLore.add(ChatColor.GRAY + " Grants +" + PatternDisplayColor.SILENCE.displayColor + silenceAttackChance + ChatColor.GRAY + "% chance for");
                trimLore.add(ChatColor.GRAY + " attacks to hit again");

                //Silence Trim Pattern
                // Grants +1.25% chance for
                // attacks to hit again

            } else if (trimPattern.equals(TrimPattern.TIDE)) {
                // Bonus damage to aquatic mobs

                trimLore.add(PatternDisplayColor.TIDE.displayColor + "Tide Trim Pattern");
                trimLore.add(ChatColor.GRAY + " Grants +" + PatternDisplayColor.TIDE.displayColor + tideAquaticDamage + ChatColor.GRAY + "% bonus damage");
                trimLore.add(ChatColor.GRAY + " against aquatic mobs");

                //Tide Trim Pattern
                // Grants +5% bonus damage
                // against aquatic mobs

            } else if (trimPattern.equals(TrimPattern.SNOUT)) {
                // Chance to do counter-attack when blocking with a shield

                trimLore.add(PatternDisplayColor.SNOUT.displayColor + "Snout Trim Pattern");
                trimLore.add(ChatColor.GRAY + " Grants +" + PatternDisplayColor.SNOUT.displayColor + snoutCounterChance + ChatColor.GRAY + "% chance to perform");
                trimLore.add(ChatColor.GRAY + " a counter attack when blocking");
                trimLore.add(ChatColor.GRAY + " with a shield");

                //Snout Trim Pattern
                // Grants +1.75% chance to perform
                // a counter attack when blocking
                // with a shield

            } else if (trimPattern.equals(TrimPattern.RIB)) {
                // Inflict wither on mobs

                trimLore.add(PatternDisplayColor.RIB.displayColor + "Rib Trim Pattern");
                trimLore.add(ChatColor.GRAY + " Attacks inflict " + ChatColor.DARK_GRAY + "Wither " + ribWitherLevel);
                trimLore.add(ChatColor.GRAY + " for +" + PatternDisplayColor.RIB.displayColor + ribWitherDuration + ChatColor.GRAY + " seconds");


                //Rib Trim Pattern
                // Attacks inflict wither 3
                // for +5 seconds

            } else if (trimPattern.equals(TrimPattern.EYE)) {
                // Bonus magic damage based on experience levels

                trimLore.add(PatternDisplayColor.EYE.displayColor + "Eye Trim Pattern");
                trimLore.add(ChatColor.GRAY + " Grants +" + PatternDisplayColor.EYE.displayColor + eyeMagicDamagePerLevel + ChatColor.GRAY + "% more " + ChatColor.AQUA + "⚝" + ChatColor.GRAY + " damage");
                trimLore.add(ChatColor.GRAY + " for every " + ChatColor.GREEN + 5 + ChatColor.GRAY + " experience levels");

                //Eye Trim Pattern
                // Grants +0.175% more ⚝ damage
                // for every 5 experience levels

            } else if (trimPattern.equals(TrimPattern.SPIRE)) {
                // Small chance to not consume elytra fuel

                trimLore.add(PatternDisplayColor.SPIRE.displayColor + "Spire Trim Pattern");
                trimLore.add(ChatColor.GRAY + " Grants +" + PatternDisplayColor.SPIRE.displayColor + spireSaveFuelChance + ChatColor.GRAY + "% chance to not");
                trimLore.add(ChatColor.GRAY + " consume fireworks while flying");

                //Spire Trim Pattern
                // Grants +1.75% chance to not
                // consume fireworks while flying
            }

            // TODO: Materials
            double copperSpeed = armorTrimConfig.getDouble("copperSpeed");
            double goldSaturation = armorTrimConfig.getDouble("goldSaturation");
            double ironHealthBoost = armorTrimConfig.getDouble("ironHealthBoost");
            double lapisExpBonus = armorTrimConfig.getDouble("lapisExpBonus");
            double quartzMiningExp = armorTrimConfig.getDouble("quartzMiningExp");
            double redstonePotionBonus = armorTrimConfig.getDouble("redstonePotionBonus");
            double emeraldLuck = armorTrimConfig.getDouble("emeraldLuck");
            double amethystDamage = armorTrimConfig.getDouble("amethystDamage");
            double diamondMana = armorTrimConfig.getDouble("diamondMana");
            double netheriteDefense = armorTrimConfig.getDouble("netheriteDefense");
            double netheriteFireDefense = armorTrimConfig.getDouble("netheriteFireDefense");

            if (trimMaterial.equals(TrimMaterial.COPPER)) {
                // Grants Speed

                trimLore.add(MaterialDisplayColor.COPPER.displayColor + "Copper Trim Material");
                trimLore.add(ChatColor.GRAY + " Grants +" + ChatColor.WHITE + copperSpeed + ChatColor.GRAY + "% speed");

                //Copper Trim Material
                // Grants +10% speed

            } else if (trimMaterial.equals(TrimMaterial.GOLD)) {
                // Grants Saturation

                trimLore.add(MaterialDisplayColor.GOLD.displayColor + "Gold Trim Material");
                trimLore.add(ChatColor.GRAY + " Grants +" + ChatColor.YELLOW + goldSaturation + ChatColor.GRAY + "% saturation");

                //Gold Trim Material
                // Grants +5% saturation

            } else if (trimMaterial.equals(TrimMaterial.IRON)) {
                // Grants Health Boost

                trimLore.add(MaterialDisplayColor.IRON.displayColor + "Iron Trim Material");
                trimLore.add(ChatColor.GRAY + " Grants +" + ChatColor.RED + ironHealthBoost + "❤" + ChatColor.GRAY + " max health");

                //Iron Trim Material
                // Grants +5❤ max health

            } else if (trimMaterial.equals(TrimMaterial.LAPIS)) {
                // Grants bonus exp

                trimLore.add(MaterialDisplayColor.LAPIS.displayColor + "Lapis Trim Material");
                trimLore.add(ChatColor.GRAY + " Grants +" + ChatColor.GREEN + lapisExpBonus + ChatColor.GRAY + "% bonus experience");
                trimLore.add(ChatColor.GRAY + " points when killing mobs");

                //Lapis Trim Material
                // Grants +15% bonus experience
                // points when killing mobs

            } else if (trimMaterial.equals(TrimMaterial.QUARTZ)) {
                // Bonus mining exp

                trimLore.add(MaterialDisplayColor.QUARTZ.displayColor + "Quartz Trim Material");
                trimLore.add(ChatColor.GRAY + " Grants +" + MaterialDisplayColor.QUARTZ.displayColor + quartzMiningExp + ChatColor.GRAY + "% bonus");
                trimLore.add(ChatColor.DARK_BLUE + " Mining" + ChatColor.GRAY + " skill EXP");

                //Quartz Trim Material
                // Grants +7.5% bonus
                // Mining skill EXP

            } else if (trimMaterial.equals(TrimMaterial.REDSTONE)) {
                // Grants bonus potion duration

                trimLore.add(MaterialDisplayColor.REDSTONE.displayColor + "Redstone Trim Material");
                trimLore.add(ChatColor.GRAY + " Grants +" + MaterialDisplayColor.REDSTONE.displayColor + redstonePotionBonus + ChatColor.GRAY + "% longer");
                trimLore.add(ChatColor.GRAY + " potion duration");

                //Redstone Trim Material
                // Grants +15% longer
                // potion duration

            } else if (trimMaterial.equals(TrimMaterial.EMERALD)) {
                // Grants luck

                trimLore.add(MaterialDisplayColor.EMERALD.displayColor + "Emerald Trim Material");
                trimLore.add(ChatColor.GRAY + " Grants +" + ChatColor.GREEN + emeraldLuck + ChatColor.GRAY + " Luck");

                //Emerald Trim Material
                // Grants +0.25 Luck

            } else if (trimMaterial.equals(TrimMaterial.AMETHYST)) {
                // Grants damage boost to all stats

                trimLore.add(MaterialDisplayColor.AMETHYST.displayColor + "Amethyst Trim Material");
                trimLore.add(ChatColor.GRAY + " Grants +" + MaterialDisplayColor.AMETHYST.displayColor + amethystDamage + ChatColor.GRAY + "% damage");
                trimLore.add(ChatColor.GRAY + " for all damage types");

                //Amethyst Trim Material
                // Grants +1.25% damage
                // for all damage types

            } else if (trimMaterial.equals(TrimMaterial.DIAMOND)) {
                // Grants additional max mana

                trimLore.add(MaterialDisplayColor.DIAMOND.displayColor + "Diamond Trim Material");
                trimLore.add(ChatColor.GRAY + " Grants +" + ChatColor.BLUE + diamondMana + "\uD83D\uDD89" + ChatColor.GRAY + " max mana");

                //Diamond Trim Material
                // Grants +15🖉 max mana

            } else if (trimMaterial.equals(TrimMaterial.NETHERITE)) {
                // Grants defense and fire defense

                trimLore.add(MaterialDisplayColor.NETHERITE.displayColor + "Netherite Trim Material");
                trimLore.add(ChatColor.GRAY + " Grants +" + ChatColor.GREEN + netheriteDefense + "⛉" + ChatColor.GRAY + " defense");
                trimLore.add(ChatColor.GRAY + " and +" + ChatColor.GOLD + netheriteFireDefense + "⛉" + ChatColor.GRAY + " fire defense");

                //Netherite Trim Material
                // Grants +3⛉ defense
                // and +5⛉ fire defense

            }

            trimLore.add(" ");

            return trimLore;
        }
        return new ArrayList<>();
    }

    public enum MaterialDisplayColor {

        COPPER (net.md_5.bungee.api.ChatColor.of("#b2674c")),
        GOLD (ChatColor.GOLD.asBungee()),
        IRON (ChatColor.GRAY.asBungee()),
        LAPIS (ChatColor.BLUE.asBungee()),
        QUARTZ (ChatColor.WHITE.asBungee()),
        REDSTONE (ChatColor.RED.asBungee()),
        EMERALD (ChatColor.GREEN.asBungee()),
        AMETHYST (ChatColor.DARK_PURPLE.asBungee()),
        DIAMOND (ChatColor.AQUA.asBungee()),
        NETHERITE (ChatColor.DARK_GRAY.asBungee());

        private final net.md_5.bungee.api.ChatColor displayColor;

        MaterialDisplayColor(net.md_5.bungee.api.ChatColor displayColor) {
            this.displayColor = displayColor;
        }

        public net.md_5.bungee.api.ChatColor getDisplayColor() {
            return displayColor;
        }
    }

    public enum PatternDisplayColor {

        SENTRY (ChatColor.DARK_GREEN.asBungee()),
        VEX (ChatColor.DARK_BLUE.asBungee()),
        WILD (net.md_5.bungee.api.ChatColor.of("#738552")),
        COAST (ChatColor.AQUA.asBungee()),
        DUNE (net.md_5.bungee.api.ChatColor.of("#e3dbb0")),
        WAYFINDER (net.md_5.bungee.api.ChatColor.of("#947662")),
        RAISER (net.md_5.bungee.api.ChatColor.of("#947662")),
        SHAPER (net.md_5.bungee.api.ChatColor.of("#947662")),
        HOST (net.md_5.bungee.api.ChatColor.of("#947662")),
        WARD (ChatColor.DARK_GRAY.asBungee()),
        SILENCE (ChatColor.DARK_AQUA.asBungee()/*net.md_5.bungee.api.ChatColor.of("#052a32")*/),
        TIDE (net.md_5.bungee.api.ChatColor.of("#6eb9ae")),
        SNOUT (net.md_5.bungee.api.ChatColor.of("#3c3947")),
        RIB (net.md_5.bungee.api.ChatColor.of("#532e35")),
        EYE (net.md_5.bungee.api.ChatColor.of("#eef6b4")),
        SPIRE (net.md_5.bungee.api.ChatColor.of("#ac7bac"));

        private final net.md_5.bungee.api.ChatColor displayColor;

        PatternDisplayColor(net.md_5.bungee.api.ChatColor displayColor) {
            this.displayColor = displayColor;
        }

        public net.md_5.bungee.api.ChatColor getDisplayColor() {
            return displayColor;
        }
    }
}
