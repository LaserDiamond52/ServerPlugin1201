package net.laserdiamond.ventureplugin.items.menuItems.skills;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.items.menuItems.util.MenuItem;
import net.laserdiamond.ventureplugin.items.menuItems.util.VentureMenuItem;
import net.laserdiamond.ventureplugin.util.StatSymbols;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public final class SkillsMenuItems {

    private static final VenturePlugin PLUGIN = VenturePlugin.getInstance();

    public static List<String> skillLore(MenuItem menuItem, List<String> abilityLore, int skillLevel, double totalExp, double expToNextLevel, double requiredExpToNextLevel)
    {
        int nextLvl = skillLevel + 1;
        double progress = expToNextLevel / requiredExpToNextLevel;

        DecimalFormat doubleDecimal = new DecimalFormat("0.00");

        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add(ChatColor.GRAY + "Current " + menuItem.getDisplayName() + ChatColor.GRAY + " Level: " + ChatColor.YELLOW + skillLevel);
        lore.add(" ");
        if (skillLevel < 50)
        {
            lore.add(ChatColor.GRAY + "Progress to level " + ChatColor.RED + nextLvl + ChatColor.GRAY + ":");
            lore.add(ChatColor.YELLOW + doubleDecimal.format(expToNextLevel) + ChatColor.WHITE + "/" + ChatColor.RED + doubleDecimal.format(requiredExpToNextLevel) + ChatColor.DARK_GRAY + " (" + doubleDecimal.format(progress) + "%)");
            lore.add(" ");
        }
        lore.add(ChatColor.GOLD + "Level Bonus: ");
        lore.addAll(abilityLore);
        lore.add(" ");
        ChatColor skillExpColor;
        if (skillLevel < 50)
        {
            skillExpColor = ChatColor.YELLOW;
        } else
        {
            skillExpColor = ChatColor.GOLD;
        }
        lore.add(ChatColor.GRAY + "Total " + menuItem.getDisplayName() + ChatColor.GRAY + " Experience: " + skillExpColor + doubleDecimal.format(totalExp));
        lore.add(" ");
        lore.add(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Click to view");

        return lore;
    }

    public static final VentureMenuItem COMBAT_SKILL_ITEM = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.SKILL_COMBAT;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);
            int combatLvl = statPlayer.getSkillsLevel().getCombatLevel();
            double totalExp = statPlayer.getSkillsEXP().getTotalCombatEXP();
            double expToNextLevel = statPlayer.getSkillsEXP().getCombatExpToNextLevel();
            double requiredExpToNextLevel = statPlayer.getSkillsEXP().getRequiredCombatExpToNextLevel();
            double damageBonus = statPlayer.getSkillsReward().getCombatDamageBonus();

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + " Deal " + ChatColor.YELLOW + damageBonus + ChatColor.GRAY + "% more damage");

            return skillLore(menuItem(), lore, combatLvl, totalExp, expToNextLevel, requiredExpToNextLevel);
        }
    };

    public static final VentureMenuItem MINING_SKILL_ITEM = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.SKILL_MINING;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);
            int miningLevel = statPlayer.getSkillsLevel().getMiningLevel();
            double totalExp = statPlayer.getSkillsEXP().getTotalMiningEXP();
            double expToNextLevel  = statPlayer.getSkillsEXP().getMiningExpToNextLevel();
            double requiredExpToNextLevel = statPlayer.getSkillsEXP().getRequiredMiningExpToNextLevel();
            double fortuneBonus = statPlayer.getSkillsReward().getMiningFortuneBonus();
            double defenseBonus = statPlayer.getSkillsReward().getMiningDefenseBonus();

            List<String> lore = new ArrayList<>();

            lore.add(ChatColor.GRAY + " Grants +" + ChatColor.YELLOW + fortuneBonus + ChatColor.GRAY + " more mining fortune");
            lore.add(ChatColor.GRAY + " Grants +" + ChatColor.GREEN + defenseBonus + StatSymbols.DEFENSE.getSymbol() + ChatColor.GRAY + " more defense");

            return skillLore(menuItem(), lore, miningLevel, totalExp, expToNextLevel, requiredExpToNextLevel);
        }
    };

    public static final VentureMenuItem FORAGING_SKILL_ITEM = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.SKILL_FORAGING;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);
            int foragingLevel = statPlayer.getSkillsLevel().getForagingLevel();
            double totalExp = statPlayer.getSkillsEXP().getTotalForagingEXP();
            double expToNextLevel = statPlayer.getSkillsEXP().getForagingExpToNextLevel();
            double requiredExpToNextLevel = statPlayer.getSkillsEXP().getRequiredForagingExpToNextLevel();
            double fortuneBonus = statPlayer.getSkillsReward().getForagingFortuneBonus();

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + " Grants +" + ChatColor.YELLOW + fortuneBonus + ChatColor.GRAY + " more foraging fortune");

            return skillLore(menuItem(), lore, foragingLevel, totalExp, expToNextLevel, requiredExpToNextLevel);
        }
    };

    public static final VentureMenuItem FARMING_SKILL_ITEM = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.SKILL_FARMING;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);
            int farmingLvl = statPlayer.getSkillsLevel().getFarmingLevel();
            double totalExp = statPlayer.getSkillsEXP().getTotalFarmingEXP();
            double expToNextLevel = statPlayer.getSkillsEXP().getFarmingExpToNextLevel();
            double requiredExpToNextLevel = statPlayer.getSkillsEXP().getRequiredFarmingExpToNextLevel();
            double fortuneBonus = statPlayer.getSkillsReward().getFarmingFortuneBonus();

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + " Grants +" + ChatColor.YELLOW + fortuneBonus + ChatColor.GRAY + " more farming fortune");

            return skillLore(menuItem(), lore, farmingLvl, totalExp, expToNextLevel, requiredExpToNextLevel);
        }
    };

    public static final VentureMenuItem ENCHANTING_SKILL_ITEM = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.SKILL_ENCHANTING;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);
            int enchantingLvl = statPlayer.getSkillsLevel().getEnchantingLevel();
            double totalExp = statPlayer.getSkillsEXP().getTotalEnchantingEXP();
            double expToNextLevel = statPlayer.getSkillsEXP().getEnchantingExpToNextLevel();
            double requiredExpToNextLevel = statPlayer.getSkillsEXP().getRequiredEnchantingExpToNextLevel();
            double manaBonus = statPlayer.getSkillsReward().getEnchantingManaBonus();

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + " Grants +" + ChatColor.BLUE + manaBonus + StatSymbols.MANA.getSymbol() + ChatColor.GRAY + " more mana");

            return skillLore(menuItem(), lore, enchantingLvl, totalExp, expToNextLevel, requiredExpToNextLevel);
        }
    };

    public static final VentureMenuItem FISHING_SKILL_ITEM = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.SKILL_FISHING;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);
            int fishingLvl = statPlayer.getSkillsLevel().getFishingLevel();
            double totalExp = statPlayer.getSkillsEXP().getTotalFishingExp();
            double expToNextLevel = statPlayer.getSkillsEXP().getFishingExpToNextLevel();
            double requiredExpToNextLevel = statPlayer  .getSkillsEXP().getRequiredFishingExpToNextLevel();
            double fishingLuckBonus = statPlayer.getSkillsReward().getFishingLuckBonus();

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + " Grants +" + ChatColor.YELLOW + fishingLuckBonus + ChatColor.GRAY + " more fishing luck");

            return skillLore(menuItem(), lore, fishingLvl, totalExp, expToNextLevel, requiredExpToNextLevel);
        }
    };

    public static final VentureMenuItem BREWING_SKILL_ITEM = new VentureMenuItem(PLUGIN) {
        @Override
        public MenuItem menuItem() {
            return MenuItem.SKILL_BREWING;
        }

        @Override
        public List<String> createLore(Player player) {

            StatPlayer statPlayer = new StatPlayer(player);
            int brewingLvl = statPlayer.getSkillsLevel().getBrewingLevel();
            double totalExp = statPlayer.getSkillsEXP().getTotalBrewingExp();
            double expToNextLevel = statPlayer.getSkillsEXP().getBrewingExpToNextLevel();
            double requiredExpToNextLevel = statPlayer.getSkillsEXP().getRequiredBrewingExpToNextLevel();
            double brewingLongevity = statPlayer.getSkillsReward().getBrewingLongevity();
            double brewingCaffeination = statPlayer.getSkillsReward().getBrewingCaffeination();

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + " Grants +" + ChatColor.YELLOW + brewingLongevity + StatSymbols.LONGEVITY.getSymbol() + ChatColor.GRAY + " more longevity");
            lore.add(ChatColor.GRAY + " Grants +" + ChatColor.YELLOW + brewingCaffeination + StatSymbols.CAFFEINATION.getSymbol() + ChatColor.GRAY + " more caffeination");

            return skillLore(menuItem(), lore, brewingLvl, totalExp, expToNextLevel, requiredExpToNextLevel);
        }
    };

    public enum SkillItemSlots
    {
        COMBAT (COMBAT_SKILL_ITEM, 19),
        MINING (MINING_SKILL_ITEM, 20),
        FORAGING (FORAGING_SKILL_ITEM, 21),
        FARMING (FARMING_SKILL_ITEM, 22),
        ENCHANTING (ENCHANTING_SKILL_ITEM, 23),
        FISHING (FISHING_SKILL_ITEM, 24),
        BREWING (BREWING_SKILL_ITEM, 25);

        private final VentureMenuItem ventureMenuItem;
        private final int inventorySlot;

        SkillItemSlots(VentureMenuItem ventureMenuItem, int inventorySlot)
        {
            this.ventureMenuItem = ventureMenuItem;
            this.inventorySlot = inventorySlot;
        }

        public VentureMenuItem getVentureMenuItem() {
            return ventureMenuItem;
        }

        public int getInventorySlot() {
            return inventorySlot;
        }
    }
}
