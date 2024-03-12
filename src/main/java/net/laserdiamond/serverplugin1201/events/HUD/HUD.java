package net.laserdiamond.serverplugin1201.events.HUD;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.events.effects.Components.Timers.ManaFreezeTimer;
import net.laserdiamond.serverplugin1201.events.effects.Components.Timers.NecrosisTimer;
import net.laserdiamond.serverplugin1201.events.effects.Managers.EffectManager;
import net.laserdiamond.serverplugin1201.stats.Components.DefenseStats;
import net.laserdiamond.serverplugin1201.stats.Components.Stats;
import net.laserdiamond.serverplugin1201.stats.Manager.StatProfileManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;

public class HUD extends BukkitRunnable implements Listener {

    private final ServerPlugin1201 plugin;
    private final StatProfileManager statProfileManager;
    private final EffectManager effectManager;

    public HUD(ServerPlugin1201 plugin) {
        this.plugin = plugin;
        statProfileManager = plugin.getStatProfileManager();
        effectManager = plugin.getEffectManager();
    }

    @Override
    public void run() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            Stats stats = statProfileManager.getStatProfile(player.getUniqueId()).stats();
            DefenseStats defenseStats = statProfileManager.getStatProfile(player.getUniqueId()).defenseStats();

            double availableMana = stats.getAvailableMana();
            double maxMana = stats.getMaxMana();

            double currentHealth = player.getHealth();
            double maxHealth = player.getMaxHealth();

            double armor = defenseStats.getDefense();
            double toughness = defenseStats.getToughness();

            DecimalFormat singleDecimalPlace = new DecimalFormat("0.0");
            DecimalFormat doubleDecimalPlace = new DecimalFormat("0.00");

            if (maxHealth >= 20) {
                //HealthScale = 40;
                player.setHealthScale(20);
                player.setHealthScaled(true);
            }
            if (currentHealth > maxHealth) {
                player.setHealth(maxHealth);
            }

            if (availableMana > maxMana) {
                stats.setAvailableMana(maxMana);
            }

            //EffectDurations effectDurations = effectManager.getEffectProfile(player.getUniqueId()).getEffectDurations();

            //int manaFreezeDuration = effectDurations.getManaFreezeDuration();
            //int necrosisDuration = effectDurations.getNecrosisDuration();

            String manaDisplayString = "Mana: " + singleDecimalPlace.format(availableMana) + "/" + singleDecimalPlace.format(maxMana) + ChatColor.BLUE + "\uD83D\uDD89";
            String finalManaDisplay;

            String healthDisplay = singleDecimalPlace.format(currentHealth) + "/" + singleDecimalPlace.format(maxHealth) + ChatColor.RED + "❤";
            String finalHealthDisplay;

            String defenseDisplay = "Armor: " + doubleDecimalPlace.format(armor) + ChatColor.GREEN + "⛉";
            String finalDefenseDisplay = ChatColor.GREEN + defenseDisplay;

            if (!ManaFreezeTimer.hasNoEffect(player)) {
                finalManaDisplay = ChatColor.AQUA + manaDisplayString;
            } else {
                finalManaDisplay = ChatColor.BLUE + manaDisplayString;
            }

            if (!NecrosisTimer.hasNoEffect(player)) {
                finalHealthDisplay = ChatColor.YELLOW + healthDisplay;
            } else if (player.hasPotionEffect(PotionEffectType.POISON)) {
                finalHealthDisplay = ChatColor.DARK_GREEN + healthDisplay;
            } else if (player.hasPotionEffect(PotionEffectType.WITHER)) {
                finalHealthDisplay = ChatColor.DARK_GRAY + healthDisplay;
            } else if (player.hasPotionEffect(PotionEffectType.ABSORPTION)) {
                finalHealthDisplay = ChatColor.GOLD + healthDisplay;
            } else if (player.getFreezeTicks() > 0) {
                finalHealthDisplay = ChatColor.AQUA + healthDisplay;
            } else {
                finalHealthDisplay = ChatColor.RED + healthDisplay;
            }

            displayHUD(player, finalHealthDisplay, finalDefenseDisplay, finalManaDisplay);

        }
    }

    private void displayHUD(Player player, String healthHUD, String manaHUD) {

        String HUD = "    " + healthHUD + ChatColor.BLACK + "         " + manaHUD;
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(HUD));

    }

    private void displayHUD(Player player, String healthHUD, String defenseHUD, String manaHUD) {

        String HUD = "      " + healthHUD + "   " + defenseHUD + "   " + manaHUD;
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(HUD));
    }
}
